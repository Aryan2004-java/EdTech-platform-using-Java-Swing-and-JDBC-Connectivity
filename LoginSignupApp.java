import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.sql.*;
import java.util.ArrayList;

// Main class
public class LoginSignupApp {

    // JDBC driver and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/edtech";
    static final String USER = "root";  // Your MySQL username
    static final String PASS = "Arjun2005";  // Your MySQL password

    // Connection
    private Connection conn = null;
    private int currentUserId = -1;  // Store the ID of the currently logged-in user

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginSignupApp app = new LoginSignupApp();
            app.createGUI();
        });
    }

    public void createGUI() {
        // Initialize connection
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Login Frame
        JFrame loginFrame = new JFrame("Educrow - Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null); // Center the frame

        // Login panel with background image
        BackgroundPanel loginPanel = new BackgroundPanel("C:\\Users\\arjk2\\Downloads\\background.jpg"); // Update with your background image path
        Color textColor = loginPanel.getTextColor();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set margin for components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the west

        // Title Label - Adding the title above Gmail ID
        JLabel titleLabel = new JLabel("Welcome to Educrow");
        titleLabel.setForeground(textColor); // Set text color dynamically
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20)); // Set font style

        // Gmail ID and password labels/fields
        JLabel emailLabel = new JLabel("Gmail ID:");
        emailLabel.setForeground(textColor); // Set text color dynamically
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(textColor); // Set text color dynamically
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        JButton signupButton = new JButton("Sign Up");

        // Add the titleLabel to the layout first
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Span across both columns
        gbc.anchor = GridBagConstraints.CENTER; // Center the title
        loginPanel.add(titleLabel, gbc);

        // Reset gridwidth and anchor for the rest of the components
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Add Gmail ID and password components
        gbc.gridx = 0; gbc.gridy = 1; loginPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; loginPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; loginPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; loginPanel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; loginPanel.add(loginButton, gbc);
        gbc.gridx = 1; gbc.gridy = 3; loginPanel.add(signupButton, gbc);

        // Add panel to frame and make it visible
        loginFrame.add(loginPanel);
        loginFrame.setVisible(true);

        // Action listener for Login button
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            if (validateLogin(email, password)) {
                loginFrame.dispose();
                showExplorePage();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials!");
            }
        });

        // Action listener for Sign Up button
        signupButton.addActionListener(e -> {
            loginFrame.dispose();
            showSignupPage();
        });
    }

    // Method to show the Signup Page
    public void showSignupPage() {
        JFrame signupFrame = new JFrame("Sign Up");
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        signupFrame.setSize(400, 350);
        signupFrame.setLocationRelativeTo(null); // Center the frame

        // Signup panel with background image
        BackgroundPanel signupPanel = new BackgroundPanel("C:\\Users\\arjk2\\Downloads\\background.jpg"); // Update with your background image path
        Color textColor = signupPanel.getTextColor();
        signupPanel.setLayout(new GridBagLayout());
        signupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set margin for components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the west

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(textColor); // Set text color dynamically
        JTextField nameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Gmail ID:");
        emailLabel.setForeground(textColor); // Set text color dynamically
        JTextField emailField = new JTextField(20);
        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setForeground(textColor); // Set text color dynamically
        JPasswordField passwordField = new JPasswordField(20);
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(textColor); // Set text color dynamically
        JPasswordField confirmPasswordField = new JPasswordField(20);
        JButton signupButton = new JButton("Sign Up");

        gbc.gridx = 0; gbc.gridy = 0; signupPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; signupPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; signupPanel.add(emailLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; signupPanel.add(emailField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; signupPanel.add(passwordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; signupPanel.add(passwordField, gbc);
        gbc.gridx = 0; gbc.gridy = 3; signupPanel.add(confirmPasswordLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; signupPanel.add(confirmPasswordField, gbc);
        gbc.gridx = 1; gbc.gridy = 4; signupPanel.add(signupButton, gbc);

        signupFrame.add(signupPanel);
        signupFrame.setVisible(true);

        // Action listener for Sign Up button
        signupButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(signupFrame, "Passwords do not match!");
            } else if (saveUserDetails(name, email, password)) {
                signupFrame.dispose();
                showExplorePage();
            } else {
                JOptionPane.showMessageDialog(signupFrame, "Sign up failed. Try again!");
            }
        });
    }

    public void showExplorePage() {
        JFrame exploreFrame = new JFrame("Explore Courses");
        exploreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        exploreFrame.setSize(500, 400);
        exploreFrame.setLocationRelativeTo(null); // Center the frame

        // Explore panel with background image
        BackgroundPanel explorePanel = new BackgroundPanel("C:\\Users\\arjk2\\Downloads\\background.jpg"); // Update with your background image path
        explorePanel.setLayout(new BoxLayout(explorePanel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        explorePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // Create course panels
        explorePanel.add(createCoursePanel("Course 1: Introduction to AI",
                "In this course, you will learn the fundamentals of Artificial Intelligence, including key concepts like machine learning, neural networks, and natural language processing.",
                "https://people.engr.tamu.edu/guni/csce421/files/AI_Russell_Norvig.pdf",
                "C:\\Users\\arjk2\\Downloads\\the-4-top-artificial-intelligence-trends-for-2021.jpeg"));
        explorePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between courses

        explorePanel.add(createCoursePanel("Course 2: Advanced Java Programming",
                "This course dives deep into advanced Java programming concepts, including multithreading, generics, and design patterns.",
                "https://www.cs.cmu.edu/afs/cs.cmu.edu/user/gchen/www/download/java/LearnJava.pdf",
                "C:\\Users\\arjk2\\Downloads\\java_logo_640.jpg"));
        explorePanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add space between courses

        explorePanel.add(createCoursePanel("Course 3: Web Development with React",
                "In this comprehensive course, you will learn how to build interactive and dynamic web applications using React.",
                "https://legacy.reactjs.org/docs/getting-started.html",
                "C:\\Users\\arjk2\\Downloads\\685-6854994_react-logo-no-background-hd-png-download.png"));

        // Add profile button
        JButton profileButton = new JButton("View Profile");
        profileButton.setPreferredSize(new Dimension(80, 25)); // Set a smaller preferred size
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button horizontally
        profileButton.addActionListener(e -> showProfilePage());

        // Add the profile button after the courses
        explorePanel.add(Box.createVerticalGlue()); // Push the button to the bottom
        explorePanel.add(profileButton); // Add profile button to the explore panel

        exploreFrame.add(new JScrollPane(explorePanel));
        exploreFrame.setVisible(true);
    }

    private JPanel createCoursePanel(String courseName, String description, String link, String imagePath) {
        JPanel coursePanel = new JPanel();
        coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.Y_AXIS));
        coursePanel.setBorder(BorderFactory.createTitledBorder(courseName));
        coursePanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the course panels

        // Load and set the course image
        try {
            BufferedImage courseImage = ImageIO.read(new File(imagePath));
            ImageIcon courseIcon = new ImageIcon(courseImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
            JLabel imageLabel = new JLabel(courseIcon);
            coursePanel.add(imageLabel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JLabel descriptionLabel = new JLabel("<html>" + description + "</html>");
        JButton enrollButton = new JButton("Enroll");
        enrollButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size

        // Create button to open the course link
        JButton linkButton = new JButton("Course Link");
        linkButton.setPreferredSize(new Dimension(150, 30)); // Set preferred size

        enrollButton.addActionListener(e -> {
            if (enrollInCourse(courseName)) {
                JOptionPane.showMessageDialog(coursePanel, "Successfully enrolled in " + courseName);
            } else {
                JOptionPane.showMessageDialog(coursePanel, "You are already enrolled in " + courseName);
            }
        });

        linkButton.addActionListener(e -> {
            try {
                Desktop.getDesktop().browse(new java.net.URI(link));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(coursePanel, "Failed to open link!");
            }
        });

        coursePanel.add(descriptionLabel);
        coursePanel.add(enrollButton);
        coursePanel.add(linkButton); // Add link button to the panel
        return coursePanel;
    }


    // Method to show the Profile Page
    public void showProfilePage() {
        if (currentUserId == -1) return; // Ensure a user is logged in

        JFrame profileFrame = new JFrame("User Profile");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setSize(400, 400);
        profileFrame.setLocationRelativeTo(null); // Center the frame

        JPanel profilePanel = new JPanel();
        profilePanel.setLayout(new GridBagLayout());
        profilePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set margin for components
        gbc.anchor = GridBagConstraints.WEST; // Align components to the west

        // Load and set the profile picture
        JLabel profilePictureLabel = new JLabel();
        try {
            BufferedImage profileImage = ImageIO.read(new File("C:\\Users\\arjk2\\Downloads\\picture-profile-icon-male-icon-human-or-people-sign-and-symbol-free-vector.jpg")); // Update with your profile image path
            ImageIcon profileIcon = new ImageIcon(profileImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            profilePictureLabel.setIcon(profileIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Retrieve user details
        String[] userDetails = getUserDetails(currentUserId);
        JLabel nameLabel = new JLabel("Name: " + userDetails[0]);
        JLabel emailLabel = new JLabel("Email: " + userDetails[1]);

        gbc.gridx = 0; gbc.gridy = 0; profilePanel.add(profilePictureLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; profilePanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; profilePanel.add(emailLabel, gbc);

        // List enrolled courses
        ArrayList<String> enrolledCourses = getEnrolledCourses(currentUserId);
        if (enrolledCourses.isEmpty()) {
            JLabel coursesLabel = new JLabel("No courses enrolled.");
            gbc.gridx = 0; gbc.gridy = 2; profilePanel.add(coursesLabel, gbc);
        } else {
            JLabel coursesLabel = new JLabel("Enrolled Courses:");
            gbc.gridx = 0; gbc.gridy = 2; profilePanel.add(coursesLabel, gbc);
            for (int i = 0; i < enrolledCourses.size(); i++) {
                JLabel courseLabel = new JLabel((i + 1) + ". " + enrolledCourses.get(i));
                gbc.gridx = 0; gbc.gridy = 3 + i; profilePanel.add(courseLabel, gbc);
            }
        }

        profileFrame.add(profilePanel);
        profileFrame.setVisible(true);
    }

    // Method to validate login credentials
    public boolean validateLogin(String email, String password) {
        String query = "SELECT id FROM users WHERE email = ? AND password = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                currentUserId = rs.getInt("id"); // Store the user ID
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to save user details during signup
    public boolean saveUserDetails(String name, String email, String password) {
        String query = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to enroll in a course
    public boolean enrollInCourse(String courseName) {
        // Check if the user is already enrolled
        String checkQuery = "SELECT COUNT(*) FROM enrollments WHERE user_id = ? AND course = ?";
        try {
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, currentUserId);
            checkStmt.setString(2, courseName);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                return false; // Already enrolled
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        // Enroll in the course
        String insertQuery = "INSERT INTO enrollments (user_id, course) VALUES (?, ?)";
        try {
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setInt(1, currentUserId);
            insertStmt.setString(2, courseName);
            insertStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get user details
    public String[] getUserDetails(int userId) {
        try {
            String query = "SELECT name, email FROM users WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new String[]{rs.getString("name"), rs.getString("email")};
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new String[]{"", ""}; // Return empty strings if not found
    }

    // Method to get enrolled courses
    public ArrayList<String> getEnrolledCourses(int userId) {
        ArrayList<String> courses = new ArrayList<>();
        try {
            String query = "SELECT course FROM enrollments WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                courses.add(rs.getString("course"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses; // Return the list of enrolled courses
    }

    // Custom JPanel class for background images
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }

        public Color getTextColor() {
            // Return a color based on the background for better readability
            return Color.WHITE; // Modify this as needed
        }
    }
}
