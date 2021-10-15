package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
 * Controller logic for Modify Part page.
 *
 * @author Nicholas Balabis
 */
public class ModifyPartController implements Initializable {
    @FXML
    public ToggleGroup partTG;

    @FXML
    public RadioButton partInHouseRBtn;

    @FXML
    public RadioButton partOutsourcedRBtn;

    @FXML
    public TextField partIdTxt;

    @FXML
    public TextField partNameTxt;

    @FXML
    public TextField partInvTxt;

    @FXML
    public TextField partPriceTxt;

    @FXML
    public TextField partMaxTxt;

    @FXML
    public TextField partMinTxt;

    @FXML
    public TextField partTypeTxt;

    @FXML
    public Label partType;

    @FXML
    public Label errorMessage;

    @FXML
    public Button homeButton;

    @FXML
    public Label errorMsg;

    private static Part selectedPart = null;

    /**
     * Grabs selected part from Main screen.
     *
     * @param part Selected part.
     */
    public static void getSelectedPart(Part part) {
        selectedPart = part;
    }

    /**
     * Initializes controller, fills text fields with starting values, and selects radio button.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partIdTxt.setText(String.valueOf(selectedPart.getId()));
        partNameTxt.setText(selectedPart.getName());
        partInvTxt.setText(String.valueOf(selectedPart.getStock()));
        partPriceTxt.setText(String.valueOf(selectedPart.getPrice()));
        partMaxTxt.setText(String.valueOf(selectedPart.getMax()));
        partMinTxt.setText(String.valueOf(selectedPart.getMin()));

        if(selectedPart instanceof InHouse) {
            partInHouseRBtn.setSelected(true);
            partInHouseRBtn.fireEvent(new ActionEvent());
            partTypeTxt.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if(selectedPart instanceof Outsourced) {
            partOutsourcedRBtn.setSelected(true);
            partOutsourcedRBtn.fireEvent(new ActionEvent());
            partTypeTxt.setText(((Outsourced) selectedPart).getCompanyName());
        }
    }

    /**
     * Sets label to "Machine ID"
     *
     * @param actionEvent In-House radio button clicked.
     */
    @FXML
    public void onInHouse(ActionEvent actionEvent) {
        partType.setText("Machine ID");
    }

    /**
     * Sets label to "Company Name"
     *
     * @param actionEvent Outsourced radio button clicked.
     */
    @FXML
    public void onOutsourced(ActionEvent actionEvent) {
        partType.setText("Company Name");
    }

    /**
     * Creates a new part and replaces old part.
     *
     * @param actionEvent Save button clicked.
     * @throws IOException Throws IO Exception.
     */
    @FXML
    public void savePart(ActionEvent actionEvent) throws IOException {
        displayError(0);
        try {
            int id = selectedPart.getId();
            String name = partNameTxt.getText();
            if (name.equals("")) {
                displayError(2);
                return;
            }
            double price = Double.parseDouble(partPriceTxt.getText());
            int stock = Integer.parseInt(partInvTxt.getText());
            int min = Integer.parseInt(partMinTxt.getText());
            int max = Integer.parseInt(partMaxTxt.getText());
            if (min < 0 || min > max) {
                displayError(3);
                return;
            }
            if (stock < min || stock > max) {
                displayError(4);
                return;
            }
            if (partOutsourcedRBtn.isSelected()) {
                String companyName = partTypeTxt.getText();
                Part newPart = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(newPart);
            }
            if (partInHouseRBtn.isSelected()) {
                try {
                    int machineId = Integer.parseInt(partTypeTxt.getText());
                    Part newPart = new InHouse(id, name, price, stock, min, max, machineId);
                    Inventory.addPart(newPart);
                } catch (Exception e) {
                    displayError(5);
                    return;
                }
            }
            Inventory.deletePart(selectedPart);
            homeButton.fireEvent(new ActionEvent());
        } catch (Exception e) {
            displayError(1);
        }
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
    //FIXME: can I change this to notnull?
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
