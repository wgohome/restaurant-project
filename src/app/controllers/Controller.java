package app.controllers;

import java.util.Scanner;

import app.RestaurantApp;
import app.db.Data;
import app.interfaces.EntityStorable;

public abstract class Controller {
  protected static Scanner sc = RestaurantApp.sc;
  protected Data data;

  public Controller(Data dataIn) {
    data = dataIn;
  }

  public void saveAll() { data.saveAll(); }

  public void create() {
    // Ask what entity to add, enter the attributes too
    EntityStorable entity = createCaller();
    data.add(entity);
  }

  public void delete() {
    // Ask what entitiy to remove
    int choice = deleteChoicePicker();
    data.remove(choice - 1);
    /* Note: choice starts from 1, index start from 0 */
  }

  protected abstract EntityStorable createCaller();
  protected abstract int deleteChoicePicker();
  public abstract void mainOptions();
}
