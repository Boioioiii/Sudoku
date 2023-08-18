package sudoku.buildLogic;

import java.io.IOException;

import sudoku.computationalLogic.GameLogic;
import sudoku.persistance.localDataStorage;
import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;
import sudoku.userInterface.ControlLogic;
import sudoku.userInterface.IUserInterfaceContract;


public class SudokuBuildLogic {

    public static void build(IUserInterfaceContract.View userInterface) throws Exception{
        SudokuGame intialState;
        IStorage storage = new localDataStorage();

        try{
            intialState = storage.getGameData();
        } catch (IOException e){
            intialState = GameLogic.getNewGame();
            storage.updateGameData(intialState);
        }

        IUserInterfaceContract.EventListener uiLogic = new ControlLogic(storage, userInterface);

        userInterface.setListener(uiLogic);
        userInterface.updateBoard(intialState);
    }
    
}
