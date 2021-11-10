package app.boundaries;

import java.time.LocalDateTime;
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

  public ReservationBoundary(CustomerBoundary cc, TableBoundary tc, StaffBoundary sfc) {
    super(new ReservationController());
    entityName = "Reservation";
    cusBoundary = cc;
    tableBoundary = tc;
    staffBoundary = sfc;
  }

  @Override
  protected EntityStorable entityCreator() {
    Customer customer;
    Reservation newResv;
    Table table;
    LocalDateTime resvDT;
    int pax;

    customer = pickOrCreateCust();
    resvDT = askForTime();
    pax = askForPax();
    /* Create the reservation */
    newResv = new Reservation(staffBoundary.getCurStaff(), customer, pax, resvDT);
    /* Assign a free table to the reservation */
    table = tableBoundary.getOneFreeTable(pax, newResv);
      /* This will assign newResv to the table
        if an available table is returned */
    if (table != null) {
      newResv.setTable(table);
        /* Next, assign the table to newResv.
          The two-way association is complete, yay 🥳 */
      return newResv;
    } else {
      return null;
    }
  }

  private Customer pickOrCreateCust() {
    int choice;
    Customer customer;
    TreeMap<Integer, String> options;

    /* Ask if staff exists */
    options = new TreeMap<Integer, String>();
    options.put(1, "Yes, it is for an existing customer");
    options.put(2, "No, it is a new customer");
    ChoicePicker picker = new ChoicePicker("Is reservation for an existing customer?", options);
    choice = picker.run();
    if (choice == 1) {
      /* Pick from existing customers */
      options = cusBoundary.getChoiceMap();
      ChoicePicker cusPicker = new ChoicePicker("Which customer is this reservation for?", options);
      choice = cusPicker.run();
      customer = (Customer) cusBoundary.getEntity(choice - 1);
    } else {
      /* Create a new customer */
      customer = (Customer) cusBoundary.create();
    }
    return customer;
  }

  private LocalDateTime askForTime() {
    LocalDateTime dt;
    boolean accepted = false;

    System.out.println("What time would you like to reserve for? Format (YYYY-MM-DD)T(HH:MM), for eg. 2021-09-09T10:11");
    do {
      try {
        dt = LocalDateTime.parse(sc.nextLine());
        // TODO: Need to check if time is in the past or future
        // TODO: Check if time is within opening hours
        accepted = true;
        return dt;
      } catch (Exception e) {
        System.out.println("Ensure the correct format (YYYY-MM-DD)T(HH:MM), for eg. 2021-09-09T10:11");
      }
    } while (!accepted);
    return null;
  }

  private int askForPax() {
    int pax = -1;
    System.out.println("How many pax would you like to reserve for? (maximum 10)");
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
        printAll();
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
