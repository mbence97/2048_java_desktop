import java.awt.*;

/**
 * A csempéknek az osztálya.
 */
public class Tile {

/**
 * Itt vannak a statikus változók, melyek minden csempére
 * érvényesek. Tároljuk a csempék szélességét, magasságát,
 * a csúcsaik lekerekítésének mértékét, a köztük lévõ üres
 * hely nagyságát, valamint a rajtuk lévõ felirat betûtípusát.
 */
        public static final int WIDTH = 80;
        public static final int HEIGHT = 80;
        public static final int ARC = 8;
        public static final int SPACE = 8;
        public static final Font font = new Font("Courier", Font.BOLD, 30);

/**
 * Itt vannak a csempéknek azon adatai melyek minden példányra
 * különbözõek. Tároljuk a csempének az értékét, a háttérszínét,
 * a rajta lévõ feliratnak a színét valamint az x és y pozícióját
 * ami a képernyõn való kirajzoláshoz szükséges.
 */
        private int value;
        private Color backgroundColor;
        private Color textColor;
        private int pos_x;
        private int pos_y;
 
/**
 * Az osztály konstruktora. Beállítja az x és y pozíciót és
 * a csempe értékét 0-ra állítja.
 * @param x a csempe x pozíciója (pos_x)
 * @param y a csempe y pozíciója (pos_y)
 */
        public Tile(int x, int y) {
            pos_x = x;
            pos_y = y;
            setValue(0);
        }	
        
/**
 * Az objektum értékének lekérdezésére szolgál.       
 * @return az objektum értéke
 */
        public int getValue(){
            return value;
        }

/**
 * Az objektum háttérszín lekérdezésére szolgál.
 * @return az objektum háttérszíne
 */
        public Color getBackgroundColor(){
            return backgroundColor;
        }
        
/**
 * Az objektum feliratának a színének a lekérdezésére szolgál.        
 * @return az objektum feliratának színe
 */
        public Color getTextColor(){
            return textColor;
        }
        
/**
 * Az objektum pozíciójának x koordinátájának lekérdezésére szolgál.
 * @return az objektum x pozíciója
 */
        public int getPos_x(){
            return pos_x;
        }
        
/**
 * Az objektum pozíciójának y koordinátájának lekérdezésére szolgál.
 * @return az objektum y pozíciója
 */
        public int getPos_y(){
            return pos_y;
        }

/**
 * Az objektum értékének beállítására szolgál. Az érték
 * beállításával egyidejûleg 2-tõl 2048-ig minden
 * 2 hatványra egyedi háttérszínt és feliratszínt állít be.        
 * @param v a beállítandó érték
 */
        public void setValue(int v){
            this.value = v;
            if (value == 2) {
                backgroundColor = new Color(0xFF121824);
                textColor = new Color(0x8F95A1);
            }
            else if (value == 4) {
                backgroundColor = new Color(0xFF131F37);
                textColor = new Color(0x8F95A1);
            }
            else if (value == 8) {
                backgroundColor = new Color(0xFF124C84);
                textColor = new Color(0x000000);
            }
            else if (value == 16) {
                backgroundColor = new Color(0x0C6897);
                textColor = new Color(0x000000);
            }
            else if (value == 32) {
                backgroundColor = new Color(0x0D8399);
                textColor = new Color(0x000000);
            }
            else if (value == 64) {
                backgroundColor = new Color(0x09A0BB);
                textColor = new Color(0x000000);
            }
            else if (value == 128) {
                backgroundColor = new Color(0x153087);
                textColor = new Color(0x000000);
            }
            else if (value == 256) {
                backgroundColor = new Color(0x113595);
                textColor = new Color(0x000000);
            }
            else if (value == 512) {
                backgroundColor = new Color(0x1435A6);
                textColor = new Color(0x000000);
            }
            else if (value == 1024) {
                backgroundColor = new Color(0x193BB0);
                textColor = new Color(0x000000);
            }
            else if (value == 2048) {
                backgroundColor = new Color(0x1741B1);
                textColor = new Color(0x000000);
            }
            else if(value == 0){
                backgroundColor = new Color(0x343D4C);
                textColor = new Color(0x343D4C);
            }
            else {
                backgroundColor = new Color(0x000000);
                textColor = new Color(0xffffff);
            }
        }

}

