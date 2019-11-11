package Controller;

import DB.DBConnection;
import TableModel.SearchTM;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import static javafx.scene.paint.Color.LIGHTSKYBLUE;

public class SearchBook {

    public AnchorPane anpHome;
    public ImageView picIssueBook;
    public ImageView picManageReturn;
    public ImageView picManageBook;
    public ImageView picManageMembers;
    public ImageView picSearch;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView picHome;
    public Label lblDescription1;
    public JFXTextField txtSearch;
    public TableView <SearchTM>tblList_Id;
    public Connection connection;

    public void initialize(){
        connection = DBConnection.getInstance().getConnection();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(SearchBook.this.connection !=null){
                    try{
                        SearchBook.this.connection.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }));

        tblList_Id.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblList_Id.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblList_Id.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));
        tblList_Id.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("availability"));

        load();
        txtSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tblList_Id.getItems().clear();
                load();
            }
        });
    }
    public void load() {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Book WHERE booKId LIKE ? OR title LIKE ? OR author LIKE ?");
            preparedStatement.setString(1, "%" + txtSearch.getText() + "%");
            preparedStatement.setString(2, "%" + txtSearch.getText() + "%");
            preparedStatement.setString(3, "%" + txtSearch.getText() + "%");
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<SearchTM> search = tblList_Id.getItems();

            while (resultSet.next()) {
                search.add(new SearchTM(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4)));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
    public void home_OnAction(MouseEvent mouseEvent) throws IOException {
        show("/view/MainFrom.fxml");
    }

    @FXML
    private void playMouseEnterAnimation(MouseEvent event) {
        if (event.getSource() instanceof ImageView){
            ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT =new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1.2);
            scaleT.setToY(1.2);
            scaleT.play();

            DropShadow glow = new DropShadow();
            //glow.setColor(CORNFLOWERBLUE);
            glow.setColor(LIGHTSKYBLUE);
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
        }
    }

    public void tblClick(MouseEvent mouseEvent) { }

    public void txtSearch_OnAction(ActionEvent actionEvent) { }

}
