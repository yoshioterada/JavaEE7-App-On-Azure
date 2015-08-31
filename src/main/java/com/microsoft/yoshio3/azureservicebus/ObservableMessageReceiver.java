/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azureservicebus;

import java.util.Observable;

/**
 *
 * @author tyoshio2002
 */
public class ObservableMessageReceiver extends Observable {

    /**
     * 観察者に通知します。
     *
     * @param オブジェクト
     */
    public void notifyObservers(Object arg) {

        setChanged();

        // 通知
        super.notifyObservers();
        clearChanged();

    }
}
