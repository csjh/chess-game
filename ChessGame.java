import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ChessGame extends JFrame implements Runnable, MouseListener, MouseMotionListener{
    JLabel board = new JLabel();
    JLabel winner = new JLabel();
    JButton replay = new JButton("Replay?");
    
    public static int turn = 0;
    
    Container pane;
    Thread myThread;
    boolean mouseMoving = false;
    boolean validMove = false;
    boolean kingsDead;
    int mouseX, mouseY;
    int targetRow, targetCol;
    int endRow, endCol;
    ChessPiece targetPiece, deadPiece;
    char[] colors = new char[]{'w', 'b'};
    String winColor;

    ImageIcon pic = new ImageIcon("Resources/Chessboard/chessboard.png");
    
    ChessPiece[][]pieces = new ChessPiece[][]{   {new Rook(0, 0, true, 'b'), new Knight(1, 0, true, 'b'), new Bishop(2, 0, true, 'b'), new Queen(3, 0, true, 'b'), new King(4, 0, true, 'b'), new Bishop(5, 0, true, 'b'), new Knight(6, 0, true, 'b'), new Rook (7, 0, true, 'b')},
                                                 {new Pawn(0, 1, true, 'b'), new Pawn  (1, 1, true, 'b'), new Pawn  (2, 1, true, 'b'), new Pawn (3, 1, true, 'b'), new Pawn(4, 1, true, 'b'), new Pawn  (5, 1, true, 'b'), new Pawn  (6, 1, true, 'b'), new Pawn (7, 1, true, 'b')},
                                                 {null, null, null, null, null, null, null, null},
                                                 {null, null, null, null, null, null, null, null},
                                                 {null, null, null, null, null, null, null, null},
                                                 {null, null, null, null, null, null, null, null},
                                                 {new Pawn(0, 6, true, 'w'), new Pawn  (1, 6, true, 'w'), new Pawn  (2, 6, true, 'w'), new Pawn (3, 6, true, 'w'), new Pawn(4, 6, true, 'w'), new Pawn  (5, 6, true, 'w'), new Pawn  (6, 6, true, 'w'), new Pawn (7, 6, true, 'w')},
                                                 {new Rook(0, 7, true, 'w'), new Knight(1, 7, true, 'w'), new Bishop(2, 7, true, 'w'), new Queen(3, 7, true, 'w'), new King(4, 7, true, 'w'), new Bishop(5, 7, true, 'w'), new Knight(6, 7, true, 'w'), new Rook (7, 7, true, 'w')}};

    public ChessGame(){
        super ("Chess");
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        pane.addMouseListener(this);
        pane.addMouseMotionListener(this);
        replay.addMouseListener(this);

        board.setIcon(pic);

        pane.add(board);
        pane.add(winner);
        pane.add(replay);
        Font font = new Font("Times New Roman", Font.BOLD, 30);
        winner.setFont(font);
        replay.setFont(font);
        board.setVisible(true);
        replay.setVisible(false);

        setSize(570,592);
        setVisible(true);
        repaint();
    }
    public void mousePressed(MouseEvent e){
        if (kingsDead && e.getX() >= 0 && e.getX() <= 136 && e.getY() >= 0 && e.getY() <= 43){
            kingsDead = false;
            pieces = new ChessPiece[][]{   {new Rook(0, 0, true, 'b'), new Knight(1, 0, true, 'b'), new Bishop(2, 0, true, 'b'), new Queen(3, 0, true, 'b'), new King(4, 0, true, 'b'), new Bishop(5, 0, true, 'b'), new Knight(6, 0, true, 'b'), new Rook (7, 0, true, 'b')},
                    {new Pawn(0, 1, true, 'b'), new Pawn  (1, 1, true, 'b'), new Pawn  (2, 1, true, 'b'), new Pawn (3, 1, true, 'b'), new Pawn(4, 1, true, 'b'), new Pawn  (5, 1, true, 'b'), new Pawn  (6, 1, true, 'b'), new Pawn (7, 1, true, 'b')},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {new Pawn(0, 6, true, 'w'), new Pawn  (1, 6, true, 'w'), new Pawn  (2, 6, true, 'w'), new Pawn (3, 6, true, 'w'), new Pawn(4, 6, true, 'w'), new Pawn  (5, 6, true, 'w'), new Pawn  (6, 6, true, 'w'), new Pawn (7, 6, true, 'w')},
                    {new Rook(0, 7, true, 'w'), new Knight(1, 7, true, 'w'), new Bishop(2, 7, true, 'w'), new Queen(3, 7, true, 'w'), new King(4, 7, true, 'w'), new Bishop(5, 7, true, 'w'), new Knight(6, 7, true, 'w'), new Rook (7, 7, true, 'w')}};
            turn = 0;
            winner.setText("");
            board.setVisible(true);
            replay.setVisible(false);
            repaint();
        }
        mouseMoving = true;
        targetPiece = null;
        mouseX = e.getX();
        mouseY = e.getY();
        targetRow = mouseX/69;
        targetCol = mouseY/69;
        targetRow = Math.min(targetRow, 7);
        targetRow = Math.max(targetRow, 0);
        targetCol = Math.min(targetCol, 7);
        targetCol = Math.max(targetCol, 0);
        if (pieces[targetCol][targetRow] != null && // if there's no piece to select
                pieces[targetCol][targetRow].alive &&  // if the selected piece is alive
                pieces[targetCol][targetRow].color == colors[turn%2]){ // if it isn't the selected piece's turn
            targetPiece = pieces[targetCol][targetRow]; // target piece is the selected piece
            pieces[targetCol][targetRow].alive = false; // selected piece is made invisible
        }
        else{
            mouseMoving = false;
        }
    }
    public void mouseReleased(MouseEvent e){
        if(mouseMoving && targetPiece != null){
            validMove = false;
            mouseX = e.getX();
            mouseY = e.getY();
            endRow = (mouseX-10)/69;
            endCol = (mouseY-32)/69;
            endRow = Math.min(endRow, 7);
            endRow = Math.max(endRow, 0);
            endCol = Math.min(endCol, 7);
            endCol = Math.max(endCol, 0);
            if (endRow!=targetRow || endCol!=targetCol){ // if the mouse has moved to a new column/row
                validMove = targetPiece.move(endRow, endCol, pieces, false); // runs move() to see if the move is valid and to move
            }
            if(validMove) {
                if (pieces[endCol][endRow] instanceof King) { // if the piece that was just taken was of the King class
                    kingsDead = true;
                    winColor = pieces[endCol][endRow].color == 'w' ? "Black" : "White"; // if the king's suit was white, black wins or vice versa
                }
                deadPiece = pieces[endCol][endRow];
                pieces[endCol][endRow] = targetPiece; // targetpiece takes over the taken piece's square
                pieces[targetCol][targetRow] = null; // targetpiece's prior square becomes null
                pieces[endCol][endRow].alive = true; // new square is alive again
                if (targetPiece.color == 'b') {
                    if (targetPiece.isCheck(King.tokenBlackKing.col, King.tokenBlackKing.row, pieces)) { // if the black king is in check
                        targetPiece.row = targetRow; // move reverts
                        targetPiece.col = targetCol;
                        pieces[targetCol][targetRow] = targetPiece;
                        pieces[endCol][endRow] = deadPiece; // dead piece is revived
                        if(targetPiece instanceof King){
                            King.tokenBlackKing.row = targetRow;
                            King.tokenBlackKing.col = targetCol;
                        }
                        validMove = false; // move becomes invalid
                    }
                }
                if (targetPiece.color == 'w') {
                    if (targetPiece.isCheck(King.tokenWhiteKing.col, King.tokenWhiteKing.row, pieces)) { // if the white king is in check
                        targetPiece.row = targetRow; // move reverts
                        targetPiece.col = targetCol;
                        pieces[targetCol][targetRow] = targetPiece;
                        pieces[endCol][endRow] = deadPiece; // dead piece is revived
                        if(targetPiece instanceof King){
                            King.tokenWhiteKing.row = targetRow;
                            King.tokenWhiteKing.col = targetCol;
                        }
                        validMove = false; // move becomes invalid
                    }
                }
            }
            if(validMove){
                turn++;
                pieces[endCol][endRow].moveSound();
            }
            targetPiece.alive = true;
            if(targetPiece instanceof Pawn && // if the piece moved was a pawn
              (targetPiece.col == 7 || targetPiece.col == 0)){ // if the pawn's new place is the other side of the board
                pieces[endCol][endRow] = new Queen(targetPiece.row, targetPiece.col,  // the pawn becomes a queen
                                                   true, targetPiece.color);
            }
            repaint();
        }
        mouseMoving = false;
        if (kingsDead){
            winner.setText(winColor + " is the winner.  ");
            board.setVisible(false);
            replay.setVisible(true);
        }
    }
    public void mouseDragged(MouseEvent e){
        if (targetPiece == null){
            mouseMoving = false;
        }
        if (mouseMoving){
            mouseX = e.getX();
            mouseY = e.getY();
            if (myThread == null){
                myThread = new Thread(this);
                myThread.start();
            }
        }
    }
    public void mouseClicked(MouseEvent e){}
    public void mouseMoved(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void run(){
        Thread thisThread = Thread.currentThread();
        while(thisThread == myThread){
            try{
                //noinspection BusyWait
                Thread.sleep(200);
            }
            catch (InterruptedException ie){
                System.out.println(ie.getMessage());
            }
            repaint();
            myThread = null;
        }
    }
    public void paint(Graphics g){
        super.paint(g);
        if(mouseMoving){
            targetPiece.pic.paintIcon(pane, g, mouseX-20, mouseY);
        }
        for (int colIndex = 0; colIndex<8; colIndex++){
            for(int rowIndex = 0;rowIndex<8;rowIndex++){
                if (pieces[colIndex][rowIndex] != null && pieces[colIndex][rowIndex].alive && !kingsDead){
                    pieces[colIndex][rowIndex].draw(pane, g);
                }
            }
        }
    }
    public static void main(String[] args)
    {
        ChessGame game = new ChessGame();
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}