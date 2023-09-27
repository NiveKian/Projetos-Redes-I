/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Convert
* Function: Do certain conversions
*
* ****************************************************************
*/

package util;

import view.Computer;
import view.ComputerTwo;

/**
 * Convert
 */
public class Convert {
  ////////////// TRANSMISION //////////////

  /*********************************************
  * Method: stringToAscii
  * Function: converts string to ascii array
  * Parameters: String message - string to be converted
  * Return: int[] - ascii array
  *********************************************/
  public static int[] stringToAscii(String message) {
    int[] tabel = new int[message.length()];

    for (int i = 0; i < message.length(); i++) {
      tabel[i] = Integer.valueOf(message.charAt(i));
    }

    return tabel;
  }

  /*********************************************
  * Method: tamanhoArrayDeBits
  * Function: calculate the bit array length
  * Parameters: int tamanho - original array lenght
  * Return: int - new array lenght
  *********************************************/
  public static int tamanhoArrayDeBits(int tamanho) {
    int novoTamanho = 0;

    if (tamanho % 4 == 0) {
      novoTamanho = tamanho / 4;
    } else {
      novoTamanho = tamanho / 4 + 1;
    }

    return novoTamanho;
  }

  /*********************************************
  * Method: asciiToBits
  * Function: converts ascii array to bit array
  * Parameters: int[] table - ascii array
  * Return: int[] - bit array
  *********************************************/
  public static int[] asciiToBits(int[] table) {
    int[] frame = new int[tamanhoArrayDeBits(table.length)];
    int mask = 0;
    int flag = 0;

    for (int i = 0; i < table.length; i++) {
      mask <<= 8;
      mask = mask | table[i];

      if (i % 4 == 3) {
        frame[flag] = mask;
        mask = 0;
        flag++;
      } else if (i == table.length - 1) {
        frame[flag] = mask;
      }
    }

    //////////////
    Computer.printAsciiToBit(table, frame);
    //////////////

    return frame;
  }

  ////////////// RECIVE ///////////////////

  /*********************************************
  * Method: tamanhoArrayDeAscii
  * Function: calculate ascii array length
  * Parameters: int[] frame - bit array
  * Return: int - new array lenght
  *********************************************/
  public static int tamanhoArrayDeAscii (int[] frame){
    int numBitsLastFrame = 32 - Integer.numberOfLeadingZeros(frame[frame.length-1]);
    int tamanho = 0;

    if(numBitsLastFrame <= 8){
      tamanho = (frame.length*4)-3;
    }else if(numBitsLastFrame <= 16){
      tamanho = (frame.length*4)-2;
    }else if(numBitsLastFrame <= 24){
      tamanho = (frame.length*4)-1;
    }else if(numBitsLastFrame <= 32){
      tamanho = frame.length*4;
    }
    
    return tamanho;
  }

  /*********************************************
  * Method: bitToAscii
  * Function: converts bit array to ascii array
  * Parameters: int[] frame - bit array
  * Return: int[] - converted ascii array
  *********************************************/
  public static int[] bitToAscii(int[] frame) {
    int[] arrayDeNumbers = new int[tamanhoArrayDeAscii(frame)];
    int displayMask = 1 << 31;
    int indexArrayNumber = 0;
    int mask = 0;

    for (int i = 0; i < frame.length; i++) {
      int number = frame[i];
      int numBitsFrame = 32 - Integer.numberOfLeadingZeros(number);

      // confere o numero de bytes no frame
      if (numBitsFrame <= 8) {
        numBitsFrame = 8;
      } else if (numBitsFrame <= 16) {
        numBitsFrame = 16;
      } else if (numBitsFrame <= 24) {
        numBitsFrame = 24;
      } else if (numBitsFrame <= 32) {
        numBitsFrame = 32;
      }

      number <<= 32 - numBitsFrame;

      for (int j = 1; j <= numBitsFrame; j++) {
        mask <<= 1;
        mask |= (number & displayMask) == 0 ? 0 : 1;
        number <<= 1;

        // adicona o mask no array
        if (j % 8 == 0) {
          arrayDeNumbers[indexArrayNumber] = mask;
          indexArrayNumber++;
          mask = 0;
        }
      }
    }

    //////////////
    ComputerTwo.printBitToAscii(arrayDeNumbers, frame);
    //////////////

    return arrayDeNumbers;
  }

  /*********************************************
  * Method: asciiToString
  * Function: converts ascii array to string
  * Parameters: int[] table - ascii array
  * Return: String - message
  *********************************************/
  public static String asciiToString (int[] table){
    String finalMessage = "";
    String addMessage = "";

    for(int i=0;i<table.length;i++){
      addMessage = Character.toString ((char)table[i]);
      finalMessage = finalMessage + addMessage;
    }

    return finalMessage;
  }
}