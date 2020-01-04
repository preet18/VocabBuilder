package com.example.vocabbuilder.JsonResponses.oxfordResponse;

public class LexicalEntries {
    private Entries entries;
    private LexicalCategory lexicalCategory;
    private Pronunciations pronunciations;

    public Entries getEntries() {
        return entries;
    }

    public void setEntries(Entries entries) {
        this.entries = entries;
    }

    public LexicalCategory getLexicalCategory() {
        return lexicalCategory;
    }

    public void setLexicalCategory(LexicalCategory lexicalCategory) {
        this.lexicalCategory = lexicalCategory;
    }

    public Pronunciations getPronunciations() {
        return pronunciations;
    }

    public void setPronunciations(Pronunciations pronunciations) {
        this.pronunciations = pronunciations;
    }
}
