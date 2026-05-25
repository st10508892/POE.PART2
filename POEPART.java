
package com.mycompany.poepart;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

public class POEPART {

    // Arrays to store users
    private String[] username = new String[10];
    private String[] password = new String[10];
    private String[] cellPhoneNumber = new String[10];
    private int userCount = 0;

    // Login state
    private boolean isRunning = true;
    private boolean isLoggedIn = false;

    public static void main(String[] args) {

        POEPART system = new POEPART();
        Scanner scanner = new Scanner(System.in);

        while (system.isRunning) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            if (scanner.hasNextInt()) {

                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {

                    case 1:
                        system.register(scanner);
                        break;

                    case 2:
                        system.loginUser(scanner);

                        // Open QuickChat after successful login
                        if (system.isLoggedIn) {
                            system.openQuickChat(scanner);
                        }

                        break;

                    case 3:
                        System.out.println("Exiting program...");
                        system.isRunning = false;
                        break;

                    default:
                        System.out.println("Invalid choice!");
                }

            } else {

                System.out.println("Please enter a valid number.");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    // ================= REGISTER =================

    public void register(Scanner scanner) {

        if (userCount >= username.length) {
            System.out.println("User limit reached.");
            return;
        }

        System.out.print("Enter username (must contain _ and max 5 chars): ");
        String tempUsername = scanner.nextLine().trim();

        if (!checkUsername(tempUsername)) {

            System.out.println("Username is not correctly formatted.");
            return;
        }

        // Check duplicate usernames
        for (int i = 0; i < userCount; i++) {

            if (username[i].equalsIgnoreCase(tempUsername)) {

                System.out.println("Username already exists.");
                return;
            }
        }

        System.out.print("Enter password: ");
        String tempPassword = scanner.nextLine();

        if (!checkPasswordComplexity(tempPassword)) {

            System.out.println("Password is not correctly formatted.");
            return;
        }

        System.out.print("Enter SA phone number (+27xxxxxxxxx): ");
        String tempCell = scanner.nextLine().trim();

        if (!checkCellPhoneNumber(tempCell)) {

            System.out.println("Invalid cell phone number.");
            return;
        }

        // Store data
        username[userCount] = tempUsername;
        password[userCount] = tempPassword;
        cellPhoneNumber[userCount] = tempCell;

        userCount++;

        System.out.println("Registration successful!");
    }

    // ================= LOGIN =================

    public void loginUser(Scanner scanner) {

        if (userCount == 0) {

            System.out.println("No users registered.");
            return;
        }

        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine().trim();

        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();

        boolean success = false;

        for (int i = 0; i < userCount; i++) {

            if (username[i].equals(loginUsername)
                    && password[i].equals(loginPassword)) {

                success = true;
                isLoggedIn = true;

                System.out.println("\nWelcome back " + loginUsername + "!");
                System.out.println("Login successful!");

                break;
            }
        }

        if (!success) {

            System.out.println("Username or password incorrect.");
            isLoggedIn = false;
        }
    }

    // ================= QUICKCHAT =================

    public void openQuickChat(Scanner sc) {

        Random rand = new Random();

        System.out.println("\n====================");
        System.out.println("Welcome to QuickChat");
        System.out.println("====================");

        System.out.print("How many messages would you like to enter: ");
        int maxMessages = sc.nextInt();
        sc.nextLine();

        // Arrays for messages
        String[] messageID = new String[maxMessages];
        String[] messageText = new String[maxMessages];
        String[] messageHash = new String[maxMessages];
        String[] recipient = new String[maxMessages];
        boolean[] isSent = new boolean[maxMessages];

        int totalSent = 0;
        int messageCounter = 1;

        int choice = 0;

        do {

            System.out.println("\n===== QUICKCHAT MENU =====");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {

                for (int i = totalSent; i < maxMessages; i++) {

                    System.out.println("\n---- Message " + messageCounter + " ----");

                    // Generate Message ID
                    long id = 1000000L
                            + (long) (rand.nextDouble() * 9000000L);

                    messageID[i] = String.valueOf(id);

                    // Recipient validation
                    boolean validCell = false;

                    while (!validCell) {

                        System.out.print("Enter recipient number: ");
                        recipient[i] = sc.nextLine();

                        if (recipient[i].startsWith("+")
                                && recipient[i].length() <= 13) {

                            validCell = true;

                        } else {

                            System.out.println("Invalid number.");
                        }
                    }

                    // Message validation
                    boolean validMessage = false;

                    while (!validMessage) {

                        System.out.print("Enter message (max 250 chars): ");
                        messageText[i] = sc.nextLine();

                        if (messageText[i].length() > 0
                                && messageText[i].length() <= 250) {

                            validMessage = true;

                        } else {

                            System.out.println("Invalid message.");
                        }
                    }

                    // Generate Message Hash
                    String[] words = messageText[i].split(" ");

                    String firstWord = words[0];
                    String lastWord = words[words.length - 1];

                    messageHash[i] =
                            messageID[i].substring(0, 2)
                            + ":" + messageCounter
                            + ":" + firstWord.toUpperCase()
                            + "+" + lastWord.toUpperCase();

                    // Action Menu
                    System.out.println("\n1. Send Message");
                    System.out.println("2. Disregard Message");
                    System.out.println("3. Store Message");
                    System.out.print("Choose option: ");

                    int action = sc.nextInt();
                    sc.nextLine();

                    if (action == 1) {

                        isSent[i] = true;
                        totalSent++;

                        System.out.println("\nMessage successfully sent!");

                        System.out.println("Message ID: " + messageID[i]);
                        System.out.println("Message Hash: " + messageHash[i]);
                        System.out.println("Recipient: " + recipient[i]);
                        System.out.println("Message: " + messageText[i]);

                    } else if (action == 2) {

                        System.out.println("Message disregarded.");

                    } else if (action == 3) {

                        saveToJSON(
                                messageID[i],
                                messageHash[i],
                                recipient[i],
                                messageText[i],
                                messageCounter
                        );

                        System.out.println("Message stored to JSON.");

                    } else {

                        System.out.println("Invalid option.");
                    }

                    messageCounter++;
                }

                System.out.println("\nTotal messages sent: " + totalSent);

            } else if (choice == 2) {

                System.out.println("\n===== SENT MESSAGES =====");

                boolean found = false;

                for (int i = 0; i < maxMessages; i++) {

                    if (isSent[i]) {

                        System.out.println(
                                "ID: " + messageID[i]
                                + " | To: " + recipient[i]
                                + " | Message: " + messageText[i]
                        );

                        found = true;
                    }
                }

                if (!found) {

                    System.out.println("No messages sent yet.");
                }

            } else if (choice == 3) {

                System.out.println("Logging out...");
                isLoggedIn = false;

            } else {

                System.out.println("Invalid choice.");
            }

        } while (choice != 3);
    }

    // ================= VALIDATION METHODS =================

    public boolean checkUsername(String username) {

        return username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {

        String pattern =
                "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";

        return Pattern.matches(pattern, password);
    }

    public boolean checkCellPhoneNumber(String cellPhoneNumber) {

        String pattern = "^\\+27[0-9]{9}$";

        return Pattern.matches(pattern, cellPhoneNumber);
    }

    // ================= JSON STORAGE =================

    private static void saveToJSON(
            String messageID,
            String messageHash,
            String recipient,
            String messageText,
            int messageCounter) {

        try {

            JSONObject message = new JSONObject();

            message.put("messageID", messageID);
            message.put("messageHash", messageHash);
            message.put("recipient", recipient);
            message.put("messageText", messageText);
            message.put("messageNumber", messageCounter);

            JSONArray messagesArray = new JSONArray();

            try {

                String content =
                        new String(Files.readAllBytes(Paths.get("messages.json")));

                messagesArray = new JSONArray(content);

            } catch (IOException e) {

                System.out.println("Creating new messages.json file...");
            }

            messagesArray.put(message);

            try (FileWriter file = new FileWriter("messages.json")) {

                file.write(messagesArray.toString(4));

                System.out.println("Message saved successfully.");
            }

        } catch (Exception e) {

            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }
}
