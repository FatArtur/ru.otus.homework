package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String filename;

    public FileSerializer(String fileName) {
        this.filename = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        try (var writer = new OutputStreamWriter(new FileOutputStream(filename))) {
            writer.write(new Gson().toJson(data));
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }
}
