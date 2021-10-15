package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller logic for Modify Product page.
 *
 * @author Nicholas Balabis
 */
public class ModifyProductController implements Initializable {
    @FXML
    public TextField productNameTxt;

    @FXML
    public TextField productInvTxt;

    @FXML
    public TextField productPriceTxt;

    @FXML
    public TextField productMaxTxt;

    @FXML
    public TextField productMinTxt;

    @FXML
    public TextField partSearchTxt;

    @FXML
    public TableView<Part> partTableView;

    @FXML
    public TableColumn<Part, Integer> partIdCol;

    @FXML
    public TableColumn<Part, String> partNameCol;

    @FXML
    public TableColumn<Part, Integer> partInventoryCol;

    @FXML
    public TableColumn<Part, Double> partPriceCol;

    @FXML
    public TableView<Part> associatedPartTableView;

    @FXML
    public TableColumn<Part, Integer> associatedPartIdCol;

    @FXML
    public TableColumn<Part, String> associatedPartNameCol;

    @FXML
    public TableColumn<Part, Integer> associatedPartInventoryCol;

    @FXML
    public TableColumn<Part, Double> associatedPartPriceCol;

    @FXML
    public Button homeButton;

    @FXML
    public TextField productIdTxt;

    @FXML
    public Label errorMsg;

    private static Product selectedProduct = null;

    @FXML
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Grabs selected product from Main screen.
     *
     * @param product Selected product.
     */
    public static void getSelectedProduct(Product product) {
        selectedProduct = product;
    }

    /**
     * Initializes controller, fills text fields with starting values, and populates tables.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productIdTxt.setText(String.valueOf(selectedProduct.getId()));
        productNameTxt.setText(selectedProduct.getName());
        productInvTxt.setText(String.valueOf(selectedProduct.getStock()));
        productPriceTxt.setText(String.valueOf(selectedProduct.getPrice()));
        productMinTxt.setText(String.valueOf(selectedProduct.getMin()));
        productMaxTxt.setText(String.valueOf(selectedProduct.getMax()));

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedParts.addAll(selectedProduct.getAllAssociatedParts());
        associatedPartTableView.setItems(associatedParts);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Searches inventory for part with matching ID or partial string and displays results.
     *
     * @param actionEvent Part search field action event.
     */
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
            catch (NumberFormatException ignored){}
        }
        partTableView.setItems(parts);
        partSearchTxt.setText("");
    }

    /**
     * Adds a part to the Associated Parts table and list.
     *
     * @param actionEvent Add button clicked.
     */
    public void onAddAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
    }

    /**
     * Removes a part from the Associated Parts table and list.
     *
     * @param actionEvent Remove button clicked.
     */
    public void onRemoveAssociatedPart(ActionEvent actionEvent) {
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        associatedParts.remove(selectedPart);
    }

    /**
     * Creates a new Product and replaces old product.
     *
     * @param actionEvent Save button clicked.
     */
    public void onSaveProduct(ActionEvent actionEvent) {
        displayError(0);
        try {
            int id = Integer.parseInt(productIdTxt.getText());
            String name = productNameTxt.getText();
            if (name.equals("")) {
                displayError(2);
                return;
            }
            double price = Double.parseDouble(productPriceTxt.getText());
            int stock = Integer.parseInt(productInvTxt.getText());
            int min = Integer.parseInt(productMinTxt.getText());
            int max = Integer.parseInt(productMaxTxt.getText());
            if(min < 0 || min > max) {
                displayError(3);
                return;
            }
            if(stock < min || stock > max) {
                displayError(4);
                return;
            }
            Product newProduct = new Product(id, name, price, stock, min, max);
            Inventory.addProduct(newProduct);
            for (Part pt : associatedParts) {
                newProduct.addAssociatedPart(pt);
            }
            Inventory.deleteProduct(selectedProduct);
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
                errorMsg.setText("Product must have a valid name.");
                break;
            case 3:
                errorMsg.setText("Min cannot be < 0 or > max.");
                break;
            case 4:
                errorMsg.setText("Inv must be b/t min and max.");
                break;
        }
    }

    /**
     * Returns to mains screen.
     *
     * @param actionEvent Cancel button clicked.
     * @throws IOException Throws IO Exception.
     */
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/Main.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 760, 320);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
