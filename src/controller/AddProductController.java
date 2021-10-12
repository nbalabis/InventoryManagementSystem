package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    public TextField productNameTxt;
    public TextField productInvTxt;
    public TextField productPriceTxt;
    public TextField productMaxTxt;
    public TextField productMinTxt;
    public TextField partSearchTxt;
    public TableView<Part> partTableView;
    public TableColumn partIdCol;
    public TableColumn partNameCol;
    public TableColumn partInventoryCol;
    public TableColumn partPriceCol;
    public TableView<Part> associatedPartTableView;
    public TableColumn associatedPartIdCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedPartInventoryCol;
    public TableColumn associatedPartPriceCol;
    public Button homeButton;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartTableView.setItems(associatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    public void getPartResults(ActionEvent actionEvent) {
        String query = partSearchTxt.getText();
        ObservableList<Part> parts = Inventory.lookupPart(query);
        if(parts.size() == 0){
            try {
                int id = Integer.parseInt(query);
                Part pt = Inventory.lookupPart(id);
                if (pt != null) {
                    parts.add(pt);
                }
            }
            catch (NumberFormatException e){}
        }
        partTableView.setItems(parts);
        partSearchTxt.setText("");
    }

    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);

    }

    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        associatedParts.remove(selectedPart);
    }

    public void onSaveProduct(ActionEvent actionEvent) {
        int id = generateId();
        String name = productNameTxt.getText();
        double price =  Double.parseDouble(productPriceTxt.getText());
        int stock = Integer.parseInt(productInvTxt.getText());
        int min = Integer.parseInt(productMinTxt.getText());
        int max = Integer.parseInt(productMaxTxt.getText());
        Product newProduct = new Product(id, name, price, stock, min, max);
        Inventory.addProduct(newProduct);
        for(Part pt : associatedParts) {
            newProduct.addAssociatedPart(pt);
        }
        homeButton.fireEvent(new ActionEvent());
    }

    private int generateId() {
        int maxId = 1000;
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product pd : allProducts) {
            if(pd.getId() > maxId) {
                maxId = pd.getId();
            }
        }
        return maxId + 1;
    }
}
