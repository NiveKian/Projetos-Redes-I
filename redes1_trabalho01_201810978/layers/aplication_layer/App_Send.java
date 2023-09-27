/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: App_Send
* Function: send the message to next layer
*
* ****************************************************************
*/

package layers.aplication_layer;

import layers.physical_layer.Physical_Send;
import util.Convert;

/**
 * App_Send
 */
public class App_Send {
  
  /*********************************************
  * Method: camadaDeAplicacaoTransmissora
  * Function: convert the message to bit and sent to next layer
  * Parameters: String mensagem - input message
  * Return: return void
  *********************************************/
  public static void camadaDeAplicacaoTransmissora(String mensagem) {
    int[] strToAscii = Convert.stringToAscii(mensagem);
    int[] asciiToBit = Convert.asciiToBits(strToAscii);

    Physical_Send fisica = new Physical_Send();
    fisica.CamadaFisicaTransmissora(asciiToBit);
  }
}