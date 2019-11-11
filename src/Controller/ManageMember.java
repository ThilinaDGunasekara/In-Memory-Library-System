package Controller;

import DB.DBConnection;
import TableModel.MemberTM;
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

public class ManageMember {

    public AnchorPane anpHome;
    public ImageView picIssueBook;
    public ImageView picManageReturn;
    public ImageView picManageBook;
    public ImageView picManageMembers;
    public ImageView picSearch;
    public Label lblMenu;
    public Label lblDescription;
    public ImageView picHome;
    public ImageView picNewCus_id;
    public JFXTextField txtId_ID;
    public JFXTextField txtName_id;
    public JFXTextField txtAddress_Id;
    public JFXButton btnSave_Id;
    public JFXButton btnDelete_Id;
    public TableView <MemberTM>tblList_Id;
    public ImageView picNewMember;
    public JFXTextField txtContactNo_id;
    public Label lblname;
    public Label lblAddress;
    public Label lblContactNo;

    public Connection connection;

    public PreparedStatement pstForInsert;
    public PreparedStatement pstForQuery;

    public void initialize() throws ClassNotFoundException {

        connection = DBConnection.getInstance().getConnection();

        tblList_Id.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("memberId"));
        tblList_Id.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblList_Id.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblList_Id.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contactNo"));

        Class.forName("com.mysql.jdbc.Driver");

        try {
            load();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        txtId_ID.setDisable(true);
        txtName_id.setDisable(true);
        txtAddress_Id.setDisable(true);
        txtContactNo_id.setDisable(true);
        btnDelete_Id.setDisable(true);
        btnSave_Id.setDisable(true);



        tblList_Id.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<MemberTM>() {
            @Override
            public void changed(ObservableValue<? extends MemberTM> observable, MemberTM oldValue, MemberTM newValue) {
                MemberTM selectedItem = tblList_Id.getSelectionModel().getSelectedItem();

                if(selectedItem==null){
                    btnSave_Id.setText("Save");
                    btnDelete_Id.setDisable(true);
                    return;
                }
                btnSave_Id.setText("Update");
                btnSave_Id.setDisable(false);
                btnDelete_Id.setDisable(false);
                txtName_id.setDisable(false);
                txtAddress_Id.setDisable(false);
                txtContactNo_id.setDisable(false);
                txtId_ID.setText(selectedItem.getMemberId());
                txtName_id.setText(selectedItem.getName());
                txtAddress_Id.setText(selectedItem.getAddress());
                txtContactNo_id.setText(selectedItem.getContactNo());
            }
        });

