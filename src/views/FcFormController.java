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

import exceptions.ExceptionDisplay;
import database.CategoriascontasDAO;
import database.FluxocaixaDAO;
import database.SubcategoriasDAO;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import models.Fluxocaixa;
import models.Pagamento;
import models.Subcategorias;

public class FcFormController {

    private boolean edit;
    private CategoriascontasDAO ccdao = new CategoriascontasDAO();
    private SubcategoriasDAO subdao = new SubcategoriasDAO();
    private FluxocaixaDAO fldao = new FluxocaixaDAO();
    private ObservableList<Categoriascontas> listaCategorias;
    private ObservableList<Subcategorias> listaSubCategorias;

    @FXML
    private BorderPane root;

    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnSalvar;

    @FXML
    private ComboBox<Categoriascontas> cbCtcFlc;

    @FXML
    private ComboBox<Pagamento> cbFrmPag;

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
    void initialize() {
        assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert btnSalvar != null : "fx:id=\"btnSalvar\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert cbCtcFlc != null : "fx:id=\"cbCtcFlc\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert cbFrmPag != null : "fx:id=\"cbFrmPag\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert cbSubCtc != null : "fx:id=\"cbSubCtc\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert dpDataFlc != null : "fx:id=\"dpDataFlc\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert tfCodFlc != null : "fx:id=\"tfCodFlc\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert tfDesFlc != null : "fx:id=\"tfDesFlc\" was not injected: check your FXML file 'FcForm.fxml'.";
        assert tfValFlc != null : "fx:id=\"tfValFlc\" was not injected: check your FXML file 'FcForm.fxml'.";
        try {

            listaCategorias = FXCollections.observableArrayList(
                    ccdao.consultarTodas());
            cbCtcFlc.setItems(listaCategorias);

            listaSubCategorias = FXCollections.observableArrayList(
                    subdao.consultarTodas());
            cbSubCtc.setItems(listaSubCategorias);

            System.out.println(Arrays.toString(Pagamento.values()));
            cbFrmPag.setItems(FXCollections.observableArrayList(Pagamento.values()));
        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao mudar para a tela:" + ex.getMessage());

        }
    }

    public void loadUI(String nomeArq) {
        try {
            Pane novaTela = (Pane) FXMLLoader.load(getClass().getResource(nomeArq));
            root.setCenter(novaTela);
        } catch (IOException ex) {
            new ExceptionDisplay("Erro ao mudar para a tela (IOException):" + ex.getMessage());
        }
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

}
