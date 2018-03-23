package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.model.MessageEntity;
import io.blockchain.pushkin.model.MessagePK;
import org.springframework.data.repository.CrudRepository;

public interface MessageEntityRepository extends CrudRepository<MessageEntity, MessagePK> {
}
