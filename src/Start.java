/**
 * A játék indítását végzõ osztály.
 */
public class Start {
	
    public static void main(String[] args) {
        Window w = new Window("Negative 2048");
        w.pack();
        w.setLocationRelativeTo(null);
        w.setResizable(false);
        w.setVisible(true);
    }

}
