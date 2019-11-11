import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class AppInitializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL resource = this.getClass().getResource("view/MainFrom.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene mainSence = new Scene(root);
        primaryStage.setScene(mainSence);
        primaryStage.setTitle("Application");
        primaryStage.show();
    }
}
