package sudoku.problemDomain;
import sudoku.constants.GameState;
import sudoku.computationalLogic.SudokuUtilities;
public class SudokuGame {
    private final GameState gameState;
    private final int[][] gridState;

    public static final int GRID_BOUNDARY = 9;

    public SudokuGame(GameState gameState, int[][] gridState){
        this.gameState = gameState;
        this.gridState = gridState;

    }

    public GameState getGameState(){
        return gameState;
    }


    //Return a copy of gamestate so the given array can not be modified over time. Protects the game state form modification
    public int[][] getCopyofGridState(){
        return SudokuUtilities.copyToNewArray(gridState);
    }


}

