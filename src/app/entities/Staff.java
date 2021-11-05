package app.entities;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import app.utils.Serializer;

public class Staff implements Serializable {
  public enum Title { Manager, Senior, Junior }

  private int id;
  private String name;
  private Title role;

  public Staff(int idIn, String nameIn, Title r) {
    this.id = idIn;
    this.name = nameIn;
    this.role = r;
  }

  public int getId() { return this.id; }
  public String getName() { return this.name; }
  public Title getRole() { return this.role; }
  public void setId(int i) { this.id = i; }
  public void setName(String n) { this.name = n; }
  public void setRole(Title r) { this.role = r; }

  /* Static Class Methods to handle all Staff */
  public static List<Staff> loadAllStaffs() {
    List<Staff> staffs;
    File file = new File("data/staffs.dat");
    if (file.exists()) {
      staffs = Serializer.readSerializedObject("data/staffs.dat");
    } else {
      staffs = new ArrayList<Staff>();
      staffs.add(new Staff(1, "Annie", Title.Manager));
      staffs.add(new Staff(2, "Ben", Title.Senior));
      staffs.add(new Staff(3, "Carrie", Title.Junior));
    }
    return staffs;
  }

  public static void saveAllStaffs(List<Staff> staffs) {
    Serializer.writeSerializedObject("data/staffs.dat", staffs);
  }
}
