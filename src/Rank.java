import java.io.Serializable;

/**
 * Helyez�s oszt�ly. Ennek az oszt�lynak az
 * objektumai alkotj�k a j�t�k toplist�j�t.
 */
public class Rank implements Serializable {
/**
 * Az oszt�lynak 2 adattagja van, helyez�s
 * �s  ahozz� tartoz� pontsz�m
 */
    private int rank;
    private int score;

/**
 * Az oszt�ly konstruktora. Be�ll�tja az
 * adattagokat.    
 * @param r helyez�s
 * @param s pontsz�m
 */
    public Rank(int r, int s){
        this.rank = r;
        this.score = s;
    }

/**
 * A helyez�s �ll�t�s�ra szolg�l.    
 * @param r helyez�s
 */
    public void setRank(int r){
        this.rank = r;
    }

/**
 * A pontsz�m �ll�t�s�ra szolg�l.    
 * @param s pontsz�m
 */
    public void setScore(int s){
        this.score = s;
    }
   
/**
 * A helyez�s lek�r�s�t teszi lehet�v�.
 * @return helyez�s
 */
    public int getRank(){
        return rank;
    }

/**
 * A pontsz�m lek�r�s�t teszi lehet�v�.    
 * @return pontsz�m
 */
    public int getScore(){
        return score;
    }

}
