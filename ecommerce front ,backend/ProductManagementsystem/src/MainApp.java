import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        WelcomePage welcomePage = new WelcomePage(primaryStage);
        welcomePage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}




