package Controller;

import DB.DBConnection;
import TableModel.IssueBookTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static javafx.scene.paint.Color.LIGHTSKYBLUE;

public class IssueBook {
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
    public JFXTextField txtIssueBook_ID;
    public JFXTextField txtDate_id;
    public TableView <IssueBookTM>tblList_Id;
    public ImageView picNewBook;
    public JFXComboBox cmbMemberId_id;
    public JFXComboBox<String> cmbBookId_id;
    public JFXButton btnIssue_Id;
    public JFXDatePicker datePicker;
    public JFXTextField txtReturnDate_id;
    public Connection connection;

    public void initialize(){
        connection = DBConnection.getInstance().getConnection();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date = new Date();
        txtDate_id.setText(formatter.format(date));

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH,14);
        txtReturnDate_id.setText(formatter.format(c.getTime()));


        txtIssueBook_ID.setDisable(true);
        txtDate_id.setDisable(true);
        txtReturnDate_id.setDisable(true);
        cmbMemberId_id.setDisable(true);
        cmbBookId_id.setDisable(true);
        btnIssue_Id.setDisable(true);
        tblList_Id.setDisable(true);

        tblList_Id.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblList_Id.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblList_Id.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblList_Id.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblList_Id.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("bookId"));

        comboAddBook();
        comboAddMember();
        try {
            load();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {

            int maxId =0;
            String selectId = "SELECT issueId FROM IssueBook ORDER BY  issueId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(selectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String memberId = resultSet.getString("issueId");
                maxId = Integer.parseInt(memberId.replace("II", ""));
            }

            maxId++;
            String id = "";
            if (maxId < 10) {
                id = "II00" + maxId;
            } else if (maxId < 100) {
                id = "II0" + maxId;
            } else {
                id = "II" + maxId;
            }
            txtIssueBook_ID.setText(id);
            tblList_Id.refresh();

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

    public void tblClick(MouseEvent mouseEvent) {

    }


    public void txtDate_OnAction(ActionEvent actionEvent) {

    }

    public void txtIssueBook_OnAction(ActionEvent actionEvent) {

    }

    public void newBook_OnAction(MouseEvent mouseEvent) {

        cmbMemberId_id.getItems().clear();
        cmbBookId_id.getItems().clear();

        initialize();

        cmbBookId_id.setDisable(false);
        cmbMemberId_id.setDisable(false);
        btnIssue_Id.setDisable(false);
    }

    public void cmbMemberId_OnAction(ActionEvent actionEvent) {

    }

    public void cmbBookId_OnAction(ActionEvent actionEvent) {

    }

    public void btnIssue_OnAction(ActionEvent actionEvent) {

        try {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO IssueBook VALUES (?,?,?,?,?)");

            preparedStatement.setString(1,txtIssueBook_ID.getText());
            preparedStatement.setString(2,(String) cmbMemberId_id.getSelectionModel().getSelectedItem());
            preparedStatement.setString(3,txtDate_id.getText());
            preparedStatement.setString(4, txtReturnDate_id.getText());
            preparedStatement.setString(5,cmbBookId_id.getSelectionModel().getSelectedItem());

            int row = preparedStatement.executeUpdate();
            if(row>0){
                System.out.println("done");
            }


            ObservableList<String> items = cmbBookId_id.getItems();
            PreparedStatement preparedStatement1 = connection.prepareStatement("UPDATE Book SET Book.availability = FALSE WHERE Book.booKId=?");
            preparedStatement1.setString(1,cmbBookId_id.getSelectionModel().getSelectedItem());
            int rows = preparedStatement1.executeUpdate();

            items.clear();
            comboAddBook();
            load();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        cmbBookId_id.getItems().clear();
        cmbMemberId_id.getItems().clear();
        txtDate_id.setDisable(true);
        txtIssueBook_ID.setDisable(true);
        cmbBookId_id.setDisable(true);
        cmbMemberId_id.setDisable(true);
        btnIssue_Id.setDisable(true);
        txtReturnDate_id.setDisable(true);
    }

    public void txtReturnDate_OnAction(ActionEvent actionEvent) {

    }

    public void load() throws SQLException {
        tblList_Id.getItems().clear();

        PreparedStatement pstForQuery = connection.prepareStatement("SELECT * FROM IssueBook ");

        ResultSet resultSet = pstForQuery.executeQuery();
        ObservableList<IssueBookTM> books = tblList_Id.getItems();
        while(resultSet.next()){
            books.add(new IssueBookTM(resultSet.getString(1),resultSet.getString(2),LocalDate.parse(resultSet.getString(3)),LocalDate.parse(resultSet.getString(4)),resultSet.getString(5)));
        }

    }

    public void comboAddBook(){
        try {


            PreparedStatement preparedStatement = connection.prepareStatement("SELECT bookId FROM Book WHERE Book.availability=true ");
            ResultSet resultSet = preparedStatement.executeQuery();
            ObservableList<String> items = cmbBookId_id.getItems();

            while (resultSet.next()) {
                items.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void comboAddMember(){
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT memberId FROM Member");
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<String> items = cmbMemberId_id.getItems();
            while (resultSet.next()){
                items.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
