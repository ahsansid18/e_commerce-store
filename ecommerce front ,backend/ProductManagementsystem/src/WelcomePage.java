import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomePage {
    private Stage stage;

    public WelcomePage(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        Label welcomeLabel = new Label("🛒 WELCOME TO ECOMMERCE STORE");
        welcomeLabel.setStyle("-fx-font-size: 26px; -fx-text-fill: #2A7AE2; -fx-font-weight: bold;");

        FadeTransition ft = new FadeTransition(Duration.seconds(2), welcomeLabel);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        Button proceedBtn = new Button("Explore Products");
        proceedBtn.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #2A7AE2;
            -fx-text-fill: white;
            -fx-padding: 10 20 10 20;
            -fx-background-radius: 8;
        """);
        proceedBtn.setOnMouseEntered(e -> proceedBtn.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #1B5BBF;
            -fx-text-fill: white;
            -fx-padding: 10 20 10 20;
            -fx-background-radius: 8;
        """));
        proceedBtn.setOnMouseExited(e -> proceedBtn.setStyle("""
            -fx-font-size: 16px;
            -fx-background-color: #2A7AE2;
            -fx-text-fill: white;
            -fx-padding: 10 20 10 20;
            -fx-background-radius: 8;
        """));

        proceedBtn.setOnAction(e -> {
            ProductAvailabilityPage productPage = new ProductAvailabilityPage(stage);
            productPage.show();
        });

        VBox layout = new VBox(30, welcomeLabel, proceedBtn);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-padding: 50; -fx-background-color: linear-gradient(to bottom right, #f5faff, #d0e5ff);");

        Scene scene = new Scene(layout, 500, 300);
        stage.setTitle("Welcome");
        stage.setScene(scene);
        stage.show();
    }
}


