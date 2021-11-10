package app.controllers;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

import app.interfaces.*;

public abstract class Controller {
  private List<EntityStorable> list;
  private String filepath;

  public Controller(String filepathIn) {
    filepath = filepathIn;
    list = loadAll(filepath);
  }

  public List<EntityStorable> getList() { return list; }
  public String getFilepath() { return filepath; }
  public int size() { return list.size(); }

  private List<EntityStorable> loadAll(String filepath) {
    /* Private because only for use in this (superclass) constructor */
    /* Subclass should be calling super constructor from its own constructor to access this feature */
    List<EntityStorable> list;
    File file = new File(filepath);
    if (file.exists()) {
      list = Serializer.readSerializedObject(filepath);
    } else {
      list = seedList();
    }
    return list;
  }

  /* If no .dat file present, will call this method from specific EntityController class to seed some entities */
  protected abstract List<EntityStorable> seedList();

  public void printAll() {
    if (list == null || list.isEmpty()) return;
    System.out.println("List of all " + list.get(0).getClass().getSimpleName());
    for (int i = 0; i < list.size(); i++) {
      System.out.println((i + 1) + ". " + list.get(i).getName() + ": " + list.get(i).getDesc());
    }
  }

  /* To be called at the end of RestaurantApp main method (Upon choice to exit) to serialise and save into .dat file */
  public void saveAll() {
    Serializer.writeSerializedObject(filepath, list);
  }

  private String getEntityIdentifier(EntityStorable entity) {
    return entity.getClass().getSimpleName() + ": " + entity.getName();
  }

  /* To create a TreeMap of options to be passed into ChoicePicker boundary class instance, to ask use for their choice */
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

  /* To be called from create() in Boundary class */
  public void add(EntityStorable entity) {
    if (!list.contains(entity)) {
      /* List add(E) method returns boolean */
      if (list.add(entity)) System.out.println("Added new " + getEntityIdentifier(entity));
    } else {
      System.out.println(getEntityIdentifier(entity) + " already present");
    }
  }

  /* To be called from delete() in Boundary class */
  /* VERSION 1: Returns boolean */
  public void remove(EntityStorable entity) {
    if (list.remove(entity)) {
      System.out.println("Removed " + getEntityIdentifier(entity));
      afterRemove(entity);
    } else {
      System.out.println("Failed to remove " + getEntityIdentifier(entity));
    }
  }
  /* VERSION 2: Returns EntityStorable object */
  public EntityStorable remove(int index) {
    EntityStorable e = list.remove(index);
    if (e != null) {
      System.out.println("Removed " + getEntityIdentifier(e));
      afterRemove(e);
    } else {
      System.out.println("Failed to remove " + getEntityIdentifier(e));
    }
    return e;
  }

  /* To be called from edit() in Boundary class */
  public EntityStorable update(int index, EntityStorable updatedEntity) {
    EntityStorable oldEntity = list.set(index, updatedEntity);
    if (oldEntity != null) {
      System.out.println(
        "Replaced old " + oldEntity.getName() +
        " entity with new " + updatedEntity.getName()
      );
      afterRemove(oldEntity);
    } else {
      System.out.println("Failed to replace old entity with new " + updatedEntity.getName());
    }
    return oldEntity;
  }

  /* Not used in the app yet */
  /* To delete data/Entity.dat file for this Entity */
  public void deleteControllerFile() {
    File datFile = new File(filepath);
    if (datFile.delete()) {
      System.out.println("Deleted file at " + filepath);
    } else {
      System.out.println("Failed to delete " + filepath);
    }
  }

  protected void afterRemove(EntityStorable entity) {
    /* To be overridden in EntityController class if need to */
    return;
  }
}
