/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.foi.uzdis.davmoslav.mvc.Controller;
import org.foi.uzdis.davmoslav.mvc.Model;
import org.foi.uzdis.davmoslav.mvc.View;

/**
 *
 * @author Moki
 */
public class Main {

    public static void main(String[] args) {
        if(args.length == 0){
            System.err.println("Unesite parametre u konfiguraciji!");
            return;
        }
        String path = args[0];
        if(!isPathDirectory(path)){
            System.err.println("Putanja mora voditi do direktorija!");
            return;
        }
        
        int interval = 0;
        if(args.length >= 2){
            try
            {
                interval = Integer.parseInt(args[1]);
            }
            catch(Exception ex)
            {
                System.err.println("Drugi parametar mora biti broj!");
                return;
            }
        }
        
        String datoteka = "";
        if (args.length == 3) {
            datoteka = args[2];
            if(!fileIsNotTxt(datoteka)){
                System.err.println("Treći parametar mora voditi do tekstualne datoteke");
                return;
            }
        }
        
        Model model = new Model(path, datoteka);
        View view = new View(model);
        model.addObserver(view);
        model.setInterval(interval);
        Controller controller = new Controller(interval, model, view);
        controller.startApplication();
    }
    
    private static boolean isPathDirectory(String path){
        try
        {
            File aFile = new File(path);
            return aFile.isDirectory();
        }
        catch(Exception ex)
        {
            return false;
        }
    }

    private static boolean fileIsNotTxt(String datoteka) {
        try
        {
            Path path = Paths.get(datoteka);
            String res = Files.probeContentType(path);
            return res.equals("text/plain");
        }
        catch(Exception ex)
        {
            return false;
        }
    }
}
