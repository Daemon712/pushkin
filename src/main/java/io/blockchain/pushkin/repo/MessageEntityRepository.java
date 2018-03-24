package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.model.MessageEntity;
import io.blockchain.pushkin.model.MessagePK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageEntityRepository extends CrudRepository<MessageEntity, MessagePK> {

    @Query("SELECT avg(me.literacy) " +
            "FROM MessageEntity me " +
            "WHERE me.user.userId = :userID " +
            "AND me.literacy IS NOT NULL")
    Optional<Double> averageLiteracy(@Param("userID") Integer userId);

    @Query("SELECT new io.blockchain.pushkin.dto.UserRating(me.user, avg(me.literacy)) " +
            "FROM MessageEntity me " +
            "WHERE me.messagePK.chatId = :chatId " +
            "AND me.literacy IS NOT NULL " +
            "GROUP BY me.user.userId " +
            "ORDER BY avg(me.literacy)")
    List<UserRating> calcUserLiteracyByChat(@Param("chatId") Long chatId);
}
