import java.util.concurrent.ForkJoinPool;

/**
 * Created by sam on 24/11/16.
 * __Appunti e idee__
 * + paralellizare readInput
 * + parallelMatrix da parallelizare
 * + cutoff applicato?
 */
public class parallelSudoku {
    public static void main(String[] args){
        readInput input = new readInput("input.text");
        parallelMatrix matrix = new parallelMatrix(input.read());
        resolve(matrix);
    }
    static final ForkJoinPool fjPool = new ForkJoinPool();
    static int resolve(parallelMatrix matrix) {
        resolveSudoku sdk = new resolveSudoku(matrix);
        return fjPool.invoke(sdk);
    }
}