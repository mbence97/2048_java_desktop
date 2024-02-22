import javax.swing.*;
import java.awt.*;

/**
 * A játéktábla osztálya. Ez az osztály tárolja a csempéket
 * egy kétdimenziós (4*4) tömbben és a JPanel osztály 
 * leszármazottjaként lehetõvé teszi a játéktábla  megjelenítését.
 *
 */
public class Board extends JPanel {
	
/**
 * A játéktábla változói: a csempékbõl álló kétdimenziós tömbb,
 * egy-egy boolean változó a játék lehetséges kimeneteire:
 * game_over: a játéknak vége, nem sikerült a 2048-as érték elérése
 * win: a játéknak vége, sikerült elérni a 2048-as értéket
 */
    public Tile[][] list;
    public boolean game_over;
    public boolean win;

/**
 * Az osztály konstruktora. Paraméterként kap egy kétdimenziós
 * Tile tömböt aminek értékét beállítja a saját kétdimenziós tömbjének.
 * Beállítja a JPanel pontos méretét, valamint false-ra állítja a két
 * boolean változó értékét.
 * @param l kétdimenziós Tile tömb
 */
    Board(Tile[][] l){
        setPreferredSize(new Dimension(Tile.SPACE*7+Tile.WIDTH*4,Tile.SPACE*7+Tile.HEIGHT*4));
        list = l;
        game_over = false;
        win = false;
    }

/**
 * A játéktábla képernyõre rajzolását valósítja meg.
 * Rajzol egy alap kitöltött négyszöget ami a tábla alapja lesz,
 * majd egyesével felrajzolja a négyszögre a szintén négyszög 
 * csempéket. A játék végezetével kiírja hogy nyert-e a játékos.
 */
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        this.setBackground(new Color(0x202A36));
        g.setColor(new Color(0x41515E));
        g.fillRoundRect(Tile.SPACE, Tile.SPACE, Tile.SPACE*5+Tile.WIDTH*4, Tile.SPACE*5+Tile.HEIGHT*4,10,10);

        for (int i = 0; i<4; i++) {
            for (int j = 0; j < 4; j++) {
                g.setColor(list[i][j].getBackgroundColor());
                g.fillRoundRect(list[i][j].getPos_x(), list[i][j].getPos_y(), Tile.WIDTH, Tile.HEIGHT, Tile.ARC, Tile.ARC);

                g.setColor(list[i][j].getTextColor());
                g.setFont(Tile.font);
                String text = String.valueOf(list[i][j].getValue());
                FontMetrics fm = g.getFontMetrics();
                int x = (list[i][j].getPos_x()+(Tile.WIDTH - fm.stringWidth(text)) / 2);
                int y = (list[i][j].getPos_y()+(Tile.HEIGHT - fm.getHeight()) / 2) + fm.getAscent();
                g.drawString(text, x, y);
            }
        }
        if (game_over || win) {
            String s;
            g.setFont(new Font("Arial", Font.BOLD, 55));
            FontMetrics fm = g.getFontMetrics();
            if(game_over){
            s = new String("Game over");
            }
            else{
                s = new String("You won!");
            }
            int x = ((Tile.SPACE*7+Tile.WIDTH*4 - fm.stringWidth(s))/2);
            int y = ((Tile.SPACE*7+Tile.HEIGHT*4 - fm.getHeight())/2)+fm.getAscent();
            g.setColor(new Color(0x202A36));
            g.fillRoundRect(x-Tile.SPACE,y-fm.getAscent()-Tile.SPACE,fm.stringWidth(s)+2*Tile.SPACE,fm.getHeight()+2*Tile.SPACE,15,15);
            g.setColor(new Color(0xF5846F));
            g.drawString(s, x, y);
        }
    }
}
