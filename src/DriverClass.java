import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class DriverClass {

    //Hashmap that will map each character index position to its coordinates in puzzlegrid matrix
    public static HashMap<Integer, LinkedList<Coordinates>> charToCoordinates = new HashMap<>();

    //List of dictionary
    private static  final List<String> dictionaryList = new ArrayList<>();

    //List that will store each row of puzzle grid
    private static  final List<String> rowList = new ArrayList<>();

    //2-D character array for storing puzzle
    public  static char[][] puzzleGrid;

    //Array that will contain all dictionary words
    public static String[] dictionary;

    //boolean result that will store true if puzzle is created else false
    private boolean pResult;

    public static void main(String args[]) throws IOException, InterruptedException {


        int index;

        //Object of the driver class
        DriverClass driverClass = new DriverClass();

        //Object of the boggleSolver class
        BoggleSolver boggleSolver = new BoggleSolver();


        //Getting inputs from the Dictionary.txt file
        FileInputStream dictionaryStream = new FileInputStream("Dictionary.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(dictionaryStream));

        //calling getDictionary() method and storing returned values in result
        boolean result = driverClass.getDictionary(br);

        //Getting inputs from the puzzle.txt file
        FileInputStream puzzleStream = new FileInputStream("puzzle.txt");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(puzzleStream));

        try {
            //calling getPuzzle() method and storing returned values in result
            //This method will throw exception if number of letters in each row of the puzzle is different
             driverClass.pResult = driverClass.getPuzzle(br1);
        }
        catch (MismatchRowLengthException e)
        {
            //Setting boolean value false if exception thrown
            driverClass.pResult = false;
            System.out.println(e.toString());
        }


        //Initializing map value
        for (int i = 0; i < 26; i++) {
            DriverClass.charToCoordinates.put(i, new LinkedList<>());
        }

        //calling solve() method if both getDictionary() and getPuzzle() method return true
        if (result && driverClass.pResult) {

            System.out.println(driverClass.print());
            //Iterate over puzzlegrid matrix and finding and storing of character coordinates in the map
            for (int i = 0; i < DriverClass.puzzleGrid.length; i++) {
                for (int j = 0; j < DriverClass.puzzleGrid.length; j++) {
                    index = driverClass.getIndexPosition(i, j, DriverClass.puzzleGrid);
                    try {
                        DriverClass.charToCoordinates.get(index).addLast(new Coordinates(i, j));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            //Startingtime before execution starts of the solve() method
            long startTime = System.nanoTime();
            List<String> foundSolutionString  = boggleSolver.solve();

            //Endingtime- The time solve() method took to find the final solution
            long endTime = System.nanoTime();
           // System.out.println("end time is: " + endTime);
            long timeElapsed = endTime - startTime;

            //Printing total time elapsed
            System.out.println("Execution time in milliseconds: " + timeElapsed / 1000000);

            //Printing the final solution
            for(String s:foundSolutionString)
            {
                System.out.println(s);
            }
        }

    }

    /**
     * This method will print the entire puzzleGrid matrix
     * @return
     * String  - This method will return the string containing entire puzzleGrid matrix
     */

    private String print()
    {
        String puzzleGridString = "";
        System.out.println("\nThe puzzle grid is: ");
        for (int i = 0; i < DriverClass.puzzleGrid.length; i++) {
            for (int j = 0; j < DriverClass.puzzleGrid.length; j++) {
                puzzleGridString = puzzleGridString + DriverClass.puzzleGrid[i][j] + " ";
            }
            puzzleGridString = puzzleGridString + "\n";
        }


        return puzzleGridString;
    }

    /**
     * This method will get each row of the puzzle from puzzle.txt file and will
     * throw exception if number of letters in each row is different
     * @param
     * stream - The stream where our input data from puzzle.txt resides
     *
     * @return
     * boolean - return true if puzzle created else false
     */
    private boolean getPuzzle(BufferedReader stream) throws IOException, MismatchRowLengthException {

        String line;
        int length;
        //Read File Line By Line
        while ((line = stream.readLine()) != null) {

            //Store each line in an array of strings
            if (rowList.isEmpty()) {
                rowList.add(line.toUpperCase());
                length = rowList.get(0).length();
                System.out.println("Length of row is:" + length);
            } else {
                length = rowList.get(0).length();
                if (line.length() != length) {
                    throw new MismatchRowLengthException("Every row of the puzzle should have same number of letters");
                } else {
                    rowList.add(line.toUpperCase());
                }

            }
            DriverClass.puzzleGrid = new char[length][length];

            //initializing puzzlegrid with the correct data
            for (int i = 0; i < rowList.size(); i++) {
                DriverClass.puzzleGrid[i] = rowList.get(i).toCharArray();
            }

        }
        return true;
    }

    /**
     * This method will store each word found from dictionary.txt into dictioanry[] array
     * @param
     * stream -  The stream where our input data from dictionary.txt resides
     *
     * @return
     * boolean - return true if dictionary is not empty otherwise false
     */
    private boolean getDictionary(BufferedReader stream) throws IOException {


        String strLine;

        //Read File Line By Line
        while ((strLine = stream.readLine()) != null) {


            if(strLine.length()>=2)
            {
                String[] moreword = strLine.split(" ");
                if(moreword.length>1)
                {
                    System.out.println("The word ---  " + strLine + " --- can'be entered in dictionary: ");
                    continue;
                }
                else
                {
                    dictionaryList.add(strLine.toUpperCase());
                }
            }
            else
            {
                System.out.println("Word not added in dictionary: --- " + strLine );
            }
            //Store each line in an array of strings



        }
        DriverClass.dictionary = new String[dictionaryList.size()];
        //Array containing dictionary words
        dictionaryList.toArray(DriverClass.dictionary);

        if (DriverClass.dictionary.length != 0) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * This method will get the inputs and will return the index/key where this character resides in charToCoordinates map
     * @param
     * i - for row value
     * @param
     * j - for column value
     * @param
     * puzzleGrid - passing whole puzzleGrid matrix
     *
     * @return
     * int  - index position
     */
    private int getIndexPosition(int i, int j, char[][] puzzleGrid) {

        char c = puzzleGrid[i][j];
        return c - 'A';
    }
}
