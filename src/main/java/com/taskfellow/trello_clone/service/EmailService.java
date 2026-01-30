package com.taskfellow.trello_clone.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Async // <--- This runs in a separate thread!
    public void sendEmail(String to, String subject, String body) {
        try {
            // Simulate a slow network call (5 seconds delay)
            System.out.println("... Connecting to Email Server ...");
            Thread.sleep(5000);

            System.out.println("EMAILING TO: " + to);
            System.out.println("SUBJECT: " + subject);
            System.out.println("BODY: " + body);
            System.out.println("--------------------------------");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}