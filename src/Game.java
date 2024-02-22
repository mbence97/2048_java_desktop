import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * Ez az oszt�ly val�s�tja meg a j�t�k mechanizmus�t.
 * A JPanel oszt�ly lesz�rmazottja, valamint implement�lja
 * a KeyListener interf�szt.
 */
public class Game extends JPanel implements KeyListener{
    
/**
 * Az oszt�ly adattagjai: k�tdimenzi�s t�mb a csemp�kr�l,
 * inicializ�l�sn�l ezt a t�mb�t kapja meg j�t�kt�bla adattag.
 * Tartalmaz az oszt�ly tov�bb� egy pontsz�mot, egy c�mk�t
 * (JLabel) ami a pontsz�mot hivatott mutatni �s egy 
 * LeaderBoardTable objektumot, ami a toplist�t kezeli.
 */
	private Tile[][] list;
    private Board t;
    private JLabel scorelabel;
    private int score;
    public LeaderboardTable leaderboard;

/**
 * Az oszt�ly konstruktora. Az oszt�ly a JPanel lesz�rmazottja,
 * erre a panelra ker�l a j�t�kt�bla �s a cimke a pontsz�mr�l.
 * A konstruktor be�ll�tja a layoutot, h�vja az inicializ�l�
 * met�dust �s elv�gzi a sz�ks�ges be�ll�t�sokat a j�t�khoz.    
 */
    public Game() {
        setLayout(new BorderLayout());
        init();
        twoRandomTile();
        add(t, BorderLayout.CENTER);
        add(scorelabel, BorderLayout.NORTH);
        addKeyListener(this);
        initLeaderBoard();
        setBackground(new Color(0x202A36));
    }
    
/**
 * A k�tdimenzi�s Tile t�mb lek�rdez�s�t teszi lehet�v�.
 * Tesztel�s c�lj�b�l j�tt l�tre.    
 * @return k�tdimenzi�s Tile t�mb
 */
    public Tile[][] getTileList(){
    	return list;
    }
    
/**
 * A j�t�kt�bla (Board) adattag lek�rdez�s�t teszi lehet�v�.
 * Tesztel�s c�lj�b�l j�tt l�tre.
 * @return j�t�kt�bla (Board)
 */
    public Board getBoard() {
    	return t;
    }
    
/**
 * A jelenlegi pontsz�m lek�rdez�s�t teszi lehet�v�.
 * Tesztel�s c�lj�b�l j�tt l�tre.
 * @return pontsz�m 
 */
    public int getScore() {
    	return score;
    }  
    
/**
 * A k�tdimenzi�s t�mb tetsz�leges elem�nek �rt�k�t
 * m�dos�t� met�dus.
 * Tesztel�s c�lj�b�l j�tt l�tre.
 * @param i a t�mb horizont�lis indexe
 * @param j a t�mb vertik�lis indexe
 * @param value a be�ll�tani k�v�nt �rt�k
 */
    public void setTileValue(int i, int j, int value) {
    	list[i][j].setValue(value);
    }

/**
 * A toplista t�bl�t inicializ�l� met�dus. Beolvassa az �rt�keket
 * a "ranks.dat" f�jlb�l. Ha a f�jl nem l�tezik akkor egy �res 
 * t�bl�t hoz l�tre.
 */
    @SuppressWarnings("unchecked")
	private void initLeaderBoard(){
        try {
            leaderboard = new LeaderboardTable();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("ranks.dat"));
            leaderboard.ranks = (List<Rank>)ois.readObject();
            ois.close();
        } catch(Exception ex) {
            leaderboard = new LeaderboardTable();
        }
    }

