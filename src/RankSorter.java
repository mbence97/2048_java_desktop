import java.util.Comparator;

/**
 * A toplista rendezését teszi lehetõvé az osztály.
 * Implementálja a Comparator interfészt.
 */
public class RankSorter implements Comparator<Rank> {

/**
 * Két helyezés objektum összehasonlítására szolgál.
 * @param o1 egyik helyezés
 * @param o2 másik helyezés
 * @return a két helyezés pontszámainak különbsége
 */
    @Override
    public int compare(Rank o1, Rank o2) {
        return o2.getScore()-o1.getScore();
    }

}
