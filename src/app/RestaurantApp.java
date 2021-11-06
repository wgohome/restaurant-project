package app;

import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

import app.controllers.StaffController;
import app.entities.*;
import app.utilities.ChoicePicker;

public class RestaurantApp {
  /* Use a single scanner across the app to prevent accidentally closing System.in input stream */
  public static final Scanner sc = new Scanner(System.in);


  public static void main(String[] args) {
    int choice = -1;

    System.out.println("Welcome to the restaurant POS Application ");
    // Select staff operating the POS
    StaffController staffControl = new StaffController();

    //
    String prompt = "Which section do you want to go to? ";
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "Menu Item");
    options.put(2, "Promotional Sets");
    options.put(3, "Orders");
    options.put(4, "Reservations");
    options.put(5, "Invoice");
    options.put(6, "Quit");
    options.put(7, "Edit Staff");
    ChoicePicker picker = new ChoicePicker(prompt, options);
    while (choice != 6) {
      choice = picker.run();
      switch (choice) {
      case 1:
        System.out.println("Entering option 1");
        break;
      case 2:
        System.out.println("Entering option 2");
        break;
      case 3:
        System.out.println("Entering option 3");
        break;
      case 4:
        System.out.println("Entering option 4");
        break;
      case 5:
        System.out.println("Entering option 5");
        break;
      case 6:
        System.out.println("Exiting the app ...");
        break;
      case 7:
        System.out.println("Edit the staff ...");
        staffControl.mainOptions();
        break;
      default:
        break;
      }
    }

    // TODO: save all ephemeral data to stores
    staffControl.saveAll();
    sc.close();
  }
}