        txtName_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String name = txtName_id.getText();
                if(!name.matches("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$")){
                    txtName_id.selectAll();
                    lblname.setText("[ Please enter,Correct format of the Name.. ]");
                    txtName_id.requestFocus();
                    btnSave_Id.setDisable(true);
                }else {
                    lblname.setText("");
                    btnSave_Id.setDisable(true);
                }
                if(txtName_id.getText().equals("")){
                    lblname.setText("");
                }

            }
        });

        txtAddress_Id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String address = txtAddress_Id.getText();
                if(!address.matches("\\d{1,3}.?\\d{0,3}\\s[a-zA-Z]{2,30}\\s[a-zA-Z]{2,15}")) {
                    txtAddress_Id.selectAll();
                    lblAddress.setText("[ it's should contain post No,and tow words.. ]");
                    btnSave_Id.setDisable(true);
                }else {

                    lblAddress.setText("");
                    btnSave_Id.setDisable(true);
                }
                if(txtAddress_Id.getText().equals("")){
                    lblAddress.setText("");
                }
            }
        });

        txtContactNo_id.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String description = txtContactNo_id.getText();
                if(!description.matches("\\d+")){
                    txtContactNo_id.selectAll();
                    lblContactNo.setText("[ Please Enter Telephone with 10 Numbers.]");
                    txtContactNo_id.requestFocus();
                    btnSave_Id.setDisable(true);
                }else {
                    lblContactNo.setText("");
                    btnSave_Id.setDisable(false);
                }
                if(txtContactNo_id.getText().equals("")){
                    lblContactNo.setText("");
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
    public void txtId_OnAction(ActionEvent actionEvent) {

    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        String contact = txtContactNo_id.getText();
        if(!contact.matches("\\d{10}")){
            txtContactNo_id.requestFocus();
            txtContactNo_id.selectAll();
        }
        btnSave_Id.requestFocus();

        if (btnSave_Id.getText().equals("Save")) {
            try {

                String selectId = "INSERT INTO Member VALUES (?,?,?,?)";

                PreparedStatement preparedStatement = connection.prepareStatement(selectId);
                preparedStatement.setString(1,txtId_ID.getText());
                preparedStatement.setString(2,txtName_id.getText());
                preparedStatement.setString(3,txtAddress_Id.getText());
                preparedStatement.setString(4,txtContactNo_id.getText());

                int affectedRows = preparedStatement.executeUpdate();
                tblList_Id.getItems().clear();
                load();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            btnSave_Id.setDisable(true);
            txtAddress_Id.setDisable(true);
            txtName_id.setDisable(true);
            txtContactNo_id.setDisable(true);
            tblList_Id.refresh();

        } else {
            try {

                PreparedStatement prepsUpdate = connection.prepareStatement("UPDATE Member SET name = ?, address= ?,contactNo =? WHERE memberId= ?");
                prepsUpdate.setString(1,txtName_id.getText());
                prepsUpdate.setString(2,txtAddress_Id.getText());
                prepsUpdate.setString(3,txtContactNo_id.getText());
                prepsUpdate.setString(4,txtId_ID.getText());

                tblList_Id.getItems().clear();



                txtContactNo_id.clear();
                txtId_ID.clear();
                txtAddress_Id.clear();
                txtName_id.clear();
                btnSave_Id.setDisable(true);
                btnDelete_Id.setDisable(true);
                load();

                if(prepsUpdate.executeUpdate()>0){
                    System.out.println("Done");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void btnDelete_OnAction(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure whether you want to delete this member?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {


            Connection connection = null;
            try {

                PreparedStatement prepsUpdate = connection.prepareStatement("DELETE FROM Member WHERE memberId=?");
                prepsUpdate.setString(1,txtId_ID.getText());

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

    public void newMember_OnAction(MouseEvent mouseEvent) {

        btnSave_Id.setText("Save");
        txtId_ID.clear();
        txtName_id.clear();
        txtAddress_Id.clear();
        txtContactNo_id.clear();

        txtName_id.setDisable(false);
        txtAddress_Id.setDisable(false);
        txtContactNo_id.setDisable(false);
        btnDelete_Id.setDisable(true);
        btnSave_Id.setDisable(false);

        //Genarate A new ID
        idGenerate(connection);
    }

    public void tblClick(MouseEvent mouseEvent) {
        btnDelete_Id.setDisable(false);
    }

    public void load() throws SQLException {
        tblList_Id.getItems().clear();

        PreparedStatement pstForQuery = connection.prepareStatement("SELECT * FROM Member ");

        ResultSet resultSet = pstForQuery.executeQuery();
        ObservableList<MemberTM> members = tblList_Id.getItems();

        while(resultSet.next()){
            members.add(new MemberTM(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)));
        }
    }

    public void idGenerate(Connection connection){
        try {

            int maxId =0;
            String selectId = "SELECT memberId FROM Member ORDER BY  memberId DESC LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(selectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String memberId = resultSet.getString("memberId");
                maxId = Integer.parseInt(memberId.replace("M", ""));
            }

            maxId++;
            String id = "";
            if (maxId < 10) {
                id = "M00" + maxId;
            } else if (maxId < 100) {
                id = "M0" + maxId;
            } else {
                id = "M" + maxId;
            }
            txtId_ID.setText(id);
            tblList_Id.refresh();
            txtName_id.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
