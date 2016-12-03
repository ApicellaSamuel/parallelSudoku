import parallel.*;
import sequential.*;
import java.util.concurrent.ForkJoinPool;

import static java.lang.System.out;

/**
 * Created by sam on 24/11/16.
 * __Appunti e idee__
 * + paralellizare parallel.readInput?
 * + parallel.parallelMatrix da parallelizare?
 * + cutoff applicato?
 */
public class sudokuMain {

    private static final ForkJoinPool fjPool = new ForkJoinPool();
    static countSolvers countSolvers;

    public static void main(String[] args){
        readInput input;
        int solutionsPar, solutionsSeq;
        long time, timeSeq, timePar;
        for(String arg : args){
            input = new readInput(arg);
            long ms, s, m;
            parallelMatrix matrix = new parallelMatrix(input.read());
            matrix.printEmptyCellsAndFillFactor();
            out.println("search space before elimination: "+matrix.searchSpace() + " branches");
            out.println();
            out.println("solving in parallel...");
            countSolvers = new countSolvers();
            time = System.currentTimeMillis();
            solutionsPar = resolveParallel(matrix);
            timePar = System.currentTimeMillis() - time;
            m = ( timePar / 1000 ) / 60;
            s = timePar / 1000;
            ms = timePar % 1000;
            out.println("done in: " + m + "m " + s + "s " + ms + "ms" );
            out.println("solutions: " + solutionsPar);
            out.println("forked solvers: "+countSolvers.getCountSolvers());
            out.println();

            out.println("solving sequentially...");//creazione della matrice è da contare nel tempo?
            time = System.currentTimeMillis();
            Matrix sMatrix = new Matrix(input.read());
            solutionsSeq = new sequentialSudoku(sMatrix).resolve();
            timeSeq = System.currentTimeMillis() - time;
            m = ( timeSeq / 1000 ) / 60;
            s = timeSeq / 1000;
            ms = timeSeq % 1000;
            out.println("done in: " + m + "m " + s + "s " + ms + "ms" );
            out.println("solutions: " + solutionsSeq);

            out.println();

            out.println("Speedup: " + timeSeq / timePar +"."+ timeSeq % timePar);
        }
    }

    private static int resolveParallel(parallelMatrix matrix) {
        resolveSudoku sdk = new resolveSudoku(matrix);
        return fjPool.invoke(sdk);
    }
}