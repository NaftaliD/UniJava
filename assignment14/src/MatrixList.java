
/**
 * The Class MatrixList holds and represents an int LinkedNodes matrix of size m*n.
 *
 * @author Naftali Deustch
 * @version 0
 */
public class MatrixList
{
    IntNodeMat _m00;

    /**
     * empty constructor initializes the matrix to null
     */
    public MatrixList()
    {
        _m00 = null;
    }

    /**
     * Constructs a matrix from mat
     * @param mat an int two dimension array, if empty matrix will be initialized to null
     */
    public MatrixList(int[][]mat)
    {
        this();
        if(mat.length == 0) return;

        _m00 = new IntNodeMat(mat[0][0]);
        IntNodeMat tempRow = _m00;
        IntNodeMat prevCol = null;

        for(int col = 0; col < mat[0].length; col++){
            IntNodeMat startOfCol = tempRow; //save pointer to start of column

            //loop over a column of the array and create fitting IntNodes, each intNode created links itself to nodes above and behind (left, could be null) itself
            for(int row = 1; row < mat.length; row++){
                IntNodeMat next = new IntNodeMat(mat[row][col]);

                //set connection to the prevRow
                next.setPrevRow(tempRow);
                tempRow.setNextRow(next);

                //set connection to the prevCol
                if(prevCol != null){
                    prevCol = prevCol.getNextRow();
                    next.setPrevCol(prevCol);
                    prevCol.setNextCol(next);
                }

                tempRow = next;
            }

            //create the first IntNode of a col from which we can start building the entire column
            prevCol = startOfCol;
            if(col != mat[0].length-1){
                tempRow = new IntNodeMat(mat[0][col+1]);
                tempRow.setPrevCol(prevCol);
                prevCol.setNextCol(tempRow);
            }
        }
    }


    /**
     * Getter for data from node at i j
     * @param i row index
     * @param j col index
     * @return The value of the node at i,j. if input is invalid return Integer.MIN_VALUE
     */
    public int getData_i_j (int i, int j)
    {
        IntNodeMat temp = getIntNode(i, j);
        if (temp != null) { return temp.getData(); }

        return Integer.MIN_VALUE;
    }

    /**
     * Setter for data in node i j
     * @param data int value to set
     * @param i row index
     * @param j col index
     */
    public void setData_i_j (int data, int i, int j)
    {
        IntNodeMat temp = getIntNode(i, j);
        if (temp != null) { temp.setData(data); }
    }

    //get reference to int node in i, j returns null if any of them is out of bounds
    private IntNodeMat getIntNode(int i, int j){
        IntNodeMat temp = _m00;

        //go over the matrix until i==0, which means we are in the correct row
        while(i > 0){
            if(temp.getNextRow() == null){ // i index is out of bound for this matrix
                return null;
            }
            i--;
            temp = temp.getNextRow();
        }
        //go over the matrix until j==0, which means we are in the correct col
        while(j > 0){
            if(temp.getNextCol() == null){ // j index is out of bound for this matrix
                return null;
            }
            j--;
            temp = temp.getNextCol();
        }
        return temp;
    }

    /**
     * prints the matrix to String, adds "\t" between values in a row and "\n" between rows (and after the last one)
     * @return String representation of the matrix
     */
    public String toString()
    {
        String out = "";
        IntNodeMat tempRow = _m00;
        IntNodeMat tempCol = _m00;

        //loop on an entire row and add it to string, once we are done with a row start adding the next one, using the saved pointer in the outside loop
        while(tempRow != null){
            while(tempCol != null){
                out += tempCol.getData() + ((tempCol.getNextCol() != null) ? "\t" : "\n");
                tempCol = tempCol.getNextCol();
            }
            tempRow = tempRow.getNextRow();
            tempCol = tempRow;
        }
        return out;
    }

    /**
     * Recursive function that finds the maximum value in the matrix
     * the function uses findMaxInLine that recursively finds the maximum value in a line and then compares all the lines
     * @return the max value in the matrix, or Integer.MIN_VALUE if the matrix is empty
     */
    public int findMax()
    {
        return findMax(_m00, Integer.MIN_VALUE);
    }

    private int findMax(IntNodeMat temp, int max){
        if(temp == null){ //We are past the last row
            return max;
        }
        max = Math.max(findMaxInLine(temp, Integer.MIN_VALUE), max); //get the maximum number in this row, and compare it to the previews maximum
        return findMax(temp.getNextRow(), max);
    }

    private int findMaxInLine(IntNodeMat temp, int max){
        if(temp == null){ //we are past the last col
            return max;
        }
        return findMaxInLine(temp.getNextCol(), Math.max(temp.getData(), max));
    }

    /**
     * Efficient function to how many instances of value x appear in a sorted matrix of size M by N (rules of sort are num increase in size for every row and col, a number cannot appear twice in the same row/col)
     * First go to the last row - this is M actions
     * Then Start progressing right by the columns (with the number sizes), when we reach a number equal or greater than x we go up a line and continue searching in that line
     * At most we will travel all the way right and up in the matrix which is M + N actions
     * Time Complexity - O(n + m)
     * Space Complexity - O(1)
     * @param x value to look for in the matrix
     * @return number of appearances of x in the matrix
     */
    public int howManyX(int x)
    {
        //get to last row
        IntNodeMat temp = _m00;
        while(temp != null && temp.getNextRow() != null){ temp = temp.getNextRow(); }

        int counter = 0;
        //Go over the matrix with this logic:
        //if value is smaller than X keep moving in the line,
        //if value is x add to counter and move up a line and continue searching in that line
        //if value is greater the x move up a line and continue searching
        while(temp != null){
            int value = temp.getData();
            if(value == x){
                counter++;
                temp = temp.getPrevRow();
            }
            else if(value < x){
                temp = temp.getNextCol();
            }
            else if(value > x){
                temp = temp.getPrevRow();
            }
        }
        return counter;
    }

}
