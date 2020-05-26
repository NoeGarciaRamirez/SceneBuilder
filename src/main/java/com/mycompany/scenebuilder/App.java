package com.mycompany.scenebuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    private static Scene scene;

    @Override
    public void start(Stage stage1) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("primary.fxml"));
        Parent root = fxmlLoader.load();
        
        emf = Persistence.createEntityManagerFactory("SceneBuilderPU");
        em = emf.createEntityManager();
        
        scene = new Scene(root, 300, 250);
        stage1.setTitle("Artículos Ordenador");
        stage1.setScene(scene);
        stage1.show();
        
        PrimaryController primaryController = (PrimaryController) fxmlLoader.getController(); 
        primaryController.setEntityManager(em);

        primaryController.cargarTodosArticulos();
    }

    @Override
public void stop() throws Exception {
    em.close(); 
    emf.close(); 
    try { 
        DriverManager.getConnection("jdbc:derby:BDcatalogoArticulosOrdenador;shutdown=true"); 
    } catch (SQLException ex) { 
    }        
}

    
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}