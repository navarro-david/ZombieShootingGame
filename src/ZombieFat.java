import java.awt.Color;
import java.io.*;
import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class ZombieFat extends Monster{

	Color col;
	BufferedImage imgZombieRight, imgZombieLeft;

	public ZombieFat(){
		super();
		setColor(new Color(0,255,255));
		setColor(this.getColor().darker());
		setColor(this.getColor().darker());
		setHealth(getHealth()*6);
		setMoveTime(getMoveTime() * 3);
		col = getColor();

		imgZombieRight = null;
		imgZombieLeft = null;

  			try {
		imgZombieRight = ImageIO.read(new File("images/zombie_game_Sprite_zombie3_right.png"));

			} catch (IOException e) {
		System.out.println(e);
		}

  			try {
		imgZombieLeft = ImageIO.read(new File("images/zombie_game_Sprite_zombie3_left.png"));

			} catch (IOException e) {
		System.out.println(e);
		}
		setImageRight(imgZombieRight);
		setImageLeft(imgZombieLeft);

		setAnimationDelay(20);



	}

	public void drawActor(Graphics2D win) {
	animationTimer++;
	if(animationTimer > animationDelay){
	frameStage++;
	if(frameStage >= 5){
		frameStage = 1;
	}
	animationTimer = 0;
	}
	BufferedImage frame = null;
	if(!facingRight){
		frame = imgZombieRight.getSubimage(0,frameStage * 104, imgZombieRight.getWidth(), 104);
	} else{
		frame = imgZombieLeft.getSubimage(0,frameStage * 104, imgZombieLeft.getWidth(), 104);
	}

	win.drawImage(frame, getXPos(), getYPos() - frame.getHeight()/2, null);

	}
}