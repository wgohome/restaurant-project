package app;
/*
A class to test out things
*/

// import java.io.File;
import app.db.*;
import app.entities.*;
import app.entities.Staff.Title;

import java.util.Scanner;
import java.util.TreeMap;

import app.controllers.*;

public class SandboxApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // File f = new File("data/staffs.dat");
        // System.out.println(f.exists());
        // System.out.println(f.isDirectory());

        // Restaurant resto = new Restaurant();
        // resto.staffSelection();

        // StaffData sd = new StaffData();
        // System.out.println(sd.getClass().getName());
        // System.out.println(sd.getClass().getSimpleName());

        // Controller staffControl = new StaffController();
        // staffControl.create();
        // staffControl.remove(staffControl.data.getList().get(3));
        // staffControl.saveAll();

        // TreeMap<Integer, String> tm1 = new TreeMap<Integer, String>();
        // tm1.put(5, "Aiya");
        // tm1.put(10, "Lame");
        // tm1.put(6, "no");
        // System.out.println(tm1);

        System.out.println(Title.values()[0].name());
    }
}
