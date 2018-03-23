package io.blockchain.pushkin.model;

import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class UserDict {
    @EmbeddedId
    private UserDictPK userDictPK;

    @Embedded
    private Word word;

    public UserDict() {
    }

    public UserDict(MessagePK messagePK, Integer index, Word word) {
        this.userDictPK = new UserDictPK(messagePK, index);
        this.word = word;
    }

    public UserDict(UserDictPK userDictPK, Word word) {
        this.userDictPK = userDictPK;
        this.word = word;
    }

    public UserDictPK getUserDictPK() {
        return userDictPK;
    }

    public void setUserDictPK(UserDictPK userDictPK) {
        this.userDictPK = userDictPK;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }
}
