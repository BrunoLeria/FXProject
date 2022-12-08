package views;

import components.ColumnFormatter;
import exceptions.ExceptionDisplay;
import database.CategoriascontasDAO;
import database.FluxocaixaDAO;
import database.SubcategoriasDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import models.Categoriascontas;
import models.Fluxocaixa;
import models.Pagamento;
import models.Subcategorias;

public class HomeScreenController {

    private FluxocaixaDAO dao;
    private CategoriascontasDAO ccdao = new CategoriascontasDAO();
    private SubcategoriasDAO subdao = new SubcategoriasDAO();
    private FluxocaixaDAO fldao = new FluxocaixaDAO();
    private ObservableList<Fluxocaixa> listaFluxos;
    private ObservableList<Categoriascontas> listaCategorias;
    private ObservableList<Subcategorias> listaSubCategorias;
    private ObservableList<Pagamento> listaPagamento;
    private ObservableList<String> listaCategoriasDesc;
    private ObservableList<String> listaSubCategoriasDesc;
    private Categoriascontas categoriaEscolhida;
    private Subcategorias subCategoriaEscolhida;
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
    private ComboBox<String> cbCtcFlc;

    @FXML
    private ComboBox<Pagamento> cbFrmPag;

    @FXML
    private ComboBox<String> cbSubCtc;

    @FXML
    private DatePicker dpDataFlc;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem miAddFluxo;

    @FXML
    private MenuItem miCancelar;

    @FXML
    private MenuItem miCategorias;

    @FXML
    private MenuItem miDeleteFluxo;

    @FXML
    private MenuItem miEditFluxo;

    @FXML
    private MenuItem miExit;

    @FXML
    private MenuItem miHelp;

    @FXML
    private MenuItem miReportFluxo;

