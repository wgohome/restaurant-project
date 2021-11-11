package app.boundaries;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.TreeMap;

import app.controllers.ReservationController;
import app.entities.Customer;
import app.entities.Reservation;
import app.entities.Table;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class ReservationBoundary extends Boundary {
  private CustomerBoundary cusBoundary;
  private TableBoundary tableBoundary;
  private StaffBoundary staffBoundary;

  public ReservationBoundary(CustomerBoundary cb, TableBoundary tb, StaffBoundary sb) {
    super(new ReservationController());
    entityName = "Reservation";
    cusBoundary = cb;
    tableBoundary = tb;
    staffBoundary = sb;
  }

  @Override
  protected EntityStorable entityCreator() {
    Customer customer;
    Reservation newResv;
    Table table;
    LocalDateTime start;
    int pax;

    customer = pickOrCreateCust();
    start = askForStartTime();
    pax = askForPax();
    /* Create the reservation */
    newResv = new Reservation(staffBoundary.getCurStaff(), customer, pax, start);
    /* Assign a free table to the reservation */
    table = tableBoundary.getOneFreeTable(pax, start, newResv.getEnd());
    if (table != null) {
      System.out.println("Allocating " + table.getName() + " to " + newResv.getName());
      newResv.setTable(table);
      System.out.println("Adding " + newResv.getName() + " to " + table.getName());
      table.addReservation(newResv);
      /* The two-way association is complete, yay 🥳 */
      return newResv;
    } else {
      return null;
    }
  }

  private Customer pickOrCreateCust() {
    int choice;
    Customer customer;
    TreeMap<Integer, String> options;

    /* Ask if Customer exists */
    options = new TreeMap<Integer, String>();
    options.put(1, "Yes, it is for an existing customer");
    options.put(2, "No, it is a new customer");
    ChoicePicker picker = new ChoicePicker("Is reservation for an existing customer?", options);
    choice = picker.run();
    if (choice == 1) {
      /* Pick from existing Customers */
      options = cusBoundary.getChoiceMap();
      ChoicePicker cusPicker = new ChoicePicker("Which customer is this reservation for?", options);
      choice = cusPicker.run();
      customer = (Customer) cusBoundary.getEntity(choice - 1);
    } else {
      /* Create a new Customer */
      customer = (Customer) cusBoundary.create();
    }
    return customer;
  }

  private LocalDateTime askForStartTime() {
    LocalDateTime dt = null;
    boolean accepted = false;

    System.out.println("Note that booking will be for " + Table.MAX_DINING_HRS + " hours.");
    System.out.println("What time would you like to reserve for? Format (YYYY-MM-DD)T(HH:MM), for eg. 2021-09-09T10:11");
    do {
      try {
        dt = LocalDateTime.parse(sc.nextLine());
        if (dt.isBefore(LocalDateTime.now())) {
          // Checks if time is in the past or future
          System.out.println("The start time should not have past! What time would you like to reserve for?");
        } else if (dt.toLocalTime().isBefore(Reservation.OPENING_TIME) ||
                   dt.plusHours(Table.MAX_DINING_HRS).toLocalTime().isAfter(Reservation.CLOSING_TIME)) {
          // Check if time is within opening hours
          System.out.println("This will be outside closing hours of " + Reservation.CLOSING_TIME + "! What time would you like to reserve for?");
        } else {
          accepted = true;
        }
      } catch (DateTimeParseException e) {
        System.out.println("Ensure the correct format (YYYY-MM-DD)T(HH:MM), for eg. 2021-09-09T10:11");
      }
    } while (!accepted);
    return dt;
  }

  private int askForPax() {
    int pax = -1;
    while (pax < 1 || pax > 10) {
      System.out.println("How many pax would you like to reserve for? (maximum 10)");
      pax = sc.nextInt(); sc.nextLine();
    }
    return pax;
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
        indexAll();
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
