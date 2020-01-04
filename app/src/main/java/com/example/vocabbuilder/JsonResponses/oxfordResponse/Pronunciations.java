package com.example.vocabbuilder.JsonResponses.oxfordResponse;

public class Pronunciations {
    private String audioFile;
    private String dialects;
    private String phoneticNotation;
    private String phoneticSpelling;

    public String getAudioFile() {
        return audioFile;
    }

    public void setAudioFile(String audioFile) {
        this.audioFile = audioFile;
    }

    public String getDialects() {
        return dialects;
    }

    public void setDialects(String dialects) {
        this.dialects = dialects;
    }

    public String getPhoneticNotation() {
        return phoneticNotation;
    }

    public void setPhoneticNotation(String phoneticNotation) {
        this.phoneticNotation = phoneticNotation;
    }

    public String getPhoneticSpelling() {
        return phoneticSpelling;
    }

    public void setPhoneticSpelling(String phoneticSpelling) {
        this.phoneticSpelling = phoneticSpelling;
    }
}
