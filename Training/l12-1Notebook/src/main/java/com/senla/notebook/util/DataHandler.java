package com.senla.notebook.util;

import android.app.Activity;
import android.util.Log;

import com.senla.notebook.bean.NoteItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class DataHandler {

    private final String DOCUMENTS_FOLDER = "documents";
    private final String FILE_SEPARATOR = "/";
    private File filePath;

    public DataHandler(Activity activity) {
        filePath = new File(activity.getFilesDir() + FILE_SEPARATOR + DOCUMENTS_FOLDER + FILE_SEPARATOR);
        if (!filePath.exists()) {
            filePath.mkdir();
        }
    }

    public boolean writeToFile(String data) {
        File file = new File(filePath + FILE_SEPARATOR + data.split("\n")[0].trim() + ".txt");

        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos);
             BufferedWriter bw = new BufferedWriter(osw)) {

            bw.write(data);
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNote (String title) {
        File file = new File (filePath + FILE_SEPARATOR + title + ".txt");
        StringBuilder sb = new StringBuilder();

        try (FileInputStream fis = new FileInputStream(file); InputStreamReader isr = new InputStreamReader(fis); BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public List<NoteItem> getAll() {
        List<NoteItem> docs = new ArrayList<>();
        File[] files = filePath.listFiles();
        for (File file: files) {
            NoteItem newItem = new NoteItem(file.getName().replace(".txt", ""), file.lastModified());
            docs.add(newItem);
        }
        return docs;
    }
}
