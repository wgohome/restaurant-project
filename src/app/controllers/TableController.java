package app.controllers;

import java.util.TreeMap;

import app.db.TableData;
import app.entities.Bookable;
import app.entities.Table;
import app.interfaces.EntityStorable;
import app.utilities.ChoicePicker;

public class TableController extends Controller {
  public TableController() {
    super(new TableData());
    entityName = "Table";
  }

  @Override
  protected EntityStorable entityCreator() {
    int id;
    int pax;

    System.out.println("Enter table ID: ");
    id = sc.nextInt(); sc.nextLine();

    System.out.println("Enter table capacity (pax): ");
    pax = sc.nextInt(); sc.nextLine();

    return (EntityStorable) (new Table(id, pax));
  }

  @Override
  public void mainOptions() {
    int choice = -1;
    TreeMap<Integer, String> options = new TreeMap<Integer, String>();
    options.put(1, "List all Tables");
    options.put(2, "Add new Table");
    options.put(3, "Remove a Table");
    // options.put(4, "Edit a Table");
    options.put(5, "Free up Tables with expired Reservations");
    options.put(9, "Exit - Back to main menu");
    ChoicePicker mainPicker = new ChoicePicker("This is the Table menu, what would you like to do? ", options);
    while (choice != 9) {
      choice = mainPicker.run();
      switch (choice) {
      case 1:
        printAll();
        break;
      case 2:
        create();
        break;
      case 3:
        delete();
        break;
      // case 4:
      //   edit();
      //   break;
      case 5:
        freeUpTables();
        break;
      case 9:
        System.out.println("Going back to the main menu ... ");
        break;
      default:
        break;
      }
    }
  }

  public Table getOneFreeTable(int pax, Bookable booker) {
    TableData tableData = (TableData) getData();
    return tableData.getOneFreeTable(pax, booker);
  }

  public void freeUpTables() {
    TableData tableData = (TableData) getData();
    tableData.freeUpTables();
    /* TODO: delete the reservation too */
  }
}
