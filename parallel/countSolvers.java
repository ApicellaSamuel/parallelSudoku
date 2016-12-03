package parallel;

import java.math.BigInteger;

/**
 * Created by sam on 03/12/16.
 *
 */
public class countSolvers {
    private static BigInteger countSolvers = new BigInteger("0");

    public static synchronized void increment(){
        countSolvers = countSolvers.add(BigInteger.ONE);
    }

    public BigInteger getCountSolvers(){
        return countSolvers;
    }
}
