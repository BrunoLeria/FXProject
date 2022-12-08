package views;

import exceptions.ExceptionDisplay;
import database.CategoriascontasDAO;
import database.FluxocaixaDAO;
import database.SubcategoriasDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Categoriascontas;
import models.Fluxocaixa;
import models.Subcategorias;

public class HomeScreenController {

    private FluxocaixaDAO dao;
    private CategoriascontasDAO ccdao = new CategoriascontasDAO();
    private SubcategoriasDAO subdao = new SubcategoriasDAO();
    private FluxocaixaDAO fldao = new FluxocaixaDAO();
    private ObservableList<Categoriascontas> listaCategorias;
    private ObservableList<Subcategorias> listaSubCategorias;
    private boolean edit;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddFluxo;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnCategorias;

    @FXML
    private Button btnDeleteFluxo;

    @FXML
    private Button btnEditFluxo;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnHelp;

    @FXML
    private Button btnReportFluxo;

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<?> cbCtcFlc;

    @FXML
    private ComboBox<?> cbFrmPag;

    @FXML
    private ComboBox<?> cbSubCtc;

    @FXML
    private DatePicker dpDataFlc;

    @FXML
    private TableColumn<?, ?> flcCategoria;

    @FXML
    private TableColumn<?, ?> flcCodigo;

    @FXML
    private TableColumn<?, ?> flcDataOcorrencia;

    @FXML
    private TableColumn<?, ?> flcDescricao;

    @FXML
    private TableColumn<?, ?> flcFormaPagamento;

    @FXML
    private TableColumn<?, ?> flcSubCategoria;

    @FXML
    private TableColumn<?, ?> flcValor;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<?> tableFluxo;

    @FXML
    private TextField tfCodFlc;

    @FXML
    private TextField tfDesFlc;

    @FXML
    private TextField tfValFlc;

    @FXML
    void addFluxo(ActionEvent event) {
        try {
            listaCategorias = FXCollections.observableArrayList(
                    ccdao.consultarTodas());
            listaSubCategorias = FXCollections.observableArrayList(
                    subdao.consultarTodas());
            if (listaCategorias.isEmpty() || listaSubCategorias.isEmpty()) {
                new ExceptionDisplay("Por favor, adicione pelo menos uma categoria e uma sub-categoria antes de cadastrar um fluxo.");
            } else {
                loadUI("FcForm.fxml");
            }
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao consultar as categorias ou subcategorias: " + ex.getMessage());
        }
    }

    @FXML
    void btnCancelar(ActionEvent event) {

    }

    @FXML
    void btnSalvar(ActionEvent event) {
        try {
            if (!tfDesFlc.getText().isEmpty() && !tfValFlc.getText().isEmpty() && !dpDataFlc.getValue().toString().isEmpty() && cbCtcFlc.getValue() != null) {
                int index = tfCodFlc.getText().isEmpty() ? 0 : Integer.parseInt(tfCodFlc.getText());
                LocalDate localDate = dpDataFlc.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);
                Fluxocaixa fc = new Fluxocaixa(index, tfDesFlc.getText(), date, BigDecimal.valueOf(Double.parseDouble(tfValFlc.getText())), index, cbCtcFlc.getValue(), cbSubCtc.getValue());
                if (isEdit()) {
                    fldao.editar(fc);
                } else {
                    fldao.inserir(fc);
                }
            } else {
                new ExceptionDisplay("Erro ao salvar: Campos faltando.");
            }
        } catch (Exception e) {
            new ExceptionDisplay("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    void deleteFluxo(ActionEvent event) {

    }

    @FXML
    void editFluxo(ActionEvent event) {

    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void openCat(ActionEvent event) {
        loadUI("CtcTable.fxml");
    }

    @FXML
    void openHelp(ActionEvent event) {

    }

    @FXML
    void openReport(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert btnAddFluxo != null : "fx:id=\"btnAddFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnCategorias != null : "fx:id=\"btnCategorias\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnDeleteFluxo != null : "fx:id=\"btnDeleteFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnEditFluxo != null : "fx:id=\"btnEditFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnHelp != null : "fx:id=\"btnHelp\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnReportFluxo != null : "fx:id=\"btnReportFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert btnSalvar != null : "fx:id=\"btnSalvar\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert cbCtcFlc != null : "fx:id=\"cbCtcFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert cbFrmPag != null : "fx:id=\"cbFrmPag\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert cbSubCtc != null : "fx:id=\"cbSubCtc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert dpDataFlc != null : "fx:id=\"dpDataFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcCategoria != null : "fx:id=\"flcCategoria\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcCodigo != null : "fx:id=\"flcCodigo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcDataOcorrencia != null : "fx:id=\"flcDataOcorrencia\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcDescricao != null : "fx:id=\"flcDescricao\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcFormaPagamento != null : "fx:id=\"flcFormaPagamento\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcSubCategoria != null : "fx:id=\"flcSubCategoria\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert flcValor != null : "fx:id=\"flcValor\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tableFluxo != null : "fx:id=\"tableFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tfCodFlc != null : "fx:id=\"tfCodFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tfDesFlc != null : "fx:id=\"tfDesFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tfValFlc != null : "fx:id=\"tfValFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";

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

//     @FXML
//    private TableColumn<Fluxocaixa, String> flcCategoria;
//
//    @FXML
//    private TableColumn<Fluxocaixa, Integer> flcCodigo;
//
//    @FXML
//    private TableColumn<Fluxocaixa, Date> flcDataOcorrencia;
//
//    @FXML
//    private TableColumn<Fluxocaixa, String> flcDescricao;
//
//    @FXML
//    private TableColumn<Fluxocaixa, String> flcFormaPagamento;
//
//    @FXML
//    private TableColumn<Fluxocaixa, String> flcSubCategoria;
//
//    @FXML
//    private TableColumn<Fluxocaixa, Double> flcValor;
}