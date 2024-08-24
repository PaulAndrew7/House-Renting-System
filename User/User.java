package User;

import java.io.*;
import java.util.Scanner;

public class User {
    private String username;
    private String password;
    private String email;
    private Scanner sc;
    private static final String FILE_PATH = "user_credentials.txt";

   
    public User() {
        sc = new Scanner(System.in);
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(email + "," + username + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean signin() {
        System.out.println("Enter your email ID: ");
        String inputEmail = sc.nextLine();
        System.out.println("Enter Username: ");
        String inputUsername = sc.nextLine();
        System.out.println("Enter Password: ");
        String inputPassword = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(inputEmail) && parts[1].equals(inputUsername) && parts[2].equals(inputPassword)) {
                  setEmail(inputEmail);
                  setUsername(inputUsername);
                  setPassword(inputPassword);
                    return true; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; 
    }
  public void signup() {
        System.out.println("Enter your email ID: ");
        setEmail(sc.nextLine());
        System.out.println("Enter new Username: ");
        setUsername(sc.nextLine());
        System.out.println("Enter new Password: ");
        setPassword(sc.nextLine());
        saveToFile();
    }
}
