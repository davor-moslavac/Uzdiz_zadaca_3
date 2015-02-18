/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.mvc;

import java.util.List;
import org.foi.uzdis.davmoslav.observer.Observer;

/**
 *
 * @author Moki
 */
public class View implements Observer {

    Model model;

    public View(Model model) {
        this.model = model;
    }

    public void totalNumberOfFilesAndFolders(){
        System.out.println( "Contains: " + model.getFileCount() + " Files, " + model.getDirectoryCount() + " Folders");
    }

    public void printAllFolders() {
        if (model.getRootFolder().getChilds().isEmpty()) {
            model.getRootFolder().print("");
            System.out.println("Ne sadrži direktorije i datoteke!");
        } else {
            model.getRootFolder().print("");
        }
    }
    
    public void printSavedChanges(){
        model.dajSvePromjene();
    }
    
    public void printChange(int p){
        model.dajPromjenu(p);
    }

    public void printRefresh() {
        System.out.println("\n =!Sadržaj je promijenjen u odnosu na prošli sadržaj!= \n");
    }

    public void printStatistics(List<String> print) {
        System.out.println("--------------------");
        for (String p : print) {
            System.out.println(p);
            System.out.println("--------------------");
        }
    }

    @Override
    public void update(Model model) {
        this.model = model;
    }

}
