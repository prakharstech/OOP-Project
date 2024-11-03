import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Booking Class
class Booking {
    private String clubName;
    private String applicantName;
    private String regNumber;
    private String contactNumber;
    private String functionType;
    private String venue;
    private String purpose;
    private String date;
    private String time;
    private List<String> equipmentRequested;
    private String status; // Approved, Disapproved, or Pending

    public Booking(String clubName, String applicantName, String regNumber, String contactNumber, String functionType,
                   String venue, String purpose, String date, String time, List<String> equipmentRequested) {
        this.clubName = clubName;
        this.applicantName = applicantName;
        this.regNumber = regNumber;
        this.contactNumber = contactNumber;
        this.functionType = functionType;
        this.venue = venue;
        this.purpose = purpose;
        this.date = date;
        this.time = time;
        this.equipmentRequested = equipmentRequested;
        this.status = "Pending";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getApplicantName() {
        return applicantName;
    }

    @Override
    public String toString() {
        return "Booking Details:\n" +
               "Club Name: " + clubName + "\n" +
               "Applicant Name: " + applicantName + "\n" +
               "Registration Number: " + regNumber + "\n" +
               "Contact Number: " + contactNumber + "\n" +
               "Function Type: " + functionType + "\n" +
               "Venue: " + venue + "\n" +
               "Purpose: " + purpose + "\n" +
               "Date: " + date + "\n" +
               "Time: " + time + "\n" +
               "Equipment: " + equipmentRequested + "\n" +
               "Status: " + status + "\n";
    }
}

// Repository Class
class Repository {
    private List<Booking> bookings = new ArrayList<>();

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public List<Booking> getBookings() {
        return bookings;
    }
}

// Service Class
class Service {
    private Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public boolean submitBooking(Booking booking) {
        repository.addBooking(booking);
        return true;
    }

    public List<Booking> getAllBookings() {
        return repository.getBookings();
    }
}

// Controller Class
class Controller {
    private Service service;

    public Controller(Service service) {
        this.service = service;
    }

    public String submitBooking(String clubName, String applicantName, String regNumber, String contactNumber,
                                String functionType, String venue, String purpose, String date, String time,
                                List<String> equipmentRequested, boolean agreeToTerms) {
        if (!agreeToTerms) {
            return "Error: You must agree to the terms and conditions.";
        }

        if (clubName.isEmpty() || applicantName.isEmpty() || regNumber.isEmpty() ||
            contactNumber.isEmpty() || functionType.isEmpty() || venue.isEmpty() ||
            purpose.isEmpty() || date.isEmpty() || time.isEmpty()) {
            return "Error: Please fill in all fields.";
        }

        Booking booking = new Booking(clubName, applicantName, regNumber, contactNumber, functionType,
                                      venue, purpose, date, time, equipmentRequested);
        service.submitBooking(booking);
        System.out.println("Booking submitted successfully: " + booking); // Debug line
        return "Booking submitted successfully!";
    }

    public List<Booking> getAllBookings() {
        List<Booking> bookings = service.getAllBookings();
        System.out.println("Controller retrieving bookings: " + bookings.size()); // Debug line
        return bookings;
    }

    public void updateBookingStatus(Booking booking, String status) {
        booking.setStatus(status);
        System.out.println("Booking status updated: " + booking); // Debug line
    }

    public Booking getBookingByApplicantName(String applicantName) {
        for (Booking booking : service.getAllBookings()) {
            if (booking.getApplicantName().equalsIgnoreCase(applicantName)) {
                return booking;
            }
        }
        return null; // Return null if not found
    }
}

// Login Page Class
class LoginPage extends Stage {
    private App app;

    public LoginPage(App app) {
        this.app = app;

        VBox loginLayout = new VBox(10);
        loginLayout.setAlignment(Pos.CENTER); // Center align all controls except the logo
        loginLayout.setPrefWidth(300);

        // Load image and set its size
        Image logoImage = new Image("file:/Users/prakharsrivastava/Downloads/logo.jpeg");
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitWidth(500); // Set the width of the logo
        logoImageView.setPreserveRatio(true); // Preserve aspect ratio
        logoImageView.setSmooth(true); // Smooth rendering

        // Create an HBox for the logo to position it at the top left
        HBox logoContainer = new HBox();
        logoContainer.getChildren().add(logoImageView);
        logoContainer.setAlignment(Pos.TOP_LEFT); // Align logo to the top left

        Label heading = new Label("Venue Booking Portal");
        heading.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        Button loginButton = new Button("Login");

        // Add the logo container and other controls to the layout
        loginLayout.getChildren().addAll(logoContainer, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        // Center the VBox on the scene
        VBox centeredLayout = new VBox(10);
        centeredLayout.setAlignment(Pos.CENTER);
        centeredLayout.getChildren().addAll(heading, usernameLabel, usernameField, passwordLabel, passwordField, loginButton);

        // Create a main layout that combines the logo and centered controls
        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(logoContainer);
        mainLayout.setCenter(centeredLayout);

        Scene scene = new Scene(mainLayout, 500, 400);
        this.setScene(scene);
        this.setTitle("Login Page");
        this.getIcons().add(logoImage); // Optional: Set icon for the window

        // Login functionality
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if ("thrust.mit@manipal.edu".equals(username) && "thrust".equals(password) ||
                "goonj.mit@manipal.edu".equals(username) && "goonj".equals(password) ||
                "acm.mit@manipal.edu".equals(username) && "acm".equals(password)||
                "swo.mit@manipal.edu".equals(username) && "swo".equals(password)) {
                // Successful login
                this.close();
                Stage mainStage = new Stage();
                mainStage.setScene(app.createMainScene(mainStage));
                mainStage.setTitle("Main Application");
                mainStage.show();
            } else {
                // Error message
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password. Please try again.");
                alert.showAndWait();
            }
        });
    }
}

