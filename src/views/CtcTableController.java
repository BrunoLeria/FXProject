/**
 * Sample Skeleton for 'CtcTable.fxml' Controller Class
 */
package views;

import exceptions.ExceptionDisplay;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import database.CategoriascontasDAO;
import database.SubcategoriasDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.swing.JOptionPane;
import models.Categoriascontas;
import models.Subcategorias;

public class CtcTableController {

    private CategoriascontasDAO ccdao = new CategoriascontasDAO();
    private SubcategoriasDAO subdao = new SubcategoriasDAO();
    private ObservableList<Categoriascontas> listaCategorias;
    private ObservableList<Subcategorias> listaSubCategorias;
    private ObservableList<String> listaCategoriasDesc;
    private Categoriascontas categoriaEscolhida;
    private boolean edit = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnAddCategoria;

    @FXML
    private Button btnAddSubCategoria;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnDeleteCategoria;

    @FXML
    private Button btnDeleteSubCategoria;

    @FXML
    private Button btnEditCategoria;

    @FXML
    private Button btnEditSubCategoria;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnExit1;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnSubCancelar;

    @FXML
    private Button btnSubSalvar;

    @FXML
    private ComboBox<String> cbCatSub;

    @FXML
    private CheckBox ckbPosCat;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem miAddCategoria;

    @FXML
    private MenuItem miAddSubCategoria;

    @FXML
    private MenuItem miCancelar;

    @FXML
    private MenuItem miDeleteCategoria;

    @FXML
    private MenuItem miDeleteSubCategoria;

    @FXML
    private MenuItem miEditCategoria;

    @FXML
    private MenuItem miEditSubCategoria;

    @FXML
    private MenuItem miExit;

    @FXML
    private MenuItem miHelp;

    @FXML
    private MenuItem miSalvar;

    @FXML
    private MenuItem miSubCancelar;

    @FXML
    private MenuItem miSubSalvar;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<Categoriascontas> tableCategoria;

    @FXML
    private TableView<Subcategorias> tableSubCategoria;

    @FXML
    private TextField tfCodCat;

    @FXML
    private TextField tfCodSub;

    @FXML
    private TextField tfDesCat;

    @FXML
    private TextField tfDesSub;

    @FXML
    void addCategoria(ActionEvent event) {
        setEdit(false);
        clearCatFields();
        setupTextFieldsAndButtonsCtc(false);
    }

    @FXML
    void addSubCategoria(ActionEvent event) {
        setEdit(false);
        clearSubFields();
        setupTextFieldsAndButtonsSub(false);
    }

    @FXML
    void btnCancelar(ActionEvent event) {
        setupTextFieldsAndButtonsCtc(true);
        setEdit(false);
    }

    @FXML
    void btnSalvar(ActionEvent event) {
        if (tfDesCat.getText().isEmpty()) {
            new ExceptionDisplay("Erro ao salvar: Campos faltando.");
        } else {
            int index = listaCategorias.isEmpty() ? 1 : (!tfCodCat.getText().isEmpty() ? Integer.parseInt(tfCodCat.getText()) : listaCategorias.toArray().length + 1);
            Categoriascontas ct = new Categoriascontas(index, tfDesCat.getText(), ckbPosCat.isSelected());
            try {
                if (isEdit()) {
                    ccdao.editar(ct);
                } else {
                    ccdao.inserir(ct);
                }
                loadUI("CtcTable.fxml");
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao salvar a categoria:" + ex);
            }
        }
    }

    @FXML
    void btnSubCancelar(ActionEvent event) {
        setupTextFieldsAndButtonsSub(true);
        setEdit(false);
    }

    @FXML
    void btnSubSalvar(ActionEvent event) {
        if (tfDesSub.getText().isEmpty() || cbCatSub.getSelectionModel() == null) {
            new ExceptionDisplay("Erro ao salvar: Campos faltando.");
        } else {
            int index = listaSubCategorias.isEmpty() ? 1 : (!tfCodSub.getText().isEmpty() ? Integer.parseInt(tfCodSub.getText()) : listaSubCategorias.toArray().length + 1);
            listaCategorias.forEach((categoria) -> {
                if (categoria.getCtcDescricao().equals(cbCatSub.getValue())) {
                    categoriaEscolhida = categoria;
                }
            });
            Subcategorias sb = new Subcategorias(index, tfDesSub.getText(), categoriaEscolhida);
            try {
                if (isEdit()) {
                    subdao.editar(sb);
                } else {
                    subdao.inserir(sb);
                }
                loadUI("CtcTable.fxml");
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao salvar a subcategoria:" + ex.getMessage());
            }

        }
    }

