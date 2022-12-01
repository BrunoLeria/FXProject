package views;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
import database.CriarBanco;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Bruno
 */
public class Home extends Application {

    @Override
    public void start(Stage stage) {

        CriarBanco app = new CriarBanco();
        try {
            app.criarBanco();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomeScreen.fxml"));
            Parent mainWindow = loader.load();
            Scene mainScene = new Scene(mainWindow);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException ex) {
            System.out.println(ex);
            new ExceptionDisplay("Erro ao iniciar a cena:" + ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex);
            new ExceptionDisplay("Erro ao iniciar o banco:" + ex.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
