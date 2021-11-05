package app.utils;

import java.io.*;
import java.util.List;
import java.util.ArrayList;

/* Note: relative file path begins at the main folder of restaurant-project, not the scope of this file itself */

public class Serializer {
  public static List readSerializedObject(String filename) {
    List list;
    try {
      FileInputStream fis = new FileInputStream(filename);
      ObjectInputStream ois = new ObjectInputStream(fis);
      list = (ArrayList) ois.readObject();
      ois.close();
      return list;
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("ClassNotFoundException: " + e.getMessage());
      e.printStackTrace();
    }
    return null;
  }

  public static void writeSerializedObject(String filename, List list) {
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
