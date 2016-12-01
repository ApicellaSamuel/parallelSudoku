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
        readInput input = new readInput("input.txt");
        parallelMatrix matrix = new parallelMatrix(input.read());
        System.out.println(resolve(matrix));
    }
    private static final ForkJoinPool fjPool = new ForkJoinPool();
    private static int resolve(parallelMatrix matrix) {
        resolveSudoku sdk = new resolveSudoku(matrix);
        return fjPool.invoke(sdk);
    }
}