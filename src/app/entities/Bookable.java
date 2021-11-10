package app.entities;

import app.interfaces.EntityStorable;

public abstract class Bookable implements EntityStorable {
  protected Staff staff;
  protected Customer customer;
  protected Table table;

  public Bookable(Staff s, Customer c) {
    staff = s;
    customer = c;
  }

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
}
