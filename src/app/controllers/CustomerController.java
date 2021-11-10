package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Customer;
import app.interfaces.EntityStorable;

public class CustomerController extends Controller {
  public CustomerController() {
    super("data/customers.dat");
  }

  protected List<EntityStorable> seedList() {
    List<EntityStorable> customers = new ArrayList<EntityStorable>();
    customers.add(new Customer("Karen", "98981212", true));
    customers.add(new Customer("Paul", "98981111", false));
    customers.add(new Customer("Peter", "98982222", true));
    customers.add(new Customer("Jane", "98980000", true));
    return customers;
  }
}
