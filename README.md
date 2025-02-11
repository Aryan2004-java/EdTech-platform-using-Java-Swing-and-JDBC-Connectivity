# Educrow - Login and Signup Application

This project is a simple Java-based desktop application using `Swing` for the GUI and `JDBC` for database connectivity. The application allows users to sign up, log in, view courses, and manage their profiles. It connects to a MySQL database to store user information and course enrollments.

## Features

- **Login/Signup**: Users can sign in with their Gmail ID and password or sign up if they don't have an account.
- **Course Enrollment**: Users can explore available courses and enroll in them.
- **User Profile**: A profile page shows user details like their name, email, and the list of enrolled courses.
- **Background Images**: Custom background images for the login, signup, and explore pages.
- **Course Links**: Each course has a link to external resources and an option to enroll.
- **Responsive Layout**: The layout is dynamically adjusted for components with the help of `GridBagLayout` and `BoxLayout`.

## Technologies Used

- **Java Swing**: For creating the Graphical User Interface (GUI).
- **JDBC**: For connecting to and interacting with a MySQL database.
- **MySQL**: For storing user data, including user credentials and course enrollments.
- **Images**: The application supports adding background images and course thumbnails.

## Setup and Installation

### Prerequisites

- Java Development Kit (JDK 8 or higher)
- MySQL server and MySQL JDBC driver
- An IDE like IntelliJ IDEA or Eclipse (optional)
- Basic knowledge of SQL and JDBC.

### Database Setup

1. **MySQL Database Configuration**:
    - Create a database called `edtech`.
    - Create the following tables in the database:
    
    ```sql
    CREATE TABLE users (
        id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL
    );
    
    CREATE TABLE enrollments (
        id INT AUTO_INCREMENT PRIMARY KEY,
        user_id INT NOT NULL,
        course VARCHAR(255) NOT NULL,
        FOREIGN KEY (user_id) REFERENCES users(id)
    );
    ```

2. **JDBC Setup**:
   - Download the MySQL JDBC driver from [here](https://dev.mysql.com/downloads/connector/j/).
   - Add the `mysql-connector-java-x.x.x.jar` file to your project classpath.

### Configuring the Application

1. **Clone the repository** (or copy the code):
   ```bash
   git clone <repository_url>

2. **Update Database Credentials**

In the LoginSignupApp.java file, update the following variables with your MySQL username and password:
```java
static final String USER = "root"; // Your MySQL username
static final String PASS = "YourPassword"; // Your MySQL password
```
3. **Images**

Update the paths for the background images and course thumbnails in the BackgroundPanel and createCoursePanel methods. Make sure to use the correct file paths for your local system:
```java
BackgroundPanel loginPanel = new BackgroundPanel("C:\\path_to_your_image\\background.jpg");
```
**Running the Application**
1.Compile and run the LoginSignupApp.java file from your IDE or the command line.
2.The Login screen will appear, allowing users to log in or sign up.
3.After logging in, users can explore available courses and enroll in them.
4.The user profile page displays user details and enrolled courses.

**Folder Structure**
```graphql
├── src
│   ├── LoginSignupApp.java   # Main application file
│   └── BackgroundPanel.java  # Custom JPanel for background images
├── resources
│   ├── background.jpg        # Background image for login/signup
│   └── course_thumbnails     # Directory for course images
├── README.md
└── mysql-connector-java-x.x.x.jar  # JDBC driver for MySQL
```
**How to Use**
1.Sign Up: If you're a new user, click on the "Sign Up" button. Fill in your details (name, Gmail ID, password), and submit the form to create a new account.
2.Login: If you have an account, enter your Gmail ID and password, then click "Login".
3.Explore Courses: After logging in, you can browse through a list of available courses, enroll, or click "Course Link" to learn more about each course.
4.Profile Page: Click the "View Profile" button to view your profile information and the list of courses you are enrolled in.

**Future Enhancements**
1.Add password encryption for security.
2.Implement a "Forgot Password" functionality.
3.Enable users to update their profile information.
4.Allow users to view detailed progress for each enrolled course.

**Acknowledgments**
1.MySQL for the database.
2.Java Swing for the GUI framework.
3.Unsplash for images.

 
