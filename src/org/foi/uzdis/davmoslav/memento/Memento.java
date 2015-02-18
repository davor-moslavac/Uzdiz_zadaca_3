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
public class Memento {

    private Folder state;

    public Memento(Folder state) {
        this.state = state;
    }

    public Folder getState() {
        return state;
    }

}
