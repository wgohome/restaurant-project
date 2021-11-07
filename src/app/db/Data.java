package app.db;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

import app.interfaces.*;

public abstract class Data {
  private List<EntityStorable> list;
  private String filepath;

  public Data(String filepathIn) {
    filepath = filepathIn;
    list = loadAll(filepath);
  }

  public List<EntityStorable> getList() { return list; }
  public String getFilepath() { return filepath; }
  public int size() { return list.size(); }

  private List<EntityStorable> loadAll(String filepath) {
    /* Private because only for use in this (superclass) constructor */
    List<EntityStorable> list;
    File file = new File(filepath);
    if (file.exists()) {
      list = Serializer.readSerializedObject(filepath);
    } else {
      list = seedList();
    }
    return list;
  }
  protected List<EntityStorable> loadAll() {
    /* Protected in case subclasses want to use but not needed yet as of now */
    return this.loadAll(filepath);
  }

  /* To seed specifically based on the kind of entity subclasses */
  protected abstract List<EntityStorable> seedList();

  public void printAll() {
    if (list == null || list.isEmpty()) return;
    System.out.println("List of all " + list.get(0).getClass().getSimpleName());
    for (int i = 0; i < list.size(); i++) {
      System.out.println((i + 1) + ". " + list.get(i).getName() + ": " + list.get(i).getDesc());
    }
  }

  public void saveAll() {
    Serializer.writeSerializedObject(filepath, list);
  }

  private String getEntityIdentifier(EntityStorable entity) {
    return entity.getClass().getSimpleName() + ": " + entity.getName();
  }

  public TreeMap<Integer, String> getChoiceMap() {
    /* To be passed as parameter to ChoicePicker */
    EntityStorable e;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    for (int i = 0; i < list.size(); i++) {
      e = list.get(i);
      options.put(i + 1, e.getName() + ": " + e.getDesc());
    }
    return options;
  }

  public void add(EntityStorable entity) {
    if (!list.contains(entity)) {
      /* List add(E) method returns boolean */
      if (list.add(entity)) System.out.println("Added new " + getEntityIdentifier(entity));
    } else {
      System.out.println(getEntityIdentifier(entity) + " already present");
    }
  }

  public void remove(EntityStorable entity) {
    if (list.remove(entity)) { /* Returns boolean */
      System.out.println("Removed " + getEntityIdentifier(entity));
    } else {
      System.out.println("Failed to remove " + getEntityIdentifier(entity));
    }
  }
  public EntityStorable remove(int index) {
    EntityStorable e = list.remove(index); /* Returns object */
    if (e != null) {
      System.out.println("Removed " + getEntityIdentifier(e));
    } else {
      System.out.println("Failed to remove " + getEntityIdentifier(e));
    }
    return e;
  }

  public void update(int index, EntityStorable updatedEntity) {
    try {
      /* EntityStorable entity =  */list.set(index, updatedEntity);
    } catch (Exception e) {
      System.out.println("Failed to update entity. ");
    }
  }
}
