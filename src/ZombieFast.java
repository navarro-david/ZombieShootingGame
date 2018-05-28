import java.awt.Color;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class ZombieFast extends Monster{

	Color col;
	BufferedImage imgZombieRight, imgZombieLeft;

	public ZombieFast(){
		super();
		setColor(this.getColor().darker());
		setColor(this.getColor().darker());
		setMoveTime(1);
		setSoundTime(500);
		setAnimationDelay(5);
		col = getColor();

		imgZombieRight = null;
		imgZombieLeft = null;

  			try {
		imgZombieRight = ImageIO.read(new File("images/zombie_game_Sprite_zombie2_right.png"));

			} catch (IOException e) {
		System.out.println(e);
		}

  			try {
		imgZombieLeft = ImageIO.read(new File("images/zombie_game_Sprite_zombie2_left.png"));

			} catch (IOException e) {
		System.out.println(e);
		}
		setImageRight(imgZombieRight);
		setImageLeft(imgZombieLeft);



	}
}