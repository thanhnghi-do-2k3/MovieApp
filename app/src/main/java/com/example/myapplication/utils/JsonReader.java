package com.example.myapplication.utils;

import android.util.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {
    public static String readJsonFileAsString(String filePath) {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("JsonReader", "Error reading file: " + filePath + e.toString());
        }
        Log.d("JsonReader", "content: " + content.length());
        return content;
    }
}
