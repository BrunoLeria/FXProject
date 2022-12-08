package views;

import components.ColumnFormatter;
import database.FluxocaixaDAO;
import exceptions.ExceptionDisplay;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import models.Fluxocaixa;
import models.Pagamento;

public class ReportScreenController {

    private ObservableList<Fluxocaixa> listaFlow;
    private ObservableList<Fluxocaixa> listaReceived;
    private ObservableList<Fluxocaixa> listaToPay;
    private FluxocaixaDAO fldao = new FluxocaixaDAO();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnUpdate;

    @FXML
    private BorderPane root;

    @FXML
    private TableView<Fluxocaixa> tableFlow;

    @FXML
    private TableView<Fluxocaixa> tableReceived;

    @FXML
    private TableView<Fluxocaixa> tableToPay;

    @FXML
    void exit(ActionEvent event) {
        loadUI("HomeScreen.fxml");
    }

    @FXML
    void update(ActionEvent event) {
        loadUI("ReportScreen.fxml");
    }

    @FXML
    void initialize() {
        try {
            assert btnExit != null : "fx:id=\"btnExit\" was not injected: check your FXML file 'ReportScreen.fxml'.";
            assert btnUpdate != null : "fx:id=\"btnUpdate\" was not injected: check your FXML file 'ReportScreen.fxml'.";
            assert root != null : "fx:id=\"root\" was not injected: check your FXML file 'ReportScreen.fxml'.";
            assert tableFlow != null : "fx:id=\"tableFlow\" was not injected: check your FXML file 'ReportScreen.fxml'.";
            assert tableReceived != null : "fx:id=\"tableReceived\" was not injected: check your FXML file 'ReportScreen.fxml'.";
            assert tableToPay != null : "fx:id=\"tableToPay\" was not injected: check your FXML file 'ReportScreen.fxml'.";
            updateLists();
            setupTableToPay();
            setupTableReceive();
            setupTableFlow();

        } catch (Exception ex) {
            new ExceptionDisplay("Erro ao montar a lista de fluxo de caixa:" + ex.getMessage());
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

    private void setupTableToPay() throws Exception {
        TableColumn<Fluxocaixa, String> tc_flc_categoria = new TableColumn<>();
        tc_flc_categoria.setText("Categoria");
        tc_flc_categoria.setPrefWidth(100.0);
        tc_flc_categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkCtcCodigo().getCtcDescricao()));

        TableColumn<Fluxocaixa, Integer> tc_flc_codigo = new TableColumn<>();
        tc_flc_codigo.setText("Código");
        tc_flc_codigo.setPrefWidth(75.0);
        tc_flc_codigo.setCellValueFactory(new PropertyValueFactory("flcCodigo"));

        TableColumn<Fluxocaixa, Date> tc_flc_data_ocorrencia = new TableColumn<>();
        tc_flc_data_ocorrencia.setText("Data");
        tc_flc_data_ocorrencia.setPrefWidth(194.0);
        tc_flc_data_ocorrencia.setCellValueFactory(new PropertyValueFactory("flcDataOcorrencia"));
        tc_flc_data_ocorrencia.setCellFactory(new ColumnFormatter<>(new SimpleDateFormat("dd/MM/YYYY")));
        
        TableColumn<Fluxocaixa, String> tc_flc_descricao = new TableColumn<>();
        tc_flc_descricao.setText("Descrição");
        tc_flc_descricao.setPrefWidth(140.0);
        tc_flc_descricao.setCellValueFactory(new PropertyValueFactory("flcDescricao"));

        TableColumn<Fluxocaixa, String> tc_flc_forma_pagamento = new TableColumn<>();
        tc_flc_forma_pagamento.setText("Forma de Pagamento");
        tc_flc_forma_pagamento.setPrefWidth(140.0);
        tc_flc_forma_pagamento.setCellValueFactory(cellData -> new SimpleStringProperty(Pagamento.values()[cellData.getValue().getFlcFormaPagamento()].getNome()));

        TableColumn<Fluxocaixa, String> tc_flc_sub_categoria = new TableColumn<>();
        tc_flc_sub_categoria.setText("Sub-categoria");
        tc_flc_sub_categoria.setPrefWidth(136.0);
        tc_flc_sub_categoria.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkSbcCodigo().getSbcDescricao()));

        TableColumn<Fluxocaixa, Double> tc_flc_valor = new TableColumn<>();
        tc_flc_valor.setText("Valor");
        tc_flc_valor.setPrefWidth(60.0);
        tc_flc_valor.setCellValueFactory(new PropertyValueFactory("flcValor"));

        tableToPay.getColumns().addAll(tc_flc_codigo,
                tc_flc_descricao,
                tc_flc_data_ocorrencia,
                tc_flc_forma_pagamento,
                tc_flc_categoria,
                tc_flc_sub_categoria,
                tc_flc_valor);
        tableToPay.setItems(listaToPay);
    }

    private void setupTableReceive() throws Exception {
        TableColumn<Fluxocaixa, String> tc_flc_categoria_receive = new TableColumn<>();
        tc_flc_categoria_receive.setText("Categoria");
        tc_flc_categoria_receive.setPrefWidth(100.0);
        tc_flc_categoria_receive.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkCtcCodigo().getCtcDescricao()));

        TableColumn<Fluxocaixa, Integer> tc_flc_codigo_receive = new TableColumn<>();
        tc_flc_codigo_receive.setText("Código");
        tc_flc_codigo_receive.setPrefWidth(75.0);
        tc_flc_codigo_receive.setCellValueFactory(new PropertyValueFactory("flcCodigo"));

        TableColumn<Fluxocaixa, Date> tc_flc_data_ocorrencia_receive = new TableColumn<>();
        tc_flc_data_ocorrencia_receive.setText("Data");
        tc_flc_data_ocorrencia_receive.setPrefWidth(194.0);
        tc_flc_data_ocorrencia_receive.setCellValueFactory(new PropertyValueFactory("flcDataOcorrencia"));
        tc_flc_data_ocorrencia_receive.setCellFactory(new ColumnFormatter<>(new SimpleDateFormat("dd/MM/YYYY")));

        TableColumn<Fluxocaixa, String> tc_flc_descricao_receive = new TableColumn<>();
        tc_flc_descricao_receive.setText("Descrição");
        tc_flc_descricao_receive.setPrefWidth(140.0);
        tc_flc_descricao_receive.setCellValueFactory(new PropertyValueFactory("flcDescricao"));

        TableColumn<Fluxocaixa, String> tc_flc_forma_pagamento_receive = new TableColumn<>();
        tc_flc_forma_pagamento_receive.setText("Forma de Pagamento");
        tc_flc_forma_pagamento_receive.setPrefWidth(140.0);
        tc_flc_forma_pagamento_receive.setCellValueFactory(cellData -> new SimpleStringProperty(Pagamento.values()[cellData.getValue().getFlcFormaPagamento()].getNome()));

        TableColumn<Fluxocaixa, String> tc_flc_sub_categoria_receive = new TableColumn<>();
        tc_flc_sub_categoria_receive.setText("Sub-categoria");
        tc_flc_sub_categoria_receive.setPrefWidth(136.0);
        tc_flc_sub_categoria_receive.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkSbcCodigo().getSbcDescricao()));

        TableColumn<Fluxocaixa, Double> tc_flc_valor_receive = new TableColumn<>();
        tc_flc_valor_receive.setText("Valor");
        tc_flc_valor_receive.setPrefWidth(60.0);
        tc_flc_valor_receive.setCellValueFactory(new PropertyValueFactory("flcValor"));

        tableReceived.getColumns().addAll(tc_flc_codigo_receive,
                tc_flc_descricao_receive,
                tc_flc_data_ocorrencia_receive,
                tc_flc_forma_pagamento_receive,
                tc_flc_categoria_receive,
                tc_flc_sub_categoria_receive,
                tc_flc_valor_receive);
        tableReceived.setItems(listaReceived);
    }

    private void setupTableFlow() throws Exception {
        TableColumn<Fluxocaixa, String> tc_flc_categoria_flow = new TableColumn<>();
        tc_flc_categoria_flow.setText("Categoria");
        tc_flc_categoria_flow.setPrefWidth(100.0);
        tc_flc_categoria_flow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkCtcCodigo().getCtcDescricao()));

        TableColumn<Fluxocaixa, Integer> tc_flc_codigo_flow = new TableColumn<>();
        tc_flc_codigo_flow.setText("Código");
        tc_flc_codigo_flow.setPrefWidth(75.0);
        tc_flc_codigo_flow.setCellValueFactory(new PropertyValueFactory("flcCodigo"));

        TableColumn<Fluxocaixa, Date> tc_flc_data_ocorrencia_flow = new TableColumn<>();
        tc_flc_data_ocorrencia_flow.setText("Data");
        tc_flc_data_ocorrencia_flow.setPrefWidth(194.0);
        tc_flc_data_ocorrencia_flow.setCellValueFactory(new PropertyValueFactory("flcDataOcorrencia"));
        tc_flc_data_ocorrencia_flow.setCellFactory(new ColumnFormatter<>(new SimpleDateFormat("dd/MM/YYYY")));

        TableColumn<Fluxocaixa, String> tc_flc_descricao_flow = new TableColumn<>();
        tc_flc_descricao_flow.setText("Descrição");
        tc_flc_descricao_flow.setPrefWidth(140.0);
        tc_flc_descricao_flow.setCellValueFactory(new PropertyValueFactory("flcDescricao"));

        TableColumn<Fluxocaixa, String> tc_flc_forma_pagamento_flow = new TableColumn<>();
        tc_flc_forma_pagamento_flow.setText("Forma de Pagamento");
        tc_flc_forma_pagamento_flow.setPrefWidth(140.0);
        tc_flc_forma_pagamento_flow.setCellValueFactory(cellData -> new SimpleStringProperty(Pagamento.values()[cellData.getValue().getFlcFormaPagamento()].getNome()));

        TableColumn<Fluxocaixa, String> tc_flc_sub_categoria_flow = new TableColumn<>();
        tc_flc_sub_categoria_flow.setText("Sub-categoria");
        tc_flc_sub_categoria_flow.setPrefWidth(136.0);
        tc_flc_sub_categoria_flow.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFlcFkSbcCodigo().getSbcDescricao()));

        TableColumn<Fluxocaixa, Double> tc_flc_valor_flow = new TableColumn<>();
        tc_flc_valor_flow.setText("Valor");
        tc_flc_valor_flow.setPrefWidth(60.0);
        tc_flc_valor_flow.setCellValueFactory(new PropertyValueFactory("flcValor"));

        tableFlow.getColumns().addAll(tc_flc_codigo_flow,
                tc_flc_descricao_flow,
                tc_flc_data_ocorrencia_flow,
                tc_flc_forma_pagamento_flow,
                tc_flc_categoria_flow,
                tc_flc_sub_categoria_flow,
                tc_flc_valor_flow);
        tableFlow.setItems(listaFlow);
    }

    private void updateLists() throws Exception {
        if (listaFlow != null) {
            listaFlow.clear();
        }
        listaFlow = FXCollections.observableArrayList(
                fldao.consultarTodas());
        if (listaReceived != null) {
            listaReceived.clear();
        }
        listaReceived = FXCollections.observableArrayList();
        if (listaToPay != null) {
            listaToPay.clear();
        }
        listaToPay = FXCollections.observableArrayList();
        listaFlow.forEach((entrada) -> {
            if (entrada.getFlcFkCtcCodigo().getCtcPositva()) {
                listaReceived.add(entrada);
            } else {
                listaToPay.add(entrada);
            }
        });
    }
}
