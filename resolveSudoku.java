import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by sam on 01/12/16.
 *
 */
public class resolveSudoku extends RecursiveTask<Integer> {
    private Matrix matrix;
    private Integer globalCount = 0;
    public static long countSolvers;

        public resolveSudoku(Matrix matrix) {
            this.matrix = matrix;
        }
        protected Integer compute(){

            if (matrix.isEnd())
                return ++globalCount;

            boolean CUTOFF = matrix.isEnd < 59;

            List<ForkJoinTask<Integer>> ret;
            int min=10;
            int currentCount=0;
            int choiceRow=0, choiceColumn=0;

            for (int row = 0; row < 9; row++)
                for (int column = 0; column < 9; column++)
                    if(matrix.isEmpty(row, column)){
                        for (int n = 1; n < 10; n++)
                            if(matrix.check(n, row, column)){
                                currentCount++;
                                }
                        // se è il minimo aggiorno le coordinate della casella da scegliere
                        if(currentCount < min){
                            min = currentCount;
                            choiceRow=row;choiceColumn=column;
                        }
                        currentCount=0;
                    }

            if(CUTOFF)
                return new sequentialSudoku(new Matrix(matrix.matrixCopy())).resolve();

            else{
                ret = new ArrayList<>();//di ForkJoinTask
                for (int number = 1; number < 10; number++)
                    if (matrix.check(number, choiceRow, choiceColumn)) {
                        Integer[][] copyMat = matrix.matrixCopy();
                        matrix.put(number, choiceRow, choiceColumn);
                        ret.add(new resolveSudoku(matrix).fork());
                        countSolvers++;
                        matrix = new Matrix(copyMat);
                    }

                for (ForkJoinTask<Integer> rSDK : ret)
                    globalCount += rSDK.join();
            }

            return globalCount;
        }

}