    @FXML
    void deleteCategoria(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Você tem certeza que quer deletar essa categoria?", "Você tem certeza?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                ccdao.excluir(Integer.parseInt(tfCodCat.getText()));
                loadUI("CtcTable.fxml");
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao deletar a categoria:" + ex.getMessage());
            }

        }
    }

    @FXML
    void deleteSubCategoria(ActionEvent event) {
        if (JOptionPane.showConfirmDialog(null, "Você tem certeza que quer deletar essa categoria?", "Você tem certeza?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                subdao.excluir(Integer.parseInt(tfCodSub.getText()));
                loadUI("CtcTable.fxml");
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao deletar a subcategoria:" + ex.getMessage());
            }

        }
    }

    @FXML
    void editCategoria(ActionEvent event) {
        setEdit(true);
        setupTextFieldsAndButtonsCtc(false);
    }

    @FXML
    void editSubCategoria(ActionEvent event) {
        setEdit(true);
        setupTextFieldsAndButtonsSub(false);
    }

    @FXML
    void exit(ActionEvent event) {
        loadUI("HomeScreen.fxml");
    }

    @FXML
    void openHelp(ActionEvent event) {
        loadUI("HelpScreen.fxml");
    }

    @FXML
    void initialize() {
        assert btnAddCategoria != null : "fx:id=\"btnAddCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnAddSubCategoria != null : "fx:id=\"btnAddSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnDeleteCategoria != null : "fx:id=\"btnDeleteCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnDeleteSubCategoria != null : "fx:id=\"btnDeleteSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnEditCategoria != null : "fx:id=\"btnEditCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnEditSubCategoria != null : "fx:id=\"btnEditSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnExit1 != null : "fx:id=\"btnExit1\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnSalvar != null : "fx:id=\"btnSalvar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnSubCancelar != null : "fx:id=\"btnSubCancelar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert btnSubSalvar != null : "fx:id=\"btnSubSalvar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert cbCatSub != null : "fx:id=\"cbCatSub\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert ckbPosCat != null : "fx:id=\"ckbPosCat\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miAddCategoria != null : "fx:id=\"miAddCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miAddSubCategoria != null : "fx:id=\"miAddSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miCancelar != null : "fx:id=\"miCancelar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miDeleteCategoria != null : "fx:id=\"miDeleteCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miDeleteSubCategoria != null : "fx:id=\"miDeleteSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miEditCategoria != null : "fx:id=\"miEditCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miEditSubCategoria != null : "fx:id=\"miEditSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miExit != null : "fx:id=\"miExit\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miHelp != null : "fx:id=\"miHelp\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miSalvar != null : "fx:id=\"miSalvar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miSubCancelar != null : "fx:id=\"miSubCancelar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert miSubSalvar != null : "fx:id=\"miSubSalvar\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tableCategoria != null : "fx:id=\"tableCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tableSubCategoria != null : "fx:id=\"tableSubCategoria\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tfCodCat != null : "fx:id=\"tfCodCat\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tfCodSub != null : "fx:id=\"tfCodSub\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tfDesCat != null : "fx:id=\"tfDesCat\" was not injected: check your FXML file 'CtcTable.fxml'.";
        assert tfDesSub != null : "fx:id=\"tfDesSub\" was not injected: check your FXML file 'CtcTable.fxml'.";

        try {

            addListenerForTableCategorias();
            addListenerForTableSubCategorias();
            updateTableCategorias();
            setupTableCategorias();
            updateTableSubCategorias();
            setupTableSubCategorias();
            setupTextFieldsAndButtonsCtc(true);
            setupTextFieldsAndButtonsSub(true);
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao montar a lista de categorias:" + ex.getMessage());
        }
    }

    public void loadUI(String nomeArq) {
        try {
            root.getChildren().remove(menubar);
            Pane novaTela = (Pane) new FXMLLoader().load(getClass().getResource(nomeArq));
            root.setCenter(novaTela);
        } catch (IOException ex) {
            new ExceptionDisplay("Erro ao mudar para a tela:" + ex.getMessage());
        }
    }

    private void updateTableCategorias() throws Exception {
        if (listaCategorias != null) {
            listaCategorias.clear();
        }
        listaCategorias = FXCollections.observableArrayList(
                ccdao.consultarTodas());
        listaCategoriasDesc = FXCollections.observableArrayList();
        listaCategorias.forEach((categoria) -> {
            listaCategoriasDesc.add(categoria.getCtcDescricao());
        });
    }

    private void updateTableSubCategorias() throws Exception {
        if (listaSubCategorias != null) {
            listaSubCategorias.clear();
        }
        listaSubCategorias = FXCollections.observableArrayList(
                subdao.consultarTodas());
    }

    private void setupTableCategorias() throws Exception {
        TableColumn<Categoriascontas, Integer> tc_ctc_codigo = new TableColumn<>();
        tc_ctc_codigo.setText("Código");
        tc_ctc_codigo.setPrefWidth(75.0);
        tc_ctc_codigo.setCellValueFactory(new PropertyValueFactory("ctcCodigo"));

        TableColumn<Categoriascontas, String> tc_ctc_descricao = new TableColumn<>();
        tc_ctc_descricao.setText("Descriçao");
        tc_ctc_descricao.setPrefWidth(500.0);
        tc_ctc_descricao.setCellValueFactory(new PropertyValueFactory("ctcDescricao"));

        TableColumn<Categoriascontas, Boolean> tc_ctc_positiva = new TableColumn<>();
        tc_ctc_positiva.setText("Positiva");
        tc_ctc_positiva.setPrefWidth(70.0);
        tc_ctc_positiva.setCellValueFactory(new PropertyValueFactory("ctcPositva"));

        tableCategoria.getColumns().addAll(tc_ctc_codigo, tc_ctc_descricao, tc_ctc_positiva);
        tableCategoria.setItems(listaCategorias);

    }

    private void setupTableSubCategorias() throws Exception {
        TableColumn<Subcategorias, Integer> tc_sbc_codigo = new TableColumn<>();
        tc_sbc_codigo.setText("Código");
        tc_sbc_codigo.setPrefWidth(75.0);
        tc_sbc_codigo.setCellValueFactory(new PropertyValueFactory("sbcCodigo"));

        TableColumn<Subcategorias, String> tc_sbc_descricao = new TableColumn<>();
        tc_sbc_descricao.setText("Descriçao");
        tc_sbc_descricao.setPrefWidth(500.0);
        tc_sbc_descricao.setCellValueFactory(new PropertyValueFactory("sbcDescricao"));

        TableColumn<Subcategorias, String> tc_sbc_fk_ctc_codigo = new TableColumn<>();
        tc_sbc_fk_ctc_codigo.setText("Categoria");
        tc_sbc_fk_ctc_codigo.setPrefWidth(70.0);
        tc_sbc_fk_ctc_codigo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSbcFkCtcCodigo().getCtcDescricao()));

        tableSubCategoria.getColumns().addAll(tc_sbc_codigo, tc_sbc_descricao, tc_sbc_fk_ctc_codigo);
        tableSubCategoria.setItems(listaSubCategorias);
    }

    private void setupTextFieldsAndButtonsCtc(boolean clear) {
        tfCodCat.setDisable(true);
        tfDesCat.setDisable(clear);
        ckbPosCat.setDisable(clear);
        btnSalvar.setDisable(clear);
        btnCancelar.setDisable(clear);
        miSalvar.setDisable(clear);
        miCancelar.setDisable(clear);
        if (clear) {
            clearCatFields();
        }
    }

    private void setupTextFieldsAndButtonsSub(boolean clear) {
        tfCodSub.setDisable(true);
        tfDesSub.setDisable(clear);
        cbCatSub.setDisable(clear);

        cbCatSub.setItems(null);
        cbCatSub.setItems(listaCategoriasDesc);
        btnSubSalvar.setDisable(clear);
        btnSubCancelar.setDisable(clear);
        miSubSalvar.setDisable(clear);
        miSubCancelar.setDisable(clear);
        if (clear) {
            clearSubFields();
        }
    }

    private void clearSubFields() {
        btnEditSubCategoria.setDisable(true);
        btnDeleteSubCategoria.setDisable(true);
        miEditSubCategoria.setDisable(true);
        miDeleteSubCategoria.setDisable(true);
        tfCodSub.setText("");
        tfDesSub.setText("");
        cbCatSub.getSelectionModel().selectFirst();
    }

    private void clearCatFields() {
        btnEditCategoria.setDisable(true);
        btnDeleteCategoria.setDisable(true);
        miEditCategoria.setDisable(true);
        miDeleteCategoria.setDisable(true);
        tfCodCat.setText("");
        tfDesCat.setText("");
        ckbPosCat.setSelected(false);
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    private void addListenerForTableCategorias() {
        tableCategoria.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnEditCategoria.setDisable(false);
                btnDeleteCategoria.setDisable(false);
                miEditCategoria.setDisable(false);
                miDeleteCategoria.setDisable(false);
                tfCodCat.setText(String.valueOf(newSelection.getCtcCodigo()));
                tfDesCat.setText(newSelection.getCtcDescricao());
                ckbPosCat.setSelected(newSelection.getCtcPositva());
            } else {
                btnEditCategoria.setDisable(true);
                btnDeleteCategoria.setDisable(true);
                miEditCategoria.setDisable(true);
                miDeleteCategoria.setDisable(true);
                tfCodCat.setText("");
                tfDesCat.setText("");
                ckbPosCat.setSelected(false);
            }
        });
    }

    private void addListenerForTableSubCategorias() {
        tableSubCategoria.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                btnEditSubCategoria.setDisable(false);
                btnDeleteSubCategoria.setDisable(false);
                miEditSubCategoria.setDisable(false);
                miDeleteSubCategoria.setDisable(false);
                tfCodSub.setText(String.valueOf(newSelection.getSbcCodigo()));
                tfDesSub.setText(newSelection.getSbcDescricao());
                listaCategorias.forEach((categoria) -> {
                    if (categoria.getCtcDescricao().equals(newSelection.getSbcFkCtcCodigo().getCtcDescricao())) {
                        categoriaEscolhida = categoria;
                    }
                });
                cbCatSub.getSelectionModel().select(categoriaEscolhida.getCtcDescricao());
            } else {
                btnEditSubCategoria.setDisable(true);
                btnDeleteSubCategoria.setDisable(true);
                miEditSubCategoria.setDisable(true);
                miDeleteSubCategoria.setDisable(true);
                tfCodSub.setText("");
                tfDesSub.setText("");
                cbCatSub.getSelectionModel().selectFirst();
            }
        });
    }
}
