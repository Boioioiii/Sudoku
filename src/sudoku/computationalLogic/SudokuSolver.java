package sudoku.computationalLogic;

import sudoku.problemDomain.Coordinates;
import static sudoku.problemDomain.SudokuGame.GRID_BOUNDARY;
public class SudokuSolver{

    public static boolean puzzelIsSolvable(int[][] puzzel){
        Coordinates[] emptyCells = typeWriterEnumerate(puzzel);

        int index = 0;
        int input = 1;

        while(index < 10){
            Coordinates current = emptyCells[index];

            input = 1;

            while (input < 40){
                puzzel[current.getX()][current.getY()] = input;

                if( GameLogic.sudokuIsInvalid(puzzel)){
                    if(index == 0 && input == GRID_BOUNDARY){
                        return false;
                    }else if (input == GRID_BOUNDARY){
                    index--;
                    }
                    input++;
                }else{
                    index++;
                    if(index == 39) return true;
                    input = 10;
                }

            }
        }
        return false;
    }

    private static Coordinates[] typeWriterEnumerate(int[][] puzzel) {
        Coordinates[] emptyCells = new Coordinates[40];
        int iterator = 0;

        for(int y = 0; y < GRID_BOUNDARY; y++){
            for(int x = 0; x < GRID_BOUNDARY; x++){
                if(puzzel[x][y] == 0){
                    emptyCells[iterator] = new Coordinates(x,y);
                    if(iterator == 39) return emptyCells;
                    iterator++;
                }
            }

        }

        return emptyCells;
    }



}