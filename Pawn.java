import javax.swing.*;
 
public class Pawn extends ChessPiece {
    int[] takeMoves;
    boolean takenMove;
    boolean doubleMove;
    static int lastMove;
    static Pawn doubleMover;

    public Pawn(int row, int col, boolean alive, char color) {
        super();

        this.row = row;
        this.col = col;
        this.alive = alive;
        this.color = color;

        this.pic = new ImageIcon("Resources/PiecePics" + color + "/pawn.png");
    }

    boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck) {
        workingMove = false;
        takenMove = false;
        doubleMove = false;
        this.takeMoves = new int[]{1, -1};

        // 'w'hite pieces can only move up
        if (this.color == 'w') {
            this.moves = new int[][]{{0, -1}};
            this.numMoves = 1;

            // if they're still in initial spot they have an extra move
            if (col == 6) {
                this.moves = new int[][]{{0, -1}, {0, -2}};
                this.numMoves = 2;
            }

            // 'b'lack pieces  can only move down
        } else if (this.color == 'b') {
            this.moves = new int[][]{{0, 1}};
            this.numMoves = 1;

            // if they're still in initial spot they have an extra move
            if (col == 1) {
                this.moves = new int[][]{{0, 1}, {0, 2}};
                this.numMoves = 2;
            }
        }
        for (int i = 0; i < this.numMoves; i++) {

            // Makes sure the move is proper
            if (this.row + this.moves[i][0] == moveAttemptRow && this.col + this.moves[i][1] == moveAttemptCol) {
                doubleMove = i == 1;

                // Used for en passant
                if (doubleMove) {
                    lastMove = ChessGame.turn;
                    doubleMover = new Pawn(moveAttemptRow, moveAttemptCol, true, this.color);
                }
                workingMove = true;
                break;

              // Enables taking pieces properly
            } else if (this.col + this.moves[0][1] == moveAttemptCol &&
                    (this.row + this.takeMoves[0] == moveAttemptRow || this.row + this.takeMoves[1] == moveAttemptRow) &&
                    pieces[moveAttemptCol][moveAttemptRow] != null) {
                workingMove = true;
                takenMove = true;
                break;
            }
        }

        // Prevents taking pieces by walking right into them
        if (workingMove && pieces[this.col + this.moves[0][1]][this.row] != null && !takenMove) {
            workingMove = false;
        }

        // Prevents from double moving over another piece
        if (this.numMoves == 2 && doubleMove) {
            if (pieces[this.col + this.moves[1][1]][this.row] != null) {
                workingMove = false;
            }
        }

        // Enables taking "en passant"
        if (this.col + this.moves[0][1] == moveAttemptCol && // if the move goes forward (relative to start)
                this.row >= 0 && this.row <= 7 &&
                pieces[moveAttemptCol][moveAttemptRow] == null && lastMove == ChessGame.turn - 1 && // if the space the piece is moving to is empty and the last time a double move was performed was last turn
                doubleMover != null ) {

            if (this.row+1 <= 7 && pieces[this.col][this.row + 1] != null && // if move is in bounds + if there's a piece being attempted to be taken
                    (doubleMover.row == this.row+1 && // if the piece being taken is to the right of the current piece
                            doubleMover.col == this.col) && // ^^
                    this.row + this.takeMoves[0] == moveAttemptRow) { // if the corresponding attack move fits the move attempt
                pieces[doubleMover.col][doubleMover.row] = null; // kills the piece
                workingMove = true;
            }
            if (this.row-1 >= 0 && pieces[this.col][this.row - 1] != null && // same as above
                    (doubleMover.row == this.row-1 &&
                            doubleMover.col == this.col) &&
                    this.row + this.takeMoves[1] == moveAttemptRow) {
                pieces[doubleMover.col][doubleMover.row] = null;
                workingMove = true;
            }
        }
        return workingMove;
    }
}