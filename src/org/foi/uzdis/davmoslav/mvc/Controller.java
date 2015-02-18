/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.mvc;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Moki
 */
public class Controller {

    Model model;
    View view;
    int interval;
    ScheduledExecutorService exec;
    boolean kraj = false;

    public Controller(int interval, Model model, View view) {
        this.model = model;
        this.view = view;
        this.interval = interval;
    }

    public void startApplication() {
        help();
        boolean exit = false;
        String pattern = "(^6) ([0-9]{1,4})";
        String pattern2 = "(^7) ([0-9]{1,4}) ([0-9]{1,4})";

        Pattern r = Pattern.compile(pattern);
        Pattern r2 = Pattern.compile(pattern2);
        do {
            String command = newCommand().trim();
            Matcher m = r.matcher(command);
            Matcher m2 = r2.matcher(command);
            if (command.equals("1")) {
                view.totalNumberOfFilesAndFolders();
            } else if (command.equals("2")) {
                view.printAllFolders();
            } else if (command.equals("3")) {
                model.pokreniDretvu();
            } else if (command.equals("4")) {
               if(model.zaustaviDretvu()){
                    System.out.println("Dretva zaustavljena!");
               }
               else {
                   System.out.println("Dretva nije pokrenuta!");
               }
            } else if (command.equals("5")) {
                view.printSavedChanges();
            } else if (m.find()) {
                if (Integer.valueOf(m.group(2)) < 0) {
                    System.out.println("Broj je manji od 0 ili je izvan raspona!");
                } else {
                    view.printChange(Integer.valueOf(m.group(2)));
                }
            } else if (m2.find()) {
                if (Integer.valueOf(m2.group(2)) < 0) {
                    System.out.println("Broj je manji od 0 ili je izvan raspona!");
                } else {

                }
            } else if (command.equals("8")) {

            } else if (command.equals("9")) {
                System.out.println(""+model.pobrojiVrsteDokumenata());
            } else if (command.equalsIgnoreCase("q")) {
                System.out.println("Izašli ste iz aplikacije");
                exit = true;
                model.zaustaviDretvu();
            } else if (command.equalsIgnoreCase("h")) {
                help();
            } else {
                System.out.println("Unesena naredba nije podržana.");
            }

        } while (!exit);
    }

    public void help() {
        System.out.println("============ Help ============");
        System.out.println("1 - ispis ukupnog broja direktorija i datoteka u strukturi\n"
                + "2 - ispis sadržaja strukture direktorija i datoteka\n"
                + "3 - izvršavanje dretve\n"
                + "4 - prekid izvršavanja dretve\n"
                + "5 - ispis rednog broja i vremena spremljenih promjena stanja strukture\n"
                + "6 n - postavljanje stanja strukture na promjenu broj n čime ono postaje novo trenutno stanje strukture\n"
                + "7 n m - uspoređivanje stanja strukture promjena broj n u odnosu na promjenu broj m\n"
                + "8 - postavljanje trenutnog stanja strukture kao početno stanje i odbacivanje svih prethodnih stanja\n"
                + "9 - prebrojavanje videa, slika i tekstualnih datoteka\n"
                + "Q - prekid rada programa\n"
                + "H - help");
        System.out.println("==============================");
    }

    public String newCommand() {
        System.out.print("Unesite naredbu: ");
        Scanner input = new Scanner(System.in);
        String newCommand = input.nextLine();
        return newCommand;
    }

}
