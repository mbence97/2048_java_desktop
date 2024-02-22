import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

/**
 * Ez az oszt�ly kezeli a j�t�k toplist�j�t.
 * AbstractTableModel lesz�rmazottja, hogy �t 
 * lehessen adni egy JTable-nek megjelen�t�sre.
 */
public class LeaderboardTable extends AbstractTableModel{
	
/**
 * Az oszt�ly egyetlen adattagja a helyez�seket
 * tartalmaz� lista.
 */
    List<Rank> ranks = new ArrayList<>();

/**
 * A lista b�v�t�s�re szolg�l� met�dus.    
 * @param r a hozz�adni k�v�nt elem
 */
    public void add(Rank r){
        ranks.add(r);
        this.fireTableDataChanged();
    }

/**
 * Visszaadja h�ny sorb�l �ll a lista.   
 * @return a sorok sz�ma
 */
    @Override
    public int getRowCount() {
        return ranks.size();
    }

/**
 * Visszaadja h�ny oszlopb�l �ll a lista.    
 * @return az oszlopok sz�ma
 */
    @Override
    public int getColumnCount() {
        return 2;
    }

/**
 * Param�terk�nt kap egy oszlopsz�mot, ennek az
 * oszlopnak az objektumt�pus�val t�r vissza.
 * @param columnIndex oszlopsz�m
 * @return az oszlop objektumt�pusa
 */
    public Class<?> getColumnClass(int columnIndex) {
        if (ranks.isEmpty()) {
            return Object.class;
        }
            return Integer.class;
    }

/**
 * Visszaadja a param�terk�nt kapott sorsz�m �s 
 * oszlopsz�m alapj�n a megfelel� �rt�ket.
 * @param rowIndex sorsz�m
 * @param columnIndex oszlopsz�m
 * @return a param�tereknek megfelel� �rt�k
 */
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Rank rank = ranks.get(rowIndex);
        if (columnIndex == 0)
            return rank.getRank();
        else
            return rank.getScore();
    }

/**
 * A toplista tetsz�leges �rt�k�nek (legyen az egy
 * sor helyez�se vagy pontsz�ma) megv�ltoztat�s�t
 * teszi lehet�v�.
 * @param value a be�ll�tani k�v�nt �rt�k
 * @param rowIndex sorsz�m
 * @param columnIndex oszlopsz�m    
 */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0)
        	ranks.get(rowIndex).setRank((int)value);
        else
        	ranks.get(rowIndex).setScore((int)value);
    }

}

