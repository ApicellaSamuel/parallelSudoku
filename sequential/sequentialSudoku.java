package sequential;

import java.util.Stack;

/**
 * Created by sam on 24/11/16.
 *
 */
public class sequentialSudoku{

    private Matrix matrix;

    public sequentialSudoku(Matrix matrix){

        this.matrix = matrix;

    }
    private static int count;

    public int resolve(){
        noRecursive(this.matrix);
        return count;
    }


    private static void noRecursive(Matrix mat){
        int min=10;
        int momentCount=0;
        int choicesRow=0, choicesColumn=0;
        Stack<Move> stack = new Stack<>();
        for (int row = 0; row < 9; row++)
            for (int column = 0; column < 9; column++)
                if(mat.isEmpty(row, column)){
                    for (int n = 1; n < 10; n++)
                        if(mat.check(n, row, column))
                            momentCount++;
                    if(momentCount < min){
                        min = momentCount;
                        choicesRow=row;choicesColumn=column;
                    }
                    momentCount=0;
                }

        for (int n = 1; n < 10; n++)
            if(mat.check(n, choicesRow, choicesColumn))
                stack.push(new Move(n, choicesRow, choicesColumn));

        if(stack.isEmpty())
            if(mat.isEnd()){
                count++;
                return;
            }

        while(!stack.isEmpty()){
            Move move = stack.pop();
            Integer[][] copyMat = mat.matrixCopy();
            mat.put(move.number, move.row, move.column);
            noRecursive(mat);
            mat = new Matrix(copyMat);
        }
    }
}