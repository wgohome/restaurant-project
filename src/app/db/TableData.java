package app.db;

import java.util.ArrayList;
import java.util.List;

import app.entities.Bookable;
import app.entities.Table;
import app.interfaces.EntityStorable;

public class TableData extends Data {
  public TableData() {
    super("data/tables.dat");
  }

  protected List<EntityStorable> seedList() {
    List<EntityStorable> tables = new ArrayList<EntityStorable>();
    tables.add(new Table(1, 2));
    tables.add(new Table(2, 2));
    tables.add(new Table(3, 4));
    tables.add(new Table(4, 4));
    tables.add(new Table(5, 6));
    tables.add(new Table(6, 6));
    tables.add(new Table(7, 8));
    tables.add(new Table(8, 8));
    tables.add(new Table(9, 10));
    tables.add(new Table(10, 10));
    return tables;
  }

  public Table getOneFreeTable(int pax, Bookable booker) {
    Table table = null;
    /* Free up tables before trying to get a table */
    freeUpTables();
    for (EntityStorable e:getList()) {
      table = (Table) e;
      if (!table.isOccupied() && table.getPax() >= pax) {
        table.bookTable(booker);
        break;
      } else {
        table = null;
      }
    }
    if (table == null)
      System.out.println("No available table for " + pax + " pax now");
    return table;
  }

  public void freeUpTables() {
    System.out.println("Freeing up any table with expired reservations ...");
    for (EntityStorable e:getList()) {
      Table table = (Table) e;
      table.freeIfResvExpired();
    }
  }
}
