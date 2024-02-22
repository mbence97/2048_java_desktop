import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * A JFrame leszármazottjaként ez az osztály felelõs
 * az összes megjeleníteni kívánt elem (JPanel, JButton,
 * JLabel és JTable elemek) megfelelõ elhelyezésére
 * a megjelenítésre szolgáló ablakban.
 */
public class Window extends JFrame {

/**
 * Adattagként tárolja az összes megjeleníteni kívánt elemet.
 */
    private Game game;				//A játékpanel (a Game osztály JPanel leszármazott)
    private JPanel main_panel;		//A fõpanel, erre kerül rá az összes többi panel majd ezt adja át a JFrame-nek
    private JPanel b_panel;			//A gombok panelja
    private JPanel lbt_panel;		//A toplista panelja, a toplista táblát jeleníti meg
    private JButton ng_button;		//New game gomb
    private JButton lb_button;		//Leaderboard gomb
    private JTable lb_table;		//A toplista táblája

/**
 * Az osztály konstruktora. Hívja az inicializáló metódusokat
 * valamint gondoskodik a toplista fájlba írásáról az ablak
 * bezárásakor.    
 * @param title az ablak címe
 */
    public Window(String title){
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initButtons();
        initPanels();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("ranks.dat"));
                    oos.writeObject(game.leaderboard.ranks);
                    oos.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }});

    }
    
/**
 * A New game gomb elérésére szolgál.
 * Tesztelés céljából jött létre
 * @return New game gomb
 */
    public JButton getNg_button() {
    	return ng_button;
    }
    
/**
 * A Leaderboard gomb elérésére szolgál
 * Tesztelés céljából jött létre    
 * @return Leaderboard gomb
 */
    public JButton getLb_button() {
    	return lb_button;
    }
    
/**
 * A toplista paneljának elérésére szolgál.
 * Tesztelés céljából jött létre.    
 * @return a toplista panelja
 */
    public JPanel getLbt_panel() {
    	return lbt_panel;
    }
    
/**
 * A játék adattag elérésére szolgál
 * @return játék adattag
 */
    public Game getGame() {
    	return game;
    }

/**
 * A gombok inicializálására szolgáló metódus. A New game
 * gomb lenyomására a játék újraindul. A Leaderboard gomb
 * lenyomására láthatóvá válik a toplista panel, a gomb
 * ismételt lenyomására eltûnik a panel.    
 */
    private void initButtons() {
        ng_button = new JButton("New Game");
        ng_button.setPreferredSize(new Dimension(150,50));
        ng_button.setFont(new Font("Arial", Font.BOLD, 17));
        ng_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restart();
            }
        });
        ng_button.setBackground(new Color(0x5B6C7E));
        ng_button.setForeground(new Color(0x000000));
        ng_button.setFocusable(false);

        lb_button = new JButton("Leaderboard");
        lb_button.setPreferredSize(new Dimension(150,50));
        lb_button.setFont(new Font("Arial", Font.BOLD, 17));
        lb_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lbt_panel.isVisible())
                lbt_panel.setVisible(false);
                else
                    lbt_panel.setVisible(true);
                    pack();
            }
        });
        lb_button.setBackground(new Color(0x5B6C7E));
        lb_button.setForeground(new Color(0x000000));
        lb_button.setFocusable(false);
    }

/**
 * A panelok és layoutok inicializálására szolgáló metódus.    
 */
    private void initPanels(){
        game = new Game();

        b_panel = new JPanel();
        b_panel.add(ng_button);
        b_panel.add(lb_button);
        b_panel.setBackground(new Color(0x202A36));

        lbt_panel = new JPanel();
        lb_table = new JTable(game.leaderboard);
        lb_table.setRowHeight(25);
        lb_table.setFont(new Font("Arial", Font.BOLD, 17));
        TableColumnModel columnModel = lb_table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(120);
        lb_table.setBackground(new Color(0x5B6C7E));
        lb_table.setForeground(new Color(0x000000));
        lbt_panel.add(lb_table);
        lbt_panel.setBackground(new Color(0x202A36));
        lbt_panel.setVisible(false);

        main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.PAGE_AXIS));
        main_panel.add(game);
        main_panel.add(b_panel);
        main_panel.add(lbt_panel);
        main_panel.setBackground(new Color(0x202A36));
        pack();

        add(main_panel);
    }
}
