/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.state;

import java.text.SimpleDateFormat;
import org.foi.uzdis.davmoslav.types.Folder;

/**
 *
 * @author Denis
 */
public class ActiveFolderState implements State {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    @Override
    public String statistics(Folder folder) {
        String print = folder.getId() + ". " + folder.getPath();
        return print;
    }

    @Override
    public String log(Folder folder) {
        String print = folder.getId() + ". " + folder.getPath()+ 

                "\n\tDatum zadnjeg učitavanja: " + dateFormat.format(folder.getVrijeme()) + "\n";
        return print;
    }
    
}
