package com.senla.l15_1toolbarexternalizable.dao;

import android.app.Activity;
import android.util.Log;

import com.senla.l15_1toolbarexternalizable.bean.Element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DataHandler {

    private final String FILE_SEPARATOR = "/";
    private File file;

    public DataHandler(Activity activity) {
        file = new File(activity.getFilesDir()+ FILE_SEPARATOR + "element");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean writeToFile(List<Element> elements) {
        Iterator iterator = elements.iterator();

        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            while (iterator.hasNext()) {
                oos.writeObject(iterator.next());
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Element> readFromFile() {
        ArrayList<Element> elements = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
            Object o;
            while ((o = ois.readObject()) != null) {
                elements.add((Element) o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return elements;
    }
}
