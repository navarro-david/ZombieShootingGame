import java.awt.Color;

public class Bullet extends Actor {

	public Bullet(int x, int y, int w, int h, int xv, int yv, Color c )  {
		super(x, y, w, h, xv, yv, c);
	}

	public void move() {

		hitBox.translate(xvel, yvel);

	}

}