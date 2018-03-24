package io.blockchain.pushkin.repo;

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

    @Query("SELECT avg(gd.rate) " +
            "FROM WordUsage wu " +
            "JOIN GlobalDict gd ON wu.word = gd.word " +
            "WHERE wu.message.userId = :userID" +
            " AND wu.word.speechPart in (:speechParts)")
    Optional<Double> averageWordsRatingByMessageUserId(@Param("userID") Integer userId, @Param("speechParts")List<SpeechPart> speechParts);
}
