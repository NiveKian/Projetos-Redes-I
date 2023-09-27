/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: Physical_Receive
* Function: do the bit decoding
*
* ****************************************************************
*/

package layers.physical_layer;

import layers.aplication_layer.App_Receive;
import view.Componentes;
import view.ComputerTwo;

/**
 * Physical_Receive
 */
public class Physical_Receive {

  /*********************************************
  * Method: CamadaFisicaReceptora
  * Function: decode the bit array and send to the next layer
  * Parameters: int quadro[] - bit frame array
  * Return: void
  *********************************************/
  public void CamadaFisicaReceptora(int quadro[]) {
    String tipoDeDecodificacao = Componentes.getCodefication();
    int fluxoBrutoDeBits[] = new int[0];

    switch (tipoDeDecodificacao) {
      case "Binaria": // codificao binaria
        fluxoBrutoDeBits = CamadaFisicaReceptoraDecodificacaoBinaria(quadro);
        break;
      case "Manchester": // codificacao manchester
        fluxoBrutoDeBits = CamadaFisicaReceptoraDecodificacaoManchester(quadro);
        break;
      case "Manchester Diferencial": // codificacao manchester diferencial
        fluxoBrutoDeBits = CamadaFisicaReceptoraDecodificacaoManchesterDiferencial(quadro);
        break;
    }

    App_Receive app = new App_Receive();
    app.CamadaDeAplicacaoReceptora(fluxoBrutoDeBits);
  }

  /*********************************************
  * Method: CamadaFisicaReceptoraDecodificacaoBinaria
  * Function: decode frame in binary
  * Parameters: int quadro[] - bit frame array
  * Return: void
  *********************************************/
  private int[] CamadaFisicaReceptoraDecodificacaoBinaria(int quadro[]) {
    return quadro;
  }

  /*********************************************
  * Method: CamadaFisicaReceptoraDecodificacaoManchester
  * Function: decode frame in manchester
  * Parameters: int quadro[] - bit frame array
  * Return: void
  *********************************************/
  private int[] CamadaFisicaReceptoraDecodificacaoManchester(int quadro[]) {
    // calcula tamanho do fluxo de bits
    int novoTamanho = 0;

    if (quadro.length % 2 == 0) {
      novoTamanho = quadro.length / 2;
    } else {
      novoTamanho = (quadro.length / 2) + 1;
    }

    // cria as varieveis necessarias para a codificacao
    int[] bitsManchesterDecodificados = new int[novoTamanho];
    int indexArrayBitsDecode = 0;
    int displayMask = 1 << 31;
    int tempMask = 0;
    int mask = 0;

    // variaveis referentes ao displayMask
    StringBuilder sb = new StringBuilder();
    String beforeDecodification = "";
    String afterDecodification = "";

    // decodifica os bits e armazena em bitsManchesterDecodificados
    for (int i = 0; i < quadro.length; i++) {
      int number = quadro[i];
      int numBitsLastquadro = 32 - Integer.numberOfLeadingZeros(number);

      if (numBitsLastquadro <= 16) {
        numBitsLastquadro = 16;
      } else {
        numBitsLastquadro = 32;
      }

      number <<= 32 - numBitsLastquadro;

      // percorre bits
      for (int j = 1; j <= numBitsLastquadro; j++) {
        tempMask <<= 1;
        tempMask |= (number & displayMask) == 0 ? 0 : 1;
        number <<= 1;

        if (j % 2 == 0) {
          if (tempMask == 1) {
            mask <<= 1;
            mask |= 0;
            tempMask = 0;

            // Representation
            beforeDecodification = beforeDecodification + "01";
            afterDecodification = afterDecodification + "0";
          } else {
            mask <<= 1;
            mask |= 1;
            tempMask = 0;

            // Representation
            beforeDecodification = beforeDecodification + "10";
            afterDecodification = afterDecodification + "1";
          }
        }

        if (j % 16 == 0 || j == numBitsLastquadro) {
          sb.append(beforeDecodification + " => " + afterDecodification + "\n");
          beforeDecodification = "";
          afterDecodification = "";
        }
      }

      if ((i + 1) % 2 == 0 || i == (quadro.length - 1)) {
        bitsManchesterDecodificados[indexArrayBitsDecode] = mask;
        indexArrayBitsDecode++;
        mask = 0;
      }
    }

    ComputerTwo.printDecodification(sb.toString());
    return bitsManchesterDecodificados;
  }

  /*********************************************
  * Method: CamadaFisicaReceptoraDecodificacaoManchesterDiferencial
  * Function: decode frame in diferencial manchester
  * Parameters: int quadro[] - bit frame array
  * Return: void
  *********************************************/
  private int[] CamadaFisicaReceptoraDecodificacaoManchesterDiferencial(int quadro[]) {
    // calcula tamanho do fluxo de bits
    int novoTamanho = 0;

    if (quadro.length % 2 == 0) {
      novoTamanho = quadro.length / 2;
    } else {
      novoTamanho = (quadro.length / 2) + 1;
    }

    // cria as varieveis necessarias para a codificacao
    int[] bitsManchesterDecodificados = new int[novoTamanho];
    int indexArrayBitsDecode = 0;
    int sequenciaAtual = 1;
    int displayMask = 1 << 31;
    int tempMask = 0;
    int mask = 0;

    // variaveis referentes ao displayMask
    StringBuilder sb = new StringBuilder();
    String model = "01";
    String beforeDecodification = "";
    String afterDecodification = "";

    // decodifica os bits e armazena em bitsManchesterDecodificados
    for (int i = 0; i < quadro.length; i++) {
      int number = quadro[i];
      int numBitsLastquadro = 32 - Integer.numberOfLeadingZeros(number);

      if (numBitsLastquadro <= 16) {
        numBitsLastquadro = 16;
      } else {
        numBitsLastquadro = 32;
      }

      number <<= 32 - numBitsLastquadro;

      // percorre os bits
      for (int j = 1; j <= numBitsLastquadro; j++) {
        tempMask <<= 1;
        tempMask |= (number & displayMask) == 0 ? 0 : 1;
        number <<= 1;

        if (j % 2 == 0) {
          if (tempMask == sequenciaAtual) { // TRANSITION
            mask <<= 1;
            mask |= 0;
            tempMask = 0;

            // Representation
            beforeDecodification = beforeDecodification + model;
            afterDecodification = afterDecodification + "0";
          } else { // NO TRANSITION
            if (sequenciaAtual == 1) {
              sequenciaAtual = 2;
              model = "10";
            } else {
              sequenciaAtual = 1;
              model = "01";
            }

            mask <<= 1;
            mask |= 1;
            tempMask = 0;

            // Representation
            beforeDecodification = beforeDecodification + model;
            afterDecodification = afterDecodification + "1";
          }

          if (j % 16 == 0 || j == numBitsLastquadro) {
            sb.append(beforeDecodification + " => " + afterDecodification + "\n");
            beforeDecodification = "";
            afterDecodification = "";
          }
        }
      }

      // armazena os bits decodificados
      if ((i + 1) % 2 == 0 || i == (quadro.length - 1)) {
        bitsManchesterDecodificados[indexArrayBitsDecode] = mask;
        indexArrayBitsDecode++;
        mask = 0;
      }
    }

    ComputerTwo.printDecodification(sb.toString());
    return bitsManchesterDecodificados;
  }
}