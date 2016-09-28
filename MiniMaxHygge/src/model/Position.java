package model;

import java.util.LinkedList;
import java.util.List;

public class Position {

    // Heuristik værdier for hvert felt
    public static final int[] cellValues = new int[]{3, 2, 3, 2, 4, 2, 3, 2, 3};

    // Cells i et array alt efter hvilke der er mest preferred = Mest preferred bliver analyseret først i algoritmen.
    // Heuristiske værdier er brugt til at vælge mest preferred cells. Heuristiske værdi for en cell er mængden
    // af vinderkombinationer som feltet indgår i.
    // ses her: 3 | 2 | 3
    //          2 | 4 | 2
    //          3 | 2 | 3
    public static final int[] preferredCells = new int[]{5, 1, 3, 7, 9, 2, 4, 6, 8};

    // Opsat array mere enkelt så kun et tal repræsentere hver cell.
    // Sådan her: 1 | 2 | 3
    //            4 | 5 | 6
    //            7 | 8 | 9
    public final Player[] pos;

    // Laver et tomt board.
    // Hvert felt er en player, sådan at playeren blot bliver gemt i feltet for at vide hvem der ejer det.
    public Position() {
        this(new Player[9]);
    }

    // laver en player fra et givent positions array
    public Position(Player[] pos) {
        this.pos = pos;
    }

    // Returnerer positionen når det givne træk er taget.
    public Position apply(Player player, int cell) {
    	// Checker bounds og at cellen ikke allerede er markeret.
        if (cell < 1 || cell > 9 || pos[cell-1] != null)
            return null;
        // Kopierer den nuværende position og markere den nye celle.
        Player[] newPos = new Player[9];
        for (int i = 0; i < 9; i++) {
            newPos[i] = pos[i];
        }
        newPos[cell-1] = player;
        return new Position(newPos);
    }

    // Returnerer en liste af mulige træk i en preferred rækkefølge.
    // Hvis listen er tom så er der ingen mulige træk, og spillet er færdigt (won/drawn).
    public List<Integer> getMoves() {
        List<Integer> moves = new LinkedList<>();
        for (int i = 0; i < preferredCells.length; i++) {
            if (pos[preferredCells[i] - 1] == null)
                moves.add(preferredCells[i]);
        }
        return moves;
    }

    // Checker om spillet er vundet.
    public Player checkWin() {
        // Checker for wins, hvis et win findes, returneres den spiller der er i det første felt af vinderkombinationen.
        // Horisontale wins
        if (isWin(1, 2, 3)) return pos[0];
        if (isWin(4, 5, 6)) return pos[3];
        if (isWin(7, 8, 9)) return pos[6];
        // Verticale wins
        if (isWin(1, 4, 7)) return pos[0];
        if (isWin(2, 5, 8)) return pos[1];
        if (isWin(3, 6, 9)) return pos[2];
        // Diagonale wins
        if (isWin(1, 5, 9)) return pos[0];
        if (isWin(3, 5, 7)) return pos[2];
        return null;
    }

    // Returnere en tekst(string) representation af spillebrættet.
    @Override
    public String toString() {
        return getBoard(false);
    }

    // Returnerer en text version af spillebrættet og tager et argument der indikere om
    // felterne skal indeholde blanks (altså at der ikke står noget) eller tal (i tomme felter).
    public String getBoard(boolean blanks) {
        String board = "";
        for (int i = 0; i < pos.length; i++) {
            if (pos[i] == null)
                board += (blanks? "   " : " " + (i+1) + " ");
            else
                board += String.format(" %c ", pos[i].symbol);
            if (i == 2 || i == 5)
                board += "\n";
            else if (i != 8)
                board += "|";
        }
        return board;
    }

    // Hjælpemetode til at tjekke en mulig win
    private boolean isWin(int a, int b, int c) {
        return (pos[a-1] != null && pos[a-1] == pos[b-1] && pos[a-1] == pos[c-1]);
    }

}
