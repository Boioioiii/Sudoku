package sudoku.userInterface;


import javafx.scene.control.TextField;

//maintains a x and y coordinate value
public class SudokuTextField extends TextField {

    private final int x;
    private final int y;

    public SudokuTextField(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    //if user hits 1 on a text field, weird things won't happen and it will just be a single 1 that gets gets entered in the txt field
    @Override
    public void replaceText(int i, int i1, String s){
        //regex is a regular expression
        //a way of matching strings.
        //if the string s matches any of hte characters in the regex aka nums 0-9, then it will be replaced with the new string
        if (!s.matches("[0-9]")){
            super.replaceText(i, i1, s);
        }
    }
    @Override
    public void replaceSelection(String s){
        if (!s.matches("[0-9]")){
            super.replaceSelection(s);
        }
    }



}