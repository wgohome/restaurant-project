package app.boundaries;

import java.util.Scanner;
import java.util.TreeMap;

import app.RestaurantApp;
import app.controllers.Controller;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public abstract class Boundary {
  protected static Scanner sc = RestaurantApp.sc;

  private Controller controller;
  protected String entityName; /* To be set in Subclass Constructor */

  public Boundary(Controller controllerIn) {
    controller = controllerIn;
  }

  public String getEntityName() { return entityName; }
  public Controller getController() { return controller; }
  /* No setters as they are not meant to be changed
    in the lifetime of the object */


  /* Wrappers to Controller subclasses' methods */
  public void saveAll() { getController().saveAll(); }
  public void printAll() {
    getController().printAll();
  }
  public EntityStorable getEntity(int index)
  { return getController().getEntity(index); }
  public TreeMap<Integer, String> getChoiceMap()
  { return getController().getChoiceMap(); }

  public void indexAll() {
    int choice = -1;

    TreeMap<Integer, String> options = getController().getChoiceMap();
    options.put(0, "Exit this submenu");
    ChoicePicker picker = new ChoicePicker(
      "Which " + entityName + " do you want to see all its attributes for? (Select 0 if you want to go back to exit this submenu)",
      options);
    choice = picker.run(); /* Run with all options listed the first time */
    while (choice != 0) {
      if (choice != 0) printEntityAttrs(choice - 1);
      choice = picker.run(false); /* Run without options listed */
    }
    System.out.println("Leaving index for " + entityName + " ...");
  }

  /* Implementation for the sub menu, i.e. the CRUD++ actions */
  public abstract void mainOptions();


  /* To be called in mainOptions(): CREATE */
  public EntityStorable create() {
    System.out.println("Creating new " + entityName + " ...");
    /* Ask for the new entity's attributes,
      create the entity, return the entity */
    EntityStorable entity = entityCreator();
    if (entity != null) {
      /* EntityController class to store the entity in their List */
      getController().add(entity);
      return entity;
    } else {
      System.out.println("Failed to create " + entityName + "!");
      return null;
    }
  }


  /* To be called in mainOptions(): DELETE */
  public void delete() {
    /* Ask what entitiy to remove, choose from the EntityController's List */
    int choice = entityIntChoicePicker("Which " + entityName + " do you want to delete? ");
    /* EntityController class to remove the entity in their List */
    getController().remove(choice - 1);
      /* Note: choice starts from 1, index start from 0 */
  }


  /* To be called in mainOptions(): EDIT */
  public void edit() {
    /* Ask what entitiy to edit, from the EntityController's List */
    int choice = entityIntChoicePicker("Which " + entityName + " do you want to edit? ");
    /* Show what the current fields are */
    printEntityAttrs(getController().getList().get(choice - 1));
    /* Create a new Entity instance */
    System.out.println("Edit: Fill up the fields for updated " + entityName + " ...");
    EntityStorable entity = entityCreator();
    /* Replace the old entity with new entity at the appropriate index */
    getController().update(choice - 1, entity);
      /* side effects of afterRemove() will be called in Controller class */
  }


  /* Show attributes of ONE entity */
  protected void printEntityAttrs(EntityStorable entity) {
    System.out.println("The current attributes for this " + entityName + " are: ");
    System.out.println(entity.getAttrsString());
  }
  protected void printEntityAttrs(int index) {
    EntityStorable entity = getController().getEntity(index);
    this.printEntityAttrs(entity);
  }


  /* Asks the user for all the input arguments,
    create a new entity */
  protected abstract EntityStorable entityCreator();


  /* Prints the list of all instances of this Entity,
    ask the user to select one, returns the choice number */
  protected int entityIntChoicePicker(String prompt) {
    ChoicePicker entityPicker = new ChoicePicker(prompt, getController().getChoiceMap());
    return entityPicker.run();
    /* Note: choice starts from 1, index start from 0 */
  }
}
