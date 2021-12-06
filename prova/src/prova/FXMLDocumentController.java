/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prova;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Cimol
 */
public class FXMLDocumentController implements Initializable {
    
    ObservableList<Contato> lista = FXCollections.observableArrayList();
    int idCount = 0;
    boolean novo = true;
    
    @FXML
    private TableView<Contato> tableView;
    
    @FXML
    private TableColumn<Contato, Integer> colId;

    @FXML
    private TableColumn<Contato, String> colNome;

    @FXML
    private TableColumn<Contato, String> colEmail;

    @FXML
    private TableColumn<Contato, String> colTelefone;

    @FXML
    private TableColumn<Contato, String> colTipoContato;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTelefone;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private Button btnDeletar;

    @FXML
    private Button btnSalvar;

    public int gerarId(){
        Random aleatorio = new Random();
        return aleatorio.nextInt(1000);
    }
    
    
    @FXML
    void acaoSalvar(ActionEvent event) {
    String nome = txtNome.getText();
    String email = txtEmail.getText();
    String telefone = txtTelefone.getText();
    String tipoContato = comboTipo.getSelectionModel().getSelectedItem();
    if(novo){
        int id = gerarId();
        lista.add(new Contato(id, nome, email, telefone, tipoContato));
    }
    else{
        int indice = tableView.getSelectionModel().getSelectedIndex();
        int id= tableView.getSelectionModel().getSelectedItem().getId();
        lista.remove(indice);
        lista.add(indice, new Contato(id, nome, email, telefone, tipoContato));
    }
    limparCampos();
    }
    
    public void limparCampos(){
        txtNome.setText("");
        txtEmail.setText("");
        txtTelefone.setText("");
        comboTipo.getSelectionModel().clearSelection();
        novo=true;
    }
    
    @FXML
    void acaoDelete(ActionEvent event) {
    if(tableView.getSelectionModel().getSelectedItem()!=null){
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);
        Contato contatoDeletar = tableView.getSelectionModel().getSelectedItem();
        confirmacao.setTitle("Deseja mesmo deletar o contato"+contatoDeletar.getNome()+"?");
        confirmacao.setContentText("Deletar"+contatoDeletar.getNome()+"?");
        Optional<ButtonType> retorno = confirmacao.showAndWait();
        if(retorno.get()==ButtonType.OK){
            int indice = tableView.getSelectionModel().getSelectedIndex();
            lista.remove(indice);
        }
    }
    else{
        Alert mensagemErro = new Alert(Alert.AlertType.INFORMATION);
        mensagemErro.setTitle("Selecione um registro primeiro");
        mensagemErro.setContentText("É preciso selecionar um registro para deletar.");
    }
    }
    
    @FXML
    void itemSelecionado(MouseEvent event) {
        if(tableView.getSelectionModel().getSelectedItem()!=null){
            Contato contato = tableView.getSelectionModel().getSelectedItem();
            txtNome.setText(contato.getNome());
            txtTelefone.setText(contato.getTelefone());
            txtEmail.setText(contato.getEmail());
            comboTipo.getSelectionModel().select(contato.getTipoContato());
            novo = false;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> listaTipoContato = FXCollections.observableArrayList();
        listaTipoContato.add("Amigo");
        listaTipoContato.add("Profissional");
        listaTipoContato.add("Família");
        listaTipoContato.add("Outros");
        comboTipo.setItems(listaTipoContato);
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colNome.setCellValueFactory(new PropertyValueFactory("nome"));
        colEmail.setCellValueFactory(new PropertyValueFactory("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory("telefone"));
        colTipoContato.setCellValueFactory(new PropertyValueFactory("tipoContato"));
        tableView.setItems(lista);
        
        
    }    
    
}
