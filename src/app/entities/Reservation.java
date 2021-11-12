package app.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Reservation extends Bookable {
  /* The duration past a reservation for it to be invalid, in minutes */
  public static final int MINS_EXPIRY = 30;
  public static final LocalTime OPENING_TIME = LocalTime.of(8,0);
  public static final LocalTime CLOSING_TIME = LocalTime.of(22,0);

  private static int nextId = 1;

  public Reservation(Staff s, Customer c, int p, LocalDateTime startDT) {
    super(s, c, p);
    start = startDT;
    setEnd();
    id = nextId++;
  }

  /* Method overloading */
  public void setEnd() {
    end = start.plusHours(app.entities.Table.MAX_DINING_HRS);
  }

  public boolean isExpired() {
    return start
      .plus(Duration.ofMinutes(MINS_EXPIRY))
      .isBefore(LocalDateTime.now());
  }

  public void unsetTable(Table t) {
    if (getTable() == t) {
      System.out.println("Unlinked " + t.getName() + " from this " + this.getName());
      setTable(null);
    } else {
      System.out.println(this.getName() + " is not currently linked to " + t.getName() + ", cannot unset.");
    }
  }

  @Override
  public String getName() {
    return "Reservation #" + getId();
  }

  @Override
  public String getDesc() {
    return this.getName() +
      " (" + table.getName() +
      ", " + pax +
      " pax, " + start +")";
  }

  @Override
  public String getAttrsString() {
    return getName() +
      "\nCustomer: " + getCustomerName() +
      "\nStaff: " + getStaffName() +
      "\nTable: " + getTableName() +
      "\nNum pax: " + getPax() +
      "\nBooked for: " + getStartString() +
      " (" + Table.MAX_DINING_HRS + "hours)";
  }
}
