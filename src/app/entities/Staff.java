package app.entities;

import app.interfaces.EntityStorable;

public class Staff implements EntityStorable {
  public enum Title { MANAGER, SENIOR, JUNIOR }

  private int id;
  private String name;
  private Title jobTitle;

  public Staff(int idIn, String nameIn, Title r) {
    this.id = idIn;
    this.name = nameIn;
    this.jobTitle = r;
  }

  public int getId() { return this.id; }
  public void setId(int i) { this.id = i; }

  @Override
  public String getName() { return this.name; }
  public void setName(String n) { this.name = n; }

  public Title getJobTitle() { return this.jobTitle; }
  public void setJobTitle(Title r) { this.jobTitle = r; }

  @Override
  public String getDesc() {
    return getName() + ": " + getJobTitle().name();
  }

  @Override
  public String getAttrsString() {
    return "Name: " + getName() +
      "\nStaff ID: " + getId() +
      "\nJob title: " + getJobTitle().name();
  }
}
