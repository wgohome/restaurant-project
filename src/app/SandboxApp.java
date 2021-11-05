package app;
/*
A class to test out things
*/

import java.io.File;
import java.util.Arrays;

import app.entities.*;
import app.entities.Staff.Title;

public class SandboxApp {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        // File f = new File("data/staffs.dat");
        // System.out.println(f.exists());
        // System.out.println(f.isDirectory());

        Restaurant resto = new Restaurant();
        resto.staffSelection();
    }
}
