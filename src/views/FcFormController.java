/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
/**
 * FXML Controller class
 *
 * @author Bruno
 */
package views;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Categoriascontas;
import models.Subcategorias;

public class FcFormController {

    
    @FXML
    private BorderPane root;
    
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<Categoriascontas> cbCtcFlc;

    @FXML
    private ComboBox<Subcategorias> cbSubCtc;

    @FXML
    private DatePicker dpDataFlc;

    @FXML
    private TextField tfCodFlc;

    @FXML
    private TextField tfDesFlc;

    @FXML
    private TextField tfValFlc;

    @FXML
    void btnCancelar(ActionEvent event) {
        loadUI("HomeScreen.fxml");
    }

    @FXML
    void btnSalvar(ActionEvent event) {

    }

    public void loadUI(String nomeArq) {
        try {
            Pane novaTela = (Pane) new FXMLLoader().load(getClass().getResource(nomeArq));
            root.setCenter(novaTela);
        } catch (IOException ex) {
            new ExceptionDisplay("Erro ao mudar para a tela:" + ex.getMessage());
        }
    }
}
