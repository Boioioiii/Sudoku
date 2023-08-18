package sudoku.userInterface;

import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sudoku.problemDomain.SudokuGame;
import sudoku.userInterface.IUserInterfaceContract.EventListener;
import sudoku.constants.GameState;
import sudoku.problemDomain.Coordinates;



public class UserInterfaceImpl implements IUserInterfaceContract.View,
    EventHandler<KeyEvent>{

        //Stage comes from javafx

        private final Stage stage;
        private final Group root;

        //How do we keep track of 81 different text feilds
        private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

        private IUserInterfaceContract.EventListener listener;

        //We're putting UI stuff here bc its only one UI screen. Had it been multiple, we would move it to be in its own class.
        private static final double WINDOW_Y = 732;
        private static final double WINDOW_X = 668;
        private static final double BOARD_PADDING = 50;
        private static final double BOARD_X_AND_Y = 576;

        private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 150, 136);
        private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(224, 242, 241);
        private static final String SUDOKU = "Sudoku";

        public UserInterfaceImpl(Stage stage){
            this.stage = stage;
            this.root = new Group();
            this.textFieldCoordinates = new HashMap<>();
            initializeUserInterface();
        }

        private void initializeUserInterface(){
            drawBackground(root);
            drawTitle(root);
            drawSudokuBoard(root);
            drawTextFields(root);
            drawGridlines(root);
            stage.show();
        }

        private void drawBackground(Group root){
            Scene scene =  new Scene(root, WINDOW_X, WINDOW_Y);
            scene.setFill(WINDOW_BACKGROUND_COLOR);
            stage.setScene(scene);
        }

        private void drawTitle(Group root){
            Text title = new Text(235,690,SUDOKU);
            title.setFill(Color.WHITE);
            Font titleFont = new Font(43);
            title.setFont(titleFont);
            root.getChildren().add(title);
        }

        private void drawSudokuBoard(Group root){
            Rectangle boardBackground = new Rectangle();
            boardBackground.setX(BOARD_PADDING);
            boardBackground.setY(BOARD_PADDING);
            boardBackground.setWidth(BOARD_X_AND_Y);
            boardBackground.setHeight(BOARD_X_AND_Y);

            boardBackground.setFill(BOARD_BACKGROUND_COLOR);

            root.getChildren().addAll(boardBackground);
        }

        private void drawTextFields(Group root){

            final int xOrigin = 50;
            final int yOrigin = 50;

            final int xAndYDelta = 64;

            for (int xIndex = 0; xIndex < 9; xIndex++){
                for (int yIndex = 0; yIndex < 9; yIndex++){
                    int x = xOrigin + xIndex * xAndYDelta;
                    int y = yOrigin + yIndex * xAndYDelta;

                    SudokuTextField title = new SudokuTextField(xIndex, yIndex);
                    styleSudokuTile(title, x, y);
                    title.setOnKeyPressed(this);
                    textFieldCoordinates.put(new Coordinates(xIndex, yIndex),title);
                    root.getChildren().add(title);

                }
            }
            
        }

        private void styleSudokuTile(SudokuTextField tile, double x, double y){
            Font numberFont = new Font(32);
            tile.setFont(numberFont);
            tile.setAlignment(Pos.CENTER);

            tile.setLayoutX(x);
            tile.setLayoutY(y);
            tile.setPrefWidth(64);
            tile.setPrefHeight(64);

            tile.setBackground(Background.EMPTY);
        }


        private void drawGridlines(Group root){

            //X and Y is where we will start to draw these grid lines
            int xAndY = 114;
            int index = 0;
            while (index < 8){
                int thickness;
                if (index == 2 || index == 5){
                    thickness = 3;

                }else{
                    thickness = 2;
                }

                Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness);

                Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y);

                root.getChildren().addAll(
                    verticalLine,
                    horizontalLine);

            }
        }

        private Rectangle getLine(double x, double y, double height,double width) {

        Rectangle line = new Rectangle();
        line.setX(x);
        line.setY(y);
        line.setHeight(height);
        line.setWidth(width);
        line.setFill(Color.BLACK);

        return line;
        }



        //comes from event handler, which is the java fx interface
        //When the user enters a num into the txt box, that event will pop up here.
        //And then we will check hte event type. If it is the key that has been pressed
        @Override
        public void handle(KeyEvent event) {

            if (event.getEventType() == KeyEvent.KEY_PRESSED){
                if (event .getText().matches("[0-9]")){
                    int value = Integer.parseInt(event.getText());
                    handleInput(value, event.getSource());
                }
            }
            else if(event.getCode() == KeyCode.BACK_SPACE){
                handleInput(0,event.getSource());
            }else{
                ((TextField) event.getSource()).setText("");
            }

            //Once event gets consumed, its not going to propagate through the rest of the application.
            event.consume();
        }

       
        private void handleInput(int value, Object source){
            listener.onSudokuInput(((SudokuTextField)source).getX(), ((SudokuTextField)source).getY(), value);
        }



        @Override
        public void setListener(EventListener listener) {
            this.listener = listener;
        }

        //We want to update a single square after user has input a number instead of updating the entire board.
        @Override
        public void updateSquare(int x, int y, int input) {
            SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x,y));

            String value = Integer.toString(
                input
            );

            if (value.equals("0")) value = "";

            //TextProperty will set the actual tile to the value the user gave
            tile.textProperty().setValue(value);


        }

        //Will update teh entire board. This will update when the user finishes their game or starts a new name.
        public void updateBoard(SudokuGame game) {
            
            // final int xOrigin = 50;
            // final int yOrigin = 50;

            // final int xAndYDelta = 64;

            for (int xIndex = 0; xIndex < 9; xIndex++){
                for (int yIndex = 0; yIndex < 9; yIndex++){
                    TextField title = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));
                    String value = Integer.toString(
                        game.getCopyofGridState()[xIndex][yIndex]
                    );

                    if (value.equals("0")) value = "";

                    title.setText(value);


                    //if we have a empty square, then we want to enbale that txt frield and set it to a slighly less opaque font.
                    //if the number is already colored in we dont want the user to chnage it so we set the disable to True
                    if (game.getGameState() == GameState.NEW){
                        if (value.equals("")){
                            title.setStyle("-fx-opacity: 1;");
                            title.setDisable(false);
                        }else{
                            title.setStyle("-fx-opacity: 0.38;");
                            title.setDisable(true);
                        }
                    }
                }
            }


            
        }

        //This will get called when the logic center of the game indicates the game is completeed properly
        //we will then show the alert dialog which will ask the user if they want to play again or not.
        @Override
        public void showDialog(String message) {
            Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message,ButtonType.OK);
            dialog.showAndWait();

            if(dialog.getResult() == ButtonType.OK){
                listener.onDialogClick();
            } 
                
            
        }

        //There can be errors that occur when we write or retrieve read or write the game state from storage so if any of those
        //errors occur we can let the user know something is wrong.
        @Override
        public void showError(String message) {
            Alert dialog = new Alert(Alert.AlertType.ERROR, message,ButtonType.OK);
            dialog.showAndWait();
        }



    }




