package parallel;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sam on 01/12/16.
 *
 */
public class parallelMatrix {

    private Integer[][] matrix;
    private int[][] caratteristicMatrix = new int[9][9];
    private HashMap<Integer, HashSet<Integer>> quadrants = new HashMap<>();

    /*public parallelMatrix(String fileName){
        readInput r = new readInput(fileName);
        this.matrix = r.read();
        for(int i = 1; i < 10; i++)
            this.quadrants.put(i, new HashSet<>());
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                Integer n = this.matrix[row][column];
                if(n != 0){
                    this.caratteristicMatrix [row] [n-1]      = (this.caratteristicMatrix[row][n-1]    != 0 ? 3 : 1);
                    this.caratteristicMatrix [n-1] [column]   = (this.caratteristicMatrix[n-1][column] != 0 ? 3 : 2);
                    this.quadrantsHandler(n, row, column, true);
                }
            }
        }
    }*/
    public parallelMatrix(Integer[][] matrix){
        this.matrix=matrix;
        for(int i = 1; i < 10; i++)
            this.quadrants.put(i, new HashSet<>());
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                Integer n = this.matrix[row][column];
                if(n != 0){
                    this.caratteristicMatrix [row] [n-1]      = (this.caratteristicMatrix[row][n-1]    != 0 ? 3 : 1);
                    this.caratteristicMatrix [n-1] [column]   = (this.caratteristicMatrix[n-1][column] != 0 ? 3 : 2);
                    this.quadrantsHandler(n, row, column, true);
                }
            }
        }
    }

    /**
     * @param n il numero di cui si vuole controllare se è possibile inserirlo
     * @param row la riga
     * @param column la colonna
     * @return true se il numero può essere inserito rispettando le regole del sequentialSudoku, false altrimenti
     */

    //è possibile metterlo?
    public boolean check(int n, int row, int column) {
        if(this.matrix[row][column] != 0) return false;
        if(this.caratteristicMatrix[row][n-1] == 1 || this.caratteristicMatrix[row][n-1] == 3) return false;
        if(this.caratteristicMatrix[n-1][column] == 2 || this.caratteristicMatrix[n-1][column] == 3) return false;
        return !this.quadrantsHandler(n, row, column, false);//se non lo contiene return true;
    }

    public BigInteger searchSpace() {
        BigInteger count = new BigInteger("0"); BigInteger searchSpace= new BigInteger("1");
        for (int row = 0; row < 9; row++)
            for (int column = 0; column < 9; column++)
                if (this.matrix[row][column] == 0) {
                    for (int n = 1; n < 10; n++) {
                        if (this.check(n, row, column)) {
                            count = count.add(new BigInteger("1"));
                        }
                    }
                    searchSpace = searchSpace.multiply(count);
                    count = new BigInteger("0");
                }
        return searchSpace;
        }

        public void printEmptyCellsAndFillFactor(){
            int countCells = 0;
            for (int row = 0; row < 9; row++)
                for (int column = 0; column < 9; column++)
                    if (this.matrix[row][column] == 0) {
                        countCells++;
                    }
            System.out.println("empty cells: " + countCells);
            System.out.println("fill factor: " + ( 100 * ( 81 - countCells ) ) / 81 + "%");
        }

    /**
     * @param row la riga
     * @param column la colonna
     * @return l'insieme dei numeri che possono essere messi in {@code row} e {@code column}
     *         rispettando le proprietà del sequentialSudoku
     */

    public Set legalNumbers(int row, int column){
        HashSet<Integer> ret = new HashSet<>();
        for(int number = 1; number < 10; number++)
            if(check(number, row, column))
                ret.add(number);
        return ret;
    }

    public boolean isEnd(){
        for(int row = 0; row < 9; row++)
            for(int column = 0; column < 9; column++)
                if(this.matrix[row][column] == 0) return false;
        return true;
    }

    /**
     * @param n il numero da inserire
     * @param row la riga
     * @param column la colonna
     * @return false se il numero non è stato inserito perchè non rispetta le regole del sequentialSudoku
     *         oppure true se il numero è stato inserito rispettando le regole del sequentialSudoku
     */

    public boolean put(int n, int row, int column){
        //if(!this.check(n, row, column)) return false;
        this.quadrantsHandler(n, row, column, true);
        this.matrix[row][column] = n;
        this.caratteristicMatrix[row][n-1]=(this.caratteristicMatrix[row][n-1] == 2 ? 3 : 1);
        this.caratteristicMatrix[n-1][column]=(this.caratteristicMatrix[n-1][column] == 1 ? 3 : 2);
        return true;
    }

    public void printQuad(){
        for(int i = 1; i < 10; i = i+3){
            this.quadrants.get(i).forEach(System.out::print);System.out.print("   ");
            this.quadrants.get(i+1).forEach(System.out::print);System.out.print("   ");
            this.quadrants.get(i+2).forEach(System.out::print);System.out.println();
        }
    }

    private boolean quadrantsHandler(int n, int row, int column, boolean put){
        if(row < 3 ){
            if(column < 3){
                if (put) return this.quadrants.get(1).add(n);
                else     return this.quadrants.get(1).contains(n);
            }
            if(column < 6) {
                if (put) return this.quadrants.get(2).add(n);
                else     return this.quadrants.get(2).contains(n);
            }
            if(column < 9){
                if (put) return this.quadrants.get(3).add(n);
                else     return this.quadrants.get(3).contains(n);
            }
        }
        if(row < 6){
            if(column < 3){
                if (put) return this.quadrants.get(4).add(n);
                else     return this.quadrants.get(4).contains(n);
            }
            if(column < 6){
                if (put) return this.quadrants.get(5).add(n);
                else     return this.quadrants.get(5).contains(n);
            }
            if(column < 9){
                if (put) return this.quadrants.get(6).add(n);
                else     return this.quadrants.get(6).contains(n);
            }
        }else{
            if(column < 3){
                if (put) return this.quadrants.get(7).add(n);
                else     return this.quadrants.get(7).contains(n);
            }
            if(column < 6){
                if (put) return this.quadrants.get(8).add(n);
                else     return this.quadrants.get(8).contains(n);
            }else{
                if (put) return this.quadrants.get(9).add(n);
                else     return this.quadrants.get(9).contains(n);
            }
        }
        return true;
    }

    public Integer[][] matrixCopy(){
        Integer[][] ret = new Integer[9][9];
        for(int i = 0; i < 9; i++)
            for(int j = 0; j < 9; j++)
                ret[i][j] = this.matrix[i][j];
        return ret;
    }
    public boolean isEmpty(int row, int column){
        return this.matrix[row][column]==0;
    }

    @Override
    public String toString(){
        String matrixToReturn = "";
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                if(this.matrix[i][j].equals(0)) matrixToReturn += ". ";
                else matrixToReturn += this.matrix[i][j] + " ";
            }
            matrixToReturn += "\n";
        }
        return matrixToReturn;
    }

}
