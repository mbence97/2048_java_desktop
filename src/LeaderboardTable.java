import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.ArrayList;

/**
 * Ez az osztály kezeli a játék toplistáját.
 * AbstractTableModel leszármazottja, hogy át 
 * lehessen adni egy JTable-nek megjelenítésre.
 */
public class LeaderboardTable extends AbstractTableModel{
	
/**
 * Az osztály egyetlen adattagja a helyezéseket
 * tartalmazó lista.
 */
    List<Rank> ranks = new ArrayList<>();

/**
 * A lista bõvítésére szolgáló metódus.    
 * @param r a hozzáadni kívánt elem
 */
    public void add(Rank r){
        ranks.add(r);
        this.fireTableDataChanged();
    }

/**
 * Visszaadja hány sorból áll a lista.   
 * @return a sorok száma
 */
    @Override
    public int getRowCount() {
        return ranks.size();
    }

/**
 * Visszaadja hány oszlopból áll a lista.    
 * @return az oszlopok száma
 */
    @Override
    public int getColumnCount() {
        return 2;
    }

/**
 * Paraméterként kap egy oszlopszámot, ennek az
 * oszlopnak az objektumtípusával tér vissza.
 * @param columnIndex oszlopszám
 * @return az oszlop objektumtípusa
 */
    public Class<?> getColumnClass(int columnIndex) {
        if (ranks.isEmpty()) {
            return Object.class;
        }
            return Integer.class;
    }

/**
 * Visszaadja a paraméterként kapott sorszám és 
 * oszlopszám alapján a megfelelõ értéket.
 * @param rowIndex sorszám
 * @param columnIndex oszlopszám
 * @return a paramétereknek megfelelõ érték
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
 * A toplista tetszõleges értékének (legyen az egy
 * sor helyezése vagy pontszáma) megváltoztatását
 * teszi lehetõvé.
 * @param value a beállítani kívánt érték
 * @param rowIndex sorszám
 * @param columnIndex oszlopszám    
 */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 0)
        	ranks.get(rowIndex).setRank((int)value);
        else
        	ranks.get(rowIndex).setScore((int)value);
    }

}

