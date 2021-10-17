package controller;

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
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Control logic for main screen.
 *
 * @author Nicholas Balabis
 */
public class MainController implements Initializable {
    @FXML
    public TextField partSearchTxt;

    @FXML
    public TextField productSearchTxt;

    @FXML
    public Label errorMsg;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInventoryCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    /**
     * Initializes controller and fills tables with starting values.
     *
     * @param url url
     * @param resourceBundle resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData(); //FIXME: delete after testing --or move to main and comment out
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partTableView.getSortOrder().add(partIdCol);
        partTableView.sort();

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productTableView.getSortOrder().add(productIdCol);
        productTableView.sort();
    }

    /**
     * Loads and displays the Add Part window.
     *
     * @param actionEvent Add Part button clicked.
     * @throws IOException Throws IO exception.
     */
    @FXML
    void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddPart.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 490, 345);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads and displays the Add Product window.
     *
     * @param actionEvent Add Product button clicked.
     * @throws IOException Throws IO exception.
     */
    @FXML
    void onAddProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AddProduct.fxml")));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 475);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads and displays the Modify Part window.
     *
     * @param actionEvent Modify Part button clicked.
     * @throws IOException Throws IO exception.
     */
    @FXML
    void onModifyPart(ActionEvent actionEvent) throws IOException{
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            displayError(1);
        } else {
            ModifyPartController.getSelectedPart(selectedPart);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyPart.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 490, 345);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Loads and displays Modify Product window.
     *
     * @param actionEvent Modify Product button clicked.
     * @throws IOException Throws IO exception.
     */
    @FXML
    void onModifyProduct(ActionEvent actionEvent) throws IOException{
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            displayError(2);
        } else {
            ModifyProductController.getSelectedProduct(selectedProduct);
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyProduct.fxml")));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 475);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Removes a Part from inventory and partTableView.
     *
     * @param actionEvent Delete part button clicked.
     */
    @FXML
    public void onDeletePart(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            displayError(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to delete the selected part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
                partTableView.setItems(Inventory.getAllParts());
                displayError(0);
            }
        }
    }

    /**
     * Removes a Product from inventory and productTableView.
     *
     * @param actionEvent Delete product button clicked.
     */
    @FXML
    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            displayError(4);
        } else if(selectedProduct.getAllAssociatedParts().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning");
            alert.setContentText("Are you sure you want to delete the selected product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
                productTableView.setItems(Inventory.getAllProducts());
                displayError(0);
            }
        } else {
            displayError(5);
        }
    }

/*FIXME: Test Data */
    private static boolean firstTime = true;

    private void addTestData() {
        if(!firstTime) {
            return;
        }
        firstTime = false;

        InHouse testPart1 = new InHouse(1, "Brakes", 15.00, 10, 2, 20, 5978);
        Inventory.addPart(testPart1);
        InHouse testPart2 = new InHouse(2, "Wheel", 11.00, 16, 8, 40, 2679);
        Inventory.addPart(testPart2);
        Outsourced testPart3 = new Outsourced(3, "Seat", 15.00, 10, 5, 15, "SeatsRUs");
        Inventory.addPart(testPart3);
        Product testProduct1 = new Product(1000, "Giant Bike", 299.99, 5, 5, 10);
        Inventory.addProduct(testProduct1);
        testProduct1.addAssociatedPart(testPart1);
        testProduct1.addAssociatedPart(testPart2);
        testProduct1.addAssociatedPart(testPart2);
        testProduct1.addAssociatedPart(testPart3);
        Product testProduct2 = new Product(1001, "Tricycle", 99.99, 3, 1, 10);
        Inventory.addProduct(testProduct2);
        testProduct2.addAssociatedPart(testPart2);
        testProduct2.addAssociatedPart(testPart2);
        testProduct2.addAssociatedPart(testPart2);
        testProduct2.addAssociatedPart(testPart3);
    }

    /**
     * Searches inventory for part with matching ID or partial string and displays results.
     *
     * @param actionEvent Part search field action event.
     */
    @FXML
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
            catch (NumberFormatException ignored){
            }
        }

        partTableView.setItems(parts);
        partSearchTxt.setText("");
    }

    /**
     * Searches inventory for product with matching ID or partial string and displays results.
     *
     * @param actionEvent Product search field action event.
     */
    @FXML
    public void getProductResults(ActionEvent actionEvent) {
        String query = productSearchTxt.getText();
        ObservableList<Product> products = Inventory.lookupProduct(query);

        if(products.size() == 0){
            try {
                int id = Integer.parseInt(query);
                Product pd = Inventory.lookupProduct(id);
                if (pd != null) {
                    products.add(pd);
                }
            }
            catch (NumberFormatException e){
                //ignore
            }
        }

        productTableView.setItems(products);
        productSearchTxt.setText("");
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
                errorMsg.setText("Select a part to modify.");
                break;
            case 2:
                errorMsg.setText("Select a product to modify.");
                break;
            case 3:
                errorMsg.setText("Select a part to delete.");
                break;
            case 4:
                errorMsg.setText("Select a product to delete.");
                break;
            case 5:
                errorMsg.setText("Cannot delete a product with associated parts.");
                break;
        }
    }

    /**
     * Exits program.
     *
     * @param actionEvent Exit button clicked.
     */
    @FXML
    public void closeProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
