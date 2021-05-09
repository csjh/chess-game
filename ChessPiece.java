import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public abstract class ChessPiece{
    protected int row, col, numMoves;
    protected boolean alive, workingMove;
    protected ImageIcon pic;
    protected int[][] moves;
    protected char color;
    protected boolean[] directions;

    // abstract function for use in the subclasses
    abstract boolean subMove(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck);

    public boolean move(int moveAttemptRow, int moveAttemptCol, ChessPiece[][] pieces, boolean checkCheck){

        // checks if the raw move is valid
        workingMove = this.subMove(moveAttemptRow, moveAttemptCol, pieces, checkCheck);

        // checks for some other possible issues (moving into another piece of your piece's color)
        if (pieces[moveAttemptCol][moveAttemptRow]!=null &&
            pieces[moveAttemptCol][moveAttemptRow].color == this.color
            ){
            workingMove = false;
        }

        // if the move is fully valid, it's allowed
        if (workingMove && !checkCheck && this instanceof King){
            this.row = moveAttemptRow;
            this.col = moveAttemptCol;
            if (this.color == 'b') {
                King.tokenBlackKing.row = this.row;
                King.tokenBlackKing.col = this.col;
            }
            if (this.color == 'w') {
                King.tokenWhiteKing.row = this.row;
                King.tokenWhiteKing.col = this.col;
            }
        }
        else if(workingMove && !checkCheck){
            this.row = moveAttemptRow;
            this.col = moveAttemptCol;
        }

        return workingMove;
    }
    public void draw(Container c, Graphics g){
        this.pic.paintIcon(c, g, 20+this.row*69, 42+this.col*69);
    }
    public void moveSound(){
        try{
            File soundFile = new File("Resources/move.wav");
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundFile);

            Clip buttonClick = AudioSystem.getClip();

            buttonClick.open(audio);
            buttonClick.start();
        }
        catch(Exception x)  // if there is a problem, display error message
        {
            JOptionPane.showMessageDialog(null,x);
        }
    }
    public boolean isCheck(int col, int row, ChessPiece[][] pieces) {
        boolean danger = false;
        for (int funcColumn = 0; funcColumn < 8; funcColumn++) {
            for (int funcRow = 0; funcRow < 8; funcRow++) {
                if (pieces[funcColumn][funcRow] != null && pieces[funcColumn][funcRow].color != this.color) {
                    if (pieces[funcColumn][funcRow].move(row, col, pieces, true)) {
                        danger = true;
                    }
                }
            }
        }
        return danger;
    }
}