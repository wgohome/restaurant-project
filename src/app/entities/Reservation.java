package app.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Reservation extends Bookable {
  /* The duration past a reservation for it to be invalid, in minutes */
  public static final int MINS_EXPIRY = 30;

  private LocalDateTime resvDT;
  private int pax;

  public Reservation(Staff s, Customer c, int p, LocalDateTime dt) {
    super(s, c);
    resvDT = dt;
    pax = p;
  }

  public LocalDateTime getResvDT() { return resvDT; }
  public String getResvDTString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern ("yyyy-MM-dd HH:mm");
    return resvDT.format(formatter);
  }
  public void setResvDT(LocalDateTime dt) { resvDT = dt; }

  public int getPax() { return pax; }
  public void setPax(int p) { pax = p; }

  public boolean isExpired() {
    return resvDT.isBefore(
      LocalDateTime.now()
      .plus(Duration.ofMinutes(MINS_EXPIRY))
    );
  }

  @Override
  public String getName() {
    return "Reservation for " + customer.getName();
  }

  @Override
  public String getDesc() {
    return this.getName() +
      " (" + table.getName() +
      ", " + pax +
      " pax, " + resvDT +")";
  }

  @Override
  public String getAttrsString() {
    return "Customer: " + getCustomerName() +
      "\nStaff: " + getStaffName() +
      "\nTable: " + getTableName() +
      "\nNum pax: " + getPax() +
      "\nDatetime: " + getResvDTString();
  }
}
