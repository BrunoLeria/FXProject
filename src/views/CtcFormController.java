/**
 * Sample Skeleton for 'CtcForm.fxml' Controller Class
 */
package views;

import database.CategoriascontasDAO;
import database.SubcategoriasDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Categoriascontas;
import models.Subcategorias;

public class CtcFormController {

    private boolean edit;

    private CategoriascontasDAO ccdao = new CategoriascontasDAO();
    private SubcategoriasDAO subdao = new SubcategoriasDAO();
    
    @FXML
    private BorderPane root;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCancelar"
    private Button btnCancelar; // Value injected by FXMLLoader

    @FXML // fx:id="btnSalvar"
    private Button btnSalvar; // Value injected by FXMLLoader

    @FXML // fx:id="cbCatSub"
    private ComboBox<Categoriascontas> cbCatSub; // Value injected by FXMLLoader

    @FXML // fx:id="ckbPosCat"
    private CheckBox ckbPosCat; // Value injected by FXMLLoader

    @FXML // fx:id="tfCodCat"
    private TextField tfCodCat; // Value injected by FXMLLoader

    @FXML // fx:id="tfCodSub"
    private TextField tfCodSub; // Value injected by FXMLLoader

    @FXML // fx:id="tfDesCat"
    private TextField tfDesCat; // Value injected by FXMLLoader

    @FXML // fx:id="tfDesSub"
    private TextField tfDesSub; // Value injected by FXMLLoader

    @FXML
    void btnCancelar(ActionEvent event) {
        loadUI("CtcTable.fxml");
    }

    @FXML
    void btnSalvar(ActionEvent event) {
        if (!tfDesCat.getText().isEmpty()) {
            int index = tfCodCat.getText().isEmpty() ? 0 : Integer.parseInt(tfCodCat.getText());
            Categoriascontas ct = new Categoriascontas(index, tfDesCat.getText(), ckbPosCat.isSelected());
            try {
                if (isEdit()) {
                    ccdao.editar(ct);
                } else {
                    ccdao.inserir(ct);
                }
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao salvar uma categoria:" + ex.getMessage());
            }
        }
        if (!tfDesSub.getText().isEmpty() && cbCatSub.getSelectionModel() != null) {
            int index = tfCodSub.getText().isEmpty() ? 0 : Integer.parseInt(tfCodSub.getText());
            Subcategorias sb = new Subcategorias(index, tfDesSub.getText(), cbCatSub.getValue());
            try {
                if (isEdit()) {
                    subdao.editar(sb);
                } else {
                    subdao.inserir(sb);
                }
            } catch (Exception ex) {
                new ExceptionDisplay("Erro ao salvar uma subcategoria:" + ex.getMessage());
            }

        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert btnSalvar != null : "fx:id=\"btnSalvar\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert cbCatSub != null : "fx:id=\"cbCatSub\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert ckbPosCat != null : "fx:id=\"ckbPosCat\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert tfCodCat != null : "fx:id=\"tfCodCat\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert tfCodSub != null : "fx:id=\"tfCodSub\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert tfDesCat != null : "fx:id=\"tfDesCat\" was not injected: check your FXML file 'CtcForm.fxml'.";
        assert tfDesSub != null : "fx:id=\"tfDesSub\" was not injected: check your FXML file 'CtcForm.fxml'.";

        try {
            cbCatSub.getItems().addAll(ccdao.consultarTodas());
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao montar a lista de categorias no combobox:" + ex.getMessage());
        }
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
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
