package Controller;

import DB.DBConnection;
import TableModel.ReturnBookTM;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static javafx.scene.paint.Color.LIGHTSKYBLUE;

public class HandleReturns {

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
    public JFXTextField txtIssueDate_id;
    public JFXButton btnIssue_Id;
    public TableView <ReturnBookTM>tblList_Id;
    public ImageView picNewBook;
    public JFXComboBox<String> cmbIssue_id;
    public JFXComboBox cmbBookId_id;
    public JFXDatePicker datePicker;
    public JFXTextField txtMustReturnDate_id;
    public JFXDatePicker returnDatePicker;
    public JFXTextField txtIssueDate_id1;
    public JFXTextField txtBook_Id;
    public JFXTextField txtFine_id;
    public ImageView picNewReturnBook;
    private Connection connection;



    public void initialize() {

        connection = DBConnection.getInstance().getConnection();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                if(HandleReturns.this.connection !=null){
                    try{
                        HandleReturns.this.connection.close();
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }
            }
        }));

        tblList_Id.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("issueId"));
        tblList_Id.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblList_Id.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("issueDate"));
        tblList_Id.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("mustReturnDate"));
        tblList_Id.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        tblList_Id.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("fine"));

        comboLoad();

        cmbIssue_id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    PreparedStatement prpsGetIssue = HandleReturns.this.connection.prepareStatement("SELECT bookId,date,returnDate FROM IssueBook");
                    ResultSet resultSetIssue = prpsGetIssue.executeQuery();
                    while (resultSetIssue.next()) {
                        txtBook_Id.setText(resultSetIssue.getString(1));
                        txtIssueDate_id.setText(resultSetIssue.getString(2));
                        txtMustReturnDate_id.setText(resultSetIssue.getString(3));
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        txtBook_Id.setDisable(true);
        txtIssueDate_id.setDisable(true);
        txtMustReturnDate_id.setDisable(true);
        txtFine_id.setDisable(true);
        btnIssue_Id.setDisable(true);

    }

    void show(String value) throws IOException {
        URL resource = this.getClass().getResource(value);
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        Stage primaryStage = (Stage) (this.anpHome.getScene().getWindow());
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
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();

            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
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
        if (event.getSource() instanceof ImageView) {
            ImageView icon = (ImageView) event.getSource();
            ScaleTransition scaleT = new ScaleTransition(Duration.millis(200), icon);
            scaleT.setToX(1);
            scaleT.setToY(1);
            scaleT.play();

            icon.setEffect(null);

        }
    }

    public void returnDatePicker_OnAction(ActionEvent actionEvent) {
        btnIssue_Id.setDisable(false);
    }


    public void txtIssueDate_OnAction(ActionEvent actionEvent) {

    }

    public void cmbIssue_OnAction(ActionEvent actionEvent) {

    }

    public void newBook_OnAction(MouseEvent mouseEvent) {


    }

    public void tblClick(MouseEvent mouseEvent) {

    }

    public void btnIssue_OnAction(ActionEvent actionEvent) {

        double fineDate=0.0;
        LocalDate issueDate = LocalDate.parse(txtIssueDate_id.getText());
        LocalDate datePicker = LocalDate.parse(returnDatePicker.getValue().toString());

        try {
            int deference = (int) ChronoUnit.DAYS.between(issueDate,datePicker);

            if(deference >=15){
                fineDate = (deference-14)*15;
                System.out.println(fineDate);
            }else{
                fineDate = 0;
            }

            PreparedStatement prepSReturn = connection.prepareStatement("INSERT INTO ReturnBooks VALUES (?,?,?,?,?,?)");
            prepSReturn.setString(1,cmbIssue_id.getSelectionModel().getSelectedItem());
            prepSReturn.setString(2,txtBook_Id.getText());
            prepSReturn.setString(3,txtIssueDate_id.getText());
            prepSReturn.setString(4,txtMustReturnDate_id.getText());
            prepSReturn.setString(5,returnDatePicker.getValue().toString());
            prepSReturn.setString(6, String.valueOf(fineDate));
            int effectedRows = prepSReturn.executeUpdate();

            load();
            txtFine_id.setText(String.valueOf(fineDate));

            PreparedStatement updatePrepSate = connection.prepareStatement("UPDATE Book SET Book.availability = true ");
            if(updatePrepSate.executeUpdate()>0){
                System.out.println("Done");
            }

            cmbIssue_id.getItems().clear();
            comboLoad();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void txtBook_OnAction(ActionEvent actionEvent) { }

    public void newReBook_OnAction(MouseEvent mouseEvent) {
        cmbIssue_id.getSelectionModel().clearSelection();
        txtFine_id.clear();
        txtMustReturnDate_id.clear();
        txtIssueDate_id.clear();
        txtBook_Id.clear();
    }

    public void load(){
        try {
            ObservableList<ReturnBookTM> returns = tblList_Id.getItems();

            PreparedStatement prepSSelectReturn = connection.prepareStatement("SELECT * FROM ReturnBooks");
            ResultSet resultSet = prepSSelectReturn.executeQuery();

            while (resultSet.next()){
                returns.add(new ReturnBookTM(resultSet.getString(1),resultSet.getString(2),
                        LocalDate.parse(resultSet.getString(3)),LocalDate.parse(resultSet.getString(4)),
                        LocalDate.parse(resultSet.getString(5)),Double.parseDouble(resultSet.getString(6))));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void comboLoad(){
        ObservableList<String> issueBook = cmbIssue_id.getItems();
        try {

            PreparedStatement  prepsIssueId= connection.prepareStatement("SELECT i.issueId  FROM IssueBook i LEFT JOIN ReturnBooks r ON i.issueId = r.issueId WHERE r .issueId IS NULL " );
            ResultSet resultSet = prepsIssueId.executeQuery();

            while (resultSet.next()){
                issueBook.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
