/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.mvc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdis.davmoslav.Main;
import org.foi.uzdis.davmoslav.cor.Handler;
import org.foi.uzdis.davmoslav.cor.ImageHandler;
import org.foi.uzdis.davmoslav.cor.OtherHandler;
import org.foi.uzdis.davmoslav.cor.TextHandler;
import org.foi.uzdis.davmoslav.cor.VideoHandler;
import org.foi.uzdis.davmoslav.memento.Caretaker;
import org.foi.uzdis.davmoslav.memento.Memento;
import org.foi.uzdis.davmoslav.memento.Originator;
import org.foi.uzdis.davmoslav.observer.Observer;
import org.foi.uzdis.davmoslav.observer.Subject;
import org.foi.uzdis.davmoslav.state.ActiveFolderState;
import org.foi.uzdis.davmoslav.types.Folder;

/**
 *
 * @author Moki
 */
public class Model implements Subject {

    Folder folder;
    List<Observer> observers = new ArrayList<>();
    Integer folderNumber = 0;
    String datoteka;
    int directoryCount = 0;
    int fileCount = 0;
    private Thread dretva;
    private boolean pokrenuta = false;
    Integer interval = 0;
    Memento memento;
    public static int tekst;
    public static int slike;
    public static int video;
    public static int others;
    public static Handler documentHandler;
    Caretaker caretaker = new Caretaker();
    Originator originator = new Originator();
    String path;
    int i = 0;
    String zapis;

    public Model(String path, String datoteka) {
        this.datoteka = datoteka;
        this.path = path;
        radUPozadini(path);
    }

    public void radUPozadini(String path) {
        folder = new Folder(folderNumber, path, new File(path).isDirectory());
        folder.loadFiles(folderNumber++, new File(path), folder);
        originator.setState(folder);
        caretaker.add(originator.saveStateToMemento());
        folder.getFolderContext().setFolderState(new ActiveFolderState());
    }

    public String getDatoteka() {
        return datoteka;
    }

    public void setDatoteka(String datoteka) {
        this.datoteka = datoteka;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public int getDirectoryCount() {
        return folder.getDirectoryCount();
    }

    public int getFileCount() {
        return folder.getFileCount();
    }

    public Integer getFolderNumber() {
        return folderNumber;
    }

    public Folder getFolder() {
        Folder activeFolder = null;
        return activeFolder;
    }

    public Folder getRootFolder() {
        Folder folder2 = this.folder;
        return folder2;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setObservers(List<Observer> observers) {
        this.observers = observers;
    }

    @Override
    public void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update(this);
        }
    }

    public Folder getSpecificFolder(int folderId) {
        Folder specific = getSpecific(this.folder, folderId);
        return specific;
    }

    private Folder getSpecific(Folder getFolder, int folderId) {
        Folder folderResult = null;
        if (getFolder.getId() == folderId) {
            return getFolder;
        } else if (getFolder.getFolderContext().getFolderState() instanceof ActiveFolderState) {
            for (Folder f : getFolder.getChilds()) {
                if (folderResult != null) {
                    break;
                }
                if (f.getId() == folderId) {
                    return f;
                } else {
                    folderResult = getSpecific(f, folderId);
                }
            }
        }
        return folderResult;
    }

