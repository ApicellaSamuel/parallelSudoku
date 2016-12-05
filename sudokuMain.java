import java.util.concurrent.ForkJoinPool;

import static java.lang.System.out;

/**
 * Created by sam on 24/11/16.
 *
 */
public class sudokuMain {

    private static final ForkJoinPool fjPool = new ForkJoinPool();

    public static void main(String[] args){
        readInput input;
        int solutionsPar, solutionsSeq;
        long time, timeSeq, timePar;
        long ms, s, m;
        for(String arg : args){
            input = new readInput(arg);
            Matrix matrix = new Matrix(input.read());
            matrix.printEmptyCellsAndFillFactor();//stampa le celle vuote e il fill factor
            out.println("search space before elimination: "+matrix.searchSpace() + " branches");
            out.println();
            out.println("solving in parallel...");
            time = System.currentTimeMillis();
            solutionsPar = resolveParallel(matrix);
            timePar = System.currentTimeMillis() - time;
            m = ( timePar / 1000 ) / 60;
            timePar -= m * 60 * 1000;
            s = timePar / 1000;
            ms = timePar % 1000;
	    timePar += m * 60 * 1000;
            out.println("done in: " + m + "m " + s + "s " + ms + "ms" );
            out.println("solutions: " + solutionsPar);
            out.println("forked solvers: "+sdk.countSolvers);
            out.println();

            out.println("solving sequentially...");//creazione della matrice è da contare nel tempo?
            time = System.currentTimeMillis();
            Matrix sMatrix = new Matrix(input.read());
            solutionsSeq = new sequentialSudoku(sMatrix).resolve();
            timeSeq = System.currentTimeMillis() - time;
            m = ( timeSeq / 1000 ) / 60;
            timeSeq -= m * 60 * 1000;
            s = timeSeq / 1000;
            ms = timeSeq % 1000;
	    timeSeq += m * 1000 * 60; 
            out.println("done in: " + m + "m " + s + "s " + ms + "ms" );
            out.println("solutions: " + solutionsSeq);

            out.println();

            out.println("Speedup: " + timeSeq / timePar +"."+ timeSeq % timePar);

            out.println();
        }
    }
    private static resolveSudoku sdk;
    private static int resolveParallel(Matrix matrix) {
        sdk = new resolveSudoku(matrix);
        return fjPool.invoke(sdk);
    }
}
