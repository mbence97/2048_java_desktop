import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import java.util.List;
import javax.swing.*;

/**
 * Ez az osztály valósítja meg a játék mechanizmusát.
 * A JPanel osztály leszármazottja, valamint implementálja
 * a KeyListener interfészt.
 */
public class Game extends JPanel implements KeyListener{
    
/**
 * Az osztály adattagjai: kétdimenziós tömb a csempékrõl,
 * inicializálásnál ezt a tömböt kapja meg játéktábla adattag.
 * Tartalmaz az osztály továbbá egy pontszámot, egy címkét
 * (JLabel) ami a pontszámot hivatott mutatni és egy 
 * LeaderBoardTable objektumot, ami a toplistát kezeli.
 */
	private Tile[][] list;
    private Board t;
    private JLabel scorelabel;
    private int score;
    public LeaderboardTable leaderboard;

/**
 * Az osztály konstruktora. Az osztály a JPanel leszármazottja,
 * erre a panelra kerül a játéktábla és a cimke a pontszámról.
 * A konstruktor beállítja a layoutot, hívja az inicializáló
 * metódust és elvégzi a szükséges beállításokat a játékhoz.    
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
 * A kétdimenziós Tile tömb lekérdezését teszi lehetõvé.
 * Tesztelés céljából jött létre.    
 * @return kétdimenziós Tile tömb
 */
    public Tile[][] getTileList(){
    	return list;
    }
    
/**
 * A játéktábla (Board) adattag lekérdezését teszi lehetõvé.
 * Tesztelés céljából jött létre.
 * @return játéktábla (Board)
 */
    public Board getBoard() {
    	return t;
    }
    
/**
 * A jelenlegi pontszám lekérdezését teszi lehetõvé.
 * Tesztelés céljából jött létre.
 * @return pontszám 
 */
    public int getScore() {
    	return score;
    }  
    
/**
 * A kétdimenziós tömb tetszõleges elemének értékét
 * módosító metódus.
 * Tesztelés céljából jött létre.
 * @param i a tömb horizontális indexe
 * @param j a tömb vertikális indexe
 * @param value a beállítani kívánt érték
 */
    public void setTileValue(int i, int j, int value) {
    	list[i][j].setValue(value);
    }

/**
 * A toplista táblát inicializáló metódus. Beolvassa az értékeket
 * a "ranks.dat" fájlból. Ha a fájl nem létezik akkor egy üres 
 * táblát hoz létre.
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
 * Ellenõrzi, hogy véget ért-e a játék. Ha valamelyik csempe értéke
 * elérte a 2048-at, akkor igazra állítja a játéktábla win változóját
 * és beteszi a szerzett pontszámot a toplista táblába, ha még nincs 10
 * érték a táblában, vagy már van 10 érték a táblában, viszont nagyobb
 * mint a 10. érték. Ekkora beteszi a 10. helyére az újonnan szerzett
 * pontszámot, majd rendezi a toplistát. Ellenõrzi a metódus továbbá,
 * hogy a képesek-e még a csempék mozogni a táblán. Ha nem, és egyik
 * csempe értéke se érte el a 2048-at, akkor a játéktáblában igaz 
 * értékre állítja a game_over változót. Ezután úgyanúgy ellenõrzi, 
 * hogy az elért pontszám toplistára kerülhet-e.
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
 * A játék újraindítását teszi lehetõvé. A csempék értékét
 * alapértelmezettre állítja (mindegyik 0, 2db véletlenszerû
 * csempe értéke 2). A pontot lenullázza valamint a játéktábla
 * két boolean változóját false-ra állítja.
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
 * Két véletlenszerû csempe értékét 2-re állítja. A játék
 * indulásánál és annak újraindításánál van szerepe.
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
 * Inicializáló metódus. A kétdimenziós tömböt hozza létre
 * és állítja be az elemeit, majd a játéktáblához adja.
 * Ezen kívül lenullázza a pontszámot és beállítja a címkét.    
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
 * A játék mûködésének egyik feltétele, hogy minden csempemozgatás
 * után megjelenik egy 2-es értékkel rendelkezõ csempe a fennmaradó
 * "üres" csempék valamelyikén. Ezt teszi lehetõvé a metódus.    
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
 * A csempék felfele való mozgatását valósítja meg. Létrehoz egy
 * ugyanakkora méretû kétdimenziós boolean tömböt mint a csempék
 * tömbje. Ennek a tömbnek a segítségével ellenõrzi, hogy egy 
 * csempe két csempe összeolvadása-e. Ez azért fontos, mert ha 
 * mondjuk egy sor így alakult ki: 0 8 4 4 és ekkor jobbra mozgatjuk 
 * a csempéket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elvárt
 * mûködés, ez elvárt mûködés ez lenne: 0 0 8 8. Ennek a mechanizmusnak
 * a betartását segíti a kétdimenziós boolean tömb.
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
* A csempék lefele való mozgatását valósítja meg. Létrehoz egy
* ugyanakkora méretû kétdimenziós boolean tömböt mint a csempék
* tömbje. Ennek a tömbnek a segítségével ellenõrzi, hogy egy 
* csempe két csempe összeolvadása-e. Ez azért fontos, mert ha 
* mondjuk egy sor így alakult ki: 0 8 4 4 és ekkor jobbra mozgatjuk 
* a csempéket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elvárt
* mûködés, ez elvárt mûködés ez lenne: 0 0 8 8. Ennek a mechanizmusnak
* a betartását segíti a kétdimenziós boolean tömb.
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
* A csempék jobbra való mozgatását valósítja meg. Létrehoz egy
* ugyanakkora méretû kétdimenziós boolean tömböt mint a csempék
* tömbje. Ennek a tömbnek a segítségével ellenõrzi, hogy egy 
* csempe két csempe összeolvadása-e. Ez azért fontos, mert ha 
* mondjuk egy sor így alakult ki: 0 8 4 4 és ekkor jobbra mozgatjuk 
* a csempéket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elvárt
* mûködés, ez elvárt mûködés ez lenne: 0 0 8 8. Ennek a mechanizmusnak
* a betartását segíti a kétdimenziós boolean tömb.
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
* A csempék balra való mozgatását valósítja meg. Létrehoz egy
* ugyanakkora méretû kétdimenziós boolean tömböt mint a csempék
* tömbje. Ennek a tömbnek a segítségével ellenõrzi, hogy egy 
* csempe két csempe összeolvadása-e. Ez azért fontos, mert ha 
* mondjuk egy sor így alakult ki: 0 8 4 4 és ekkor jobbra mozgatjuk 
* a csempéket akkor 0 0 0 16 alakulna ki. Viszont ez nem az elvárt
* mûködés, ez elvárt mûködés ez lenne: 0 0 8 8. Ennek a mechanizmusnak
* a betartását segíti a kétdimenziós boolean tömb.
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
 * Billentyûzetrõl való kezeléshez szükséges.
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
 * Billentyûzet nyilaival lehet írányítani a játékot,
 * a megfelelõ KeyEvent-re a megfelelõ update metódust hívja,
 * továbbá minden update után hívja az isOver() metódust,
 * hogy ellenõrizze vége van-e a játéknak.    
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