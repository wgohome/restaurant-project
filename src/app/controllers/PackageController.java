package app.controllers;

import java.util.ArrayList;
import java.util.List;

import app.entities.Package;
import app.interfaces.EntityStorable;

public class PackageController extends Controller {
  public PackageController() {
    super("package.dat");
  }

  @Override
  protected List<EntityStorable> seedList() {
    List<EntityStorable> packages = new ArrayList<EntityStorable>();
    packages.add(new Package("Promo 1", 20));
    return packages;
  }
}
