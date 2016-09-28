import minimax.Minimax;
import model.Game;
import model.Player;

import java.util.Scanner;

public class TicTacToe {

	// Konsol klient til at spille imod minimax AI
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome!");

        // spil data 
        Player userPlayer = null;

        // Tillad spilleren at vælge X eller O (X starter)
        while (userPlayer == null) {
            System.out.print("Do you want to play as X or O? X goes first, but O is hella swag!");
            char choice = input.nextLine().toUpperCase().trim().charAt(0);
            if(choice == 'O' || choice == 'X'){            	
            	userPlayer = Player.fromSymbol(choice);
            }
            else{
            	System.out.println("Invalid choice! Write either X or O!");
            }
        }

        // Sætter den anden spiller "computerAgent" til minimax AI.
        Minimax computerAgent = new Minimax(userPlayer.next());

        // Spiller spillet.
        Game game = new Game();
        System.out.println();
        System.out.println("Game begins!");
        System.out.println(game.toString());
        System.out.println();

        // Hvis computeren er X (dvs den starter) starter den her.
        if (userPlayer == Player.O) {
            int move = computerAgent.bestMove(game.getPosition());
            System.out.println("Computer chooses move: " + move);
            game.makeMove(move);
            System.out.println();
            System.out.println(game.toString());
            System.out.println();
        }

        while (!game.isOver()) {
            // Tillader spilleren at lave et træk
            while (game.getPlayer() == userPlayer) {
                System.out.print("Choose your move (enter number): ");
                int move = input.nextInt();
                game.makeMove(move);
            }

            System.out.println();
            System.out.println(game.toString());
            System.out.println();

            if (game.isOver())
                break;

            // Viser computerens træk.
            int move = computerAgent.bestMove(game.getPosition());
            System.out.println("Computer chooses move: " + move);
            System.out.println();
            game.makeMove(move);
            System.out.println(game.toString());
            System.out.println();
        }

        // viser vinderen / draw.
        System.out.print("You're all out of possible moves! The game is over!! ");
        Player winner = game.getPosition().checkWin();
        if (winner == userPlayer) System.out.println("You WIN!! Men det her sker aldrig så bare for hyggen skriver vi det her også hey");
        else if (winner == userPlayer.next()) System.out.println("You LOSE!! What a surprise ;) ");
        else System.out.println("It's a draw! How boring...");
        System.out.println();
        System.out.println(game.getPosition().getBoard(true));
        System.out.println();
        System.out.println("Bai! Restart game to play again!");
        // lukker scanneren.
        input.close();
    }

}