/**
 * Ellen�rzi, hogy v�get �rt-e a j�t�k. Ha valamelyik csempe �rt�ke
 * el�rte a 2048-at, akkor igazra �ll�tja a j�t�kt�bla win v�ltoz�j�t
 * �s beteszi a szerzett pontsz�mot a toplista t�bl�ba, ha m�g nincs 10
 * �rt�k a t�bl�ban, vagy m�r van 10 �rt�k a t�bl�ban, viszont nagyobb
 * mint a 10. �rt�k. Ekkora beteszi a 10. hely�re az �jonnan szerzett
 * pontsz�mot, majd rendezi a toplist�t. Ellen�rzi a met�dus tov�bb�,
 * hogy a k�pesek-e m�g a csemp�k mozogni a t�bl�n. Ha nem, �s egyik
 * csempe �rt�ke se �rte el a 2048-at, akkor a j�t�kt�bl�ban igaz 
 * �rt�kre �ll�tja a game_over v�ltoz�t. Ezut�n �gyan�gy ellen�rzi, 
 * hogy az el�rt pontsz�m toplist�ra ker�lhet-e.
 */
    public void isOver(){
        int maxValue=0;
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                if(list[i][j].getValue()>maxValue)
                    maxValue=list[i][j].getValue();
                if(maxValue == 2048)
                    t.win=true;

        int moveAbleTiles = 0;
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++){
                if (i-1>=0 && (list[i][j].getValue() == list[i-1][j].getValue() || list[i-1][j].getValue() == 0))
                    moveAbleTiles++;
                if (i+1<4 && (list[i][j].getValue() == list[i+1][j].getValue() || list[i+1][j].getValue() == 0))
                    moveAbleTiles++;
                if (j-1>=0 && (list[i][j].getValue() == list[i][j-1].getValue() || list[i][j-1].getValue() == 0))
                    moveAbleTiles++;
                if (j+1<4 && (list[i][j].getValue() == list[i][j+1].getValue() || list[i][j+1].getValue() == 0))
                    moveAbleTiles++;
            }
        if (moveAbleTiles==0 && !t.win)
            t.game_over = true;

        if (t.game_over || t.win){
            int size = leaderboard.ranks.size();

            if(size == 0)
                leaderboard.add(new Rank(size, score));
            else
            if(size<10 && (int)leaderboard.getValueAt(size-1,1)!=score)
                leaderboard.add(new Rank(size, score));
            else
                if((int)leaderboard.getValueAt(size-1,1)<score)
                    leaderboard.setValueAt(score, size-1, 1);

            Collections.sort(leaderboard.ranks, new RankSorter());
            for (int i=1; i<=leaderboard.ranks.size(); i++)
                leaderboard.setValueAt(i, i-1,0);
        }
    }

/**
 * A j�t�k �jraind�t�s�t teszi lehet�v�. A csemp�k �rt�k�t
 * alap�rtelmezettre �ll�tja (mindegyik 0, 2db v�letlenszer�
 * csempe �rt�ke 2). A pontot lenull�zza valamint a j�t�kt�bla
 * k�t boolean v�ltoz�j�t false-ra �ll�tja.
 */
    public void restart() {
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                list[i][j].setValue(0);
        	twoRandomTile();
            score = 0;
            scorelabel.setText("Score: "+score);
            t.game_over = false;
            t.win = false;
            t.repaint();
        }

/**
 * K�t v�letlenszer� csempe �rt�k�t 2-re �ll�tja. A j�t�k
 * indul�s�n�l �s annak �jraind�t�s�n�l van szerepe.
 */
    public void twoRandomTile() {
    	int x1, x2, y1, y2;
        x1 = new Random().nextInt(4);
        x2 = new Random().nextInt(4);
        y1 = new Random().nextInt(4);
        y2 = new Random().nextInt(4);

        while(x1==x2 && y1==y2){
            x1 = new Random().nextInt(4);
            x2 = new Random().nextInt(4);
            y1 = new Random().nextInt(4);
            y2 = new Random().nextInt(4);
        }  
        list[x1][y1].setValue(2);
        list[x2][y2].setValue(2);
    }


/**
 * Inicializ�l� met�dus. A k�tdimenzi�s t�mb�t hozza l�tre
 * �s �ll�tja be az elemeit, majd a j�t�kt�bl�hoz adja.
 * Ezen k�v�l lenull�zza a pontsz�mot �s be�ll�tja a c�mk�t.    
 */
    public void init() {
        list = new Tile[4][4];

        score = 0;
        scorelabel = new JLabel("Score: "+score, SwingConstants.CENTER);
        scorelabel.setFont(new Font("Arial", Font.BOLD, 30));
        scorelabel.setForeground(new Color(0x5B6C7E));

        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                list[i][j]=new Tile(Tile.SPACE*2+i*(Tile.SPACE+Tile.WIDTH),Tile.SPACE*2+j*(Tile.SPACE+Tile.HEIGHT));
            }
        }

        t = new Board(list);
    }

/**
 * A j�t�k m�k�d�s�nek egyik felt�tele, hogy minden csempemozgat�s
 * ut�n megjelenik egy 2-es �rt�kkel rendelkez� csempe a fennmarad�
 * "�res" csemp�k valamelyik�n. Ezt teszi lehet�v� a met�dus.    
 */
    public void addTile() {
        boolean isEmpty = true;
        for(int i=0; i<4; i++) {
            for (int j=0; j<4; j++)
                if (list[i][j].getValue() == 0)
                    isEmpty = false;
        }
        if (!isEmpty) {
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            while(list[x][y].getValue() != 0) {
                x = new Random().nextInt(4);
                y = new Random().nextInt(4);
            }
            list[x][y].setValue(2);
        }
    }

