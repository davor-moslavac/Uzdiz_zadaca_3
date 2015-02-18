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
public class TextHandler implements Handler {

    private Handler successor;

    public TextHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    public void handleRequest(String imeDatoteke) {
        if (imeDatoteke.toLowerCase().endsWith(".doc") || imeDatoteke.toLowerCase().endsWith(".docx") || imeDatoteke.toLowerCase().endsWith(".txt")) {
            Model.tekst++;
        } else {
            successor.handleRequest(imeDatoteke);
        }
    }

}