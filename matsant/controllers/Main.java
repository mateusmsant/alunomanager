package matsant.controllers;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.firebase.client.Firebase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import matsant.Launcher;
import matsant.models.UsuarioModel;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private JFXButton meuPerfilButton;

    @FXML
    private Label header;

    @FXML
    private AnchorPane blackPane;

    @FXML
    private AnchorPane drawPane;

    @FXML
    private JFXButton changePhotoButton;

    @FXML
    private Label nomeUserLogado;

    @FXML
    private JFXButton removePhotoButton;

    @FXML
    private JFXTextField nomeInputMain, celularInputMain;

    @FXML
    private JFXTextField sobrenomeInputMain;

    @FXML
    private JFXTextField emailInputMain;

    @FXML
    private JFXTextField userInputMain;

    @FXML
    private JFXTextField disciplinaInputMain;

    @FXML
    private JFXPasswordField senhaInputMain;

    UsuarioModel usuarioModel;

    @FXML
    void changePhotoButtonClicked() throws MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\Users\\mateusmsant\\Desktop"));
        FileChooser.ExtensionFilter imageFiltro = new FileChooser.ExtensionFilter("Filtro de imagem", "*.jpg", "*.png", "*.jpeg");
        fileChooser.getExtensionFilters().addAll(imageFiltro);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String AWSAccessKeyId = "AKIAIU6RVZHZDKJIEE5Q";
            String AWSSecretKey = "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";
            AWSCredentials awsCredentials = new BasicAWSCredentials(AWSAccessKeyId, AWSSecretKey);
            AmazonS3 s3Client = new AmazonS3Client(awsCredentials);
            String bucketName = "finalbucketpoo";
            String fileName = "images" + "/" + usuarioModel.getId();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    s3Client.putObject(new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead));
                    Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                    firebase.child("usuarios").child(usuarioModel.getId()).child("foto").setValue("https://finalbucketpoo.s3-sa-east-1.amazonaws.com/images/"+ usuarioModel.getId());
                }
            }).start();
            imagemUser.setImage(new Image(file.toURI().toURL().toString()));
            fotoIcon.setImage(new Image(file.toURI().toURL().toString()));
        }
    }

    @FXML
    void removePhotoButtonClicked() {
        Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
        firebase.child("usuarios").child(usuarioModel.getId()).child("foto").setValue("/matsant/res/imgs/userAvatar.png/");
        imagemUser.setImage(new Image("/matsant/res/imgs/userAvatar.png/"));
        fotoIcon.setImage(new Image("/matsant/res/imgs/userAvatar.png/"));
    }

    @FXML
    void sairButtonClicado(ActionEvent event) throws IOException, InterruptedException {
        new Launcher().openLoginWindow();
        Launcher.mainStage.close();
    }

    @FXML
    private JFXButton sairButton;

    @FXML
    private ImageView imagemUser, fotoIcon;

    @FXML
    void blackPaneClicked(MouseEvent event) {
        FadeTransition hideBlackPlane = new FadeTransition(Duration.seconds(.5), blackPane);
        hideBlackPlane.setFromValue(0.5);
        hideBlackPlane.setToValue(0);
        hideBlackPlane.play();

        hideBlackPlane.setOnFinished(event1 -> {
            blackPane.setVisible(false);
        });

        TranslateTransition hideDraw = new TranslateTransition(Duration.seconds(.5), drawPane);
        hideDraw.setByX(-700);
        hideDraw.play();
    }

    @FXML
    void mostrarPerfilClicado(ActionEvent event) {
        blackPane.setVisible(true);

        FadeTransition showBlackPane = new FadeTransition(Duration.seconds(.3), blackPane);
        showBlackPane.setFromValue(0);
        showBlackPane.setToValue(0.5);

        TranslateTransition showDraw = new TranslateTransition(Duration.seconds(.3), drawPane);
        showDraw.setByX(700);

        SequentialTransition sequentialTransition = new SequentialTransition(showBlackPane, showDraw);
        sequentialTransition.play();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        new Login();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/matsant/views/manageAlunos.fxml"));
            AnchorPane pane = fxmlLoader.load();
            rootPane.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TranslateTransition hideDraw = new TranslateTransition(Duration.seconds(0.1), drawPane);
        hideDraw.setByX(-700);
        hideDraw.play();

        usuarioModel = Login.passModel;
        nomeUserLogado.setText(usuarioModel.getNome());
        nomeInputMain.setText(usuarioModel.getNome());
        sobrenomeInputMain.setText(usuarioModel.getSobrenome());
        senhaInputMain.setText(usuarioModel.getSenha());
        emailInputMain.setText(usuarioModel.getEmail());
        celularInputMain.setText(usuarioModel.getCelular());
        userInputMain.setText(usuarioModel.getUsuario());
        imagemUser.setImage(new Image(usuarioModel.getFoto()));
        fotoIcon.setImage(new Image(usuarioModel.getFoto()));

        emailInputMain.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                firebase.child("usuarios").child(usuarioModel.getId()).child("email").setValue(newValue);
            }
        });
        nomeInputMain.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                firebase.child("usuarios").child(usuarioModel.getId()).child("nome").setValue(newValue);
            }
        });
        sobrenomeInputMain.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                firebase.child("usuarios").child(usuarioModel.getId()).child("sobrenome").setValue(newValue);
            }
        });
        senhaInputMain.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                firebase.child("usuarios").child(usuarioModel.getId()).child("senha").setValue(newValue);
            }
        });
        celularInputMain.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                firebase.child("usuarios").child(usuarioModel.getId()).child("celular").setValue(newValue);
            }
        });
        userInputMain.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Firebase firebase = new Firebase("https://projeto-poo-64b18-default-rtdb.firebaseio.com/");
                firebase.child("usuarios").child(usuarioModel.getId()).child("usuario").setValue(newValue);
            }
        });


    }
}
