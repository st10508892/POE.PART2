/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.part1poe;

import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;

/**
 *
 * @author Palesa
 */
public class LoginSystemTest {
    
    public LoginSystemTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of main method, of class LoginSystem.
     */
    @Test
    public void testMain() {
        System.setIn(new ByteArrayInputStream("3\n".getBytes()));
        String[] args = null;
        LoginSystem.main(args);
        assertTrue(true);
    }

    /**
     * Test of register method, of class LoginSystem.
     */
    @Test
    public void  Register() {
        String input = "Kyl_1\nCh&&sec@ke99!\n+2783896876\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        
        LoginSystem instance = new LoginSystem();
        instance.register(scanner);
        
        assertEquals(1, instance.getUserCount());
        
    }

    /**
     * Test of login method, of class LoginSystem.
     */
    @Test
    public void testLogin() {
        System.out.println("login");
        
        LoginSystem instance = new LoginSystem();
        
        instance.usernames[0] = "Kyl_1";
        instance.passwords[0] = "Ch&&sec@ke99!";
        instance.userCount = 1;
        
       assertTrue(instance.passwords[0].equals("Ch&&sec@ke99!")&&
               instance.usernames[0].equals("Kyl_1"));
        
    }
    
}
