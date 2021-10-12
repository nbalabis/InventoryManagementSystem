package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class AddPartController implements Initializable {

    public Label partType;
    public TextField partTypeTxt;
    public Label errorMessage;
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

    @FXML
    void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onInHouse(ActionEvent actionEvent) {
        partType.setText("Machine ID");
    }

    public void onOutsourced(ActionEvent actionEvent) {
        partType.setText("Company Name");
    }
    //FIXME: catch empty values & incorrect entries
    public void savePart(ActionEvent actionEvent) {
        int id = generateId();
        String name = partNameTxt.getText();
        int stock = Integer.parseInt(partInvTxt.getText());
        double price = Double.parseDouble(partPriceTxt.getText());
        int min = Integer.parseInt(partMinTxt.getText());
        int max = Integer.parseInt(partMaxTxt.getText());

        if(partInHouseRBtn.isSelected()){
            int machineId = Integer.parseInt(partTypeTxt.getText());
            InHouse newPart = new InHouse(id, name, price, stock, min, max, machineId);
            Inventory.addPart(newPart);
        } else {
            String companyName = partTypeTxt.getText();
            Outsourced newPart = new Outsourced(id, name, price, stock, min, max, companyName);
            Inventory.addPart(newPart);
        }
        partNameTxt.setText("");
        partInvTxt.setText("");
        partPriceTxt.setText("");
        partMinTxt.setText("");
        partMaxTxt.setText("");
        partTypeTxt.setText("");
        errorMessage.setText("New part saved successfully.");
        //FIXME: I think I'd rather this just automatically go back to main like 'Add Product' page
    }

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

}
