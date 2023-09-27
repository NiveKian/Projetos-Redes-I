/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: PhysicalLink
* Function: links the sender and receiver physical layers
*
* ****************************************************************
*/

package layers;

import layers.physical_layer.Physical_Receive;
import view.Anime;

/**
 * PhysicalLink
 */
public class PhysicalLink {

  /*********************************************
  * Method: MeioDeComunicacao
  * Function: send the encode bit array to another physical layer
  * Parameters: int fluxoBrutoDeBits[] - bit array
  * Return: void
  *********************************************/
  public static void MeioDeComunicacao(int fluxoBrutoDeBits[]) {
    int fluxoBrutoDeBitsPontoA[] = new int[fluxoBrutoDeBits.length];
    int fluxoBrutoDeBitsPontoB[] = new int[fluxoBrutoDeBits.length];
    fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;

    // TRANSFIRA BITS DO A PARA O B BIT A BIT
    for (int i = 0; i < fluxoBrutoDeBits.length; i++) {
      fluxoBrutoDeBitsPontoB[i] |= fluxoBrutoDeBitsPontoA[i];
    }

    // CHAMADA DA ANIMACAO
    Anime animation = new Anime(fluxoBrutoDeBitsPontoB);
    animation.start();

    // ENVIO
    Physical_Receive fisica = new Physical_Receive();
    fisica.CamadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
  }
}