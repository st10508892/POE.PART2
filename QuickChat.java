package com.mycompany.quickchat;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuickChat {
    
    public static void main(String[] args) { 
        Scanner sc = new Scanner(System.in);
        Random rand = new Random();
        System.out.println("====================");
        System.out.println("Welcome to QuickChat");
        System.out.println("====================");
        
        // Login
        boolean loggedIn = false;
        System.out.print("Enter Username: ");
        String user = sc.nextLine();
        System.out.print("Enter password: ");
        String pass = sc.nextLine();
        
        if(!user.equals("") && !pass.equals("")){
            loggedIn = true;
            System.out.println("Login successful!");
        } else {
            System.out.println("Username or Password cannot be empty!");
        }
        
        if(!loggedIn){
            System.out.println("Login failed. Exiting program");
            sc.close();
            return;
        }
        
        System.out.println("\nHow many messages are to be entered:");
        int maxMessages = sc.nextInt();
        sc.nextLine();
        
        // Arrays to store messages
        String[] messageID = new String[maxMessages];
        String[] messageText = new String[maxMessages];
        String[] messageHash = new String[maxMessages];
        String[] recipient = new String[maxMessages];
        boolean[] isSent = new boolean[maxMessages];
        
        int totalSent = 0;
        int messageCounter = 1;
        
        // Main menu loop
        int choice = 0;
        do {
            System.out.println("\n---- MENU ----");
            System.out.println("1) Send messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.print("Enter your choice: ");
            
            choice = sc.nextInt();
            sc.nextLine();
            
            if(choice == 1) {
                for(int i = totalSent; i < maxMessages; i++) {
                    if(messageCounter > maxMessages) {
                        break;
                    }
                    
                    System.out.println("\n---- Message " + messageCounter + " ----");
                    
                    // Generate unique message ID
                    long id = 1000000L + (long)(rand.nextDouble() * 9000000L);
                    messageID[i] = String.valueOf(id);
                    
                    // Validate recipient cell number
                    boolean validCell = false;
                    while (!validCell){
                        System.out.print("Enter recipient cell number: ");
                        recipient[i] = sc.nextLine();
                        
                        if(recipient[i].length() <= 10 && recipient[i].startsWith("+")){
                            validCell = true;
                        } else {
                            System.out.println("Invalid number. Must start with + and max 10 characters.");
                        }
                    }
                    
                    // Validate message text
                    boolean validMsg = false;
                    while (!validMsg){
                        System.out.print("Enter message (max 250 chars): ");
                        messageText[i] = sc.nextLine();
                        
                        if(messageText[i].length() <= 250 && messageText[i].length() > 0){
                            validMsg = true;
                        } else if(messageText[i].length() == 0) {
                            System.out.println("Message cannot be empty!");
                        } else {
                            System.out.println("Please enter a message of less than 250 characters.");
                        }
                    }
                    
                    // Generate message hash
                    String[] words = messageText[i].split(" ");
                    String firstWord = words[0];
                    String lastWord = words[words.length - 1];
                    messageHash[i] = messageID[i].substring(0, 2) + ":" + messageCounter + ":" + 
                                    firstWord.toUpperCase() + "+" + lastWord.toUpperCase();
                    
                    // Action menu
                    System.out.println("\n1) Send Message");
                    System.out.println("2) Disregard message");
                    System.out.println("3) Store Message to send later");
                    System.out.print("Choose an option: ");
                    
                    int action = sc.nextInt();
                    sc.nextLine();
                    
                    if(action == 1) {
                        isSent[i] = true;
                        totalSent++;
                        
                        System.out.println("\n✓ Message successfully sent!");
                        System.out.println("\n---- Message Details ----");
                        System.out.println("Message ID: " + messageID[i]);
                        System.out.println("Message Hash: " + messageHash[i]);
                        System.out.println("Recipient: " + recipient[i]);
                        System.out.println("Message: " + messageText[i]);
                        System.out.println("------------------------");
                        
                    } else if(action == 2) {
                        System.out.println("✗ Message disregarded.");
                        isSent[i] = false;
                        
                    } else if(action == 3) {
                        isSent[i] = false;
                        saveToJSON(messageID[i], messageHash[i], recipient[i], messageText[i], messageCounter);
                        System.out.println("✓ Message stored to JSON file.");
                        
                    } else {
                        System.out.println("Invalid option. Message not processed.");
                    }
                    
                    messageCounter++;
                }
                System.out.println("\n📊 Total messages sent: " + totalSent);
                
            } else if(choice == 2) {
                System.out.println("\n---- Sent Messages ----");
                
                boolean found = false;
                for(int i = 0; i < maxMessages; i++){
                    if(isSent[i] == true){
                        System.out.println("ID: " + messageID[i] + " | To: " + recipient[i] + " | Msg: " + messageText[i]);
                        found = true;
                    }
                }
                
                if(!found){
                    System.out.println("No messages sent yet.");
                }
                System.out.println("----------------------");
                
            } else if(choice == 3) {
                System.out.println("\nExiting QuickChat. Goodbye! 👋");
                
            } else {
                System.out.println("Invalid option. Please try again.");
            }
            
        } while(choice != 3);
        
        sc.close();
    }
    
    // Method to save messages to JSON file
    private static void saveToJSON(String messageID, String messageHash, String recipient, String messageText, int messageCounter) {
        try {
            JSONObject message = new JSONObject();
            message.put("messageID", messageID);
            message.put("messageHash", messageHash);
            message.put("recipient", recipient);
            message.put("messageText", messageText);
            message.put("messageNumber", messageCounter);
            message.put("timestamp", System.currentTimeMillis());
            
            // Read existing file or create new array
            JSONArray messagesArray = new JSONArray();
            try {
                String content = new String(Files.readAllBytes(Paths.get("messages.json")));
                messagesArray = new JSONArray(content);
            } catch (IOException e) {
                // File doesn't exist yet, will create new array
                System.out.println("Creating new messages.json file...");
            }
            
            // Add new message to array
            messagesArray.put(message);
            
            // Write back to file
            try (FileWriter file = new FileWriter("messages.json")) {
                file.write(messagesArray.toString(4)); // Pretty print with 4 spaces
                System.out.println("✅ Message saved to messages.json");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Error saving to JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}