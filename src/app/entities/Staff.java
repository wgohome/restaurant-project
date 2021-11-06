package app.entities;

import app.interfaces.*;

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
  public String getName() { return this.name; }
  public Title getJobTitle() { return this.jobTitle; }
  public void setId(int i) { this.id = i; }
  public void setName(String n) { this.name = n; }
  public void setJobTitle(Title r) { this.jobTitle = r; }

  public String getDesc() {
    return getJobTitle().name();
  }
}
