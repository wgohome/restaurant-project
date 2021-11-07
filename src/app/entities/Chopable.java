package app.entities;

public class Chopable {
  protected Staff staff;
  protected Customer customer;

  public Chopable(Staff s, Customer c) {
    staff = s;
    customer = c;
  }

  public Staff getStaff() { return staff; }
  public Customer getCustomer() { return customer; }
  protected void setStaff(Staff s) { staff = s; }
  protected void setCustomer(Customer c) { customer = c; }
}
