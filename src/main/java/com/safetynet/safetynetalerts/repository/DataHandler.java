package com.safetynet.safetynetalerts.repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.safetynet.safetynetalerts.model.Data;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class DataHandler {
    private final Data data;

    public DataHandler(ResourceLoader loader) throws IOException {
        File file = loader.getResource("classpath:data.json").getFile();
        String data = FileUtils.readFileToString(file, "UTF-8");
        this.data = JsonIterator.deserialize(data, Data.class);
    }

    public Data getData() {
        return data;
    }

    public void save() {
        String json = JsonStream.serialize(data);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("classpath:data.json"));
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}