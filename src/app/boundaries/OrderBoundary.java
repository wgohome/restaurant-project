package app.boundaries;

import java.time.LocalDateTime;
import java.util.TreeMap;

import app.controllers.OrderController;
import app.entities.Customer;
import app.entities.Order;
import app.entities.Reservation;
import app.entities.Table;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class OrderBoundary extends Boundary {
  private CustomerBoundary cusBoundary;
  private TableBoundary tableBoundary;
  private StaffBoundary staffBoundary;
  private ReservationBoundary resvBoundary;

  public OrderBoundary(CustomerBoundary cb, TableBoundary tb, StaffBoundary sb, ReservationBoundary rb) {
    super(new OrderController());
    entityName = "Order";
    cusBoundary = cb;
    tableBoundary = tb;
    staffBoundary = sb;
    resvBoundary = rb;
  }

  @Override
  protected EntityStorable entityCreator() {
    Reservation resv;
    Customer customer;
    Order order;
    Table table;
    LocalDateTime start;
    LocalDateTime end;
    int pax;

    resv = askIfHaveReservation();
    if (resv != null) {
      /* If have Reservation, use the details stored in the Reservation */
      order = new Order(staffBoundary.getCurStaff(),
                        resv.getCustomer(),
                        resv.getPax(),
                        resv.getTable(),
                        resv.getStart(),
                        resv.getEnd());
      resvBoundary.getController().remove(resv);
    } else {
      customer = pickOrCreateCust();
      pax = askForPax();
      start = LocalDateTime.now();
      end = start.plusHours(Table.MAX_DINING_HRS);
      table = tableBoundary.getOneFreeTable(pax, start, end);
      if (table != null) {
        order = new Order(staffBoundary.getCurStaff(),
                          customer, pax, table, start, end);
      } else {
        order = null;
      }
    }
    /* Set Table to be occupied by this Order */
    if (order != null)
      order.getTable().setOccupiedBy(order);
    return order;
    /* TODO: Ask to add orderable into the order */
  }

  private Reservation askIfHaveReservation() {
    int choice;
    Reservation resv;
    TreeMap<Integer, String> options;

    if (resvBoundary.getController().size() > 0) {
      /* Ask if Customer have existing Reservation */
      options = new TreeMap<Integer, String>();
      options.put(1, "Yes, they have an existing Reservation");
      options.put(2, "No, it is a walk-in");
      ChoicePicker picker = new ChoicePicker("Does the customer have an existing reservation? ", options);
      choice = picker.run();
    } else {
      /* No reservations to pick from */
      choice = 2;
    }

    if (choice == 1) {
      /* Pick from existing Reservation */
      options = resvBoundary.getChoiceMap();
      ChoicePicker resvPicker = new ChoicePicker("Which Reservation is this new Order for?", options);
      choice = resvPicker.run();
      resv = (Reservation) resvBoundary.getEntity(choice - 1);
      return resv;
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

  private int askForPax() {
    int pax = -1;
    while (pax < 1 || pax > 10) {
      System.out.println("How many pax would you like to reserve for? (maximum 10)");
      pax = sc.nextInt();
      sc.nextLine();
    }
    return pax;
  }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Orders");
    options.put(2, "Add new Order");
    options.put(3, "Remove a Order");
    options.put(4, "Edit a Order");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Orders menu, what would you like to do? ", options);
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
