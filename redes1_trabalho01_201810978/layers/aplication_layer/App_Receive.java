/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: App_Receive
* Function: recive an message an show to the user
*
* ****************************************************************
*/

package layers.aplication_layer;

import util.Convert;
import view.ComputerTwo;

/**
 * App_Receive
 */
public class App_Receive {

  /*********************************************
  * Method: CamadaDeAplicacaoReceptora
  * Function: convert array bits to an string
  * Parameters: int quadro[] - bit array
  * Return: void
  *********************************************/
  public void CamadaDeAplicacaoReceptora(int quadro[]) {
    int[] bitToAscii = Convert.bitToAscii(quadro);
    String message = Convert.asciiToString(bitToAscii);
    ComputerTwo.setFieldOutput(message);
  }
}