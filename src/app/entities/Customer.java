package app.entities;

import app.interfaces.EntityStorable;

public class Customer implements EntityStorable {
  public String name;
  public String contact;
  public Boolean membership;

  public Customer(String name, String contact, Boolean mem) {
    this.name = name;
    this.contact = contact;
    this.membership = mem;
  }

  public String getContact() { return contact; }
  public Boolean getMembership() { return membership; }
  public void setName(String name) { this.name = name; }
  public void setContact(String contact) { this.contact = contact; }
  public void setMembership(Boolean bool) { this.membership = bool; }

  /* Realising methods of EntityStorable interface */
  public String getName() { return name; }
  public String getDesc() { return contact + (membership ? " (Member)" : ""); }
}
