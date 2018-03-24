package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.model.MessageEntity;
import io.blockchain.pushkin.model.MessagePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageEntityRepository extends CrudRepository<MessageEntity, MessagePK> {

    @Query("SELECT round(avg(me.errorFrequency),2) " +
            "FROM MessageEntity me " +
            "WHERE me.userId = :userID")
    Optional<Double> averageErrorFrequency(@Param("userID") Integer userId);
}
