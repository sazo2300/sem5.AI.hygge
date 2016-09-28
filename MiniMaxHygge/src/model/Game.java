package model;

// modelerer et kryds og bolle spil.
// Klassen er meget simpel s� ikke s� kommenteret som andre klasser.
public class Game {

    private Position position;
    private Player player;

    public Game() {
        position = new Position();
        player = Player.X;
    }

    // Returnerer positionen
    public Position getPosition() {
        return position;
    }

    // Returnerer den n�ste spiller der skal tage sit tr�k
    public Player getPlayer() {
        return player;
    }

    // Checker om et givent tr�k er lovligt.
    public boolean isValidMove(int move) {
        return position.getMoves().contains(move);
    }

    // Foretager det givne tr�k og giver n�ste spiller turen.
    // Returnerer "false" hvis tr�kket fejler.
    public boolean makeMove(int move) {
        Position result = position.apply(player, move);
        if (result != null) {
            position = result;
            player = player.next();
        }
        return result != null;
    }

    // Returnerer "true" hvis spillet er ovre
    public boolean isOver() {
        return position.checkWin() != null || position.getMoves().isEmpty();
    }

    // viser boardet.
    @Override
    public String toString() {
        return position.toString();
    }
}
