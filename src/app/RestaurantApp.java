package app;

import java.util.Scanner;
import java.util.TreeMap;

import app.controllers.CustomerController;
import app.controllers.MenuItemController;
import app.controllers.ReservationController;
import app.controllers.StaffController;
import app.controllers.TableController;
import app.utilities.ChoicePicker;

public class RestaurantApp {
  /* Use a single scanner across the app to prevent accidentally closing System.in input stream */
  public static final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    int choice = -1;

    System.out.println("Welcome to the restaurant POS Application ");
    // Initialize controllers
    StaffController staffControl = new StaffController();
    MenuItemController itemControl = new MenuItemController();
    CustomerController customerControl = new CustomerController();
    TableController tableControl = new TableController();
    ReservationController reservationControl = new ReservationController(customerControl, tableControl, staffControl);

    // Main menu
    String prompt = "Which section do you want to go to? ";
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "Menu Items");
    options.put(2, "Promotional Sets");
    options.put(3, "Orders");
    options.put(4, "Reservations");
    options.put(5, "Invoices");
    options.put(6, "Table Management");
    options.put(7, "Customer Management");
    options.put(8, "Staff Management");
    options.put(9, "Quit and save data");
    ChoicePicker picker = new ChoicePicker(prompt, options);
    while (choice != 9) {
      choice = picker.run();
      switch (choice) {
      case 1:
        itemControl.mainOptions();
        break;
      case 2:
        System.out.println("Entering option 2");
        break;
      case 3:
        System.out.println("Entering option 3");
        break;
      case 4:
        reservationControl.mainOptions();
        break;
      case 5:
        System.out.println("Entering option 5");
        break;
      case 6:
        tableControl.mainOptions();
        break;
      case 7:
        customerControl.mainOptions();
        break;
      case 8:
        staffControl.mainOptions();
        break;
      case 9:
        System.out.println("Exiting the app ...");
        break;
      default:
        break;
      }
    }

    // Save all ephemeral data to stores
    staffControl.saveAll();
    itemControl.saveAll();
    customerControl.saveAll();
    tableControl.saveAll();
    reservationControl.saveAll();
    sc.close();
  }
}
