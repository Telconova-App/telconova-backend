package com.telconovaP7F22025.demo.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Small utility to print a BCrypt hash of a given plaintext.
 * Run with Maven: mvn -DskipTests exec:java -Dexec.mainClass=com.telconovaP7F22025.demo.util.PasswordHashGenerator
 */
public class PasswordHashGenerator {
    public static void main(String[] args) {
        String plain = "secret";
        if (args != null && args.length > 0) {
            plain = args[0];
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(plain));
    }
}
