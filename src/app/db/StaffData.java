package app.db;

import java.util.ArrayList;
import java.util.List;

import app.entities.Staff;
import app.entities.Staff.Title;
import app.interfaces.*;

public class StaffData extends Data {
  /* Methods are generally overloaded with fewer/no params not overriden */
  public StaffData() {
    super("data/staffs.dat");
    /* Sets the protected attributes: list and filepath */
  }

  protected List<EntityStorable> seedList() {
    /* Seeds the staff instances if the serialised not available in data store */
    List<EntityStorable> staffs = new ArrayList<EntityStorable>();
    staffs.add(new Staff(1, "Annie", Title.MANAGER));
    staffs.add(new Staff(2, "Ben", Title.SENIOR));
    staffs.add(new Staff(3, "Carrie", Title.JUNIOR));
    return staffs;
  }
}
