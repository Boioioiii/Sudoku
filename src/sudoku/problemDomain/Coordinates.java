package sudoku.problemDomain;

import java.util.Objects;

public class Coordinates {

    private final int x;
    private final int y;


    /*"this" is the object we are trying to modify.
        using/calling the Coordinates method is asking to update the coordinates of the object we called .Coordinates on.
        We will be updating that obejct that .Coordinates was called on with the x and y values passed in the constructor.
    */
    
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }


    //Object, all classes are a subclass of the class OBJECT. All classes can inherit the methods and attributrs of the class Object

    /*
     * This method will compare the entire object we are calling .equals on to the object passed in as a parameter.
     */
    @Override
    public boolean equals(Object o){

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that =  (Coordinates) o;

        return x ==  that.x && y ==  that.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }


}