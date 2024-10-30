/**
 * The MaxLine class accepts the values of three points and prints the two points that are farthest apart
 *
 * @version 1
 */

import java.util.Scanner;


public class MaxLine {

    /**
     * accept three points (X, Y) from the user
     * calculate the distance between all three points (AB, BC, AC)
     * print the two point with the largest distance
     * @param args unused
     */
    public static void main(String[] args) {

        Scanner scan = new Scanner (System.in);
        System.out.println("Enter first point coordinates:"); //read value of point A
        int x1 = scan.nextInt();
        int y1 = scan.nextInt();

        System.out.println ("Enter second point coordinates:"); //read value of point B
        int x2 = scan.nextInt();
        int y2 = scan.nextInt();

        System.out.println ("Enter third point coordinates:"); //read value of point C
        int x3 = scan.nextInt();
        int y3 = scan.nextInt();

        double distance, maxDistance;
        String maxDistancePoints;

        distance = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); //calc distance of AB
        maxDistance = distance;
        maxDistancePoints = "(" + x1 + "," + y1 + "), (" + x2 + "," + y2 + ")."; //set starting max distance as AB

        distance = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2)); //calc distance of BC
        if (distance > maxDistance) { //if BC is greater than AB (maxDistance) redefine maxDistance and the points defining it
            maxDistance = distance;
            maxDistancePoints = "(" + x2 + "," + y2 + "), (" + x3 + "," + y3 + ").";
        }

        distance = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));//calc distance of AC
        if (distance > maxDistance) { //if AC is greater than AB or BC (maxDistance) redefine maxDistance and the points defining it
            maxDistancePoints = "(" + x1 + "," + y1 + "), (" + x3 + "," + y3 + ").";
        }

        System.out.println("Max line created by the following points: " + maxDistancePoints);
    }

}
