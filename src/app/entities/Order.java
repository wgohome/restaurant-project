package app.entities;

import java.time.LocalDateTime;

public class Order extends Bookable {

  private static int nextId = 1;

  public Order(Staff s, Customer c, int p, Table t) {
    /* If unlinked to any reservation, start time now,
    end MAX_DINING_HOURS hrs later */
    super(s, c, p);
    start = LocalDateTime.now();
    end = start.plusHours(Table.MAX_DINING_HRS);
    table = t;
    id = nextId++;
  }

  public Order(Staff s, Customer c, int p, Table t, LocalDateTime startDT, LocalDateTime endDT) {
    /* Used when linked to a reservation, start and end time
      depends on what was booked on the reservation */
    super(s, c, p);
    start = startDT;
    end = endDT;
    table = t;
    id = nextId++;
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
      " (" + Table.MAX_DINING_HRS + " hours)";  }
}
