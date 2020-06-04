package com.mycompany.scenebuilder;

import basededatos.Articulos;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class PrimaryController implements Initializable{
    private Articulos articuloSeleccionado;

    private EntityManager entityManager;
    @FXML
    private TableView<Articulos> articulo;
    @FXML
    private TableColumn<Articulos, String> nombre;
    @FXML
    private TableColumn<Articulos, String> descripcion;
    @FXML
    private TableColumn<Articulos, String> marca;
    @FXML
    private TextField textFieldNombre;
    //private TextField textFieldMarca;
    @FXML
    private TextField textFieldDescripcion;
    @FXML
    private AnchorPane rootArticulosView;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        marca.setCellValueFactory(new PropertyValueFactory<>("Id de la marca"));
        marca.setCellValueFactory(
            cellData -> {
                SimpleStringProperty property = new SimpleStringProperty();
                if (cellData.getValue().getMarca() != null) {
                    property.setValue(cellData.getValue().getMarca().getNombreMarca());
                }
                return property;
            });
        
        articulo.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            articuloSeleccionado = newValue;
            if (articuloSeleccionado != null) {
                textFieldNombre.setText(articuloSeleccionado.getNombre());
                textFieldDescripcion.setText(articuloSeleccionado.getDescripcion());
            } else {
                textFieldNombre.setText("");
                textFieldDescripcion.setText("");
            }
        });
        
    }  

    public void cargarTodosArticulos() {
        Query queryArticulosFindAll = entityManager.createNamedQuery("Articulos.findAll");
        List<Articulos> listArticulos = queryArticulosFindAll.getResultList();
        articulo.setItems(FXCollections.observableArrayList(listArticulos));
    }  

    @FXML
    private void onActionButtonGuardar(ActionEvent event) {
        if (articuloSeleccionado != null) {
            articuloSeleccionado.setNombre(textFieldNombre.getText());
            articuloSeleccionado.setDescripcion(textFieldDescripcion.getText());
            entityManager.getTransaction().begin();
            entityManager.merge(articuloSeleccionado);
            entityManager.getTransaction().commit();

            int numFilaSeleccionada = articulo.getSelectionModel().getSelectedIndex();
            articulo.getItems().set(numFilaSeleccionada, articuloSeleccionado);
            TablePosition pos = new TablePosition(articulo, numFilaSeleccionada, null);
            articulo.getFocusModel().focus(pos);
            articulo.requestFocus();
        }
    } 

    @FXML
    private void onActionButtonNuevo(ActionEvent event) {
        
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            Parent rootDetalleView = fxmlLoader.load();
            
            rootArticulosView.setVisible(false);
            
            StackPane rootMain = (StackPane)rootArticulosView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            
            SecondaryController secondaryController = (SecondaryController) fxmlLoader.getController();  
            secondaryController.setRootArticulosView(rootArticulosView);
            secondaryController.setTableViewPrevio(articulo);
            
            articuloSeleccionado = new Articulos();
            secondaryController.setArticulo(entityManager, articuloSeleccionado, true);
            secondaryController.mostrarDatos();
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void onActionButtonEditar(ActionEvent event) {
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            Parent rootDetalleView = fxmlLoader.load();
            rootArticulosView.setVisible(false);
            StackPane rootMain = (StackPane)rootArticulosView.getScene().getRoot();
            rootMain.getChildren().add(rootDetalleView);
            SecondaryController secondaryController = (SecondaryController) fxmlLoader.getController();  
            secondaryController.setRootArticulosView(rootArticulosView);
            secondaryController.setTableViewPrevio(articulo);
            secondaryController.setArticulo(entityManager, articuloSeleccionado, false);
            secondaryController.mostrarDatos();
        } catch (IOException ex) {
            Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionButtonSuprimir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea suprimir el siguiente registro?");
        alert.setContentText(articuloSeleccionado.getNombre());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            entityManager.getTransaction().begin();
            articuloSeleccionado = entityManager.merge(articuloSeleccionado);
            entityManager.remove(articuloSeleccionado);
            entityManager.getTransaction().commit();

            articulo.getItems().remove(articuloSeleccionado);

            articulo.getFocusModel().focus(null);
            articulo.requestFocus();
        } else {
            int numFilaSeleccionada = articulo.getSelectionModel().getSelectedIndex();
            articulo.getItems().set(numFilaSeleccionada, articuloSeleccionado);
            TablePosition pos = new TablePosition(articulo, numFilaSeleccionada, null);
            articulo.getFocusModel().focus(pos);
            articulo.requestFocus();   
        }
    }
}
