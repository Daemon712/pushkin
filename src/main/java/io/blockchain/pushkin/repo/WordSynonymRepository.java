package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.model.WordSynonym;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WordSynonymRepository extends CrudRepository<WordSynonym, Long> {
    Optional<WordSynonym> findByWord(String word);
}
