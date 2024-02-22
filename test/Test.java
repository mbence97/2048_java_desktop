import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

/**
 * A program tesztelését végzõ osztály.
 */
public class Test {
	Tile t;
	Tile [][] list;
	Rank r1;
	Rank r2;
	RankSorter sorter;
	Game game;
	Board b;
	Window w;

/**
 * Minden teszt elõtt inicializáljuk a szükséges elemeket.	
 */
	@org.junit.Before
	public void setUp() {
		t = new Tile(10,20);
		r1 = new Rank(1,500);
		r2 = new Rank(1,600);
		sorter = new RankSorter();
		game = new Game();
		list = new Tile[4][4];
		b = new Board(list);
		w = new Window("2048");
	}
	
	
/**
 * A Tile osztály tesztelése	
 */
	@org.junit.Test
	public void testTile() {
		assertEquals(10, t.getPos_x());
		assertEquals(20,t.getPos_y());
		assertEquals(0, t.getValue());
		
		t.setValue(2048);
		Color bg = new Color(0x1741B1);
		Color text = new Color(0x000000);
		assertEquals(bg.getRGB(), t.getBackgroundColor().getRGB());
		assertEquals(text.getRGB(), t.getTextColor().getRGB());
		assertEquals(2048, t.getValue());
	}
	
/**
 * A Rank osztály tesztelése
 */
	@org.junit.Test
	public void testRank() {
		assertEquals(1,r1.getRank());
		assertEquals(500, r1.getScore());
		
		r1.setRank(3);
		r1.setScore(200);
		
		assertEquals(3,r1.getRank());
		assertEquals(200,r1.getScore());
	}
	
/**
 * A RankSorter komparátor tesztelése
 */
	@org.junit.Test
	public void testRankSorter() {
		int x = sorter.compare(r1, r2);
		assertEquals(x,100);
	}
	
/**
 * A Board osztály tesztelése
 */
	 @org.junit.Test
	 public void testBoard() {
		 assertFalse(b.game_over);
		 assertFalse(b.win);
		 assertSame(b.list,list);
		 b.win = true;
	 }
	 
/**
 * A Game osztály tesztelése
 * @throws AWTException
 */
	 @SuppressWarnings("deprecation")
	@org.junit.Test
	 public void testGame() throws AWTException {
		 assertEquals(game.getScore(), 0);
		 game.twoRandomTile();
		 assertNotSame(game.getTileList(),list);
		 
		@SuppressWarnings("deprecation")
		KeyEvent e;
		 game.setTileValue(0 ,0, 2);
		 game.setTileValue(0, 1, 2);
		 game.setTileValue(2 ,0, 32);
		 game.setTileValue(2, 1, 32);
		 e = new KeyEvent(game, KeyEvent.VK_UP, 0, 0, 38);
		 game.keyPressed(e);
		 game.setTileValue(1, 0, 4);
		 e = new KeyEvent(game, KeyEvent.VK_LEFT, 0, 0, 37);
		 game.keyPressed(e);
		 game.setTileValue(3 ,3, 512);
		 game.setTileValue(3, 2, 512);
		 game.setTileValue(1 ,3, 128);
		 game.setTileValue(1, 2, 128);
		 e = new KeyEvent(game, KeyEvent.VK_DOWN, 0, 0, 40);
		 game.keyPressed(e);
		 game.setTileValue(2, 3, 1024);
		 e = new KeyEvent(game, KeyEvent.VK_RIGHT, 0, 0, 39);
		 game.keyPressed(e);
		 
		 assertEquals(game.getTileList()[3][3].getValue(),2048);
		 game.isOver();
		 assertTrue(game.getBoard().win);
		 
		 game.restart();
		 int x=1;
		 for (int i=0; i<4; i++)
			 for (int j=0; j<4; j++)
				 	game.setTileValue(i, j, x++);
		 
		 game.isOver();
		 assertTrue(game.getBoard().game_over);
	 	}
	 
/**
 * A Leaderboard osztály tesztelése
 */
	 @org.junit.Test
	 public void leaderboardTest() {
		
		 LeaderboardTable lbt = new LeaderboardTable();
		 assertSame(lbt.ranks.size(),0);
		 assertSame(lbt.getColumnClass(0), Object.class);

		 lbt.add(r1);
		 assertSame(lbt.getColumnClass(0), Integer.class);
		 assertSame(lbt.getColumnCount(), 2);
		 assertSame(lbt.getRowCount(), 1);
		 assertSame(lbt.getValueAt(0, 0), 1);
		 assertEquals(lbt.getValueAt(0, 1),500);
		 lbt.setValueAt(5, 0, 0);
		 lbt.setValueAt(3000,0,1);
		 assertSame(lbt.getValueAt(0, 0), 5);
		 assertEquals(lbt.getValueAt(0, 1), 3000);

	 }
	 
/**
 * A Window osztály tesztelése
 * @throws AWTException
 * @throws InterruptedException
 */
	 @org.junit.Test
	 public void windowTest() throws AWTException, InterruptedException {
		 assertEquals(w.getTitle(), "2048");

		 w.setVisible(true);
		 
		 w.getLb_button().doClick();
		 assertTrue(w.getLbt_panel().isVisible());
		 
		 w.getLb_button().doClick();
		 assertFalse(w.getLbt_panel().isVisible());
		 
		 w.getGame().getBoard().win=true;
		 w.getNg_button().doClick();
		 w.getGame().getBoard().game_over=true;
		
	 }
	 
/**
 * A játék indításának tesztelése
 */
	 @org.junit.Test
	 public void startTest() {
		 Start s = new Start();
		 s.main(null);
	 }
	 
}


