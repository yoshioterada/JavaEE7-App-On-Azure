package com.microsoft.yoshio3.azureservicebus;

import com.microsoft.windowsazure.Configuration;
import com.microsoft.windowsazure.exception.ServiceException;
import com.microsoft.windowsazure.services.servicebus.ServiceBusConfiguration;
import com.microsoft.windowsazure.services.servicebus.ServiceBusContract;
import com.microsoft.windowsazure.services.servicebus.ServiceBusService;
import com.microsoft.windowsazure.services.servicebus.models.BrokeredMessage;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMessageOptions;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveMode;
import com.microsoft.windowsazure.services.servicebus.models.ReceiveQueueMessageResult;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.enterprise.concurrent.ManagedExecutorService;

@Singleton
public class MessageReceiver {

    private ServiceBusContract service;

    @Resource
    ManagedExecutorService exec;

    WatchQueueService queueService;

    // JMS のように onMessage() のイベントで取れるようになると嬉しい。
    // https://java.net/projects/mq/sources/mq-hg/show/mq/src/share/java/com/sun/messaging/jmq/jmsclient?rev=23
    private void startMonitoring() {
//    public static void main(String... argv) {
        Configuration config
                = ServiceBusConfiguration.configureWithSASAuthentication(
                        "yoshio3-queue",
                        "RootManageSharedAccessKey",
                        "wG7YotHIju+K6cfLhX9ydCFvqLec6p8TSqAAIfw1uY8=",
                        ".servicebus.windows.net"
                );
        service = ServiceBusService.create(config);
        queueService = new WatchQueueService(service);
        exec.submit(queueService);
    }

    public void start() {
        startMonitoring();
    }

    public void destroy() {
        queueService.stopService();
        System.out.println("DESTOROY was called.");
    }
}

class WatchQueueService implements Runnable {

    private final static String QUEUE_NAME = "TestQueue2";

    private volatile boolean runFlag = true;
    ServiceBusContract service;

    WatchQueueService(ServiceBusContract service) {
        this.service = service;
    }

    void stopService() {
        runFlag = false;
    }

    @Override
    public void run() {
        try {
            ReceiveMessageOptions opts = ReceiveMessageOptions.DEFAULT;
            opts.setReceiveMode(ReceiveMode.PEEK_LOCK);
            // Service Bus の API がいけてない。
            while (runFlag) {
                ReceiveQueueMessageResult resultQM
                        = service.receiveQueueMessage(QUEUE_NAME, opts);
                BrokeredMessage message = resultQM.getValue();

                if (message != null && message.getMessageId() != null) {
                    // Display the queue message.
                    System.out.print("From queue: ");

                    StringBuilder inputStringBuilder = new StringBuilder();
                    try (BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(message.getBody(), "UTF-8"))) {
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            System.out.println(line);
                        }
                    }
                } else {
                    System.out.println("no more messages.");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(MessageReceiver.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (ServiceException | IOException e) {
            System.out.print("ServiceException encountered: ");
            System.out.println(e.getMessage());
        }
    }
}
