/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3;

import com.microsoft.yoshio3.azureservicebus.MessageReceiver;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 *
 * @author tyoshio2002
 */
@RequestScoped
@Named(value = "indexPage")
public class IndexView {

    @EJB
    MessageReceiver receiver;

    public void execQueueStartButton() {
        receiver.start();
    }

    public void execQueueStopButton() {
        receiver.destroy();
        System.out.println("BUTTON pushed.");
    }

}
