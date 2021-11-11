package app.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import app.entities.Table;
import app.interfaces.EntityStorable;

public class TableController extends Controller {
  public TableController() {
    super("data/tables.dat");
    updateNextId();
  }

  protected List<EntityStorable> seedList() {
    List<EntityStorable> tables = new ArrayList<EntityStorable>();
    tables.add(new Table(2));
    tables.add(new Table(2));
    tables.add(new Table(4));
    tables.add(new Table(4));
    tables.add(new Table(6));
    tables.add(new Table(6));
    tables.add(new Table(8));
    tables.add(new Table(8));
    tables.add(new Table(10));
    tables.add(new Table(10));
    return tables;
  }

  private void updateNextId() {
    for (EntityStorable entity : getList()) {
      Table table = (Table) entity;
      if (Table.getNextId() <= table.getId()) {
        Table.setNextId(table.getId() + 1);
      }
    }
  }

  public Table getOneFreeTable(int pax, LocalDateTime start, LocalDateTime end) {
    Table table = null;
    /* Free up tables before trying to get a table */
    freeUpTables();
    for (EntityStorable e:getList()) {
      table = (Table) e;
      if (table.isFree(start, end) && table.getPax() >= pax) {
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
      table.freeExpiredReservations();
    }
  }
}
