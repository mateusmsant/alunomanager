package matsant.controllers;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Duration;
import matsant.models.AlunoModel;
import matsant.models.AlunoModel2;

import java.net.URL;
import java.util.ResourceBundle;

public class Alunos implements Initializable {
    @FXML
    private JFXTreeTableView<AlunoModel> tabelaAlunos;

    @FXML
    private TreeTableColumn<AlunoModel, String> nomeColuna;

    @FXML
    private TreeTableColumn<AlunoModel, String> emailColuna;

    @FXML
    private TreeTableColumn<AlunoModel, String> addressColuna;

    @FXML
    private TreeTableColumn<AlunoModel, String> telefoneColuna;

    @FXML
    private TreeTableColumn<AlunoModel, String> celularColuna;

    @FXML
    private TreeTableColumn<AlunoModel, String> nascimentoColuna;

    @FXML
    private TreeTableColumn<AlunoModel, String> aprovadoColuna;

    @FXML
    private AnchorPane blackPane;

    @FXML
    private AnchorPane dialogPane;

    @FXML
    private GridPane gridAluno;

    @FXML
    private JFXTextField nomeAluno;

    @FXML
    private JFXTextField emailAluno;

    @FXML
    private JFXTextField telefoneAluno;

    @FXML
    private JFXTextField enderecoAluno;

    @FXML
    private JFXTextField celularAluno;

    @FXML
    private AnchorPane notaAnualPane;

    @FXML
    private JFXTextField segundoBimestreInput;

    @FXML
    private JFXTextField primeiroBimestreInput;

    @FXML
    private JFXTextField terceiroBimestreInput;

    @FXML
    private JFXTextField quartoBimestreInput;

    @FXML
    private AnchorPane notaSemestralPane;

    @FXML
    private JFXTextField segundoSemestreInput;

    @FXML
    private JFXTextField primeiroSemestreInput;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private AnchorPane notaFinalPane;

    @FXML
    private JFXTextField notaFinalInput;

    @FXML
    void limparFinal(ActionEvent event) {

    }

    double mediaPraFinal;

