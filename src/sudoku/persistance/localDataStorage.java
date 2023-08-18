package sudoku.persistance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import sudoku.problemDomain.IStorage;
import sudoku.problemDomain.SudokuGame;

public class localDataStorage implements IStorage{

    private static File GAME_DATA = new File(System.getProperty("user.home"), "gamedata.txt");

    @Override
    public void updateGameData(SudokuGame game) throws IOException {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(GAME_DATA);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(game);
            objectOutputStream.close();

        }catch (IOException e){
            throw new IOException("Unable to access Game Data" + e.getMessage());
        }
    }

    @Override
    public SudokuGame getGameData() throws IOException {
        
        FileInputStream fileInputStream = new FileInputStream(GAME_DATA);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        
        try{
            SudokuGame gameState = (SudokuGame) objectInputStream.readObject();
            objectInputStream.close();
            return gameState;
        }catch(ClassNotFoundException e){
            objectInputStream.close();
            throw new IOException("File not found");
        }
    }
    
}
