package app.entities;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class Order extends Bookable {
  private static int nextId = 1;
  private Invoice invoice;

  private TreeMap<Orderable, Integer> orderItems;

  public Order(Staff s, Customer c, int p, Table t, LocalDateTime startDT, LocalDateTime endDT) {
    /* Used when linked to a reservation, start and end time
      depends on what was booked on the reservation */
    super(s, c, p);
    start = startDT;
    end = endDT;
    table = t;
    id = nextId++;
    orderItems = new TreeMap<Orderable, Integer>();
    invoice = null;
  }

  public Order(Staff s, Customer c, int p, Table t) {
    /* If unlinked to any reservation, start time now,
    end MAX_DINING_HOURS hrs later */
    super(s, c, p);
    start = LocalDateTime.now();
    end = start.plusHours(Table.MAX_DINING_HRS);
    table = t;
    id = nextId++;
    orderItems = new TreeMap<Orderable, Integer>();
    invoice = null;
  }

  public Invoice getInvoice() { return invoice; }
  public void setInvoice(Invoice inv) {
    System.out.println("Creating invoice ...");
    invoice = inv;
  }
  public void printInvoice() {
    if (invoice != null) {
      invoice.printOrder();
    } else {
      System.out.println("Have not made payment yet!");
    }
  }

  @Override
  public String getName() {
    return "Order #" + getId();
  }

  @Override
  public String getDesc() {
    return "For " + getCustomerName() +
      ", " + getPax() ;
  }

  @Override
  public String getAttrsString() {
    return getName() +
      "\nCustomer: " + getCustomerName() +
      "\nStaff: " + getStaffName() +
      "\nTable: " + getTableName() +
      "\nNum pax: " + getPax() +
      "\nBooked for: " + getStartString() +
      " (" + Table.MAX_DINING_HRS + " hours)";
    /* Orderables in the order will be printed in the invoice instead */
    }

  public TreeMap<Orderable, Integer> getOrderables() {
    return orderItems;
  }

  public Orderable getOrderable(int index) {
    int j = 0;
    Iterator<Map.Entry<Orderable, Integer>> iterator = getOrderables().entrySet().iterator();
    Map.Entry<Orderable, Integer> entry = null;
    while (iterator.hasNext() && (j <= index)) {
      entry = iterator.next();
      j++;
    }
    return entry.getKey();
  }

  public String getOrderablesString() {
    int entryNum = 1;
    String outString = getName();
    for (Map.Entry<Orderable, Integer> entry:getOrderables().entrySet()) {
      outString += "\n" + (entryNum++) + ". " + entry.getKey().getName() + " [" + entry.getValue() + "x]";
    }
    return outString;
  }

  public TreeMap<Integer, String> getOrderablesChoiceMap() {
    /* To be passed as parameter to ChoicePicker */
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();

    int index = 1;
    Iterator<Map.Entry<Orderable, Integer>> iterator = getOrderables().entrySet().iterator();
    Map.Entry<Orderable, Integer> entry = null;
    while (iterator.hasNext()) {
      entry = iterator.next();
      options.put(index, entry.getKey().getName());
      index++;
    }
    options.put(0, "Go back");
    return options;
  }

  /* Operations to add/remove Orderable to Order */
  public void addOrderable(Orderable orderItem, Integer quantity) {
    /* Check if orderable is already in the Map */
    if (getOrderables().containsKey(orderItem)) {
      System.out.println("Order already has this Menu Item / Promotional Package. Change the quantity instead if you want to add one more. ");
    } else {
      orderItems.put(orderItem, quantity);
      System.out.println("Added " + quantity + " pcs of " + orderItem.getName() + " to " + this.getName());
    }
  }

  public void removeOrderable(Orderable orderItem) {
    if (getOrderables().containsKey(orderItem)) {
      Integer qty= orderItems.remove(orderItem);
      if (qty != null)
        System.out.println("Removed " + qty + " pcs of " + orderItem.getName() + " from " + this.getName());
    } else {
      System.out.println("Fail to remove: " + this.getName() + " does not have " + orderItem.getName() + " yet");
    }
  }

  public void editQuantityOrderable(Orderable orderItem, Integer newQuantity) {
    if (getOrderables().containsKey(orderItem)) {
      Integer oldQuantity = orderItems.replace(orderItem, newQuantity);
      if (oldQuantity != null)
        System.out.println("Changed from " + oldQuantity + " to " + newQuantity + " pcs of " + orderItem.getName() + " in " + this.getName());
    } else {
      System.out.println("Fail to edit: " + this.getName() + " does not have " + orderItem.getName() + " yet");
    }
  }
}
