package Controller;

import DB.DBConnection;
import TableModel.BookTM;
import com.jfoenix.controls.JFXButton;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
import java.util.Optional;

import static javafx.scene.paint.Color.LIGHTSKYBLUE;

public class ManageBook {
    public AnchorPane anpHome;
    public ImageView picIssueBook;
    public ImageView picManageReturn;
    public ImageView picManageBook;
    public ImageView picManageMembers;
    public ImageView picSearch;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView picHome;
    public JFXTextField txtBook_ID;
    public JFXTextField txtTitle_id;
    public JFXTextField txtAuthor_Id;
    public JFXButton btnSave_Id;
    public JFXButton btnDelete_Id;
    public TableView<BookTM> tblList_Id;
    public ImageView picNewBook;
    public Label lblDescription1;
    public Label lblTitle;
    public Label lblAuthor;
    public Connection connection;

    public void initialize() throws ClassNotFoundException {

        connection = DBConnection.getInstance().getConnection();

        tblList_Id.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("bookId"));
        tblList_Id.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("title"));
        tblList_Id.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("author"));

        Class.forName("com.mysql.jdbc.Driver");

        try {
            load();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtAuthor_Id.setDisable(true);
        txtBook_ID.setDisable(true);
        txtTitle_id.setDisable(true);
        btnDelete_Id.setDisable(true);
        btnSave_Id.setDisable(true);

        tblList_Id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<BookTM>() {
            @Override
            public void changed(ObservableValue<? extends BookTM> observable, BookTM oldValue, BookTM newValue) {
                BookTM selectedItem = tblList_Id.getSelectionModel().getSelectedItem();

                if(selectedItem==null){
                    btnSave_Id.setText("Save");
                    btnDelete_Id.setDisable(true);
                    return;
                }

                btnSave_Id.setText("Update");
                btnSave_Id.setDisable(false);
                btnDelete_Id.setDisable(false);
                txtAuthor_Id.setDisable(false);
                txtTitle_id.setDisable(false);

                txtTitle_id.setText(selectedItem.getTitle());
                txtAuthor_Id.setText(selectedItem.getAuthor());
                txtBook_ID.setText(selectedItem.getBookId());
            }
        });


        txtTitle_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String name = txtTitle_id.getText();
                if(!name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
                    txtTitle_id.selectAll();
                    lblTitle.setText("[ Please enter,Correct format of the Author Name.]");
                    txtTitle_id.requestFocus();
                    btnSave_Id.setDisable(true);
                }else {
                    lblTitle.setText("");
                    btnSave_Id.setDisable(true);
                }
                if(txtTitle_id.getText().equals("")){
                    lblTitle.setText("");
                }
            }
        });


        txtAuthor_Id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String name = txtAuthor_Id.getText();
                if(!name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
                    txtAuthor_Id.selectAll();
                    lblAuthor.setText("[ Please enter,Correct format of the Author Name.]");
                    txtAuthor_Id.requestFocus();
                    btnSave_Id.setDisable(true);
                }else {
                    lblAuthor.setText("");
                    btnSave_Id.setDisable(false);
                }
                if(txtAuthor_Id.getText().equals("")){
                    lblAuthor.setText("");
                }
            }
        });


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

    public void txtBook_OnAction(ActionEvent actionEvent) { }
    public void btnSave_OnAction(ActionEvent actionEvent) {


        if (btnSave_Id.getText().equals("Save")) {
            try {

                String selectId = "INSERT INTO Book VALUES (?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(selectId);
                preparedStatement.setString(1, txtBook_ID.getText());
                preparedStatement.setString(2, txtTitle_id.getText());
                preparedStatement.setString(3, txtAuthor_Id.getText());
                preparedStatement.setString(4, String.valueOf(1));


                preparedStatement.executeUpdate();
                tblList_Id.getItems().clear();
                load();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            btnSave_Id.setDisable(true);
            txtAuthor_Id.setDisable(true);
            txtTitle_id.setDisable(true);
            tblList_Id.refresh();
        } else {
            try {

                PreparedStatement prepsUpdate = connection.prepareStatement("UPDATE Book SET  title= ?, author= ? WHERE booKId= ?");
                prepsUpdate.setString(1, txtTitle_id.getText());
                prepsUpdate.setString(2, txtAuthor_Id.getText());
                prepsUpdate.setString(3, txtBook_ID.getText());
                prepsUpdate.executeUpdate();

                txtBook_ID.clear();
                txtAuthor_Id.clear();
                txtTitle_id.clear();
                btnSave_Id.setDisable(true);
                btnDelete_Id.setDisable(true);
                load();

                if (prepsUpdate.executeUpdate() > 0) {
                    System.out.println("Done");
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void btnDelete_OnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this Book?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            Connection connection = null;
            try {

                PreparedStatement prepsUpdate = connection.prepareStatement("DELETE FROM Book WHERE booKId=?");
                prepsUpdate.setString(1,txtBook_ID.getText());

                if(prepsUpdate.executeUpdate()>0){
                    System.out.println("done");
                }
                tblList_Id.getItems().clear();
                load();

            } catch (SQLException e) {
                e.printStackTrace();
            }


            /*
            MemberTM tblSelection = tblList_Id.getSelectionModel().getSelectedItem();
            tblList_Id.getItems().remove(tblSelection);*/
        }
    }

    public void tblClick(MouseEvent mouseEvent) {

    }

    public void newBook_OnAction(MouseEvent mouseEvent) {

        try {

            int maxId = 0;
            String selectId = "SELECT  booKId FROM Book ORDER BY  booKId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(selectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String memberId = resultSet.getString("bookId");
                maxId = Integer.parseInt(memberId.replace("B", ""));
            }

            maxId++;
            String id = "";
            if (maxId < 10) {
                id = "B00" + maxId;
            } else if (maxId < 100) {
                id = "B0" + maxId;
            } else {
                id = "B" + maxId;
            }
            txtBook_ID.setText(id);
            tblList_Id.refresh();
            txtTitle_id.requestFocus();
            txtAuthor_Id.clear();
            txtTitle_id.clear();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtTitle_id.setDisable(false);
        txtAuthor_Id.setDisable(false);
        btnSave_Id.setDisable(false);
    }

    public void load() throws SQLException {
        tblList_Id.getItems().clear();
        PreparedStatement pstForQuery = connection.prepareStatement("SELECT * FROM Book ");

        ResultSet resultSet = pstForQuery.executeQuery();
        ObservableList<BookTM> books = tblList_Id.getItems();

        while(resultSet.next()){
            books.add(new BookTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),true));
        }
    }
}
