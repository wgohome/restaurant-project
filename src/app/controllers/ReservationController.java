package app.controllers;

import java.util.TreeMap;

import app.db.ReservationData;
import app.entities.*;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class ReservationController extends Controller {
  private CustomerController cusControl;
  private Staff curStaff;

  public ReservationController(CustomerController cc, Staff curS) {
    super(new ReservationData());
    entity_name = "Reservation";
    cusControl = cc;
    curStaff = curS;
  }

  @Override
  protected EntityStorable entityCreator() {
    Staff staff;
    Customer customer;
    Reservation newResv;
    int choice;
    TreeMap<Integer, String> options;

    /* Ask if staff exists */
    options = new TreeMap<Integer, String>();
    options.put(1, "Yes, it is for an existing customer");
    options.put(2, "No, it is a new customer");
    ChoicePicker picker = new ChoicePicker("Is reservation for an existing customer?", options);
    choice = picker.run();
    if (choice == 1) {
      /* Pick from existing customers */
      cusControl.printAll();
      options = cusControl.getChoiceMap();
      ChoicePicker cusPicker = new ChoicePicker(
        "Which customer is this reservation for?",
        options
      );
      choice = cusPicker.run();
      customer = (Customer) cusControl.getEntity(choice - 1);
    } else {
      /* Create a new customer */
      customer = (Customer) cusControl.create();
    }
    /* Create the reservation */
    newResv = new Reservation(curStaff, customer);
    return newResv;
  }

  @Override
  protected void printCurrentEntity(EntityStorable entity) {
    Reservation item = (Reservation) entity;
    System.out.println("The current attributes for this " + entity_name + " are: ");
    System.out.println("Staff: " + item.getStaff().getName());
    System.out.println("Customer: " + item.getCustomer().getName());
  }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Reservations");
    options.put(2, "Add new Reservation");
    options.put(3, "Remove a Reservation");
    options.put(4, "Edit a Reservation");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Reservations menu, what would you like to do? ", options);
    while (choice != 9) {
      choice = mainPicker.run();
      switch (choice) {
      case 1:
        data.printAll();
        break;
      case 2:
        create();
        break;
      case 3:
        delete();
        break;
      case 4:
        edit();
        break;
      case 9:
        System.out.println("Going back to the main menu ... ");
        break;
      default:
        break;
      }
    }
  }
}
