package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.dto.Document;
import io.blockchain.pushkin.model.SpeechPart;
import io.blockchain.pushkin.model.Word;
import io.blockchain.pushkin.model.WordUsage;
import io.blockchain.pushkin.model.WordUsagePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordUsageRepository extends CrudRepository<WordUsage, WordUsagePK> {
    Integer countByMessageUserId(Integer userId);

    @Query("SELECT distinct wu.word FROM WordUsage wu where wu.message.userId = :userID")
    List<Word> findDistinctWordByMessageUserId(@Param("userID") Integer userId);

    @Query("SELECT new io.blockchain.pushkin.dto.Document(wu.message.userId, wu.word) " +
            "FROM WordUsage wu where wu.message.messagePK.chatId = :chatId")
    List<Document> findDocumentsByChatId(@Param("chatId") Long chatId);

    @Query("SELECT avg(gd.rate) " +
            "FROM WordUsage wu " +
            "JOIN GlobalDict gd ON wu.word = gd.word " +
            "WHERE wu.message.userId = :userID" +
            " AND wu.word.speechPart in (:speechParts)")
    Optional<Double> averageWordsRatingByMessageUserId(@Param("userID") Integer userId, @Param("speechParts")List<SpeechPart> speechParts);

    @Query("SELECT new io.blockchain.pushkin.dto.UserRating(wu.message.userId, avg(gd.rate)) " +
            "FROM WordUsage wu " +
            "JOIN GlobalDict gd ON wu.word = gd.word " +
            "WHERE wu.message.messagePK.chatId = :chatId " +
            "AND wu.word.speechPart in (:speechParts) " +
            "GROUP BY wu.message.userId " +
            "ORDER BY avg(gd.rate)")
    List<UserRating> calcUserRatingByChat(@Param("chatId") Long chatId, @Param("speechParts")List<SpeechPart> speechParts);
}
