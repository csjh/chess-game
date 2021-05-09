import javax.swing.*;
 
public class Rook extends ChessPiece{
    boolean moved = false;
    public Rook(int row, int col, boolean alive, char color){
        super();
        
        this.row = row;
        this.col = col;
        this.alive = alive;
        this.color = color;
        
        this.pic = new ImageIcon("Resources/PiecePics" + color +"/rook.png");
    }
    boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck){
        workingMove = false;

        // directions array for easier tracking of moves
        directions = new boolean[]{moveAttemptCol < this.col, moveAttemptCol > this.col, moveAttemptRow > this.row, moveAttemptRow < this.row};

        // makes sure it's a valid move
        if ((moveAttemptRow != this.row && moveAttemptCol == this.col) ||
            (moveAttemptRow == this.row && moveAttemptCol != this.col)
            ){
            workingMove = true;
        }
        if (directions[0]){
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
        if(!checkCheck) {
            moved = true;
        }
        return workingMove;
    }

    // castle function in case the king castles (basically just a super compressed move)
    public void castle(int col, int row){
        this.col = col;
        this.row = row;
        moved = true;
    }
}