// Main Application Class
public class AppF extends Application {
    private Repository repository = new Repository();
    private Service service = new Service(repository);
    private Controller controller = new Controller(service);

    @Override
    public void start(Stage primaryStage) {
        // Show the login page first
        LoginPage loginPage = new LoginPage(this);
        loginPage.show();
    }

    public Scene createMainScene(Stage mainStage) {
        // Role selection
         Label roleLabel = new Label("Home Page");

        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Student", "Security Section");
        roleComboBox.setPromptText("Select Role");

        // Main layout
        VBox mainLayout = new VBox(10, roleLabel, roleComboBox);

        // Handle role selection
        roleComboBox.setOnAction(e -> {
            // Clear all UI components except for the role selection
            mainLayout.getChildren().clear();
            mainLayout.getChildren().addAll(roleLabel, roleComboBox); // Re-add role selection

            if (roleComboBox.getValue().equals("Student")) {
                VBox studentUI = createStudentUI();
                mainLayout.getChildren().add(studentUI);
            } else if (roleComboBox.getValue().equals("Security Section")) {
                VBox securityUI = createSecurityUI();
                mainLayout.getChildren().add(securityUI);
            }
        });

        return new Scene(mainLayout, 400, 400);
    }

    private VBox createStudentUI() {
        TextField clubNameField = new TextField();
        clubNameField.setPromptText("Club Name");

        TextField applicantNameField = new TextField();
        applicantNameField.setPromptText("Applicant Name");

        TextField regNumberField = new TextField();
        regNumberField.setPromptText("Registration Number");

        TextField contactNumberField = new TextField();
        contactNumberField.setPromptText("Contact Number");

        TextField functionTypeField = new TextField();
        functionTypeField.setPromptText("Function Type");

        TextField venueField = new TextField();
        venueField.setPromptText("Venue");

        TextField purposeField = new TextField();
        purposeField.setPromptText("Purpose");

        TextField dateField = new TextField();
        dateField.setPromptText("Date");

        TextField timeField = new TextField();
        timeField.setPromptText("Time");

        CheckBox projectorCheckBox = new CheckBox("Projector");
        CheckBox micCheckBox = new CheckBox("Microphone");
        CheckBox speakersCheckBox = new CheckBox("Speakers");
        CheckBox termsCheckBox = new CheckBox("I agree to the terms and conditions");

        Label messageLabel = new Label();
        Button submitButton = new Button("Submit");

        // View Booking Button
        Button viewBookingButton = new Button("View Booking Status");
        TextArea bookingStatusArea = new TextArea();
        bookingStatusArea.setEditable(false);

        submitButton.setOnAction(e -> {
            List<String> equipmentRequested = new ArrayList<>();
            if (projectorCheckBox.isSelected()) equipmentRequested.add("Projector");
            if (micCheckBox.isSelected()) equipmentRequested.add("Microphone");
            if (speakersCheckBox.isSelected()) equipmentRequested.add("Speakers");

            String responseMessage = controller.submitBooking(
                clubNameField.getText(), applicantNameField.getText(), regNumberField.getText(),
                contactNumberField.getText(), functionTypeField.getText(), venueField.getText(),
                purposeField.getText(), dateField.getText(), timeField.getText(),
                equipmentRequested, termsCheckBox.isSelected()
            );

            messageLabel.setText(responseMessage);
        });

        // Action for View Booking Button
        viewBookingButton.setOnAction(e -> {
            String applicantName = applicantNameField.getText();
            if (applicantName.isEmpty()) {
                bookingStatusArea.setText("Error: Please enter your name to view the booking status.");
                return;
            }

            Booking booking = controller.getBookingByApplicantName(applicantName);
            if (booking != null) {
                bookingStatusArea.setText(booking.toString());
            } else {
                bookingStatusArea.setText("No booking found for this applicant.");
            }
        });

        return new VBox(10, clubNameField, applicantNameField, regNumberField, contactNumberField,
                        functionTypeField, venueField, purposeField, dateField, timeField,
                        projectorCheckBox, micCheckBox, speakersCheckBox, termsCheckBox,
                        submitButton, messageLabel, viewBookingButton, bookingStatusArea);
    }

    private VBox createSecurityUI() {
        VBox bookingsVBox = new VBox(10);
        Button refreshButton = new Button("Refresh Bookings");

        refreshButton.setOnAction(e -> updateSecurityUI(bookingsVBox));

        bookingsVBox.getChildren().add(refreshButton);
        return bookingsVBox;
    }

    // Update Security UI with bookings
    private void updateSecurityUI(VBox bookingsVBox) {
        bookingsVBox.getChildren().clear();
        List<Booking> bookings = controller.getAllBookings();
        Button refreshButton = new Button("Refresh Bookings");
        
        refreshButton.setOnAction(e -> updateSecurityUI(bookingsVBox));
        bookingsVBox.getChildren().add(refreshButton);

        for (Booking booking : bookings) {
            TextArea bookingArea = new TextArea(booking.toString());
            bookingArea.setEditable(false);

            Button approveButton = new Button("Approve");
            approveButton.setOnAction(e -> {
                controller.updateBookingStatus(booking, "Approved");
                bookingArea.setText(booking.toString()); // Update display
            });

            Button disapproveButton = new Button("Disapprove");
            disapproveButton.setOnAction(e -> {
                controller.updateBookingStatus(booking, "Disapproved");
                bookingArea.setText(booking.toString()); // Update display
            });

            HBox actions = new HBox(10, approveButton, disapproveButton);
            VBox bookingBox = new VBox(10, bookingArea, actions);
            bookingsVBox.getChildren().add(bookingBox);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
