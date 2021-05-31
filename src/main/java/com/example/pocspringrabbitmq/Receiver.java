package com.example.pocspringrabbitmq;

import java.util.Scanner;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.util.StopWatch;

public class Receiver {

    @RabbitListener(queues = {Config.reportQueueName})
    public void receiveReportRequests(String in) throws InterruptedException {
        receive(in);
    }

    @RabbitListener(queues = {Config.vendorQueueName})
    public void receiveVendorRequests(String in) throws InterruptedException {
        receive(in);
    }

    public void receive(String in) throws InterruptedException {
        //StopWatch watch = new StopWatch();
        //watch.start();
        System.out.println("[R] '" + in + "'");
        doWork(in);
        //watch.stop();
        System.out.println("[A] '" + in + "'");
        //		+" in " + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        /*for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(10000);
            }
        }*/
        System.out.println("==Click Return to continue==");
		String nextLine = new Scanner(System.in).nextLine();
    }

}