    public boolean pokreniDretvu() {
        if (pokrenuta) {
            notifyObservers();
            return true;
        }
        if (getInterval().equals(0)) {
            return false;
        }

        pokrenuta = true;

        dretva = new Thread(new Runnable() {

            @Override
            public void run() {
                notifyObservers();
                while (pokrenuta) {
                    try {
                        long pocetna = System.currentTimeMillis();
                        long trajanje = System.currentTimeMillis() - pocetna;
                        Thread.sleep(getInterval() * 1000 - trajanje);

                        provjeriStanje();
                    } catch (InterruptedException e) {
                        pokrenuta = false;
                        System.out.println("Pogreska kod spavanja dretve! " + e);
                    }
                }
            }

            private void provjeriStanje() {

                radUPozadini(path);
                i++;

                caretaker.get(i).getState().getVrijeme();
                
                if (caretaker.get(0).getState().getFileCount() != caretaker.get(i).getState().getFileCount()
                        || caretaker.get(0).getState().getDirectoryCount() != caretaker.get(i).getState().getDirectoryCount()
                        || caretaker.get(0).getState().getSize() != caretaker.get(i).getState().getSize()) {
                    if (caretaker.get(0).getState().getFileCount() != caretaker.get(i).getState().getFileCount()) {
                        System.out.println(caretaker.get(i).getState().getVrijeme() + " Promjena! " + "Putanja: " + caretaker.get(0).getState().getPath()
                                + " Različit broj file-ova, novi broj: " + caretaker.get(i).getState().getFileCount());
                        zapis = caretaker.get(i).getState().getVrijeme() + " Promjena! " + "Putanja: " + caretaker.get(0).getState().getPath()
                                + " Različit broj file-ova, novi broj: " + caretaker.get(i).getState().getFileCount();
                    }
                    if (caretaker.get(0).getState().getDirectoryCount() != caretaker.get(i).getState().getDirectoryCount()) {
                        System.out.println(caretaker.get(i).getState().getVrijeme() + " Promjena! " + "Putanja: " + caretaker.get(0).getState().getPath()
                                + " Različit broj direktorija, novi broj: " + caretaker.get(i).getState().getDirectoryCount());
                        zapis = caretaker.get(i).getState().getVrijeme() + " Promjena! " + "Putanja: " + caretaker.get(0).getState().getPath()
                                + " Različit broj direktorija, novi broj: " + caretaker.get(i).getState().getDirectoryCount();
                    }
                    if (caretaker.get(0).getState().getSize() != caretaker.get(i).getState().getSize()) {
                        System.out.println(caretaker.get(i).getState().getVrijeme() + " Promjena! " + "Putanja: " + caretaker.get(0).getState().getPath()
                                + " Različita veličina direktorija, nova veličina: " + caretaker.get(i).getState().getSize() + " bytes");
                        zapis = caretaker.get(i).getState().getVrijeme() + " Promjena! " + "Putanja: " + caretaker.get(0).getState().getPath() 
                                + " Različita veličina direktorija, nova veličina: " + caretaker.get(i).getState().getSize() + " bytes";
                    }
                } else {
                    System.out.println("Nema promjene! " + caretaker.get(i).getState().getVrijeme());
                    zapis = "Nema promjene! " + caretaker.get(i).getState().getVrijeme();
                }

                if(datoteka.length() !=0){
                    zapisiUDatoteku(zapis);
                }
            }

        });

        dretva.start();

        return true;
    }

    public void dajSvePromjene() {
        for (int k = 0; k < i+1; k++) {
            System.out.println((k) + " - " + caretaker.get(k).getState().getVrijeme());
        }
    }

    public void zapisiUDatoteku(String zapis) {
        Writer writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(datoteka + ".txt", true), "utf-8"));
            writer.write(zapis);
            writer.append(System.lineSeparator());
        } catch (IOException ex) {
            System.out.println("Error");
        } finally {
            try {
                writer.close();
            } catch (Exception ex) {
            }
        }

    }

    public void dajPromjenu(int p) {
        System.out.println((p) + " - " + caretaker.get(p).getState().getVrijeme());
    }

    public boolean zaustaviDretvu() {
        if (pokrenuta) {
            pokrenuta = false;
            return true;
        } else {
            return false;
        }
    }

    public String pobrojiVrsteDokumenata() {
        tekst = 0;
        slike = 0;
        video = 0;
        others = 0;

        documentHandler = new VideoHandler(new ImageHandler(new TextHandler(new OtherHandler(null))));
        documentHandler.handleRequest("");

        File[] files = new File(getRootFolder().getPath()).listFiles();
        showFiles(files);

        return "Tekstualnih datoteka: " + tekst + "\nSlika: " + slike + "\nVidea: " + video + "\nOstalog: " + others;

    }

    public static void showFiles(File[] files) {
        for (File file : files) {
            if (file.isDirectory()) {
                showFiles(file.listFiles());
            } else {
                documentHandler.handleRequest(file.getName());
            }
        }
    }

}
