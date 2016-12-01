import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by sam on 01/12/16.
 *
 */
public class resolveSudoku extends RecursiveTask<Integer> {
    private parallelMatrix matrix;
    //private static AtomicInteger globalCount=new AtomicInteger(0);
    private Integer globalCount = 0;

        resolveSudoku(parallelMatrix matrix) {
            this.matrix = matrix;

        }
        protected Integer compute(){
            int min=10;
            int momentCount=0;
            int choicesRow=0, choicesColumn=0;
            Stack<Move> stack = new Stack<>();
            for (int row = 0; row < 9; row++)
                for (int column = 0; column < 9; column++)
                    if(matrix.isEmpty(row, column)){
                        for (int n = 1; n < 10; n++)
                            if(matrix.check(n, row, column))
                                momentCount++;
                        if(momentCount < min){
                            min = momentCount;
                            choicesRow=row;choicesColumn=column;
                        }
                        momentCount=0;
                    }

            for (int n = 1; n < 10; n++)
                if(matrix.check(n, choicesRow, choicesColumn))
                    stack.push(new Move(n, choicesRow, choicesColumn));
            //System.out.println("ok");
            if(stack.isEmpty()) {
                if (matrix.isEnd()) {
                    globalCount++;
                }
            }else{
                resolveSudoku ret=null;
                while (!stack.isEmpty()) {
                    Move move = stack.pop();
                    Integer[][] copyMat = matrix.matrixCopy();
                    matrix.put(move.number, move.row, move.column);
                    ret = new resolveSudoku(matrix);
                    ret.fork();
                    matrix = new parallelMatrix(copyMat);
                }
                //System.out.println("ok");
                globalCount += ret.join();
                }
            /*
                SumArray left = new SumArray(arr,lo,(hi+lo)/2);
                SumArray right= new SumArray(arr,(hi+lo)/2,hi);
                left.fork();
                int rightAns = right.compute();
                int leftAns = left.join();
                return leftAns + rightAns;
            */
            return globalCount;
        }
}
