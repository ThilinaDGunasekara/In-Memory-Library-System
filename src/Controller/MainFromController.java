package Controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

import static javafx.scene.paint.Color.BLACK;

public class MainFromController {
    public ImageView picIssueBook;
    public ImageView picManageReturn;
    public ImageView picManageBook;
    public ImageView picManageMembers;
    public ImageView picSearch;
    public Label lblMenu;
    public Label lblDescription;
    public AnchorPane anpHome;
    private JFXPanel root;


    void show(String value) throws IOException {
        URL resource = this.getClass().getResource(value);
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage)( this.anpHome.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        TranslateTransition tt = new TranslateTransition(Duration.millis(350), scene.getRoot());
        tt.setFromX(-scene.getWidth());
        tt.setToX(0);
        tt.play();
    }

    public void manageCustomer_OnAction(MouseEvent mouseEvent) throws IOException {
        show("/view/ManageMember.fxml");
    }
    public void issueBook_OnAction(MouseEvent mouseEvent) throws IOException {
        show("/view/IssueBook.fxml");
    }
    public void handleReturns_OnAction(MouseEvent mouseEvent) throws IOException {
        show("/view/HandleReturns.fxml");
    }
    public void ManageBook_Clicked(MouseEvent mouseEvent) throws IOException {
        show("/view/ManageBook.fxml");
    }
    public void searchBook_clicked(MouseEvent mouseEvent) throws IOException {
        show("/view/SearchBook.fxml");
    }



    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            switch(icon.getId()){
                case "picManageMembers":
                    lblMenu.setText("Manage Member");
                    lblDescription.setText("Click to add, edit, delete, search or view members");
                    break;
                case "picIssueBook":
                    lblMenu.setText("Issue Book");
                    lblDescription.setText("Click to issue the book");
                    break;
                case "picManageReturn":
                    lblMenu.setText("Handle Returns");
                    lblDescription.setText("Click here to handle return details");
                    break;
                case "picManageBook":
                    lblMenu.setText("Manage Books");
                    lblDescription.setText("Click to manage book details");
                    break;
                case "picSearch":
                    lblMenu.setText("Search Books");
                    lblDescription.setText("Click if you want to search books");
                    break;
            }

            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            //glow.setColor(CORNFLOWERBLUE);
            glow.setColor(BLACK);
            glow.setWidth(20);
            glow.setHeight(20);
            glow.setRadius(20);
            icon.setEffect(glow);
        }
    }

    @FXML
    private void playMouseExitAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);
            lblMenu.setText("Welcome");
            lblDescription.setText("Please select one of above main operations to proceed");
        }
    }
}
