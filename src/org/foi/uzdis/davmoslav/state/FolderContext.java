/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.state;

import org.foi.uzdis.davmoslav.types.Folder;

/**
 *
 * @author Davor
 */
public class FolderContext implements State {

    private State folderState;
    
    public FolderContext() {
        this.folderState = new EmptyFolderState();
    }

    public State getFolderState() {
        return folderState;
    }

    public void setFolderState(State folderState) {
        this.folderState = folderState;
    }
    
    @Override
    public String statistics(Folder link) {
        return this.folderState.statistics(link);
    }
    
     @Override
    public String log(Folder link) {
        return this.folderState.log(link);
    }
}
