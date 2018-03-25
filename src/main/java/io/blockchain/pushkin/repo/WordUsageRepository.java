package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.dto.Document;
import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.model.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
public interface WordUsageRepository extends CrudRepository<WordUsage, WordUsagePK> {
    Integer countByMessageUserUserId(Integer userId);

    @Query("SELECT distinct wu.word FROM WordUsage wu where wu.message.user.userId = :userID")
    List<Word> findDistinctWordByMessageUserId(@Param("userID") Integer userId);

    @Query("SELECT new io.blockchain.pushkin.dto.Document(wu.message.user.userId, wu.word) " +
            "FROM WordUsage wu where wu.message.messagePK.chatId = :chatId")
    List<Document> findDocumentsByChatId(@Param("chatId") Long chatId);

    @Query(nativeQuery = true,
            value = "SELECT avg(gd.rate) " +
                    "FROM word_usage wu " +
                    "JOIN message_entity me " +
                    "ON me.chat_id = wu.chat_id AND me.message_id = me.message_id " +
                    "JOIN global_dict gd " +
                    "ON wu.word = gd.word AND wu.speech_part = gd.speech_part " +
                    "WHERE me.user_user_id = :userID " +
                    "ORDER BY avg(gd.rate) ASC " +
                    "LIMIT 25")
    Optional<Double> averageWordsRatingByMessageUserId(@Param("userID") Integer userId);


    @Query(nativeQuery = true,
            value = "SELECT min(gd.rate)" +
                    "FROM word_usage wu " +
                    "JOIN global_dict gd " +
                    "ON wu.word = gd.word AND wu.speech_part = gd.speech_part " +
                    "WHERE wu.chat_id = :chatId AND wu.message_id IN (:messages) " +
                    "GROUP BY gd.word, gd.speech_part " +
                    "ORDER BY min(gd.rate)")
    List<Double> findWordUsagesRating(@Param("chatId") Long chatId, @Param("messages") List<Integer> messages);

    @Query("SELECT new io.blockchain.pushkin.dto.UserRating(wu.message.user, avg(gd.rate)) " +
            "FROM WordUsage wu " +
            "JOIN GlobalDict gd ON wu.word = gd.word " +
            "WHERE wu.message.messagePK.chatId = :chatId " +
            "AND wu.word.speechPart in (:speechParts) " +
            "GROUP BY wu.message.user.userId " +
            "ORDER BY avg(gd.rate)")
    List<UserRating> calcUserRatingByChat(@Param("chatId") Long chatId, @Param("speechParts") List<SpeechPart> speechParts);

//    @Query("SELECT wu.word " +
//            "FROM WordUsage wu " +
//            "JOIN GlobalDict gd ON wu.word = gd.word " +
//            "WHERE wu.message.user.userId = :userID " +
//            "ORDER BY gd.rate desc")
//    List<Word> calcTopAnomalyWords(@Param("userID") Integer userId);

    List<WordUsage> findWordUsagesByMessage(MessageEntity message);
}
