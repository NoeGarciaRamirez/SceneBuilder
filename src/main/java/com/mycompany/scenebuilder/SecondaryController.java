/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.scenebuilder;

import basededatos.Articulos;
import basededatos.Marcas;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

/**
 * FXML Controller class
 *
 * @author noegr
 */
public class SecondaryController implements Initializable {
    private Pane rootArticulosView;
    boolean errorFormato = false;
    
    private TableView tableViewPrevio;
    private Articulos articulo;
    private EntityManager entityManager;
    private boolean nuevoArticulo;
    
    @FXML
    private TextField textFieldCodigo;
    @FXML
    private TextField textFieldPais;
    @FXML
    private TextField textFieldContacto;
    @FXML
    private TextField textFieldTipoMarca;
    @FXML
    private ComboBox<Marcas> comboBoxMarcas;
    @FXML
    private AnchorPane rootArticulosDetalleView;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldDescripcion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionButtonCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootArticulosDetalleView.getScene().getRoot();
        rootMain.getChildren().remove(rootArticulosDetalleView);
        rootArticulosView.setVisible(true);
        
        entityManager.getTransaction().rollback();

        int numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
        tableViewPrevio.getFocusModel().focus(pos);
        tableViewPrevio.requestFocus();
    }

    @FXML
    private void onActionButtonGuardar2(ActionEvent event) {

        articulo.setNombre(textFieldNombre.getText());
        articulo.setDescripcion(textFieldDescripcion.getText());
        
        if (comboBoxMarcas.getValue() != null) {
            articulo.setMarca(comboBoxMarcas.getValue());
        } else {
            Alert alert = new Alert(AlertType.INFORMATION, "Debe indicar una provincia");
            alert.showAndWait();
            errorFormato = true;
        }
        
        if (!errorFormato) {
            try {
                if(nuevoArticulo) {
                    entityManager.persist(articulo);
                } else {
                    entityManager.merge(articulo);
                }
                entityManager.getTransaction().commit();

                StackPane rootMain = (StackPane)rootArticulosDetalleView.getScene().getRoot();
                rootMain.getChildren().remove(rootArticulosDetalleView);
                rootArticulosView.setVisible(true);

                int numFilaSeleccionada;
                if(nuevoArticulo) {
                    tableViewPrevio.getItems().add(articulo);
                    numFilaSeleccionada = tableViewPrevio.getItems().size() - 1;
                    tableViewPrevio.getSelectionModel().select(numFilaSeleccionada);
                    tableViewPrevio.scrollTo(numFilaSeleccionada);
                } else {
                    numFilaSeleccionada = tableViewPrevio.getSelectionModel().getSelectedIndex();
                    tableViewPrevio.getItems().set(numFilaSeleccionada, articulo);
                }
                TablePosition pos = new TablePosition(tableViewPrevio, numFilaSeleccionada, null);
                tableViewPrevio.getFocusModel().focus(pos);
                tableViewPrevio.requestFocus();

            } catch (RollbackException ex) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios. "
                        + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            }
        }
    }

    
    public void setRootArticulosView(Pane rootArticulosView) {
        this.rootArticulosView = rootArticulosView;
    }
    
    public void setTableViewPrevio(TableView tableViewPrevio) {
    this.tableViewPrevio = tableViewPrevio;
}
    
    public void setArticulo(EntityManager entityManager, Articulos articulo, boolean nuevoArticulo) {
    this.entityManager = entityManager;
    entityManager.getTransaction().begin();
    if(!nuevoArticulo) {
        this.articulo = entityManager.find(Articulos.class, articulo.getId());
    } else {
        this.articulo = articulo;
    }
    this.nuevoArticulo = nuevoArticulo;
    }
    
    public void mostrarDatos() {
        textFieldNombre.setText(articulo.getNombre());
        textFieldDescripcion.setText(articulo.getDescripcion());
        // Falta implementar el código para el resto de controles
        Query queryProvinciaFindAll = entityManager.createNamedQuery("Marcas.findAll");
        List listMarca = queryProvinciaFindAll.getResultList();
        comboBoxMarcas.setItems(FXCollections.observableList(listMarca));
        if (articulo.getMarca() != null) {
            comboBoxMarcas.setValue(articulo.getMarca());
        }
        
        
        
        comboBoxMarcas.setCellFactory((ListView<Marcas> l) -> new ListCell<Marcas>() {
            @Override
            protected void updateItem(Marcas marca, boolean empty) {
                super.updateItem(marca, empty);
                if (marca == null || empty) {
                    setText("");
                } else {
                    setText(marca.getCodigo() + "-" + marca.getNombreMarca());
                }
            }
        });
        // Formato para el valor mostrado actualmente como seleccionado
        comboBoxMarcas.setConverter(new StringConverter<Marcas>() {
            @Override
            public String toString(Marcas marca) {
                if (marca == null) {
                    return null;
                } else {
                    return marca.getCodigo() + "-" + marca.getNombreMarca();
                }
            }

            @Override
            public Marcas fromString(String userId) {
                return null;
            }
        });
        
        
        
    }
}
