import javax.swing.*;
import java.awt.*;

/**
 * A j�t�kt�bla oszt�lya. Ez az oszt�ly t�rolja a csemp�ket
 * egy k�tdimenzi�s (4*4) t�mbben �s a JPanel oszt�ly 
 * lesz�rmazottjak�nt lehet�v� teszi a j�t�kt�bla  megjelen�t�s�t.
 *
 */
public class Board extends JPanel {
	
/**
 * A j�t�kt�bla v�ltoz�i: a csemp�kb�l �ll� k�tdimenzi�s t�mbb,
 * egy-egy boolean v�ltoz� a j�t�k lehets�ges kimeneteire:
 * game_over: a j�t�knak v�ge, nem siker�lt a 2048-as �rt�k el�r�se
 * win: a j�t�knak v�ge, siker�lt el�rni a 2048-as �rt�ket
 */
    public Tile[][] list;
    public boolean game_over;
    public boolean win;

/**
 * Az oszt�ly konstruktora. Param�terk�nt kap egy k�tdimenzi�s
 * Tile t�mb�t aminek �rt�k�t be�ll�tja a saj�t k�tdimenzi�s t�mbj�nek.
 * Be�ll�tja a JPanel pontos m�ret�t, valamint false-ra �ll�tja a k�t
 * boolean v�ltoz� �rt�k�t.
 * @param l k�tdimenzi�s Tile t�mb
 */
    Board(Tile[][] l){
        setPreferredSize(new Dimension(Tile.SPACE*7+Tile.WIDTH*4,Tile.SPACE*7+Tile.HEIGHT*4));
        list = l;
        game_over = false;
        win = false;
    }

/**
 * A j�t�kt�bla k�perny�re rajzol�s�t val�s�tja meg.
 * Rajzol egy alap kit�lt�tt n�gysz�get ami a t�bla alapja lesz,
 * majd egyes�vel felrajzolja a n�gysz�gre a szint�n n�gysz�g 
 * csemp�ket. A j�t�k v�gezet�vel ki�rja hogy nyert-e a j�t�kos.
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
