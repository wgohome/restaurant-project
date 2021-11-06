package app.db;

import java.io.*;
import java.util.List;

import app.interfaces.EntityStorable;

/* Note: relative file path begins at the main folder of restaurant-project, not the scope of this file itself */

public class Serializer {
  public static List<EntityStorable> readSerializedObject(String filename) {
    List<EntityStorable> list;
    try {
      FileInputStream fis = new FileInputStream(filename);
      ObjectInputStream ois = new ObjectInputStream(fis);
      list = (List<EntityStorable>) ois.readObject();
      ois.close();
      return list;
    } catch (IOException e) {
      System.out.println("Serializer IOException: " + e.getMessage());
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Serializer ClassNotFoundException: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  public static void writeSerializedObject(String filename, List<EntityStorable> list) {
    try {
      FileOutputStream fos = new FileOutputStream(filename);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(list);
      oos.close();
      // System.out.println("Object persisted");
    } catch (IOException e) {
      System.out.println("IO Error: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
