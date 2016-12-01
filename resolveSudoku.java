import java.util.Stack;
import java.util.concurrent.RecursiveTask;

/**
 * Created by sam on 01/12/16.
 */
public class resolveSudoku extends RecursiveTask<Integer> {
    private parallelMatrix matrix;
    private static int globalCount;

        resolveSudoku(parallelMatrix matrix) {
            this.matrix = matrix;
        }
        protected Integer compute(){
            noRecursive(this.matrix);
            //Ovviamente ciò che è scritto in questa classe è solo codice senza senso per adesso


// return answer
            /*
                SumArray left = new SumArray(arr,lo,(hi+lo)/2);
                SumArray right= new SumArray(arr,(hi+lo)/2,hi);
                left.fork();
                int rightAns = right.compute();
                int leftAns = left.join();
                return leftAns + rightAns;
            */
            return 0;
        }
    private static void noRecursive(parallelMatrix mat){
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
                globalCount++;
                return;
            }

        while(!stack.isEmpty()){
            Move move = stack.pop();
            Integer[][] copyMat = mat.matrixCopy();
            mat.put(move.number, move.row, move.column);
            noRecursive(mat);
            mat = new parallelMatrix(copyMat);
        }
    }
}
