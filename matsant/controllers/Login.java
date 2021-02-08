package matsant.controllers;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.jfoenix.controls.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import matsant.Launcher;
import matsant.models.UsuarioModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private AnchorPane pane2;

    @FXML
    private AnchorPane pane3;

    @FXML
    private AnchorPane pane1;

    @FXML
    private Label headerLogin;

    @FXML
    private AnchorPane registroPane;

    @FXML
    private JFXButton botaoCadastro;

    @FXML
    private JFXTextField nomeInput;

    @FXML
    private JFXTextField sobrenomeInput, celularInput;

    @FXML
    private JFXTextField emailInput;

    @FXML
    private JFXTextField userInput;

    @FXML
    private JFXButton botaoCadastro1;

    @FXML
    private JFXTextField disciplinaInput;

    @FXML
    private JFXPasswordField novaSenhaInput;

    @FXML
    private JFXPasswordField repetirNovaSenhaInput;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXButton criarButton;

    @FXML
    private Line underlineErro;

    @FXML
    private JFXPasswordField passInput;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private JFXTextField cadastroUserInput;

    @FXML
    private JFXTextField erroLabel;

    @FXML
    void entrarAction(ActionEvent event) {

    }

    @FXML
    private ImageView imagemLogo;

    @FXML
    private ImageView cadastroImagem;

    @FXML
    private Line underlineErroLogin;

    @FXML
    private JFXTextField erroLabelLogin;

    int returnValor = 0;

    @FXML
    void loginClicked(ActionEvent event) throws IOException, InterruptedException {
        if (userInput.getText().isEmpty() || passInput.getText().isEmpty()) {
            showSnackbar("Há campos vazios", loginPane);
        } else {
            for (int i = 0; i < usuarioModelList.size(); i++) {
                UsuarioModel usuarioModel = usuarioModelList.get(i);
                if (userInput.getText().equals(usuarioModel.getUsuario()) && passInput.getText().equals(usuarioModel.getSenha())) {
                    passModel = usuarioModel;
                    returnValor = 1;
                }
            }
            if (returnValor == 1) {
                new Launcher().openMainWindow();
                Launcher.loginStage.close();
            }
        }
    }

    @FXML
    void botaoCadastroClick() throws InterruptedException, IOException {
        Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
        if (novaSenhaInput.getText().isEmpty() || repetirNovaSenhaInput.getText().isEmpty() || nomeInput.getText().isEmpty() || sobrenomeInput.getText().isEmpty() || celularInput.getText().isEmpty() || emailInput.getText().isEmpty()) {
            showSnackbar("Há campos vazios", registroPane);
        } else if (!(novaSenhaInput.getText().equals(repetirNovaSenhaInput.getText()))) {
            showSnackbar("Senhas não são iguais", registroPane);
        } else {
            UsuarioModel usuarioModel = new UsuarioModel();
            usuarioModel.setUsuario(cadastroUserInput.getText());
            usuarioModel.setNome(nomeInput.getText());
            usuarioModel.setEmail(emailInput.getText());
            usuarioModel.setSenha(novaSenhaInput.getText());
            usuarioModel.setCelular(celularInput.getText());
            usuarioModel.setSobrenome(sobrenomeInput.getText());
            usuarioModel.setFoto("/matsant/res/imgs/userAvatar.png");
            firebase.child("usuarios").push().setValue(usuarioModel);
            usuarioModelList.add(usuarioModel);
            registroPane.setVisible(false);
            loginPane.setVisible(true);
            passModel = usuarioModel;

            changeListener();
            showSnackbar("Usuário cadastrado com sucesso", loginPane);
        }
    }

    private void showSnackbar(String mensagem, AnchorPane pane) {
        JFXSnackbar snackbar = new JFXSnackbar(pane);
        final JFXSnackbar.SnackbarEvent snackbarEvent = new JFXSnackbar.SnackbarEvent(new Label(mensagem), Duration.seconds(1), null);
        snackbar.enqueue(snackbarEvent);
    }


    @FXML
    void mostrarRegistroPane(ActionEvent event) {
        loginPane.setVisible(false);
        registroPane.setVisible(true);

    }

    @FXML
    void voltarTelaLogin(ActionEvent event) {
        loginPane.setVisible(true);
        registroPane.setVisible(false);
    }

    List<UsuarioModel> usuarioModelList;
    public static UsuarioModel passModel;

    public void changeListener() {
        Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
        firebase.child("usuarios").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    UsuarioModel usuarioModel = data.getValue(UsuarioModel.class);
                    usuarioModel.setId(data.getKey());
                    usuarioModelList.add(usuarioModel);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                showSnackbar("Erro no login. Pode ser a internet", loginPane);
            }
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {



        usuarioModelList = new ArrayList<>();

        mainPane.setStyle("-fx-background-image: url(\"/matsant/res/imgs/loginBackground.jpg\")");

        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("src/matsant/res/imgs/adminLogo.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image image = new Image(inputstream);
        imagemLogo.setImage(image);

        FileInputStream inputstream2 = null;
        try {
            inputstream2 = new FileInputStream("src/matsant/res/imgs/cadastroIcon.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        Image image2 = new Image(inputstream2);
        cadastroImagem.setImage(image2);

        removeAutofoco();


        loginPane.setVisible(true);

        changeListener();



    }

    private void removeAutofoco() {
        userInput.setFocusTraversable(false);
    }




}
