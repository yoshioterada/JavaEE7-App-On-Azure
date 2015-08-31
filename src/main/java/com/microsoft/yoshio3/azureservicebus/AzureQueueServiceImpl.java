/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.microsoft.yoshio3.azureservicebus;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;
import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.servicebus.models.CreateQueueResult;
import com.microsoft.windowsazure.services.servicebus.models.QueueInfo;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tyoshio2002
 */
public class AzureQueueServiceImpl {
    
    private final static String QUEUE_NAME = "TestQueue2";
    
    private ServiceBusContract service;
    
    public static void main(String... argv) {
        AzureQueueServiceImpl queueService = new AzureQueueServiceImpl();
//        queueService.createQueue();
        for (int i = 0; i < 100; i++) {
            queueService.sendMessage("TestQueue2", "こんにちは : " + Integer.toString(i));
        }
    }
    
    public AzureQueueServiceImpl() {
        Configuration config
                = ServiceBusConfiguration.configureWithSASAuthentication(
                        "yoshio3-queue",
                        "RootManageSharedAccessKey",
                        "********************************************",
                        ".servicebus.windows.net"
                );
        service = ServiceBusService.create(config);
    }
    
    public void createQueue() {
        long maxSizeInMegabytes = 5120;
        QueueInfo queueInfo = new QueueInfo("TestQueue2");
        queueInfo.setMaxSizeInMegabytes(maxSizeInMegabytes);
        try {
            CreateQueueResult result = service.createQueue(queueInfo);
        } catch (ServiceException e) {
            System.out.print("ServiceException encountered: ");
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    
    public void sendMessage(String queueName, String messages) {
        try {
            BrokeredMessage message = new BrokeredMessage(messages);
            service.sendQueueMessage(queueName, message);
        } catch (ServiceException ex) {
            Logger.getLogger(AzureQueueServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
