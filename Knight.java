import javax.swing.*;
 
public class Knight extends ChessPiece{
    public Knight(int row, int col, boolean alive, char color){
        super();
        
        moves = new int[][]{ {-1, -2}, {1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1} };

        this.row = row;
        this.col = col;
        this.alive = alive;
        this.color = color;
        
        this.pic = new ImageIcon("Resources/PiecePics" + color +"/knight.png");
        this.numMoves = 8;
    }
    boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck){
        workingMove = false;
        for (int i = 0;i<this.numMoves;i++){

            // makes sure the move is proper
            if (this.row+this.moves[i][0]==moveAttemptRow &&
                this.col+this.moves[i][1]==moveAttemptCol){
                workingMove = true;
                break;
            }
        }
        return workingMove;
    }
}