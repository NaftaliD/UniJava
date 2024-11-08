/**
 * The Game class accepts values from two players and prints out who won the game
 *
 * @Owner Naftali Deutsch
 * @Version 1
 */
import java.util.Scanner;


public class Game
{
    /**
     * The program accepts an object value from two players (R, P, S) and prints out who wins the game (Rock Paper Scissors rules)
     * @param args unused
     */
    public static void main (String [] args)
    {
        final char ROCK = 'R';
        final char SCISSORS = 'S';
        final char PAPER = 'P';
        final String PLAYER_1_WIN = "Player 1 wins.";
        final String PLAYER_2_WIN = "Player 2 wins.";
        final String TIE = "Game ends with a tie.";

        Scanner scan = new Scanner (System.in);
        System.out.println("Enter first player's object:");
        char player1 = scan.next().charAt(0);

        System.out.println("Enter second player's object:");
        char player2 = scan.next().charAt(0);

        if (player1 == player2){ //if the two values match, it's a tie.
            System.out.println(TIE);
            return;
        }

        if(player1 == ROCK){
            if (player2 == SCISSORS){
                System.out.println(PLAYER_1_WIN);
            } else if (player2 == PAPER) {
                System.out.println(PLAYER_2_WIN);
            }
        } else if (player1 == PAPER){
            if (player2 == SCISSORS){
                System.out.println(PLAYER_2_WIN);
            } else if (player2 == ROCK) {
                System.out.println(PLAYER_1_WIN);
            }
        } else if (player1 == SCISSORS){
            if (player2 == ROCK){
                System.out.println(PLAYER_2_WIN);
            } else if (player2 == PAPER) {
                System.out.println(PLAYER_1_WIN);
            }
        } //By now the result should have been printed, if the value provided is valid

    } // end of method main
} //end of class Game
