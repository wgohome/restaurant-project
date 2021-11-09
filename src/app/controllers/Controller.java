package app.controllers;

import java.util.Scanner;
import java.util.TreeMap;

import app.RestaurantApp;
import app.db.Data;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public abstract class Controller {
  protected static Scanner sc = RestaurantApp.sc;

  private Data data;
  protected String entityName; /* To be set in Subclass Constructor */

  public Controller(Data dataIn) {
    data = dataIn;
  }

  public String getEntityName() { return entityName; }
  public Data getData() { return data; }
  /* No setters as they are not meant to be changed
    in the lifetime of the object */


  /* Wrappers to Data subclasses' methods */
  public void saveAll() { getData().saveAll(); }
  public void printAll() { getData().printAll(); }
  public EntityStorable getEntity(int index)
    { return getData().getList().get(index); }
  public TreeMap<Integer, String> getChoiceMap()
    { return getData().getChoiceMap(); }


  /* Implementation for the sub menu, i.e. the CRUD++ actions */
  public abstract void mainOptions();


  /* To be called in mainOptions(): CREATE */
  public EntityStorable create() {
    System.out.println("Creating new " + entityName + " ...");
    /* Ask for the new entity's attributes,
      create the entity, return the entity */
    EntityStorable entity = entityCreator();
    if (entity != null) {
      /* EntityData class to store the entity in their List */
      getData().add(entity);
      return entity;
    } else {
      System.out.println("Failed to create " + entityName + "!");
      return null;
    }
  }


  /* To be called in mainOptions(): DELETE */
  public void delete() {
    /* Ask what entitiy to remove, choose from the EntityData's List */
    int choice = entityIntChoicePicker("Which " + entityName + " do you want to delete? ");
    /* EntityData class to remove the entity in their List */
    getData().remove(choice - 1);
      /* Note: choice starts from 1, index start from 0 */
  }


  /* To be called in mainOptions(): EDIT */
  public void edit() {
    /* Ask what entitiy to edit, from the EntityData's List */
    int choice = entityIntChoicePicker("Which " + entityName + " do you want to edit? ");
    /* Show what the current fields are */
    printEntityAttrs(getData().getList().get(choice - 1));
    /* Create a new Entity instance */
    System.out.println("Edit: Fill up the fields for updated " + entityName + " ...");
    EntityStorable entity = entityCreator();
    /* Replace the old entity with new entity at the appropriate index */
    getData().update(choice - 1, entity);
      /* side effects of afterRemove() will be called in Data class */
  }


  /* Show attributes of ONE entity */
  protected void printEntityAttrs(EntityStorable entity) {
    System.out.println("The current attributes for this " + entityName + " are: ");
    System.out.println(entity.getAttrsString());
  }


  /* Asks the user for all the input arguments,
    create a new entity */
  protected abstract EntityStorable entityCreator();


  /* Prints the list of all instances of this Entity,
    ask the user to select one, returns the choice number */
  protected int entityIntChoicePicker(String prompt) {
    ChoicePicker delPicker = new ChoicePicker(prompt, getData().getChoiceMap());
    return delPicker.run();
    /* Note: choice starts from 1, index start from 0 */
  }
}