/**
 * A csemp�k felfele val� mozgat�s�t val�s�tja meg. L�trehoz egy
 * ugyanakkora m�ret� k�tdimenzi�s boolean t�mb�t mint a csemp�k
 * t�mbje. Ennek a t�mbnek a seg�ts�g�vel ellen�rzi, hogy egy 
 * csempe k�t csempe �sszeolvad�sa-e. Ez az�rt fontos, mert ha 
 * mondjuk egy sor �gy alakult ki: 0 8 4 4 �s ekkor jobbra mozgatjuk 
 * a csemp�ket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elv�rt
 * m�k�d�s, ez elv�rt m�k�d�s ez lenne: 0 0 8 8. Ennek a mechanizmusnak
 * a betart�s�t seg�ti a k�tdimenzi�s boolean t�mb.
 */
    public void updateUp() {
        boolean moved = false;

        boolean[][] merge = new boolean[4][4];
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                merge[i][j]=true;

        for (int k=0; k<4; k++) {
            for (int i=1; i<4; i++) {
                for (int j=1; j<4; j++) {
                    if(list[k][j].getValue()>0) {
                        if(list[k][j-1].getValue()==0) {
                            int value = list[k][j].getValue();
                            list[k][j].setValue(0);
                            list[k][j-1].setValue(value);
                            boolean tmp = merge[k][j];
                            merge[k][j] = merge[k][j-1];
                            merge[k][j-1] = tmp;
                            moved = true;
                        }else {
                            if(list[k][j-1].getValue() == list[k][j].getValue() && merge[k][j] && merge[k][j-1]) {
                                int value = list[k][j].getValue();
                                list[k][j].setValue(0);
                                list[k][j-1].setValue(value*2);
                                merge[k][j-1] = false;
                                score = score + value*2;
                                moved = true;
                            }
                        }
                    }
                }
            }
        }
        if(moved) {
            addTile();
            t.repaint();
            scorelabel.setText("Score: "+score);
        }
    }
    
/**
* A csemp�k lefele val� mozgat�s�t val�s�tja meg. L�trehoz egy
* ugyanakkora m�ret� k�tdimenzi�s boolean t�mb�t mint a csemp�k
* t�mbje. Ennek a t�mbnek a seg�ts�g�vel ellen�rzi, hogy egy 
* csempe k�t csempe �sszeolvad�sa-e. Ez az�rt fontos, mert ha 
* mondjuk egy sor �gy alakult ki: 0 8 4 4 �s ekkor jobbra mozgatjuk 
* a csemp�ket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elv�rt
* m�k�d�s, ez elv�rt m�k�d�s ez lenne: 0 0 8 8. Ennek a mechanizmusnak
* a betart�s�t seg�ti a k�tdimenzi�s boolean t�mb.
*/
    public void updateDown() {
        boolean moved = false;

        boolean[][] merge = new boolean[4][4];
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                merge[i][j]=true;

        for (int k=0; k<4; k++) {
            for (int i=1; i<4; i++) {
                for (int j=2; j>=0; j--) {
                    if(list[k][j].getValue()>0) {
                        if(list[k][j+1].getValue()==0) {
                            int value = list[k][j].getValue();
                            list[k][j].setValue(0);
                            list[k][j+1].setValue(value);
                            boolean tmp = merge[k][j];
                            merge[k][j] = merge[k][j+1];
                            merge[k][j+1] = tmp;
                            moved = true;
                        }else {
                            if(list[k][j+1].getValue() == list[k][j].getValue() && merge[k][j] && merge[k][j+1]) {
                                int value = list[k][j].getValue();
                                list[k][j].setValue(0);
                                list[k][j+1].setValue(value*2);
                                merge[k][j+1] = false;
                                score = score + value*2;
                                moved = true;
                            }
                        }
                    }
                }
            }
        }
        if (moved) {
            addTile();
            t.repaint();
            scorelabel.setText("Score: "+score);
        }
    }
    
