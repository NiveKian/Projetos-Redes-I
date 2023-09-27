/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Physical_Send
* Function: do the physical encoding
*
* ****************************************************************
*/

package layers.physical_layer;

import layers.PhysicalLink;
import view.Componentes;
import view.Computer;

/**
 * Physical_Send
 */
public class Physical_Send {
  
  /*********************************************
  * Method: CamadaFisicaTransmissora
  * Function: encode the frame and send to the link 
  * Parameters: int quadro[] - bit frame array
  * Return: void
  *********************************************/
  public void CamadaFisicaTransmissora(int quadro[]) {
    String tipoDeCodificacao = Componentes.getCodefication();
    int fluxoBrutoDeBits[] = new int[0];

    switch (tipoDeCodificacao) {
      case "Binaria":
        fluxoBrutoDeBits = CamadaFisicaTransmissoraCodificacaoBinaria(quadro);
        break;
      case "Manchester":
        fluxoBrutoDeBits = CamadaFisicaTransmissoraCodificacaoManchester(quadro);
        break;
      case "Manchester Diferencial":
        fluxoBrutoDeBits = CamadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
        break;
    }

    PhysicalLink.MeioDeComunicacao(fluxoBrutoDeBits);
  }

  /*********************************************
  * Method: CamadaFisicaTransmissoraCodificacaoBinaria
  * Function: encode frame in binary
  * Parameters: int quadro[] - bit array to encode
  * Return: void
  *********************************************/
  private int[] CamadaFisicaTransmissoraCodificacaoBinaria(int quadro[]) {
    return quadro;
  }

  /*********************************************
  * Method: CamadaFisicaTransmissoraCodificacaoManchester
  * Function: encode frame in manchester
  * Parameters: int quadro[] - bit array to encode
  * Return: void
  *********************************************/
  private int[] CamadaFisicaTransmissoraCodificacaoManchester(int quadro[]) {
    // calcula tamanho do fluxo de bits
    int novoTamanho = 0;
    int numBitsLastquadro = 32 - Integer.numberOfLeadingZeros(quadro[quadro.length - 1]);

    // define o novo tamanho
    if (numBitsLastquadro <= 16) {
      novoTamanho = (quadro.length * 2) - 1;
    } else {
      novoTamanho = quadro.length * 2;
    }

    // cria as varieveis necessarias para a codificacao
    int[] fluxoDeBitsManchester = new int[novoTamanho];
    int indexArrayBitsManchester = 0;
    int displayMask = 1 << 31;
    int mask = 0;

    // variaveis referentes ao displayMask
    StringBuilder sb = new StringBuilder();
    String beforeCodefication = "";
    String afterCodefication = "";

    // codifica os bits e armazena em fluxoDeBitsManchester
    for (int i = 0; i < quadro.length; i++) {
      int number = quadro[i];
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
          mask <<= 1;
          mask |= 0;
          mask <<= 1;
          mask |= 1;

          // Representation
          beforeCodefication = beforeCodefication + "0";
          afterCodefication = afterCodefication + "01";
        } else {
          mask <<= 1;
          mask |= 1;
          mask <<= 1;
          mask |= 0;

          // Representation
          beforeCodefication = beforeCodefication + "1";
          afterCodefication = afterCodefication + "10";
        }

        number <<= 1;

        // Representation
        if (j % 8 == 0) {
          sb.append(beforeCodefication + " => " + afterCodefication + "\n");
          beforeCodefication = "";
          afterCodefication = "";
        }

        if (j % 16 == 0 || j == numBitsQuadro) {
          fluxoDeBitsManchester[indexArrayBitsManchester] = mask;
          indexArrayBitsManchester++;
          mask = 0;
        }
      }
    }

    Computer.printCondification(sb.toString());
    return fluxoDeBitsManchester;
  }

  /*********************************************
  * Method: CamadaFisicaTransmissoraCodificacaoManchesterDiferencial
  * Function: encode frame in diferencial manchester
  * Parameters: int quadro[] - bit array to encode
  * Return: void
  *********************************************/
  private int[] CamadaFisicaTransmissoraCodificacaoManchesterDiferencial(int quadro[]) {
    // calcula tamanho do fluxo de bits
    int novoTamanho = 0;
    int numBitsLastquadro = 32 - Integer.numberOfLeadingZeros(quadro[quadro.length - 1]);

    if (numBitsLastquadro <= 16) {
      novoTamanho = (quadro.length * 2) - 1;
    } else {
      novoTamanho = quadro.length * 2;
    }

    // cria as varieveis necessarias para a codificacao
    int[] fluxoDeBitsDifManchester = new int[novoTamanho];
    int indexArrayBitsDifManchester = 0;
    int sequenciaAtual = 1;
    int displayMask = 1 << 31;
    int mask = 0;

    // variaveis referentes ao displayMask
    StringBuilder sb = new StringBuilder();
    String model = "01";
    String beforeCodefication = "";
    String afterCodefication = "";

    // codifica os bits e armazena em fluxoDeBitsManchester
    for (int i = 0; i < quadro.length; i++) {
      int number = quadro[i];
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

      // desloca 0s faltando
      number <<= 32 - numBitsQuadro;

      // Percorre entre os bits
      for (int j = 1; j <= numBitsQuadro; j++) {
        if ((number & displayMask) == 0) { // TRANSITION
          mask <<= 2;
          mask |= sequenciaAtual; // same number

          // Representation
          beforeCodefication = beforeCodefication + "0";
          afterCodefication = afterCodefication + model;
        } else { // NO TRANSITION
          if (sequenciaAtual == 1) {
            sequenciaAtual = 2;
            model = "10";
          } else {
            sequenciaAtual = 1;
            model = "01";
          }

          mask <<= 2;
          mask |= sequenciaAtual; // alternate sequence

          // Representation
          beforeCodefication = beforeCodefication + "1";
          afterCodefication = afterCodefication + model;
        }

        number <<= 1;

        // Representation
        if (j % 8 == 0) {
          sb.append(beforeCodefication + " => " + afterCodefication + "\n");
          beforeCodefication = "";
          afterCodefication = "";
        }

        // pucha o mask no array
        if (j % 16 == 0 || j == numBitsQuadro) {
          fluxoDeBitsDifManchester[indexArrayBitsDifManchester] = mask;
          indexArrayBitsDifManchester++;
          mask = 0;
        }
      }
    }

    Computer.printCondification(sb.toString());
    return fluxoDeBitsDifManchester;
  }
}