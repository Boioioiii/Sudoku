package sudoku.problemDomain;

import java.io.IOException;

/*
 * Use interfaces to design parts of your application upfront ahead of time. 
 * this is called designe by contract. or code to an interface. 
 */

 /*
  * This is read/write so throw exception if something goes wrong
  */
public interface IStorage {
    void updateGameData(SudokuGame game) throws IOException;
    SudokuGame getGameData() throws IOException;
}
