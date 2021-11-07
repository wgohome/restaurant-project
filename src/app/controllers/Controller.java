package app.controllers;

import java.util.Scanner;
import java.util.TreeMap;

import app.RestaurantApp;
import app.db.Data;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public abstract class Controller {
  protected static Scanner sc = RestaurantApp.sc;
  protected Data data;
  protected String entity_name;

  public Controller(Data dataIn) {
    data = dataIn;
  }

  public void saveAll() { data.saveAll(); }
  public void printAll() { data.printAll(); }
  public EntityStorable getEntity(int index) { return data.getList().get(index); }
  public TreeMap<Integer, String> getChoiceMap() { return data.getChoiceMap(); }

  public EntityStorable create() {
    // Ask what entity to add, enter the attributes too, return the entity to be stored
    System.out.println("Creating new " + entity_name + " ...");
    EntityStorable entity = entityCreator();
    data.add(entity);
    return entity;
  }

  public void delete() {
    // Ask what entitiy to remove, from the EntityData's List
    int choice = EntityIntChoicePicker("Which " + entity_name + " do you want to delete? ");
    data.remove(choice - 1);
    /* Note: choice starts from 1, index start from 0 */
  }

  public void edit() {
    // Ask which entity to edit
    int choice = EntityIntChoicePicker("Which " + entity_name + " do you want to edit? ");
    // Show what the current fields are
    printCurrentEntity(data.getList().get(choice - 1));
    // Ask what to edit each field of the entity to, and create a new Entity instance to return
    System.out.println("Edit: Fill up the fields for " + entity_name + " ...");
    EntityStorable entity = entityCreator();
    // update in the dataclass
    data.update(choice - 1, entity);
  }

  protected abstract EntityStorable entityCreator();
  protected abstract void printCurrentEntity(EntityStorable entity);

  protected int EntityIntChoicePicker(String prompt) {
    ChoicePicker delPicker = new ChoicePicker(prompt, data.getChoiceMap());
    return delPicker.run(); /* Returns choice number */
    /* Note: choice starts from 1, index start from 0 */
  }

  public abstract void mainOptions();
}
