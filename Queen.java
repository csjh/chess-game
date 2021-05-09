import javax.swing.*;
 
public class Queen extends ChessPiece{
    int moveMultiplier, moveRatio;
    public Queen(int row, int col, boolean alive, char color){
        super();
        
        this.row = row;
        this.col = col;
        this.alive = alive;
        this.color = color;
        
        this.pic = new ImageIcon("Resources/PiecePics" + color +"/queen.png");
    }
    boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck){
        workingMove = false;
        // sets a boolean array in case the move is a rook move so it's easier to distinguish direction
        directions = new boolean[]{moveAttemptCol < this.col, moveAttemptCol > this.col, moveAttemptRow > this.row, moveAttemptRow < this.row};
        // makes sure valid move
        if((moveAttemptRow != this.row && moveAttemptCol == this.col) ||
           (moveAttemptRow == this.row && moveAttemptCol != this.col) || 
            moveAttemptRow-this.row == moveAttemptCol-this.col ||
            moveAttemptRow-this.row == -(moveAttemptCol-this.col)){
            workingMove = true;
        }
        // cycles through all the queen's possible moves, and checks if the spaces leading up to there are occupied
        if (moveAttemptRow-this.row == moveAttemptCol-this.col || moveAttemptRow-this.row == -(moveAttemptCol-this.col)){
            // sets 2 more directional variables
            moveMultiplier = moveAttemptRow-this.row>0 ? 1 : -1;
            moveRatio = moveAttemptRow-this.row == moveAttemptCol-this.col ? 1: -1;
            for (int i = 1; i<(moveAttemptRow-this.row)*moveMultiplier; i++){
                if (pieces[this.col+(i*moveMultiplier*moveRatio)][this.row+(i*moveMultiplier)] != null){
                    workingMove = false;
                    break;
                }
            }
        }
        else if (directions[0]){
            for (int i = this.col-1; i>moveAttemptCol; i--){
                if (pieces[i][this.row] != null){
                    workingMove = false;
                    break;
                }
            }
        }
        else if (directions[1]){
            for (int i = this.col+1; i<moveAttemptCol; i++){
                if (pieces[i][this.row] != null){
                    workingMove = false;
                    break;
                }
            }
        }
        else if (directions[2]){
            for (int i = this.row+1; i<moveAttemptRow; i++){
                if (pieces[this.col][i] != null){
                    workingMove = false;
                    break;
                }
            }
        }
        else if (directions[3]){
            for (int i = this.row-1; i>moveAttemptRow; i--){
                if (pieces[this.col][i] != null){
                    workingMove = false;
                    break;
                }
            }
        }
        return workingMove;
    }
}