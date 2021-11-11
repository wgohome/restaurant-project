package app.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import app.interfaces.EntityStorable;

public abstract class Bookable implements EntityStorable {
  protected static int nextId;

  protected int id;
  protected Staff staff;
  protected Customer customer;
  protected Table table; /* Can be nil for an Order */
  protected int pax;
  protected LocalDateTime start;
  protected LocalDateTime end;

  protected Bookable(Staff s, Customer c, int p) {
    staff = s;
    customer = c;
    pax = p;
  }

  public int getId() { return id; }
  public void setId(int i) { id = i; } /* setID based on nextId instead */

  public static int getNextId() { return nextId; }
  public static int incrementNextId() { return ++nextId; }
  public static void setNextId(int id) { nextId = id; }

  public Staff getStaff() { return staff; }
  public String getStaffName() { return staff.getName(); }
  protected void setStaff(Staff s) { staff = s; }

  public Customer getCustomer() { return customer; }
  public String getCustomerName() { return customer.getName(); }
  protected void setCustomer(Customer c) { customer = c; }

  public Table getTable() { return table; }
  public String getTableName() { return table.getName(); }
  public void setTable(Table t) {
    table = t;
    /* Ensure that the boundary calling this method has stored the Bookable in the Table as well. Bi-directional */
  }

  public int getPax() { return pax; }
  public void setPax(int p) { pax = p; }

  public LocalDateTime getStart() { return start; }
  public void setStart(LocalDateTime s) { start = s; }

  public LocalDateTime getEnd() { return end; }
  public void setEnd(LocalDateTime e) { end = e; }

  public String getStartString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return start.format(formatter);
  }
  public String getEndString() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    return end.format(formatter);
  }

  /* From Interface EntityStorable < Printable */
  @Override
  public abstract String getName();
  @Override
  public abstract String getDesc();
  @Override
  public abstract String getAttrsString();
}
