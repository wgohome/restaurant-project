package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Order;
import app.interfaces.EntityStorable;

public class OrderController extends Controller {
  public OrderController() {
    super("data/orders.dat");
    updateNextId();
  }

  @Override
  protected List<EntityStorable> seedList() {
    List<EntityStorable> orders = new ArrayList<EntityStorable>();
    // orders.add(new Order(s, c, p, t));
    /* Start with no order */
    return orders;
  }

  @Override
  protected void afterRemove(EntityStorable e) {
    // TODO: Mark the table as paid. Can still keep link to the table but remove occupiedBy attribute in Table to free it.
  }

  private void updateNextId() {
    for (EntityStorable entity : getList()) {
      Order order = (Order) entity;
      if (Order.getNextId() <= order.getId()) {
        Order.setNextId(order.getId() + 1);
      }
    }
  }
}
