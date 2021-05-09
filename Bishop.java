import javax.swing.*;

public class Bishop extends ChessPiece{
    int moveMultiplier;
    int moveRatio;
    public Bishop(int row, int col, boolean alive, char color){
        super();
        
        this.row = row;
        this.col = col;
        this.alive = alive;
        this.color = color;

        this.pic = new ImageIcon("Resources/PiecePics" + color +"/bishop.png");
    }
    boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck){
        workingMove = false;

        // sets 2 variables for directional use
        moveMultiplier = moveAttemptRow-this.row>0 ? 1 : -1;

        // makes sure the move is proper
        if ((moveAttemptRow-this.row == moveAttemptCol-this.col) ||
             moveAttemptRow-this.row == -(moveAttemptCol-this.col)){
            workingMove = true;
            moveRatio = moveAttemptRow-this.row == moveAttemptCol-this.col ? 1: -1;
        }

        // cycles through all the places the bishop will go through
        if(workingMove) {
            for (int i = 1; i < (moveAttemptRow - this.row) * moveMultiplier; i++) {
                if (pieces[this.col + (i * moveMultiplier * moveRatio)][this.row + (i * moveMultiplier)] != null) {
                    workingMove = false;
                    break;
                }
            }
        }

        return workingMove;
    }
}