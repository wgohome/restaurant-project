package app;
import java.util.Arrays;
import java.util.Scanner;

import app.entities.*;

public class RestaurantApp {
  public static final Scanner sc = new Scanner(System.in);
  public static void main(String[] args) {
    int choice = 0;

    Restaurant resto = new Restaurant();
    System.out.println("Welcome to the restaurant POS Application ");
    resto.staffSelection();

    while (choice != 6) {
      printMainMenu();
      choice = mainMenuSelection();

      switch (choice) {
        case 4:
          System.out.println("Entering option 4");
          break;
        default:
          break;
      }
    }

    resto.saveEntities();
    sc.close();
  }

  private static int mainMenuSelection() {
    int choice;
    choice = sc.nextInt(); sc.nextLine();

    while (!Arrays.asList(1, 2, 3, 4, 5, 6).contains(choice)) {
      System.out.println("Please a valid choice: ");
      choice = sc.nextInt(); sc.nextLine();
    }
    return choice;
  }

  private static void printMainMenu() {
    System.out.println("\nWhich section do you want to go to?");
    System.out.println("1. Menu Item");
    System.out.println("2. Promotional Sets");
    System.out.println("3. Orders");
    System.out.println("4. Reservations");
    System.out.println("5. Invoice");
    System.out.println("6. Quit");
  }
}
