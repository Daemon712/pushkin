package io.blockchain.pushkin.model;

import javax.persistence.*;

@Entity
public class WordSynonym {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String word;

    @ElementCollection
    @CollectionTable(name = "synonyms", joinColumns = @JoinColumn(name = "word_synonym_id"))
    @Column(name = "synonym")
    @OrderColumn(name = "list_index")
    private String[] synonyms;

    public WordSynonym() {
    }

    public WordSynonym(String word, String[] synonyms) {
        this.word = word;
        this.synonyms = synonyms;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String[] getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String[] synonyms) {
        this.synonyms = synonyms;
    }
}
