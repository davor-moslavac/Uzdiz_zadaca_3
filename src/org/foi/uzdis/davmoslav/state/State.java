/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.state;

import org.foi.uzdis.davmoslav.types.Folder;

/**
 *
 * @author Moki
 */
public interface State {
    public String statistics(Folder folder);
    public String log(Folder folder);
}
