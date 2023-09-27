/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Anime
* Function: Do the bits animation
*
* ****************************************************************
*/

package view;

/**
 * Anime
 */
public class Anime extends Thread {
  private int[] bitFlow;

  /*********************************************
  * Method: Anime
  * Function: constructor
  * Parameters: int[] bitFlow - bit array to animate 
  * Return: void
  *********************************************/
  public Anime(int[] bitFlow) {
    this.bitFlow = bitFlow;
  }

  @Override
  public void run() {
    // init clean buffer
    int[] animeBuffer = new int[5];
    for (int i = 0; i < animeBuffer.length; i++) {
      animeBuffer[i] = -1;
    }

    // variables
    int displayMask = 1 << 31;

    // loop the bit array
    for (int i = 0; i < bitFlow.length; i++) {
      int number = bitFlow[i];
      int numBits = 32 - Integer.numberOfLeadingZeros(number);

      // calculate usefull bits
      if (numBits <= 8) {
        numBits = 8;
      } else if (numBits <= 16) {
        numBits = 16;
      } else if (numBits <= 24) {
        numBits = 24;
      } else if (numBits <= 32) {
        numBits = 32;
      }

      number <<= 32 - numBits;

      // runs the bits in array element
      for (int j = 1; j <= numBits; j++) {
        // update buffer
        for (int j2 = 4; j2 > 0; j2--) {
          animeBuffer[j2] = animeBuffer[j2 - 1];
        }

        // add new element in buffer
        if ((number & displayMask) == 0) {
          animeBuffer[0] = 0;
        } else {
          animeBuffer[0] = 1;
        }
        number <<= 1;

        // change in view
        for (int j2 = 0; j2 < animeBuffer.length; j2++) {
          Componentes.bitRep[j2].setImage(Componentes.modules[animeBuffer[j2] + 1]);
        }

        // Velocity control
        try { // Velocity max control
          Thread.sleep((long) (150));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    // reset transmition
    for (int i = 0; i < 5; i++) {
      Componentes.bitRep[i].setImage(Componentes.modules[0]);
    }

    Componentes.unlockButton();
  }
}