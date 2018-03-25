package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.dto.UserRating;
import io.blockchain.pushkin.model.MessageEntity;
import io.blockchain.pushkin.model.MessagePK;
import io.blockchain.pushkin.model.TgUser;
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

    List<MessageEntity> findTop200ByUserUserIdOrderByDateDesc(@Param("userID") Integer userId);

    @Query("SELECT DISTINCT me.user " +
            "FROM MessageEntity me " +
            "WHERE me.messagePK.chatId = :chatId")
    List<TgUser> findActiveChatUsers(@Param("chatId") Long chatId);

    @Query(nativeQuery = true,
            value = "SELECT me.message_id " +
                    "FROM message_entity me " +
                    "WHERE me.chat_id = :chatId " +
                    "AND me.user_user_id = :userId " +
                    "ORDER BY me.date DESC " +
                    "LIMIT 100")
    List<Integer> find100LastMessages(@Param("chatId") Long chatId, @Param("userId") Integer userId);
}
