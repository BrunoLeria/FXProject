/**
 * Sample Skeleton for 'CtcTable.fxml' Controller Class
 */

package views;

import database.FluxocaixaDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Fluxocaixa;

public class CtcTableController {
    FluxocaixaDAO dao = new FluxocaixaDAO();
    
    @FXML
    private BorderPane root;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAddCategoria"
    private Button btnAddCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="btnDeleteCategoria"
    private Button btnDeleteCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="btnEditCategoria"
    private Button btnEditCategoria; // Value injected by FXMLLoader

    @FXML // fx:id="btnExit"
    private Button btnExit; // Value injected by FXMLLoader

    @FXML // fx:id="tableCategoria"
    private TableView<Fluxocaixa> tableCategoria; // Value injected by FXMLLoader

    @FXML
    void addCategoria(ActionEvent event) {
        loadUI("CtcForm.fxml");
    }

    @FXML
    void deleteCategoria(ActionEvent event) {

    }

    @FXML
    void editCategoria(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        loadUI("HomeScreen.fxml");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAddCategoria != null : "fx:id=\"btnAddCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnDeleteCategoria != null : "fx:id=\"btnDeleteCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnEditCategoria != null : "fx:id=\"btnEditCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tableCategoria != null : "fx:id=\"tableCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        
        try {
            tableCategoria.getItems().addAll(dao.consultarTodas());
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao montar a lista de categorias:" + ex.getMessage());
        }
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
