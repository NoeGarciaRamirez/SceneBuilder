package com.mycompany.scenebuilder;

import basededatos.Articulos;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/*
@Override
public void initialize(URL url, ResourceBundle rb) {
    NOMBRE.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
    DESCRIPCION.setCellValueFactory(new PropertyValueFactory<>("Descripción"));
    NOMBREMARCA.setCellValueFactory(new PropertyValueFactory<>("Nombre de marca"));
}    
*/
public class PrimaryController implements Initializable{

    private EntityManager entityManager;
    @FXML
    private TableView<Articulos> articulo;
    @FXML
    private TableColumn<Articulos, String> nombre;
    @FXML
    private TableColumn<Articulos, String> descripcion;
    @FXML
    private TableColumn<Articulos, String> nombreMarca;
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nombre.setCellValueFactory(new PropertyValueFactory<>("Nombre"));
        descripcion.setCellValueFactory(new PropertyValueFactory<>("Descripcion"));
        nombreMarca.setCellValueFactory(new PropertyValueFactory<>("Nombre de marca"));
    }  

    public void cargarTodosArticulos() {
        Query queryArticulosFindAll = entityManager.createNamedQuery("Articulos.findAll");
        List<Articulos> listArticulos = queryArticulosFindAll.getResultList();
        articulo.setItems(FXCollections.observableArrayList(listArticulos));
    }  
    
}
