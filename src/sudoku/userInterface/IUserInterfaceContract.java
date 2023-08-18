package sudoku.userInterface;


public interface IUserInterfaceContract {

    //Controller
    interface EventListener {
        void onSudokuInput(int x, int y, int input);
        void onDialogClick();
    
    }

    //View
    interface View{
        void setListener(IUserInterfaceContract.EventListener listener);
        void updateSquare(int x, int y, int input);
        void updateBoard(sudoku.problemDomain.SudokuGame game);
        void showDialog(String message);
        void showError(String message);
    }

    
}