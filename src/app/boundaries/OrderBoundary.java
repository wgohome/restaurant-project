package app.boundaries;

import java.time.LocalDateTime;
import java.util.TreeMap;

import app.controllers.OrderController;
import app.entities.Customer;
import app.entities.Order;
import app.entities.Orderable;
import app.entities.Reservation;
import app.entities.Table;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class OrderBoundary extends Boundary {
  private CustomerBoundary cusBoundary;
  private TableBoundary tableBoundary;
  private StaffBoundary staffBoundary;
  private ReservationBoundary resvBoundary;
  private MenuItemBoundary itemBoundary;
  private PromotionBoundary promotionBoundary;

  public OrderBoundary(CustomerBoundary cb, TableBoundary tb, StaffBoundary sb, ReservationBoundary rb, MenuItemBoundary mib, PromotionBoundary pb) {
    super(new OrderController());
    entityName = "Order";
    cusBoundary = cb;
    tableBoundary = tb;
    staffBoundary = sb;
    resvBoundary = rb;
    itemBoundary = mib;
    promotionBoundary = pb;
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
    /* Add orders */
    askToManageOrder(order);
    return order;
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

  private void askToManageOrder(Order order) {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Item/Promo in Order");
    options.put(2, "Add new Item/Promo to Order");
    options.put(3, "Change quantity of Item/Promo in Order");
    options.put(4, "Remove an Item/Promo from Order");
    options.put(9, "Exit - Back to Orders menu");
    ChoicePicker itemsPicker = new ChoicePicker("How do you want to manage " + order.getName() + "? ", options);
    while (choice != 9) {
      choice = itemsPicker.run();
      switch (choice) {
      case 1:
        System.out.println(order.getOrderablesString());
        break;
      case 2:
        askAddItemToOrder(order);
        break;
      case 3:
        askChangeQuantityToOrder(order);
        break;
      case 4:
        askDeleteItemFromOrder(order);
        break;
      case 9:
        System.out.println("Going back to the Order menu ... ");
        break;
      default:
        break;
      }
    }
  }

  private void askAddItemToOrder(Order order) {
    int choice1 = -1;
    int choice2 = -1;
    int choice3 = -1;

    TreeMap<Integer, String> options1 = new TreeMap<Integer, String>();
    options1.put(1, "Menu Item");
    options1.put(2, "Promotion Package");
    options1.put(3, "Done, go back");
    ChoicePicker picker1 = new ChoicePicker("What do you want to add? ", options1);
    while (choice1 != 3) {
      choice1 = picker1.run();
      switch (choice1) {
        case 1:
          choice2 = itemBoundary.entityIntChoicePicker("Which Menu Item do you want to add? ");
          if (choice2 != 0) {
            order.addOrderable(
              (Orderable) itemBoundary.getEntity(choice2 - 1),
              askQuantity()
            );
          }
          break;
        case 2:
          choice3 = promotionBoundary.entityIntChoicePicker("Which Promotion Package do you want to add? ");
          if (choice3 != 0) {
            order.addOrderable((Orderable) promotionBoundary.getEntity(choice3 - 1),
            askQuantity());
          }
          break;
        default:
          break;
      }
    }
  }

  private void askDeleteItemFromOrder(Order order) {
    int choice1 = -1;
    ChoicePicker picker1 = new ChoicePicker(entityName, order.getOrderablesChoiceMap());
    choice1 = picker1.run();
    while (choice1 != 0) {
      order.removeOrderable(order.getOrderable(choice1 - 1));
      /* Need to reinstantiate picker as now there will be fewer options after the one deleted */
      picker1 = new ChoicePicker(entityName, order.getOrderablesChoiceMap());
      choice1 = picker1.run();
    }
  }

  private void askChangeQuantityToOrder(Order order) {
    int choice1 = -1;
    ChoicePicker picker1 = new ChoicePicker(entityName, order.getOrderablesChoiceMap());
    choice1 = picker1.run();
    while (choice1 != 0) {
      order.editQuantityOrderable(
        order.getOrderable(choice1 - 1),
        askQuantity()
      );
      choice1 = picker1.run();
    }
  }

  private int askQuantity() {
    int quantity = -1;
    while (quantity < 0 || quantity >= 10) {
      System.out.println("What quantity would you like to get? (at least 1, maximum 10)");
      quantity = sc.nextInt(); sc.nextLine();
    }
    return quantity;
  }

  @Override
  public void mainOptions() {
    int orderChoice;

    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Orders");
    options.put(2, "Add new Order");
    options.put(3, "Remove an Order");
    options.put(4, "Edit items in an Order");
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
        // edit();
        orderChoice = entityIntChoicePicker("Which Order do you want to manage? ");
        if (orderChoice != 0) {
          askToManageOrder((Order) getController().getEntity(orderChoice - 1));
        }
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
