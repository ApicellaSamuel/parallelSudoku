package parallel;

import java.util.Objects;

public class Move {
    public final int number, row, column;

    public Move(int number, int row, int column){
        this.number = number;
        this.row = row;
        this.column=column;
    }


    @Override
    public boolean equals(Object x) {
        if(x != null && x.getClass() == getClass() )
            if (x.hashCode()==hashCode())
                return true;

        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, row, column);
    }
}