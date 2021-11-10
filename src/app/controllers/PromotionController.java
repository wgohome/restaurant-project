package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Promotion;
import app.interfaces.EntityStorable;

public class PromotionController extends Controller {
  public PromotionController() {
    super("promotion.dat");
  }

  @Override
  protected List<EntityStorable> seedList() {
    List<EntityStorable> promotions = new ArrayList<EntityStorable>();
    promotions.add(new Promotion("Promo 1", 20));
    return promotions;
  }
}
