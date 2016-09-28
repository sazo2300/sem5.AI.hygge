package model;

// Enums til at repræsentere spillerene
public enum Player {
    X('X'), // Maximizing player
    O('O'); // Minimizing player

    // Symbol brugt til at display specifik spiller på boardet.
    public final char symbol;

    Player(char symbol) {
        this.symbol = symbol;
    }

    // Returnere den næste spiller (brugt til at switche).
    public Player next() {
        return (this == X? O : X);
    }

    // Returnere spilleren ud fra symbolet.
    public static Player fromSymbol(char symbol) {
        if (symbol == 'X')
            return X;
        if (symbol == 'O')
            return O;
        else return null;
    }

}
