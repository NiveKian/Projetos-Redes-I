/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Computer
* Function: Build the "computer" interface
*
* ****************************************************************
*/

package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 * Computer
 */
public class Computer extends VBox {
  private Image computerImgOn = new Image("/img/computerON.gif", 140, 140, true, true);
  private Image computerImgOff = new Image("/img/computerOFF.png", 140, 140, true, true);
  private ImageView computer = new ImageView(computerImgOff);

  private Label textInput = new Label("Text Input");
  private static TextArea fieldInput = new TextArea();

  private Label textToBit = new Label("Ascii To Bit");
  private static TextArea fieldToBit = new TextArea();

  private Label textCodification = new Label("Text Codification");
  private static TextArea fieldCodification = new TextArea();

  /*********************************************
  * Method: Computer
  * Function: constructor
  * Parameters: void
  * Return: void
  *********************************************/
  public Computer() {
    // Setting image
    VBox.setMargin(computer, new Insets(0, 0, 0, 50));

    // Setting texts
    fieldInput.setPrefColumnCount(20);
    fieldInput.setPrefRowCount(10);
    fieldInput.setWrapText(true);

    fieldToBit.setPrefColumnCount(20);
    fieldToBit.setPrefRowCount(10);
    fieldToBit.setWrapText(true);
    fieldToBit.setEditable(false);

    fieldCodification.setPrefColumnCount(20);
    fieldCodification.setPrefRowCount(10);
    fieldCodification.setWrapText(true);
    fieldCodification.setEditable(false);

    // Adding
    this.setSpacing(5);
    this.setPadding(new Insets(15, 20, 10, 10));
    this.getChildren().addAll(computer, textInput, fieldInput, textToBit, fieldToBit, textCodification,
        fieldCodification);
  }

  /*********************************************
  * Method: setComputerState
  * Function: change "computer" image
  * Parameters: boolean mode - computer state
  * Return: void
  *********************************************/
  public void setComputerState(boolean mode) {
    if (mode) {
      computer.setImage(computerImgOn);
    } else {
      computer.setImage(computerImgOff);
    }
  }

  /*********************************************
  * Method: getFieldInput
  * Function: return the area input value
  * Parameters: void
  * Return: String - message typed in field input
  *********************************************/
  public static String getFieldInput() {
    return fieldInput.getText();
  }

  /*********************************************
  * Method: printAsciiToBit
  * Function: prints int the field the ascii to bit conversion
  * Parameters: int[] asciiArray, int[] frame - arrays to print
  * Return: void
  *********************************************/
  public static void printAsciiToBit(int[] asciiArray, int[] frame) {
    // variables
    StringBuilder sb = new StringBuilder();
    int displayMask = 1 << 31;
    int indexAscii = 0;

    // loop between bits adding to the stringbuilder
    for (int i = 0; i < frame.length; i++) {
      int number = frame[i];
      int numBitsQuadro = 32 - Integer.numberOfLeadingZeros(number);

      if (numBitsQuadro <= 8) {
        numBitsQuadro = 8;
      } else if (numBitsQuadro <= 16) {
        numBitsQuadro = 16;
      } else if (numBitsQuadro <= 24) {
        numBitsQuadro = 24;
      } else if (numBitsQuadro <= 32) {
        numBitsQuadro = 32;
      }

      // desloca 0 faltando
      number <<= 32 - numBitsQuadro;

      // percorre os bits
      for (int j = 0; j < numBitsQuadro; j++) {
        if (j % 8 == 0 || j == numBitsQuadro) {
          sb.append(String.valueOf("\n" + asciiArray[indexAscii]) + " => ");
          indexAscii++;
        }

        if ((number & displayMask) == 0) {
          sb.append("0");
        } else {
          sb.append("1");
        }

        number <<= 1;
      }
    }

    fieldToBit.setText(sb.toString());
  }

  /*********************************************
  * Method: printCondification
  * Function: prints the decodification on fieldCofification
  * Parameters: String codefication - string to set
  * Return: void
  *********************************************/
  public static void printCondification(String codefication) {
    fieldCodification.setText(codefication);
  }
}