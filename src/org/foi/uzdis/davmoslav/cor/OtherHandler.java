/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.uzdis.davmoslav.cor;

import org.foi.uzdis.davmoslav.mvc.Model;

/**
 *
 * @author Moki
 */
public class OtherHandler implements Handler {

    private Handler successor;

    public OtherHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    public void handleRequest(String imeDatoteke) {
        if (imeDatoteke == null || !imeDatoteke.equals("")) {
            Model.others++;
        }
    }
}