    @FXML
    void calcularNotaFinal(ActionEvent event) {
        if (!notaFinalInput.getText().isEmpty()) {
            double notaFinal = Double.parseDouble(notaFinalInput.getText());
            double mediaFinal = 0;

            mediaFinal = (notaFinal + mediaPraFinal)/2;

            Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");

            if (mediaFinal >= 7) {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Aprovado");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Aprovado");
            } else {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Reprovado");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Reprovado");
            }
        }
    }

    @FXML
    void calcularAnual(ActionEvent event) {
        id = tabelaAlunos.getSelectionModel().getSelectedItem().getValue().getId();

        Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");

        if (primeiroBimestreInput.getText().isEmpty() || segundoBimestreInput.getText().isEmpty() || terceiroBimestreInput.getText().isEmpty() || quartoBimestreInput.getText().isEmpty()) {
            showSnackbar("Há campos vazios", notaAnualPane);
        } else {
            double primeiraNota = Double.parseDouble(primeiroBimestreInput.getText());
            double segundaNota = Double.parseDouble(segundoBimestreInput.getText());
            double terceiraNota = Double.parseDouble(terceiroBimestreInput.getText());
            double quartaNota = Double.parseDouble(quartoBimestreInput.getText());
            Calculadora calculadora = new Calculadora(primeiraNota, segundaNota, terceiraNota, quartaNota);
            double media = calculadora.getMediaAnual();
            if (media >= 7) {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Aprovado");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Aprovado");
            } else if (media >= 3) {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Prova final");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Prova final");
                notaAnualPane.setVisible(false);
                notaFinalPane.setVisible(true);
                mediaPraFinal = media;
            } else {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Reprovado");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Reprovado");
            }
        }
    }

    @FXML
    void calcularSemestral(ActionEvent event) {
        id = tabelaAlunos.getSelectionModel().getSelectedItem().getValue().getId();

        Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");

        if (primeiroSemestreInput.getText().isEmpty() || segundoSemestreInput.getText().isEmpty()) {
            showSnackbar("Há campos vazios", notaSemestralPane);
        } else {
            double primeiraNota = Double.parseDouble(primeiroSemestreInput.getText());
            double segundaNota = Double.parseDouble(segundoSemestreInput.getText());

            Calculadora calculadora = new Calculadora(primeiraNota, segundaNota);
            double media = calculadora.getMediaSemestral();
            if (media >= 7) {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Aprovado");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Aprovado");
            } else if (media >= 3) {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Prova final");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Prova final");
                notaSemestralPane.setVisible(false);
                notaFinalPane.setVisible(true);
                mediaPraFinal = media;
            } else {
                tabelaAlunos.getSelectionModel().getSelectedItem().getValue().setAprovado("Reprovado");
                firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("aprovado").setValue("Reprovado");

            }
        }
    }

    @FXML
    void changeParaAnual(ActionEvent event) {
        notaAnualPane.setVisible(true);
        notaSemestralPane.setVisible(false);
    }

    @FXML
    void changeToSemestral(ActionEvent event) {
        notaAnualPane.setVisible(false);
        notaSemestralPane.setVisible(true);
    }

    @FXML
    void limparAnual(ActionEvent event) {

    }

    @FXML
    void limparSemestral(ActionEvent event) {

    }

    @FXML
    void showMediaAnual(ActionEvent event) {
        blackPane.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.5), blackPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.5);
        fadeTransition.play();

        notaAnualPane.setVisible(true);
        notaSemestralPane.setVisible(false);
    }

    @FXML
    private JFXDatePicker nascimentoAluno;
    private Iterable<DataSnapshot> children;

    public String formatData(String data) {
        String ano = "", mes = "", dia = "";
        int i = 0;

        for (char x: data.toCharArray()) {
            i++;
            if (i <= 4) {
                ano += x;
            } else if (i > 5 && i < 8) {
                mes += x;
            } else if (i == 9 || i == 10){
                dia += x;
            }
        }
        data = dia + "/" + mes + "/" + ano;
        return data;
    }

    private void showSnackbar(String mensagem, AnchorPane pane) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(new Label(mensagem), Duration.seconds(1), null);
        snackbar.enqueue(snackbarEvent);
    }

    public static AlunoModel alunoModel;

    @FXML
    void dialogAddClicked(ActionEvent event) {
        String dataNascimento = "";

        if (nascimentoAluno.getValue() != null)
            dataNascimento = formatData(nascimentoAluno.getValue().toString());
        if (nomeAluno.getText().isEmpty() || emailAluno.getText().isEmpty() || enderecoAluno.getText().isEmpty() || telefoneAluno.getText().isEmpty() || celularAluno.getText().isEmpty() || dataNascimento.isEmpty()) {
            showSnackbar("Há campos vazios", dialogPane);
        } else {
            AlunoModel alunoModel = new AlunoModel(nomeAluno.getText(), emailAluno.getText(), enderecoAluno.getText(), telefoneAluno.getText(), celularAluno.getText(), dataNascimento, "", "", "");
            Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").push().setValue(alunoModel);
            lista.addAll(alunoModel);
        }
    }

    @FXML
    void dialogClearClicked(ActionEvent event) {
        nomeAluno.setText("");
        emailAluno.setText("");
        telefoneAluno.setText("");
        enderecoAluno.setText("");
        celularAluno.setText("");
        nascimentoAluno.setValue(null);
    }


    @FXML
    void hideBlackPane() {
        blackPane.setVisible(false);

        if (notaAnualPane.isVisible() || notaSemestralPane.isVisible() || notaFinalPane.isVisible()) {
            notaAnualPane.setVisible(false);
            notaSemestralPane.setVisible(false);
            notaFinalPane.setVisible(false);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.1), blackPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

        } else {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.1), blackPane);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.1), dialogPane);
            scaleTransition.setByY(-1);
            scaleTransition.setByX(-1);
            scaleTransition.play();
        }
    }

    @FXML
    void addAlunoButtonClicked() {
        blackPane.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(.5), blackPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(0.5);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(.3), dialogPane);
        scaleTransition.setByY(1);
        scaleTransition.setByX(1);

        SequentialTransition sequentialTransition = new SequentialTransition(fadeTransition, scaleTransition);
        sequentialTransition.play();
    }

    @FXML
    void deleteAlunoButtonClicked(ActionEvent event) {
        if (tabelaAlunos.getSelectionModel().getSelectedIndex() < 0) {
            showSnackbar("Por favor selecione um estudante", rootPane);
        } else {
            id = tabelaAlunos.getSelectionModel().getSelectedItem().getValue().getId();
            lista.remove(tabelaAlunos.getSelectionModel().getSelectedIndex());
            Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).removeValue();
        }
    }

    String id;
    int index;

    @FXML
    void editAlunoButtonClicked(ActionEvent event) {
        if (tabelaAlunos.getSelectionModel().isEmpty()) {
            showSnackbar("Selecione algum estudante", rootPane);
        } else {
            addAlunoButtonClicked();
            id = tabelaAlunos.getSelectionModel().getSelectedItem().getValue().getId();
            index = tabelaAlunos.getSelectionModel().getSelectedIndex();
        }
    }

    @FXML
    void dialogEditClicked(ActionEvent event) {
        if (nomeAluno.getText().isEmpty() || emailAluno.getText().isEmpty() || enderecoAluno.getText().isEmpty() || telefoneAluno.getText().isEmpty() || celularAluno.getText().isEmpty() || formatData(nascimentoAluno.getValue().toString()).isEmpty()) {
            showSnackbar("Há campos em branco", dialogPane);
        } else {
            Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("nome").setValue(nomeAluno.getText());
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("email").setValue(emailAluno.getText());
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("endereco").setValue(enderecoAluno.getText());
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("telefone").setValue(telefoneAluno.getText());
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("celular").setValue(celularAluno.getText());
            firebase.child("admin").child(Login.passModel.getId()).child("estudantes").child(id).child("nascimento").setValue(formatData(nascimentoAluno.getValue().toString()));
            AlunoModel alunoModel = new AlunoModel(nomeAluno.getText(), emailAluno.getText(), enderecoAluno.getText(), telefoneAluno.getText(), celularAluno.getText(), formatData(nascimentoAluno.getValue().toString()), "", "", "");
            lista.remove(index);
            lista.add(alunoModel);
        }
    }

    ObservableList<AlunoModel> lista;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideBlackPane();

        lista = FXCollections.observableArrayList();

        nomeColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().nome;
            }
        });
        emailColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().email;
            }
        });
        addressColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().endereco;
            }
        });
        telefoneColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().telefone;
            }
        });
        celularColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().celular;
            }
        });

        nascimentoColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().nascimento;
            }
        });

        aprovadoColuna.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<AlunoModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<AlunoModel, String> param) {
                return param.getValue().getValue().aprovado;
            }
        });

        TreeItem<AlunoModel> root = new RecursiveTreeItem<AlunoModel>(lista, RecursiveTreeObject::getChildren);
        tabelaAlunos.setRoot(root);
        tabelaAlunos.setShowRoot(false);

        readDataChange();


    }

    @FXML
    public void readDataChange() {
        Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
        firebase.child("admin").child(Login.passModel.getId()).child("estudantes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                AlunoModel2 alunoModel = new AlunoModel2();
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    children = data.getChildren();
                    for (DataSnapshot item: children) {
                        if (item.getKey() == "celular") {
                            alunoModel.setCelular(item.getValue().toString());
                        } else if (item.getKey() == "email") {
                            alunoModel.setEmail(item.getValue().toString());
                        }else if (item.getKey() == "endereco") {
                            alunoModel.setEndereco(item.getValue().toString());
                        }else if (item.getKey() == "telefone") {
                            alunoModel.setTelefone(item.getValue().toString());
                        }else if (item.getKey() == "nome") {
                            alunoModel.setNome(item.getValue().toString());
                        }else if (item.getKey() == "nascimento") {
                            alunoModel.setNascimento(item.getValue().toString());
                        }else if (item.getKey() == "grupoID") {
                            alunoModel.setGrupoID(item.getValue().toString());
                        } else if (item.getKey() == "aprovado") {
                            alunoModel.setAprovado(item.getValue().toString());
                        }
                    }
                    AlunoModel alunoModel1 = new AlunoModel(alunoModel.getNome(), alunoModel.getEmail(), alunoModel.getEndereco(), alunoModel.getTelefone(), alunoModel.getCelular(), alunoModel.getNascimento(), data.getKey(), "", alunoModel.getAprovado());
                    lista.add(alunoModel1);
                }


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


}
