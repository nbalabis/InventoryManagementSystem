package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller logic for Add Part page.
 *
 * @author Nicholas Balabis
 */
public class AddPartController implements Initializable {
    @FXML
    public Label errorMsg;

    @FXML
    private Label partType;

    @FXML
    private TextField partTypeTxt;

    @FXML
    private Button homeButton;

    @FXML
    private RadioButton partInHouseRBtn;

    @FXML
    private RadioButton partOutsourcedRBtn;

    @FXML
    private TextField partNameTxt;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    /**
     * Initializes controller.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Sets label text to "Machine ID"
     *
     * @param actionEvent In-House radio button selected.
     */
    @FXML
    public void onInHouse(ActionEvent actionEvent) {
        partType.setText("Machine ID");
    }

    /**
     * Sets label to "Company Name"
     *
     * @param actionEvent Outsourced radio button selected.
     */
    @FXML
    public void onOutsourced(ActionEvent actionEvent) {
        partType.setText("Company Name");
    }

    /**
     * Creates a new part and adds to inventory.
     *
     * @param actionEvent Save button clicked.
     */
    @FXML
    public void savePart(ActionEvent actionEvent) {
        displayError(0);
        try {
            int id = generateId();
            String name = partNameTxt.getText();
            if (name.equals("")) {
                displayError(2);
                return;
            }
            double price = Double.parseDouble(partPriceTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            if(min < 0 || min > max) {
                displayError(3);
                return;
            }
            if(stock < min || stock > max) {
                displayError(4);
                return;
            }
            if (partInHouseRBtn.isSelected()) {
                try {
                    int machineId = Integer.parseInt(partTypeTxt.getText());
                    InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(newPart);
                } catch (Exception e) {
                    displayError(5);
                    return;
                }
            }
            if (partOutsourcedRBtn.isSelected()) {
                String companyName = partTypeTxt.getText();
                Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(newPart);
            }
            homeButton.fireEvent(new ActionEvent());
        } catch(Exception e) {
            displayError(1);
        }
    }

    /**
     * Generates a new unique Part ID.
     *
     * @return New part ID.
     */
    private int generateId() {
        int maxId = 1;
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part pt : allParts) {
            if(pt.getId() > maxId) {
                maxId = pt.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Displays error message based on error type.
     *
     * @param errorCode Code corresponding to error type.
     */
    @FXML
    private void displayError(int errorCode) {
        switch(errorCode) {
            case 0:
                errorMsg.setText("");
                break;
            case 1:
                errorMsg.setText("Form contains empty/invalid values.");
                break;
            case 2:
                errorMsg.setText("Part must have a valid name.");
                break;
            case 3:
                errorMsg.setText("Min cannot be less than 0 or greater than max.");
                break;
            case 4:
                errorMsg.setText("Inventory must be between min and max.");
                break;
            case 5:
                errorMsg.setText("Machine ID must be a number.");
        }
    }

    /**
     * Returns to main screen.
     *
     * @param actionEvent Cancel button clicked.
     * @throws IOException Throws IO Exception.
     */
    @FXML
    void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

}
