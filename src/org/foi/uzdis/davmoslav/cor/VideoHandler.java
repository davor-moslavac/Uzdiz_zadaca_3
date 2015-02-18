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
public class VideoHandler implements Handler {
    private Handler successor;

    public VideoHandler(Handler successor) {
        this.successor = successor;
    }

    @Override
    public void handleRequest(String imeDatoteke) {
        if (imeDatoteke.toLowerCase().endsWith(".mp4") || imeDatoteke.toLowerCase().endsWith(".mkv") || imeDatoteke.toLowerCase().endsWith(".flv") || imeDatoteke.toLowerCase().endsWith(".avi")) {
            Model.video++;
        } else {
            successor.handleRequest(imeDatoteke);
        }
    }
}
