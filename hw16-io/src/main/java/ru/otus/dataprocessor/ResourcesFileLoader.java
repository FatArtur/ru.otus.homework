package ru.otus.dataprocessor;

import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.*;
import java.util.List;
import java.util.Objects;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String fileName) {
        this.filename = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        String filePath = Objects.requireNonNull(getClass().getClassLoader().getResource(filename)).getPath();
        try (var reader = new InputStreamReader(new FileInputStream(filePath))) {
            return List.of(new Gson().fromJson(reader, Measurement[].class));
        } catch (IOException e) {
            throw new FileProcessException("Some problem with read file" + e);
        }
    }
}
