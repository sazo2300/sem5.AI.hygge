package minimax;

import model.Player;
import model.Position;

import java.util.List;

// Minimax algoritme. AI vil spille som MAX
public class Minimax {

    // Score der bliver givet for at vinde.
    public static final double WIN_SCORE = 10;

    // Max dybde som algoritmen skal k�re. (h�jst 9 da der kun er 9 felter).
    public static final int MAX_DEPTH = 3;

    private Player agentPlayer;

    public Minimax(Player agentPlayer) {
        this.agentPlayer = agentPlayer;
    }

    // Returnerer det bedste tr�k fra AIs position/state, eller -1 hvis ingen tr�k er mulige.
    public int bestMove(Position pos) {
    	// Finder det bedste tr�k
        List<Integer> moves = pos.getMoves();
        double bestScore = Double.NEGATIVE_INFINITY;
        int bestMove = -1;
        for (Integer move : moves) {
            double score = alphabeta(pos.apply(agentPlayer, move), agentPlayer.next(),
                    Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
            if (score > bestScore) {
                bestMove = move;
                bestScore = score;
            }
        }
        return bestMove;
    }

    // Minimax med alpha beta pruning!!!!!
    // scoren er "discounted" med dybden for at sikre at AI foretr�kker en win ved dybde 2,
    // frem for et win ved dybde 7. Samme med losses men omvendt (foretr�kker et sen loss
    // frem for et tidligt loss). Basically er win score = 10 og loss score = -10, og hvis
    // algoritmen/AI finder et win ved dybde 2 er score for det specifikke felt 10-2, s� hvis den 
    // ogs� finder et win i dybden 6 er scoren kun 10-6 og AI vil foretr�kke det hurtige win.
    // Omvendt hvis den finder et loss condition ved dybde 2 er scoren lig -10 - 2 = -8
    // og hvis den s� finder et loss ved dybde 6 ogs� s� er scoren lig -10 - 6 = -4, hvilket vil
    // sige at AI vil spille mod det sene loss (hvis den ikke har et bedre tr�k alts�).
    private double alphabeta(Position pos, Player player, double alpha, double beta, int depth) {
        // Hvis spillet er win/draw, returneres win scoren, eller 0 for draws.
    	// Hvis vi er n�et til max dybde returneres den heuristiske score 
        List<Integer> moves = pos.getMoves();
        Player winner = pos.checkWin();
        if (winner != null) {
            double score = WIN_SCORE - depth;
            return (winner == agentPlayer? score : -score);
        }
        if (moves.isEmpty())
            return 0;
        if (depth == MAX_DEPTH)
            return heuristic(pos);
        // K�r minimax med alpha beta pruning
        double v = 0;
        if (player == agentPlayer) {
            v = Double.NEGATIVE_INFINITY;
            for (Integer move : moves) {
                v = Math.max(v, alphabeta(pos.apply(player, move), player.next(), alpha, beta, depth+1));
                alpha = Math.max(alpha, v);
                if (beta <= alpha)
                    break;
            }
        } else {
            v = Double.POSITIVE_INFINITY;
            for (Integer move : moves) {
                v = Math.min(v, alphabeta(pos.apply(player, move), player.next(), alpha, beta, depth+1));
                beta = Math.min(beta, v);
                if (beta <= alpha)
                    break;
            }
        }
        return v;
    }

    // Simpel heuristik til at evaluere cut-off seach positions
    // givet ved at tage den totale v�rdi af AIs cells og tr�kke den totale
    // v�rdi af modstanderens celler fra.
    private double heuristic(Position position) {
        double value = 0.0;
        for (int i = 0; i < position.pos.length; i++) {
            if (position.pos[i] == agentPlayer) {
                value += Position.cellValues[i];
            } else {
                value -= Position.cellValues[i];
            }
        }
        return value;
    }

}
