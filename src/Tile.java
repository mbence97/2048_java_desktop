import java.awt.*;

/**
 * A csemp�knek az oszt�lya.
 */
public class Tile {

/**
 * Itt vannak a statikus v�ltoz�k, melyek minden csemp�re
 * �rv�nyesek. T�roljuk a csemp�k sz�less�g�t, magass�g�t,
 * a cs�csaik lekerek�t�s�nek m�rt�k�t, a k�zt�k l�v� �res
 * hely nagys�g�t, valamint a rajtuk l�v� felirat bet�t�pus�t.
 */
        public static final int WIDTH = 80;
        public static final int HEIGHT = 80;
        public static final int ARC = 8;
        public static final int SPACE = 8;
        public static final Font font = new Font("Courier", Font.BOLD, 30);

/**
 * Itt vannak a csemp�knek azon adatai melyek minden p�ld�nyra
 * k�l�nb�z�ek. T�roljuk a csemp�nek az �rt�k�t, a h�tt�rsz�n�t,
 * a rajta l�v� feliratnak a sz�n�t valamint az x �s y poz�ci�j�t
 * ami a k�perny�n val� kirajzol�shoz sz�ks�ges.
 */
        private int value;
        private Color backgroundColor;
        private Color textColor;
        private int pos_x;
        private int pos_y;
 
/**
 * Az oszt�ly konstruktora. Be�ll�tja az x �s y poz�ci�t �s
 * a csempe �rt�k�t 0-ra �ll�tja.
 * @param x a csempe x poz�ci�ja (pos_x)
 * @param y a csempe y poz�ci�ja (pos_y)
 */
        public Tile(int x, int y) {
            pos_x = x;
            pos_y = y;
            setValue(0);
        }	
        
/**
 * Az objektum �rt�k�nek lek�rdez�s�re szolg�l.       
 * @return az objektum �rt�ke
 */
        public int getValue(){
            return value;
        }

/**
 * Az objektum h�tt�rsz�n lek�rdez�s�re szolg�l.
 * @return az objektum h�tt�rsz�ne
 */
        public Color getBackgroundColor(){
            return backgroundColor;
        }
        
/**
 * Az objektum felirat�nak a sz�n�nek a lek�rdez�s�re szolg�l.        
 * @return az objektum felirat�nak sz�ne
 */
        public Color getTextColor(){
            return textColor;
        }
        
/**
 * Az objektum poz�ci�j�nak x koordin�t�j�nak lek�rdez�s�re szolg�l.
 * @return az objektum x poz�ci�ja
 */
        public int getPos_x(){
            return pos_x;
        }
        
/**
 * Az objektum poz�ci�j�nak y koordin�t�j�nak lek�rdez�s�re szolg�l.
 * @return az objektum y poz�ci�ja
 */
        public int getPos_y(){
            return pos_y;
        }

/**
 * Az objektum �rt�k�nek be�ll�t�s�ra szolg�l. Az �rt�k
 * be�ll�t�s�val egyidej�leg 2-t�l 2048-ig minden
 * 2 hatv�nyra egyedi h�tt�rsz�nt �s feliratsz�nt �ll�t be.        
 * @param v a be�ll�tand� �rt�k
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

