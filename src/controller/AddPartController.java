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
import java.util.ResourceBundle;

/**
 * Controller logic for Add Part page.
 *
 * @author Nicholas Balabis
 */
public class AddPartController implements Initializable {
    @FXML
    private Label partType;

    @FXML
    private TextField partTypeTxt;

    @FXML
    private Label errorMessage; //FIXME: add error logic

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
     * @param url
     * @param resourceBundle
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
        int id = generateId();
        String name = partNameTxt.getText();
        System.out.println(name);
        int stock = Integer.parseInt(partInvTxt.getText());
        double price = Double.parseDouble(partPriceTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());

        if(partInHouseRBtn.isSelected()){
            int machineId = Integer.parseInt(partTypeTxt.getText());
            InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.addPart(newPart);
        }
        if(partOutsourcedRBtn.isSelected()){
            String companyName = partTypeTxt.getText();
            Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            Inventory.addPart(newPart);
        }
        homeButton.fireEvent(new ActionEvent());
    }

    /**
     * Generates a new unique Part ID.
     *
     * @return New part ID.
     */
    //FIXME: can I do this somewhere else, like Main?
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
     * Returns to main screen.
     *
     * @param actionEvent Cancel button clicked.
     * @throws IOException Throws IO Exception.
     */
    @FXML
    void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml")); //FIXME: can this be changed to .notnull
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

}
