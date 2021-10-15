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
     * @param url
     * @param resourceBundle
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
        int id = selectedPart.getId();
        String name = partNameTxt.getText();
        double price = Double.parseDouble(partPriceTxt.getText());
        int stock = Integer.parseInt(partInvTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());
        if(partOutsourcedRBtn.isSelected()) {
            String companyName = partTypeTxt.getText();
            Part newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            Inventory.addPart(newPart);
        }
        if(partInHouseRBtn.isSelected()) {
            int machineId = Integer.parseInt(partTypeTxt.getText());
            Part newPart = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.addPart(newPart);
        }
        Inventory.deletePart(selectedPart);
        homeButton.fireEvent(new ActionEvent());
    }

    /**
     * Returns to main screen.
     *
     * @param actionEvent Cancel button clicked.
     * @throws IOException Throws IO Exception.
     */
    //FIXME: can I change this to notnull?
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
