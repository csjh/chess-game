import javax.swing.*;
 
public class King extends ChessPiece {

    // sets 4 variables, all for castling
    boolean validCastledQS = false;
    boolean validCastledBS = false;
    boolean castled = false;
    boolean moved = false;

    protected static King tokenBlackKing = new King(4, 0, true, 'b');
    protected static King tokenWhiteKing = new King(4, 7, true, 'w');

    public King(int row, int col, boolean alive, char color) {
        super();

        moves = new int[][]{{-1, -1}, {1, 1}, {1, -1}, {-1, 1}, {0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        this.row = row;
        this.col = col;
        this.alive = alive;
        this.color = color;

        this.pic = new ImageIcon("Resources/PiecePics" + color + "/king.png");
        this.numMoves = 8;
    }

    boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck) {
        validCastledQS = true;
        validCastledBS = true;
        workingMove = false;

        // makes sure it's a valid move
        for (int i = 0; i < this.numMoves; i++) {
            if (this.row + this.moves[i][0] == moveAttemptRow && this.col + this.moves[i][1] == moveAttemptCol) {
                workingMove = true;
                break;
            }
        }

        // sees if a castle is valid, and if one is being attempted
        if ((moveAttemptRow == 6 || moveAttemptRow == 2) && moveAttemptCol == this.col && this.row == 4 && !castled) {
            for (int i = 1; i < 4; i++) {
                if (pieces[this.col][i] != null) { // if any pieces block the castle it's invalid
                    validCastledQS = false;
                    break;
                }
            }
            for (int i = 6; i > 4; i--) {
                if (pieces[this.col][i] != null) { // if any pieces block the castle it's invalid
                    validCastledBS = false;
                    break;
                }
            }
            // makes sure everything is set for the castle
            if (moveAttemptRow == 2 && pieces[this.col][0] instanceof Rook && !((Rook) pieces[this.col][0]).moved && validCastledQS && !moved) {
                workingMove = true;
                ((Rook) pieces[this.col][0]).castle(this.col, 3);
                pieces[this.col][3] = pieces[this.col][0];
                pieces[this.col][0] = null;
            }
            if (moveAttemptRow == 6 && pieces[this.col][7] instanceof Rook && !((Rook) pieces[this.col][7]).moved && validCastledBS && !moved) {
                workingMove = true;
                ((Rook) pieces[this.col][7]).castle(this.col, 5);
                pieces[this.col][5] = pieces[this.col][7];
                pieces[this.col][7] = null;
            }
        }
        if(!checkCheck) {
            moved = true;
        }
        return workingMove;
    }
}