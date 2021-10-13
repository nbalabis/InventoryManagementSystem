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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public TextField partSearchTxt;

    public TextField productSearchTxt;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partInventoryCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productInventoryCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    public Label partErrorMsg;

    public Label productErrorMsg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData(); //FIXME: delete after testing
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

    @FXML
    void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 490, 345);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onAddProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 700, 475);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void onModifyPart(ActionEvent actionEvent) throws IOException{
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            partErrorMsg.setText("Select a part to modify.");
        } else {
            ModifyPartController.getSelectedPart(selectedPart);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 490, 345);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void onModifyProduct(ActionEvent actionEvent) throws IOException{
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            productErrorMsg.setText("Select a product to modify.");
        } else {
            ModifyProductController.getSelectedProduct(selectedProduct);
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 700, 475);
            stage.setTitle("Modify Product");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onDeletePart(ActionEvent actionEvent) {
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
        partTableView.setItems(Inventory.getAllParts());
    }

    public void onDeleteProduct(ActionEvent actionEvent) {
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
        Inventory.deleteProduct(selectedProduct);
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
            catch (NumberFormatException e){
                //ignore
            }
        }

        partTableView.setItems(parts);
        partSearchTxt.setText("");
    }

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

    public void closeProgram(ActionEvent actionEvent) {
        System.exit(0);
    }
}
