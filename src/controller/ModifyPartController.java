package controller;

import javafx.event.ActionEvent;
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

public class ModifyPartController implements Initializable {
    public ToggleGroup partTG;
    public RadioButton partInHouseRBtn;
    public RadioButton partOutsourcedRBtn;
    public TextField partIdTxt;
    public TextField partNameTxt;
    public TextField partInvTxt;
    public TextField partPriceTxt;
    public TextField partMaxTxt;
    public TextField partMinTxt;
    public TextField partTypeTxt;
    public Label partType;
    public Label errorMessage;
    private static Part selectedPart = null;
    public Button homeButton;

    public static void getSelectedPart(Part part) {
        selectedPart = part;
    }

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

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    public void onInHouse(ActionEvent actionEvent) {
        partType.setText("Machine ID");
    }

    public void onOutsourced(ActionEvent actionEvent) {
        partType.setText("Company Name");
    }

    public void savePart(ActionEvent actionEvent) throws IOException {
        int id = selectedPart.getId();
        String name = partNameTxt.getText();
        Double price = Double.parseDouble(partPriceTxt.getText());
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
}
