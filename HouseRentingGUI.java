import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class HouseRentingGUI extends JFrame {
    private JTextField emailField, usernameField;
    private JPasswordField passwordField;
    private JButton signupButton, signinButton;

    private static final String FILE_PATH = "user_credentials.txt";

    public HouseRentingGUI() {
        // Set up the JFrame
        super("House Renting Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null); // Center the frame on the screen

        // Create components
        emailField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        signupButton = new JButton("Sign Up");
        signinButton = new JButton("Sign In");

        // Set layout
        setLayout(new GridLayout(4, 2));

        // Add components to the frame
        add(new JLabel("Email: "));
        add(emailField);
        add(new JLabel("Username: "));
        add(usernameField);
        add(new JLabel("Password: "));
        add(passwordField);
        add(signupButton);
        add(signinButton);

        // Attach event listeners
        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signup();
            }
        });

        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (signin()) {
                    JOptionPane.showMessageDialog(HouseRentingGUI.this, "Sign-in successful. Welcome!");
                    // Add further logic for signed-in user
                } else {
                    JOptionPane.showMessageDialog(HouseRentingGUI.this, "Sign-in failed. Please check your credentials.");
                }
            }
        });
    }

    private void signup() {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Save new user information to the file
        saveToFile(email, username, password);
    }

    private boolean signin() {
        String inputEmail = emailField.getText();
        String inputUsername = usernameField.getText();
        String inputPassword = new String(passwordField.getPassword());

        try {
            java.util.Scanner scanner = new java.util.Scanner(new java.io.File(FILE_PATH));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(inputEmail) && parts[1].equals(inputUsername) && parts[2].equals(inputPassword)) {
                    return true; // Credentials match
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // No matching credentials found
    }

    private void saveToFile(String email, String username, String password) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            writer.println(email + "," + username + "," + password);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HouseRentingGUI().setVisible(true);
            }
        });
    }
}