/**
* A csemp�k jobbra val� mozgat�s�t val�s�tja meg. L�trehoz egy
* ugyanakkora m�ret� k�tdimenzi�s boolean t�mb�t mint a csemp�k
* t�mbje. Ennek a t�mbnek a seg�ts�g�vel ellen�rzi, hogy egy 
* csempe k�t csempe �sszeolvad�sa-e. Ez az�rt fontos, mert ha 
* mondjuk egy sor �gy alakult ki: 0 8 4 4 �s ekkor jobbra mozgatjuk 
* a csemp�ket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elv�rt
* m�k�d�s, ez elv�rt m�k�d�s ez lenne: 0 0 8 8. Ennek a mechanizmusnak
* a betart�s�t seg�ti a k�tdimenzi�s boolean t�mb.
*/
    public void updateRight() {
        boolean moved = false;

        boolean[][] merge = new boolean[4][4];
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                merge[i][j]=true;

        for (int j=0; j<4; j++) {
            for (int i=1; i<4; i++) {
                for (int k=2; k>=0; k--) {
                    if(list[k][j].getValue()>0) {
                        if(list[k+1][j].getValue()==0) {
                            int value = list[k][j].getValue();
                            list[k][j].setValue(0);
                            list[k+1][j].setValue(value);
                            boolean tmp = merge[k][j];
                            merge[k][j] = merge[k+1][j];
                            merge[k+1][j] = tmp;
                            moved = true;
                        }else {
                            if(list[k+1][j].getValue() == list[k][j].getValue() && merge[k][j] && merge[k+1][j]) {
                                int value = list[k][j].getValue();
                                list[k][j].setValue(0);
                                list[k+1][j].setValue(value*2);
                                merge[k+1][j] = false;
                                score = score + value*2;
                                moved = true;
                            }
                        }
                    }
                }
            }
        }
        if(moved) {
            addTile();
            t.repaint();
            scorelabel.setText("Score: "+score);
        }
    }

/**
* A csemp�k balra val� mozgat�s�t val�s�tja meg. L�trehoz egy
* ugyanakkora m�ret� k�tdimenzi�s boolean t�mb�t mint a csemp�k
* t�mbje. Ennek a t�mbnek a seg�ts�g�vel ellen�rzi, hogy egy 
* csempe k�t csempe �sszeolvad�sa-e. Ez az�rt fontos, mert ha 
* mondjuk egy sor �gy alakult ki: 0 8 4 4 �s ekkor jobbra mozgatjuk 
* a csemp�ket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elv�rt
* m�k�d�s, ez elv�rt m�k�d�s ez lenne: 0 0 8 8. Ennek a mechanizmusnak
* a betart�s�t seg�ti a k�tdimenzi�s boolean t�mb.
*/        
    public void updateLeft() {
        boolean moved = false;

        boolean[][] merge = new boolean[4][4];
        for (int i=0; i<4; i++)
            for(int j=0; j<4; j++)
                merge[i][j]=true;

        for (int j=0; j<4; j++) {
            for (int i=1; i<4; i++) {
                for (int k=1; k<4; k++) {
                    if(list[k][j].getValue()>0) {
                        if(list[k-1][j].getValue()==0) {
                            int value = list[k][j].getValue();
                            list[k][j].setValue(0);
                            list[k-1][j].setValue(value);
                            boolean tmp = merge[k][j];
                            merge[k][j] = merge[k-1][j];
                            merge[k-1][j] = tmp;
                            moved = true;
                        }else {
                            if(list[k-1][j].getValue() == list[k][j].getValue() && merge[k][j] && merge[k-1][j]) {
                                int value = list[k][j].getValue();
                                list[k][j].setValue(0);
                                list[k-1][j].setValue(value*2);
                                score = score + value*2;
                                merge[k-1][j] = false;
                                moved = true;
                            }
                        }
                    }
                }
            }
        }
        if (moved) {
            addTile();
            t.repaint();
            scorelabel.setText("Score: "+score);
        }
    }

/**
 * Billenty�zetr�l val� kezel�shez sz�ks�ges.
 */
    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }

/**
 * Billenty�zet nyilaival lehet �r�ny�tani a j�t�kot,
 * a megfelel� KeyEvent-re a megfelel� update met�dust h�vja,
 * tov�bb� minden update ut�n h�vja az isOver() met�dust,
 * hogy ellen�rizze v�ge van-e a j�t�knak.    
 */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

            if (key == KeyEvent.VK_UP && !t.game_over && !t.win) {
                updateUp();
                isOver();
            } else if (key == KeyEvent.VK_DOWN && !t.game_over && !t.win) {
                updateDown();
                isOver();
            } else if (key == KeyEvent.VK_LEFT && !t.game_over && !t.win) {
                updateLeft();
                isOver();
            } else if (key == KeyEvent.VK_RIGHT && !t.game_over && !t.win) {
                updateRight();
                isOver();
            }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}