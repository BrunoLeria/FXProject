package views;

import database.FluxocaixaDAO;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import models.Fluxocaixa;

public class HomeScreenController {

    private FluxocaixaDAO dao;

    @FXML
    private Button btnAddFluxo;

    @FXML
    private Button btnCategorias;

    @FXML
    private Button btnDeleteFluxo;

    @FXML
    private Button btnEditFluxo;

    @FXML
    private Button btnExit;

    @FXML
    private TableColumn<Fluxocaixa, String> flcCategoria;

    @FXML
    private TableColumn<Fluxocaixa, Integer> flcCodigo;

    @FXML
    private TableColumn<Fluxocaixa, Date> flcDataOcorrencia;

    @FXML
    private TableColumn<Fluxocaixa, String> flcDescricao;

    @FXML
    private TableColumn<Fluxocaixa, String> flcFormaPagamento;

    @FXML
    private TableColumn<Fluxocaixa, String> flcSubCategoria;

    @FXML
    private TableColumn<Fluxocaixa, Double> flcValor;

    @FXML
    private TableView<Fluxocaixa> tableFluxo;

    @FXML
    private BorderPane root;

    @FXML
    void openCat(ActionEvent event) {
        loadUI("CtcTable.fxml");
    }

    @FXML
    void addFluxo(ActionEvent event) {
        loadUI("FcForm.fxml");
    }

    @FXML
    void deleteFluxo(ActionEvent event) {
        
    }

    @FXML
    void editFluxo(ActionEvent event) {
        loadUI("FcForm.fxml");
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void initialize() {
        try {
            dao = new FluxocaixaDAO();

            flcCategoria = new TableColumn<>("flcCategoria");
            flcCodigo = new TableColumn<>("flcCodigo");
            flcDataOcorrencia = new TableColumn<>("flcDataOcorrencia");
            flcDescricao = new TableColumn<>("flcDescricao");
            flcFormaPagamento = new TableColumn<>("flcFormaPagamento");
            flcSubCategoria = new TableColumn<>("flcSubCategoria");
            flcValor = new TableColumn<>("flcValor");
            tableFluxo.getItems().addAll(dao.consultarTodas());
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao iniciar o controlador:" + ex.getMessage());
        }

    }

    /**
     * Método para carregar a interface FXML em janela a parte
     *
     * @param nomeArq
     */
    private void openTela(String name) {

        try {
            System.out.println("Abrindo a Tela: " + name);
            Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource(name));
            Scene scene = new Scene(parent);
            Stage secondStage = new Stage();
            secondStage.initOwner((Stage) root.getScene().getWindow());
            secondStage.initModality(Modality.WINDOW_MODAL);

            secondStage.setScene(scene);
            secondStage.show();
            secondStage.setOnCloseRequest(e -> secondStage.close());
        } catch (IOException ex) {
            new ExceptionDisplay("Erro ao abrir a tela" + name
                    + ":" + ex.getMessage());
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para carregar a interface FXML no painel central
     *
     * @param nomeArq
     */
    public void loadUI(String nomeArq) {
        try {
            Pane novaTela = (Pane) new FXMLLoader().load(getClass().getResource(nomeArq));
            root.setCenter(novaTela);
        } catch (IOException ex) {
            new ExceptionDisplay("Erro ao mudar para a tela:" + ex.getMessage());
        }
    }
}
