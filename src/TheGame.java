import javax.swing.JFrame;

public class TheGame {

    public static void main(String[] args) {
       	JFrame j1 = new JFrame();
    	j1.setSize(800,485);
    	j1.setTitle("Zombie Apocalypse");
    	j1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	j1.add(new GameCanvas());
    	j1.setVisible(true);

    }
}
