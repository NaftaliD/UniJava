/**
 * This class implements four static functions as solutions for Ex13.
 *
 * @author  Naftali Deutsch
 * @version 1
 */
public class Ex13 {

    /**
     * The function expects an array and the median value for the array, and builds and return a special array out of the values
     * Time Complexity - O(n)
     * Space Complexity - O(n) - returned array
     * @param arr unsorted int array with unique values
     * @param med the median of the array, defined as either n/2 > med > n/2 or n/2-1 > med > n/2
     * @return a special array in the structure of arr[0] > arr[1] < arr[2] > arr[3] < arr[4] > … > arr[n-2] < arr[n-1] or arr[0] > arr[1] < arr[2] > arr[3] < arr[4] > … > arr[n-2] < arr[n-1]
     */
    public static int[] specialArr (int[] arr, int med) {
        int[] specialArr = new int[arr.length];
        int smallIndex = 1; //hold index for small values (smaller than median) in specialArr
        int largeIndex = 0; //hold index for large values (larger than median) in specialArr

        for(int i = 0; i<arr.length; i++) {
            if(arr[i] >= med){ //Due to the definition of med for both even and odd arr.len med needs to be used as a larger value
                specialArr[largeIndex] = arr[i];
                largeIndex += 2;
            }
            else //arr[i] < med
            {
                specialArr[smallIndex] = arr[i];
                smallIndex += 2;
            }
        }

        return specialArr;
    }

    /**
     * The first method finds the lowest positive number (>0) that isn't a part of arr
     * The function first sorts the array by value=index if possible eg arr[0]=1 arr[1]=2 arr[n] = n+1, this is O(n)
     * The function then goes over the array and if it finds a value that doesn't fit the above sort (arr[n] = n+1)
     * it means that the fitting positive int isn't in the array,
     * The first such index is also the lowest positive integer not in the array - this is O(n)
     * Time Complexity - O(n)
     * Space Complexity - O(1)
     * @param arr unsorted int array
     * @return the smallest positive int that isn't in arr
     */
    public static int first (int [] arr) {
        positiveSort(arr); //sort the array in O(n)

        for(int i = 0; i < arr.length; i++) { //linear O(n) pass on arr
            if(arr[i] != i+1){ //if arr[i] != i+1 that means i+1 isnt in the array, and it should be the first positive number
                return i+1;
            }
        }
        return arr.length+1;
    }

    //sorts the array by value=index if possible eg arr[0]=1 arr[1]=2 arr[n] = n+1, this is O(n)
    //ignore numbers out of bounds (n<=0 or n>arr.length)
    private static void positiveSort(int[] arr) {
        for(int i = 0; i < arr.length; i++){
            if(arr[i] > 0 && arr[i] <= arr.length){
                swap(arr, i, arr[i]-1);
            }
        }
    }

    //swap values from two cells in array arr
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * Recuse over arr to find the longest nearlyPal,
     * Go over all indexes if we find two equal numbers we treat this as a start of a nearlyPal and start counting the length
     * Return the maximum such length
     * @param arr int arr, all positive
     * @return length of the longest NearlyPal sequence in the arr
     */
    public static int longestNearlyPal (int[] arr) {
        return longestNearlyPal(arr, 0, arr.length-1);
    }

    /*
     * Go over arr until we find two equal digits, when we do start checking if it's a nearlyPal if so return its len
     * If no continue looking deeper
     */
    private static int longestNearlyPal (int[] arr, int start, int end) {
        if(start >= end){ //this is the middle of the array, we are done
            return 1;
        }

        if(arr[start] == arr[end]){
            int len = nearlyPalLen(arr, start + 1, end-1, 0, 2); //start checking len of nearlyPal (will return 0 if its not)
            if(len != 0) return len;
        }

        return Math.max(longestNearlyPal(arr, start+1, end),
                    longestNearlyPal(arr, start, end-1));

    }

    //Check Len of nearlyPal, if we find that the streak is not a nearlyPal we return 0
    private static int nearlyPalLen(int[] arr, int start, int end, int skippedDigits, int NearlyPalLen){
        if(skippedDigits >= 2){ //found two digits that break the Pal structure there-for this is not a NearlyPal and length should be zero
            return 0;
        }

        if(start == end){ //middle of a streak both pointers on the same number
            return NearlyPalLen+1;
        }
        if(start > end){//pointers passed one another, all numbers counted
            return NearlyPalLen;
        }

        if(arr[start] == arr[end]){
            return nearlyPalLen(arr, start + 1, end-1, skippedDigits, NearlyPalLen+2);
        }
        return Math.max(nearlyPalLen(arr, start+1, end, skippedDigits+1, NearlyPalLen+1),
                nearlyPalLen(arr, start, end-1, skippedDigits+1, NearlyPalLen+1));

    }


    /**
     * Go over m, a two-dimensional int array and look for paths to get from 0,0 to m.len, m.len, Progress is allowed in all straight direction (up, down. left, right)
     * The function returns the lowest maximum number of all paths
     * @param m int [][] arr, contains all positive (>0)
     * @return The lowest possible maximum number for a track between arr[0][0] and arr[len][len]
     */
    public static int extreme(int [][] m){
        return extreme(m, 0, 0, 0);
    }

    //recursive look for paths in m, check all possible direction for progression, while holding the max number for the current path
    //if the current cell is invalid this path is wrong, exit
    //if the current cell is the last this is the end of the path, return the max number of the path that led us here
    //if this is a valid cell (not last) call extreme for all possible paths and return the min of all paths
    private static int extreme(int [][] m, int row, int col, int maxDigit){
        if(row < 0 || col < 0 || row >= m.length || col >= m.length || m[row][col] == 0){ //invalid cell
            return Integer.MAX_VALUE; //this will never be selected in Math.min there I use it to point out a dead trail
        }

        int slotVal = m[row][col];

        if(row == m.length-1 && col == m.length-1){ //final cell
            return Math.max(maxDigit, slotVal);
        }
        m[row][col] = 0; //mark a cell as visited, will shift back once we return

        //As we look for a path collect the maximum number in the path
        int trail1 = extreme(m, row, col+1, Math.max(maxDigit, slotVal));
        int trail2 = extreme(m, row+1, col, Math.max(maxDigit, slotVal));
        int trail3 = extreme(m, row-1, col, Math.max(maxDigit, slotVal));
        int trail4 = extreme(m, row, col-1, Math.max(maxDigit, slotVal));
        m[row][col] = slotVal;

        //Find the trail that had the lowest maximum number, and return
        return Math.min(trail1, Math.min(trail2, Math.min(trail3, trail4)));

    }

}