    @FXML
    private MenuItem miSalvar;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<Fluxocaixa> tableFluxo;

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
                setEdit(false);
                clearFields();
                setupTextFieldsAndButtonsFlc(false);
            }
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao consultar as categorias ou subcategorias: " + ex.getMessage());
        }
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        setEdit(false);
        setupTextFieldsAndButtonsFlc(true);
    }

    @FXML
    void btnSalvar(ActionEvent event) {
        try {
            if (!tfDesFlc.getText().isEmpty() && !tfValFlc.getText().isEmpty() && !dpDataFlc.getValue().toString().isEmpty() && cbCtcFlc.getValue() != null) {
                int index = listaFluxos.isEmpty() ? 1 : (!tfCodFlc.getText().isEmpty() ? Integer.parseInt(tfCodFlc.getText()) : listaFluxos.toArray().length + 1);
                listaCategorias.forEach((categoria) -> {
                    if (categoria.getCtcDescricao().equals(cbCtcFlc.getValue())) {
                        categoriaEscolhida = categoria;
                    }
                });

                listaSubCategorias.forEach((subCategoria) -> {
                    if (subCategoria.getSbcDescricao().equals(cbSubCtc.getValue())) {
                        subCategoriaEscolhida = subCategoria;
                    }
                });
                LocalDate localDate = dpDataFlc.getValue();
                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);
                Fluxocaixa fc = new Fluxocaixa(index, tfDesFlc.getText(), date, BigDecimal.valueOf(Double.parseDouble(tfValFlc.getText())), cbFrmPag.getValue().selecionarPagamento(), categoriaEscolhida, subCategoriaEscolhida);
                if (isEdit()) {
                    fldao.editar(fc);
                } else {
                    fldao.inserir(fc);
                }
                loadUI("HomeScreen.fxml");
            } else {
                new ExceptionDisplay("Erro ao salvar: Campos faltando.");
            }
        } catch (Exception e) {
            new ExceptionDisplay("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    void deleteFluxo(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Você tem certeza que quer deletar essa fluxo de caixa?", "Você tem certeza?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                fldao.excluir(Integer.parseInt(tfCodFlc.getText()));
                loadUI("HomeScreen.fxml");
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao deletar a categoria:" + ex.getMessage());
            }

        }
    }

    @FXML
    void editFluxo(ActionEvent event) {
        setEdit(true);
        setupTextFieldsAndButtonsFlc(false);
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
        loadUI("HelpScreen.fxml");
    }

    @FXML
    void openReport(ActionEvent event) {
        loadUI("ReportScreen.fxml");
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
        assert menubar != null : "fx:id=\"menubar\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miAddFluxo != null : "fx:id=\"miAddFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miCancelar != null : "fx:id=\"miCancelar\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miCategorias != null : "fx:id=\"miCategorias\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miDeleteFluxo != null : "fx:id=\"miDeleteFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miEditFluxo != null : "fx:id=\"miEditFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miReportFluxo != null : "fx:id=\"miReportFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert miSalvar != null : "fx:id=\"miSalvar\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tableFluxo != null : "fx:id=\"tableFluxo\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tfCodFlc != null : "fx:id=\"tfCodFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tfDesFlc != null : "fx:id=\"tfDesFlc\" was not injected: check your FXML file 'HomeScreen.fxml'.";
        assert tfValFlc != null : "fx:id=\"tfValFlc\" was    not injected: check your FXML file 'HomeScreen.fxml'.";

        try {

            addListenerForTableFluxo();
            addListenerForComboBoxCategorias();
            updateLists();
            setupTableFluxo();
            setupTextFieldsAndButtonsFlc(true);
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao montar a lista de fluxo de caixa:" + ex.getMessage());
        }
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    /**
     * Método para carregar a interface FXML no painel central
     *
     * @param nomeArq
     */
    public void loadUI(String nomeArq) {
        try {
            root.getChildren().remove(menubar);
            Pane novaTela = (Pane) new FXMLLoader().load(getClass().getResource(nomeArq));
            root.setCenter(novaTela);
        } catch (IOException ex) {
            new ExceptionDisplay("Erro ao mudar para a tela:" + ex.getMessage());
        }
    }

    private void updateLists() throws Exception {
        if (listaFluxos != null) {
            listaFluxos.clear();
        }
        listaFluxos = FXCollections.observableArrayList(
                fldao.consultarTodas());

        if (listaCategorias != null) {
            listaCategorias.clear();
        }
        listaCategorias = FXCollections.observableArrayList(
                ccdao.consultarTodas());
        listaCategoriasDesc = FXCollections.observableArrayList();
        listaCategorias.forEach((categoria) -> {
            listaCategoriasDesc.add(categoria.getCtcDescricao());
        });

        if (listaSubCategorias != null) {
            listaSubCategorias.clear();
        }
        listaSubCategorias = FXCollections.observableArrayList(
                subdao.consultarTodas());
        listaSubCategoriasDesc = FXCollections.observableArrayList();

        if (listaPagamento != null) {
            listaPagamento.clear();
        }
        listaPagamento = FXCollections.observableArrayList(Pagamento.values());
    }

    private void setupTableFluxo() throws Exception {
        TableColumn<Fluxocaixa, String> tc_flc_categoria = new TableColumn<>();
        tc_flc_categoria.setText("Categoria");
        tc_flc_categoria.setPrefWidth(100.0);
        tc_flc_categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkCtcCodigo().getCtcDescricao()));

        TableColumn<Fluxocaixa, Integer> tc_flc_codigo = new TableColumn<>();
        tc_flc_codigo.setText("Código");
        tc_flc_codigo.setPrefWidth(61.0);
        tc_flc_codigo.setCellValueFactory(new PropertyValueFactory("flcCodigo"));

        TableColumn<Fluxocaixa, Date> tc_flc_data_ocorrencia = new TableColumn<>();
        tc_flc_data_ocorrencia.setText("Data");
        tc_flc_data_ocorrencia.setPrefWidth(75.0);
        tc_flc_data_ocorrencia.setCellValueFactory(new PropertyValueFactory("flcDataOcorrencia"));
        tc_flc_data_ocorrencia.setCellFactory(new ColumnFormatter<>(new SimpleDateFormat("dd/MM/YYYY")));

        TableColumn<Fluxocaixa, String> tc_flc_descricao = new TableColumn<>();
        tc_flc_descricao.setText("Descrição");
        tc_flc_descricao.setPrefWidth(190.0);
        tc_flc_descricao.setCellValueFactory(new PropertyValueFactory("flcDescricao"));

        TableColumn<Fluxocaixa, String> tc_flc_forma_pagamento = new TableColumn<>();
        tc_flc_forma_pagamento.setText("Forma de Pagamento");
        tc_flc_forma_pagamento.setPrefWidth(136.0);
        tc_flc_forma_pagamento.setCellValueFactory(cellData -> new SimpleStringProperty(Pagamento.values()[cellData.getValue().getFlcFormaPagamento()].getNome()));

        TableColumn<Fluxocaixa, String> tc_flc_sub_categoria = new TableColumn<>();
        tc_flc_sub_categoria.setText("Sub-categoria");
        tc_flc_sub_categoria.setPrefWidth(136.0);
        tc_flc_sub_categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkSbcCodigo().getSbcDescricao()));

        TableColumn<Fluxocaixa, String> tc_flc_is_profit_flow = new TableColumn<>();
        tc_flc_is_profit_flow.setText("Lucro/Despesa");
        tc_flc_is_profit_flow.setPrefWidth(100.0);
        tc_flc_is_profit_flow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkCtcCodigo().getCtcPositva() ? "Lucro" : "Despesa"));

        TableColumn<Fluxocaixa, Double> tc_flc_valor = new TableColumn<>();
        tc_flc_valor.setText("Valor");
        tc_flc_valor.setPrefWidth(60.0);
        tc_flc_valor.setCellValueFactory(new PropertyValueFactory("flcValor"));

        tableFluxo.getColumns().addAll(tc_flc_codigo,
                tc_flc_descricao,
                tc_flc_data_ocorrencia,
                tc_flc_forma_pagamento,
                tc_flc_categoria,
                tc_flc_sub_categoria,
                tc_flc_is_profit_flow,
                tc_flc_valor);
        tableFluxo.setItems(listaFluxos);

    }

    private void setupTextFieldsAndButtonsFlc(boolean clear) {
        tfCodFlc.setDisable(true);
        tfDesFlc.setDisable(clear);
        tfValFlc.setDisable(clear);
        cbCtcFlc.setDisable(clear);
        cbFrmPag.setDisable(clear);
        cbSubCtc.setDisable(clear);
        btnSalvar.setDisable(clear);
        btnCancelar.setDisable(clear);
        miSalvar.setDisable(clear);
        miCancelar.setDisable(clear);

        dpDataFlc.setDisable(clear);
        if (clear) {
            clearFields();
        }
    }

    private void clearFields() {
        btnEditFluxo.setDisable(true);
        btnDeleteFluxo.setDisable(true);
        miEditFluxo.setDisable(true);
        miDeleteFluxo.setDisable(true);
        cbCtcFlc.setItems(null);
        cbCtcFlc.setItems(listaCategoriasDesc);
        cbFrmPag.setItems(null);
        cbFrmPag.setItems(listaPagamento);
        tfCodFlc.setText("");
        tfDesFlc.setText("");
        tfValFlc.setText("");
        dpDataFlc.setValue(LocalDate.now());
        cbCtcFlc.getSelectionModel().selectFirst();
        cbFrmPag.getSelectionModel().selectFirst();
        if (!listaSubCategoriasDesc.isEmpty()) {
            cbSubCtc.setItems(null);
            cbSubCtc.setItems(listaSubCategoriasDesc);
            cbSubCtc.getSelectionModel().selectFirst();
        }
    }

    private void addListenerForTableFluxo() {
        tableFluxo.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnEditFluxo.setDisable(false);
                btnDeleteFluxo.setDisable(false);
                miEditFluxo.setDisable(false);
                miDeleteFluxo.setDisable(false);
                tfCodFlc.setText(String.valueOf(newSelection.getFlcCodigo()));
                tfDesFlc.setText(newSelection.getFlcDescricao());
                tfValFlc.setText(String.valueOf(newSelection.getFlcValor()));
                dpDataFlc.setValue(newSelection.getFlcDataOcorrencia().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
                listaCategorias.forEach((categoria) -> {
                    if (categoria.getCtcDescricao().equals(newSelection.getFlcFkCtcCodigo().getCtcDescricao())) {
                        categoriaEscolhida = categoria;
                    }
                });
                cbCtcFlc.getSelectionModel().select(categoriaEscolhida.getCtcDescricao());
                listaSubCategorias.forEach((subCategoria) -> {
                    if (subCategoria.getSbcDescricao().equals(newSelection.getFlcFkSbcCodigo().getSbcDescricao())) {
                        subCategoriaEscolhida = subCategoria;
                    }
                });
                cbSubCtc.getSelectionModel().select(subCategoriaEscolhida.getSbcDescricao());
                cbFrmPag.getSelectionModel().select(newSelection.getFlcFormaPagamento());
            } else {
                btnEditFluxo.setDisable(true);
                btnDeleteFluxo.setDisable(true);
                miEditFluxo.setDisable(true);
                miDeleteFluxo.setDisable(true);
                tfCodFlc.setText("");
                tfDesFlc.setText("");
                tfValFlc.setText("");
                dpDataFlc.setValue(LocalDate.now());
                cbCtcFlc.getSelectionModel().selectFirst();
                cbFrmPag.getSelectionModel().selectFirst();
                if (!listaSubCategoriasDesc.isEmpty()) {
                    cbSubCtc.setItems(listaSubCategoriasDesc);
                    cbSubCtc.getSelectionModel().selectFirst();
                }
            }
        });
    }

    private void addListenerForComboBoxCategorias() {
        cbCtcFlc.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                listaSubCategoriasDesc.clear();
                listaCategorias.forEach((categoria) -> {
                    if (categoria.getCtcDescricao().equals(cbCtcFlc.getValue())) {
                        categoriaEscolhida = categoria;
                    }
                });
                listaSubCategorias.forEach((subCategoria) -> {
                    if (Objects.equals(subCategoria.getSbcFkCtcCodigo().getCtcCodigo(), categoriaEscolhida.getCtcCodigo())) {
                        listaSubCategoriasDesc.add(subCategoria.getSbcDescricao());
                    }
                });
                cbSubCtc.setItems(null);
                cbSubCtc.setItems(listaSubCategoriasDesc);
            } else {
                cbSubCtc.setItems(null);
            }
        });
    }

}
