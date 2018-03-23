package io.blockchain.pushkin.repo;

import io.blockchain.pushkin.model.UserDict;
import io.blockchain.pushkin.model.UserDictPK;
import org.springframework.data.repository.CrudRepository;

public interface UserDictRepository extends CrudRepository<UserDict, UserDictPK> {
}
