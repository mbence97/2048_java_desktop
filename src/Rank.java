import java.io.Serializable;

/**
 * Helyezés osztály. Ennek az osztálynak az
 * objektumai alkotják a játék toplistáját.
 */
public class Rank implements Serializable {
/**
 * Az osztálynak 2 adattagja van, helyezés
 * és  ahozzá tartozó pontszám
 */
    private int rank;
    private int score;

/**
 * Az osztály konstruktora. Beállítja az
 * adattagokat.    
 * @param r helyezés
 * @param s pontszám
 */
    public Rank(int r, int s){
        this.rank = r;
        this.score = s;
    }

/**
 * A helyezés állítására szolgál.    
 * @param r helyezés
 */
    public void setRank(int r){
        this.rank = r;
    }

/**
 * A pontszám állítására szolgál.    
 * @param s pontszám
 */
    public void setScore(int s){
        this.score = s;
    }
   
/**
 * A helyezés lekérését teszi lehetõvé.
 * @return helyezés
 */
    public int getRank(){
        return rank;
    }

/**
 * A pontszám lekérését teszi lehetõvé.    
 * @return pontszám
 */
    public int getScore(){
        return score;
    }

}
