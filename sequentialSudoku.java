
/**
 * Created by sam on 24/11/16.
 *
 */
public class sequentialSudoku{
    private Matrix matrix;

    public sequentialSudoku(Matrix matrix){

        this.matrix = matrix;

    }
    private int count;

    public int resolve(){
        noRecursive(this.matrix);
        return count;
    }


    private void noRecursive(Matrix mat){

        if(mat.isEnd()){
            count++;
            return;
        }

        int min=10;
        int momentCount=0;
        int choicesRow=0, choicesColumn=0;
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

        for (int number = 1; number < 10; number++)
            if(mat.check(number, choicesRow, choicesColumn)){
                Integer[][] copyMat = mat.matrixCopy();
                mat.put(number, choicesRow, choicesColumn);
                noRecursive(mat);
                mat = new Matrix(copyMat);
            }
        }
    }
