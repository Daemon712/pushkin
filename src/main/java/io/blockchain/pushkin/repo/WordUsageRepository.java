package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.model.Word;
import io.blockchain.pushkin.model.WordUsage;
import io.blockchain.pushkin.model.WordUsagePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WordUsageRepository extends CrudRepository<WordUsage, WordUsagePK> {
    Integer countByMessageUserId(Integer userId);

    @Query("SELECT distinct wu.word FROM WordUsage wu where wu.message.userId = :userID")
    List<Word> findDistinctWordByMessageUserId(@Param("userID") Integer userId);
}
