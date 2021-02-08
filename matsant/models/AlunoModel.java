package matsant.models;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AlunoModel extends RecursiveTreeObject<AlunoModel> {
    public StringProperty grupoID, nome, email, endereco, telefone, celular, nascimento, id, aprovado;

    public AlunoModel() {}

    public AlunoModel(String grupoID, String nome, String email, String endereco, String telefone, String celular, String nascimento, String aprovado) {
        this.grupoID = new SimpleStringProperty(grupoID);
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.endereco = new SimpleStringProperty(endereco);
        this.telefone = new SimpleStringProperty(telefone);
        this.celular = new SimpleStringProperty(celular);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.aprovado = new SimpleStringProperty(aprovado);
    }

    public AlunoModel(String nome, String email, String endereco, String telefone, String celular, String nascimento, String id, String grupoID, String aprovado) {
        this.nome = new SimpleStringProperty(nome);
        this.email = new SimpleStringProperty(email);
        this.endereco = new SimpleStringProperty(endereco);
        this.telefone = new SimpleStringProperty(telefone);
        this.celular = new SimpleStringProperty(celular);
        this.nascimento = new SimpleStringProperty(nascimento);
        this.id = new SimpleStringProperty(id);
        this.grupoID = new SimpleStringProperty(grupoID);
        this.aprovado = new SimpleStringProperty(aprovado);
    }

    public String getAprovado() {
        return aprovado.get();
    }

    public StringProperty aprovadoProperty() {
        return aprovado;
    }

    public void setAprovado(String aprovado) {
        this.aprovado.set(aprovado);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getGrupoID() {
        return grupoID.get();
    }

    public StringProperty grupoIDProperty() {
        return grupoID;
    }

    public void setGrupoID(String grupoID) {
        this.grupoID.set(grupoID);
    }

    public String getNascimento() { return nascimento.get(); }

    public StringProperty nascimentoProperty() { return nascimento; }

    public void setNascimento(String nascimento) {
        this.nascimento.set(nascimento);
    }

    public String getNome() {
        return nome.get();
    }

    public StringProperty nomeProperty() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome.set(nome);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getEndereco() {
        return endereco.get();
    }

    public StringProperty enderecoProperty() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco.set(endereco);
    }

    public String getTelefone() {
        return telefone.get();
    }

    public StringProperty telefoneProperty() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone.set(telefone);
    }

    public String getCelular() {
        return celular.get();
    }

    public StringProperty celularProperty() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular.set(celular);
    }
}
