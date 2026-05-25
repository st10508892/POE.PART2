package com.mycompany.part1poe;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Part1POE {
    //Declare usernames and passwords as arrays
        
    //Arrays to store multiple users with specfic size
    
    

    //SOURCE:https://youtu.be/jK6NX9iyi-8?si=Yzzzy7WSkyr78tiA
    // DECLARATIONS
    private String[]username = new String[10];
    private String[] password = new String[10];
    private String[] cellPhoneNumber = new String[10];
    private int userCount = 0;
    
    //Boolean validations
    private boolean isRunning = true;
    private boolean isRegistered = false;
    private boolean isLoggedin = false;
    
    public static void main(String[] args){
        LoginSystem loginSystem = new LoginSystem();
        Scanner scanner = new Scanner(System.in);
        
        int choice;
        // SOURCE:https://claude.com/product/claude-code
        while (loginSystem.isRunning){
            // display menue and get choice
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.println("Enter your choice: ");
            
              
           choice = scanner.nextInt();
            scanner.nextLine();
        
        if (choice == 1){
            loginSystem.register(scanner);
        } else if (choice == 2) {
             loginSystem.login(scanner);
             
        } else if (choice == 3) {
              System.out.println("Exiting...");
              loginSystem.isRunning = false; // break
            
              
    } else {
      System.out.println("Invalid choice . Try again!");
    }
  }
          scanner.close();
}

  
    
   // SOURCE:https://claude.com/product/claude-code
    public void Register (Scanner scanner){     
    
    if (userCount >= username.length) {
        System.out.println("User limit reached.Cannot register more");
        return;
    }

    System.out.println("Enter username(including underscore and <= 5 chars): ");
    String tempUsername = scanner.nextLine();

    boolean validUsername = checkUsername(tempUsername);
    if (validUsername){
        System.out.println("Username successfully captured!");
    } else {
        System.out.println("Username is not correctly formatted, please ensure your username contains a underscore and is no more than 5 characters.");
        return;
    }
    System.out.println("Enter password(with atleast 8 characters, 1 CAPITAL, 1 number, 1 special character):");
    String tempPassword = scanner.nextLine();
    
    //SOURCE:https://youtu.be/Jlv_fdB4cTY?si=D14lOWCg_Bb_li-x
    boolean validPassword = checkPasswordComplexity(tempPassword);
    if (validPassword){
        System.out.println("Password successfully captured.");
    } else {
        System.out.println("Password is not correctly formatted, please ensure your password contains atleast 8 characters, a capital leter, a number, and a special character.");
        return;
    }
    
    System.out.println("Enter South African cell phone number( e.g., +27712145784):");
String tempCell = scanner.nextLine();

boolean validCell = checkCellPhoneNumber(tempCell);
if (validCell){
    System.out.println("Cell phone number successfully added.");
} else {
    System.out.println("Cell phone is incorrectly formatted or does not contain international code.");
    return;
}

//Store in arrays using loop
//SOURCE:https://youtu.be/xzjZy-dHHLw?si=EC24phx0xS9Q0rwX
username[userCount] = tempUsername;
password[userCount] = tempPassword;
cellPhoneNumber[userCount] = tempCell;
userCount++;

//boolean
isRegistered = true;

System.out.println("Registration successful!");
}

public void loginUser(Scanner scanner) {
    if(isRegistered){
        System.out.println("User is not redgistered, please register first.");
        return;
    }
    
    System.out.println("Enter username:");
    String loginUsername = scanner.nextLine();
    System.out.println("Enter password:");
    String loginPassword = scanner.nextLine();
    
    boolean loginSuccessful = false;
    
    // loop
    int i = 0;
    while(i < userCount){
        if (username[i].equals(username)&& password[i].equals(password)){
            loginSuccessful = true;
            isLoggedin = true; // boolean
            break;
        }
        i++;
    }
    
    if (loginSuccessful){
        System.out.println("Welcome back.");
    } else {
        System.out.println("Username or Password entered incorrectly, please try again.");
        isLoggedin = false;
            
        }
}

public boolean checkUsername(String username){
    return username.contains("_")&& username.length() <= 5;
}

public boolean checkPasswordComplexity(String password){
    String pattern="^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$";
    return Pattern.matches(pattern, password);
}

public boolean checkCellPhoneNumber(String cellPhoneNumber){
    String pattern = "^\\+27\\d{9}$"; //+27 followed by 9 digits
    return Pattern.matches(pattern, cellPhoneNumber);
}

// check arrays
public boolean loginUser(String[]username, String[] password, int index){
    return username[index].equals(username) && password[index].equals(password);
   }
}
  

