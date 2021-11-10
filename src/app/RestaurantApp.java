package app;

import java.util.Scanner;
import java.util.TreeMap;

import app.boundaries.Boundary;
import app.boundaries.CustomerBoundary;
import app.boundaries.MenuItemBoundary;
import app.boundaries.PromotionBoundary;
import app.boundaries.ReservationBoundary;
import app.boundaries.StaffBoundary;
import app.boundaries.TableBoundary;
import app.utilities.ChoicePicker;

public class RestaurantApp {
  /* Use a single scanner across the app to prevent accidentally closing System.in input stream */
  public static final Scanner sc = new Scanner(System.in);

  public static void main(String[] args) {
    int choice = -1;

    System.out.println("Welcome to the restaurant POS Application ");
    // Initialize boundaries
    StaffBoundary staffBoundary = new StaffBoundary();
    MenuItemBoundary itemBoundary = new MenuItemBoundary();
    PromotionBoundary promotionBoundary = new PromotionBoundary(itemBoundary);
    CustomerBoundary customerBoundary = new CustomerBoundary();
    TableBoundary tableBoundary = new TableBoundary();
    ReservationBoundary reservationBoundary = new ReservationBoundary(customerBoundary, tableBoundary, staffBoundary);

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
    options.put(100, "Quit and Delete data files (clear DB)");
    ChoicePicker picker = new ChoicePicker(prompt, options);
    while (choice != 9 && choice != 100) {
      choice = picker.run();
      switch (choice) {
      case 1:
        itemBoundary.mainOptions();
        break;
      case 2:
        promotionBoundary.mainOptions();
        break;
      case 3:
        System.out.println("Entering option 3");
        break;
      case 4:
        reservationBoundary.mainOptions();
        break;
      case 5:
        System.out.println("Entering option 5");
        break;
      case 6:
        tableBoundary.mainOptions();
        break;
      case 7:
        customerBoundary.mainOptions();
        break;
      case 8:
        staffBoundary.mainOptions();
        break;
      case 9:
        System.out.println("Exiting the app ...");
        /* Save all ephemeral data to stores */
        staffBoundary.saveAll();
        itemBoundary.saveAll();
        promotionBoundary.saveAll();
        customerBoundary.saveAll();
        tableBoundary.saveAll();
        reservationBoundary.saveAll();
        break;
      case 100:
        /* For development purposes */
        System.out.println("Deleting files ...");
        deleteFiles(staffBoundary);
        deleteFiles(itemBoundary);
        deleteFiles(promotionBoundary);
        deleteFiles(customerBoundary);
        deleteFiles(tableBoundary);
        deleteFiles(reservationBoundary);
        break;
      default:
        break;
      }
    }
    sc.close();
  }

  private static void deleteFiles(Boundary c) {
    /* For development purposes */
    c.getController().deleteControllerFile();
  }
}
