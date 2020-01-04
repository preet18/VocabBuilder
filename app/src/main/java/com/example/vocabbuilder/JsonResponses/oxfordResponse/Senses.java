package com.example.vocabbuilder.JsonResponses.oxfordResponse;

public class Senses {
    private String definitions;
    private Examples examples;
    private String id;
    private String shortDefinitions;
    private Subsenses subsenses;
    private ThesaurusLinks thesaurusLinks;

    public String getDefinitions() {
        return definitions;
    }

    public void setDefinitions(String definitions) {
        this.definitions = definitions;
    }

    public Examples getExamples() {
        return examples;
    }

    public void setExamples(Examples examples) {
        this.examples = examples;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortDefinitions() {
        return shortDefinitions;
    }

    public void setShortDefinitions(String shortDefinitions) {
        this.shortDefinitions = shortDefinitions;
    }

    public Subsenses getSubsenses() {
        return subsenses;
    }

    public void setSubsenses(Subsenses subsenses) {
        this.subsenses = subsenses;
    }

    public ThesaurusLinks getThesaurusLinks() {
        return thesaurusLinks;
    }

    public void setThesaurusLinks(ThesaurusLinks thesaurusLinks) {
        this.thesaurusLinks = thesaurusLinks;
    }
}
