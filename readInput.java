
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by sam on 01/12/16.
 *
 */
public class readInput {
        /**
         * @param fileName il file contenente l'input.txt
         */
        public readInput(String fileName){
            this.fileName = fileName;
        }
        /**
         * @return una matrice {@code matrix} di Integer 9*9 relativa all'input.txt dato (il numero 0 indica la cella vuota)
         * @throws IOException se il file {@code fileName} non esiste
         */
        public Integer[][] read(){
            Integer[][] matrix = new Integer[9][9];
            try {
                FileReader reader = new FileReader(fileName);
                Scanner in = new Scanner(reader);
                String line;
                for(int i = 0; i < 9; i++){
                    line = in.nextLine();
                    for(int j = 0; j < 9; j++){
                        matrix[i][j] = Integer.parseInt(line.charAt(j) != '.' ? (String.valueOf(line.charAt(j))) : "0") ; //da migliorare
                    }
                }
            }catch(IOException e){ e.printStackTrace(); }
            return matrix;
        }

        @Override
        public String toString(){
            Integer[][] matrix = this.read();
            String matrixToReturn = "";
            for(int i = 0; i < 9; i++){
                for(int j = 0; j < 9; j++){
                    matrixToReturn += matrix[i][j] + " ";
                }
                matrixToReturn += "\n";
            }
            return matrixToReturn;
        }

        private String fileName;
    }
