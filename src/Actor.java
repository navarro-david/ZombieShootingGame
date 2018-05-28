import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics2D;

public class Actor {

	int xpos, ypos, xvel, yvel;
	Color col;
	Rectangle hitBox;

	public Actor(int x, int y, int w, int h, int xv, int yv, Color c ) {
		xpos = x;
		ypos = y;
		xvel = xv;
		yvel = yv;
		col = c;
		hitBox = new Rectangle(x, y, w, h);
	}
	public void drawActor(Graphics2D win) {

		win.setColor(col);
		win.fill(hitBox);

	}
	public void setXPos(int value) {

		xpos = value;
	}
	public void setYPos(int value) {
		ypos = value;
	}
	public int getXPos() {
		return (int) hitBox.getX();
	}
	public int getYPos() {
		return (int) hitBox.getY();
	}
	public void setXVel(int value) {
		xvel = value;
	}
	public void setYVel(int value) {
		yvel = value;

	}
	public int getXVel() {
		return xvel;
	}
	public int getYVel() {
		return yvel;
	}
	public Color getColor(){
		return col;
	}
	public void setColor(Color c){
		col = c;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}




}