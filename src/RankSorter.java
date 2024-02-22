import java.util.Comparator;

/**
 * A toplista rendez�s�t teszi lehet�v� az oszt�ly.
 * Implement�lja a Comparator interf�szt.
 */
public class RankSorter implements Comparator<Rank> {

/**
 * K�t helyez�s objektum �sszehasonl�t�s�ra szolg�l.
 * @param o1 egyik helyez�s
 * @param o2 m�sik helyez�s
 * @return a k�t helyez�s pontsz�mainak k�l�nbs�ge
 */
    @Override
    public int compare(Rank o1, Rank o2) {
        return o2.getScore()-o1.getScore();
    }

}
