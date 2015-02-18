/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.state;

import org.foi.uzdis.davmoslav.types.Folder;

/**
 *
 * @author Denis
 */
public class EmptyFolderState implements State {

    @Override
    public String statistics(Folder folder) {
        return "";
    }

    @Override
    public String log(Folder folder) {
        String print = folder.getId() + ". " + folder.getPath()+ "\nDirektorij je prazan - nema datoteka\n";
        return print;
    }
    
}
