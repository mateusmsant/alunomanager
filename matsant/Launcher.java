package matsant;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class Launcher extends Application {

    public static Stage loginStage, mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.loginStage = primaryStage;
        this.loginStage.setResizable(false);
        openLoginWindow();
    }

    public void openLoginWindow() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/matsant/views/login.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/matsant/res/css/style.css").toExternalForm());
        loginStage.setScene(scene);
        FileInputStream mainIcone = new FileInputStream("src/matsant/res/imgs/appIcon.png");
        Image icone = new Image(mainIcone);
        loginStage.getIcons().add(icone);
        loginStage.show();
    }

    public void openMainWindow() throws IOException, InterruptedException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/matsant/views/main.fxml"));
        AnchorPane pane = loader.load();
        Scene scene = new Scene(pane);
        scene.getStylesheets().add(getClass().getResource("/matsant/res/css/style.css").toExternalForm());
        mainStage = new Stage();

        mainStage.setScene(scene);
        FileInputStream mainIcone = new FileInputStream("src/matsant/res/imgs/appIcon.png");
        Image icone = new Image(mainIcone);
        mainStage.getIcons().add(icone);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
