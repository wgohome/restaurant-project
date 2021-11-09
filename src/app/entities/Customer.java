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

  @Override
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getContact() { return contact; }
  public void setContact(String contact) { this.contact = contact; }

  public Boolean getMembership() { return membership; }
  public void setMembership(Boolean bool) { this.membership = bool; }

  @Override
  public String getDesc() {
    return contact + (membership ? " (Member)" : "");
  }

  @Override
  public String getAttrsString() {
    return "Name: " + getName() +
      "\nContact: " + getContact() +
      "\nMember: " + getMembership();
  }
}
