/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Componentes
* Function: build the components interface
*
* ****************************************************************
*/

package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import layers.aplication_layer.App_Send;

/**
 * Componentes
 */
public class Componentes extends VBox {
  private HBox container = new HBox();
  public static ImageView[] bitRep = new ImageView[5];
  public static Image[] modules = new Image[3];

  private static Button send = new Button("Send");
  private static ChoiceBox<String> codeControl = new ChoiceBox<String>();

  /*********************************************
  * Method: Componentes
  * Function: constructor
  * Parameters: void
  * Return: void
  *********************************************/
  public Componentes() {
    // setting images
    modules[0] = new Image("/img/modulo0.png", 83, 83, true, true);
    modules[1] = new Image("/img/modulo1.png", 83, 83, true, true);
    modules[2] = new Image("/img/modulo2.png", 83, 83, true, true);

    // setting Image view & container
    VBox.setMargin(container, new Insets(0, 0, 0, 0));
    for (int i = 0; i < bitRep.length; i++) {
      bitRep[i] = new ImageView(modules[0]);
      container.getChildren().add(bitRep[i]);
    }

    // setting Button
    VBox.setMargin(send, new Insets(10, 0, 0, 125));
    send.setOnAction((event) -> {
      String text = Computer.getFieldInput();

      if (text == "") {
        System.out.println("No Input Provided");
      } else {
        send.setDisable(true);
        App_Send.camadaDeAplicacaoTransmissora(text);
      }
    });

    // setting choiceBox
    VBox.setMargin(codeControl, new Insets(500, 0, 0, 70));
    codeControl.getItems().addAll("Binaria", "Manchester", "Manchester Diferencial");
    codeControl.setValue("Binaria");

    // adding
    this.setSpacing(5);
    this.getChildren().addAll(codeControl, send, container);
  }

  /*********************************************
  * Method: unlockButton
  * Function: unlocks the button
  * Parameters: void
  * Return: void
  *********************************************/
  public static void unlockButton() {
    send.setDisable(false);
  }

  /*********************************************
  * Method: getCodefication
  * Function: returns the selected type of codefication
  * Parameters: void
  * Return: string - type of encode
  *********************************************/
  public static String getCodefication() {
    return codeControl.getValue();
  }
}