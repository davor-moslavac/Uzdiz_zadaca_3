/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.memento;

import java.util.List;
import org.foi.uzdis.davmoslav.types.Folder;

/**
 *
 * @author Moki
 */
public class Originator {

    private Folder state;
    private List<Folder> states;

    public Folder getState() {
        return state;
    }

    public void setState(Folder state) {
        this.state = state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public List<Folder> getStateFromMemento(Memento Memento) {
        states = Memento.getState().getChilds();
        //System.out.println(""+state);
        return states;
    }
}
