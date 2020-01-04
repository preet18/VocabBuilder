package com.example.vocabbuilder.workerThreads;

import org.json.JSONException;

import java.io.IOException;

public interface AsyncResponse {
    void processFinish(String result, String source) throws IOException, JSONException;
}

