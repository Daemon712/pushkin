package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.model.GlobalDict;
import io.blockchain.pushkin.model.Word;
import org.springframework.data.repository.CrudRepository;

public interface GlobalDictRepository extends CrudRepository<GlobalDict, Word> {
}
