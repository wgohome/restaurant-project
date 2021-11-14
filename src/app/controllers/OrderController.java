package app.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    // Mark the table as paid. Can still keep link to the table but remove occupiedBy attribute in Table to free it.
    Order order = (Order) e;
    order.getTable().setUnoccupied(order);
  }

  private void updateNextId() {
    for (EntityStorable entity : getList()) {
      Order order = (Order) entity;
      if (Order.getNextId() <= order.getId()) {
        Order.setNextId(order.getId() + 1);
      }
    }
  }

  /* Get selected orders for revenue report */
  public List<Order> getOrdersByTime(LocalDate leftCut, LocalDate rightCut) {
    /* Collects Orders whose order start Date is within the leftCut and RightCut dates */
    List<Order> orders = getList().stream()
      .map((e) -> (Order) e)
      .filter((order) -> {
        return order.getStart().toLocalDate().isAfter(leftCut) &&
          order.getStart().toLocalDate().isBefore(rightCut);
      })
      .collect(Collectors.toList());
    return orders;
  }
}
