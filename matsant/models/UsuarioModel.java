package matsant.models;

public class UsuarioModel {
    String nome, sobrenome, email, usuario, senha, disciplina, foto, resumo, id, celular;

    public UsuarioModel() {}

    public UsuarioModel(String nome, String sobrenome, String email, String usuario, String senha, String disciplina, String foto, String resumo, String celular, String id) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.usuario = usuario;
        this.senha = senha;
        this.disciplina = disciplina;
        this.foto = foto;
        this.resumo = resumo;
        this.celular = celular;
        this.id = id;
    }

    public UsuarioModel(String id) {
        this.id = id;
    }

    public String getCelular() {return this.celular;}

    public void setCelular(String celular) {this.celular = celular;}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }
}
