/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: ComputerTwo
* Function: Build the computer 2 interface
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
 * ComputerTwo
 */
public class ComputerTwo extends VBox {
  private Image computerImgOn = new Image("/img/computerON.gif", 140, 140, true, true);
  private Image computerImgOff = new Image("/img/computerOFF.png", 140, 140, true, true);
  private ImageView computer = new ImageView(computerImgOff);

  private Label textOutput = new Label("Text Output");
  private static TextArea fieldOutput = new TextArea();

  private Label textToAscii = new Label("Bit To ASCII");
  private static TextArea fieldToAscii = new TextArea();

  private Label textDecode = new Label("Text Decode");
  private static TextArea fieldDecode = new TextArea();;

  /*********************************************
  * Method: ComputerTwo
  * Function: constructor
  * Parameters: void
  * Return: void
  *********************************************/
  public ComputerTwo() {
    // Setting image
    VBox.setMargin(computer, new Insets(0, 0, 0, 50));

    // Setting texts
    fieldOutput.setPrefColumnCount(20);
    fieldOutput.setPrefRowCount(10);
    fieldOutput.setWrapText(true);
    fieldOutput.setEditable(false);

    fieldToAscii.setPrefColumnCount(20);
    fieldToAscii.setPrefRowCount(10);
    fieldToAscii.setWrapText(true);
    fieldToAscii.setEditable(false);

    fieldDecode.setPrefColumnCount(20);
    fieldDecode.setPrefRowCount(10);
    fieldDecode.setWrapText(true);
    fieldDecode.setEditable(false);

    // Adding
    this.setSpacing(5);
    this.setPadding(new Insets(15,20, 10,10));
    this.getChildren().addAll(computer, textOutput, fieldOutput, textToAscii, fieldToAscii, textDecode, fieldDecode);
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
  * Method: setFieldOutput
  * Function: Change the value on field output
  * Parameters: String message - string to set
  * Return: void
  *********************************************/
  public static void setFieldOutput(String message) {
    fieldOutput.setText(message);
  }

  /*********************************************
  * Method: printBitToAscii
  * Function: prints into the field the bit to ascii conversion
  * Parameters: int[] asciiArray , int[] frame - arrays to print
  * Return: void
  *********************************************/
  public static void printBitToAscii(int[] asciiArray, int[] frame) {
    // variables
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    
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
      for (int j = 1; j <= numBitsQuadro; j++) {
        if ((number & displayMask) == 0) {
          sb.append("0");
        } else {
          sb.append("1");
        }

        number <<= 1;

        if (j % 8 == 0 || j == numBitsQuadro) {
          sb.append(String.valueOf(" => "+asciiArray[indexAscii])+"\n");
          indexAscii++;
        }
      }
    }

    fieldToAscii.setText(sb.toString());
  }

  /*********************************************
  * Method: printDecodification
  * Function: prints the decodification on fieldDecode
  * Parameters: String decode - string to set
  * Return: void
  *********************************************/
  public static void printDecodification(String decode) {
    fieldDecode.setText(decode);
  }
}