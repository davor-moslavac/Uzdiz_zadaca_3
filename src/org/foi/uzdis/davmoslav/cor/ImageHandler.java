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
public class ImageHandler implements Handler {

    private Handler successor;

    public ImageHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    public void handleRequest(String imeDatoteke) {
        if (imeDatoteke.toLowerCase().endsWith(".jpg") || imeDatoteke.toLowerCase().endsWith(".gif") || imeDatoteke.toLowerCase().endsWith(".png")) {
            Model.slike++;
        } else
            successor.handleRequest(imeDatoteke);
   
    }

}
