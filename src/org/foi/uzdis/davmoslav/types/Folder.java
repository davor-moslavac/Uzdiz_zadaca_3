/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.types;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.foi.uzdis.davmoslav.state.ActiveFolderState;
import org.foi.uzdis.davmoslav.state.FolderContext;

/**
 *
 * @author Davor
 */
public class Folder {

    int id;
    String path;
    int contentChanges = 0;
    List<Folder> childs = null;
    FolderContext folderContext;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    boolean isDirectory = false;
    int directoryCount;
    int fileCount;
    String vrijeme;
    long size;
    Date d;

    public Folder(int id, String path, boolean isDirectory) {
        this.id = id;
        this.path = path;
        this.folderContext = new FolderContext();
        childs = new ArrayList<>();
        this.isDirectory = isDirectory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getContentChanges() {
        return contentChanges;
    }

    public void setContentChanges(int contentChanges) {
        this.contentChanges = contentChanges;
    }

    public List<Folder> getChilds() {
        return childs;
    }

    public void setChilds(List<Folder> childs) {
        this.childs = childs;
    }

    public FolderContext getFolderContext() {
        return folderContext;
    }

    public void setFolderContext(FolderContext folderContext) {
        this.folderContext = folderContext;
    }

    public String getVrijeme() {
        vrijeme = sdf.format(d);
        return vrijeme;
    }

    public void setVrijeme(String vrijeme) {
        this.vrijeme = vrijeme;
    }

    public boolean isIsDirectory() {
        return isDirectory;
    }

    public long getSize() {
        size = 0;
        size = getFileFolderSize(new File(path));
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getDirectoryCount() {
        directoryCount = 0;
        directoryCount = countDirectories(childs, directoryCount);
        return directoryCount;
    }

    public int getFileCount() {
        fileCount = 0;
        fileCount = countFiles(childs, fileCount);
        return fileCount;
    }

    public void setIsDirectory(boolean isDirectory) {
        this.isDirectory = isDirectory;
    }

    public void print(String indent) {
        File aFile = new File(path);
        if (folderContext.getFolderState() instanceof ActiveFolderState) {
            System.out.println(indent + aFile.getName() + " - AKTIVNI DIREKTORIJ");
        } else {
            System.out.println(indent + "|_" + aFile.getName());
        }

        for (Folder f : childs) {
            f.print(indent + "  ");
        }
    }

    public static long getFileFolderSize(File dir) {
        long size = 0;
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += getFileFolderSize(file);
                }
            }
        } else if (dir.isFile()) {
            size += dir.length();
        }
        return size;
    }

    public void setDirectoryCount(int directoryCount) {
        this.directoryCount = directoryCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public int countDirectories(List<Folder> ch, int val) {
        for (Folder f : ch) {
            if (f.isDirectory) {
                val++;
                val = f.countDirectories(f.childs, val);
            }
        }
        return val;
    }

    public int countFiles(List<Folder> ch, int val) {
        for (Folder f : ch) {
            if (f.isDirectory) {
                val = f.countFiles(f.childs, val);
            } else {
                val++;
            }
        }
        return val;
    }

    public Folder loadFiles(int increment, File aFile, Folder f) {
        d = new Date();
        File[] listOfFiles = aFile.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                String fileName = "";
                if (file.isFile()) {
                    fileName = "[FILE] " + file.getName();
                } else if (file.isDirectory()) {
                    fileName = "[DIR] " + file.getName();
                }
                if (file.isDirectory()) {
                    f.childs.add(loadFiles(increment, file, new Folder(increment++, fileName, true)));
                } else {
                    f.childs.add(new Folder(increment++, fileName, false));
                }

            }
        }
        return f;
    }
}
