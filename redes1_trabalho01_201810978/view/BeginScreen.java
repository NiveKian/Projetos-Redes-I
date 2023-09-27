/*
* ***************************************************************
* 
* Author: Ian Silva Antunes Ramos 
* Enrollment: 201810978 
* Begin: 22/07/2021 Last change: 02/08/2021
* Name: BeginScreen
* Function: build the mains screen
*
* ****************************************************************
*/

package view;

import javafx.scene.layout.HBox;

/**
 * BeginScreen
 */
public class BeginScreen extends HBox {
  Computer computer = new Computer();
  Componentes comp = new Componentes();
  ComputerTwo computerTwo = new ComputerTwo();

  /*********************************************
  * Method: BeginScreen
  * Function: constructor
  * Parameters: void
  * Return: void
  *********************************************/
  public BeginScreen() {
    // Adding
    this.setSpacing(5);
    this.getChildren().addAll(computer,comp,computerTwo);
  }
}