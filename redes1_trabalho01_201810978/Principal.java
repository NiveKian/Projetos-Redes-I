/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Principal
* Function: Start program
*
* ****************************************************************
*/

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import view.BeginScreen;

public class Principal extends Application {

  private static Image icon = new Image("/img/applicationIcon.png", 140, 140, true, true);
  private Scene cenaPrincipal;
  private BeginScreen layout;
  public Stage window;

  @Override
  public void start(Stage palcoPrincipal) throws Exception {

    window = palcoPrincipal;

    /*
     * INITIALIZE SCENE
     */
    layout = new BeginScreen();
    cenaPrincipal = new Scene(layout, 880, 670);
    cenaPrincipal.getStylesheets().add("view/Theme.css");

    /*
     * INITIALIZE INTERFACE
     */
    window.setTitle("Physical Layer Simulation");
    window.getIcons().add(icon);
    window.setScene(cenaPrincipal);
    window.setResizable(true);
    window.sizeToScene();
    window.show();

    /*********************************************
     * Method: setOnCloseRequest 
     * Function: Finaliza o programa por completo ao Fechar 
     * Parameters: EventHandler 
     * Return: void
     *********************************************/
    palcoPrincipal.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent t) {
        t.consume();
        window.close();
        System.exit(0);
      }
    });

  }

  public static void main(String[] args) {
    launch(args);
  }

}