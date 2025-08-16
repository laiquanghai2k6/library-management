package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;
import model.BorrowRecord;
import model.User;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import controller.BorrowController;
import controller.UserController;
import javafx.fxml.Initializable;

public class BorrowView implements Initializable{

    private final BorrowController borrowController = new BorrowController();
    private final UserController userController = new UserController();

    @FXML
    private TableColumn<BorrowRecord, String> bookNameColumn;

    @FXML
    private TextField bookNameField;

    @FXML
    private TableColumn<BorrowRecord, LocalDate> borrowDateColumn;

    @FXML
    private DatePicker borrowDatePicker;

    @FXML
    private TableView<BorrowRecord> borrowedTable;

    @FXML
    private TableColumn<BorrowController, String> borrowerNameColumn;

    @FXML
    private TextField borrowerNameField;

    @FXML
    private Button confirmButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borrowedTable.setEditable(true);

        // Cell factory cho cột name với TextField có wrap text
        bookNameColumn.setCellFactory(column -> new TextFieldTableCell<BorrowRecord, String>(new DefaultStringConverter()) {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(column.widthProperty().subtract(10));
                setGraphic(text);
                setPrefHeight(Control.USE_COMPUTED_SIZE);
            }

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    text.setText(null);
                    setGraphic(null);
                } else {
                    text.setText(item);
                    setGraphic(text);
                }
            }
        });


        loadUsersToListView();
    }

    @FXML
    public void loadUsersToListView() {
        // userTable.getItems().clear();
        List<User> users = userController.getAllUsers();
        // userTable.getItems().addAll(users);
    }

    public void Back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UI.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/application.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
