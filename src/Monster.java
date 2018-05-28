import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*;
import javax.imageio.*;

public class Monster extends Actor {

	BufferedImage imgZombieRight, imgZombieLeft;
	int health,hurtTimer,moveTimer,moveTime,soundTimer,soundTime,animationDelay,animationTimer,frameStage;
	boolean rightSpawn;
	Color col;
	static SoundDriver sd;
	boolean facingRight;

	public Monster()  {

		super(820, (int)(Math.random()*50) + 300, 50, 50, -1, 1, Color.GREEN);
		rightSpawn = true;
		col = Color.GREEN;

		if((int)(Math.random() * 2) == 1)
		{
			getHitBox().setLocation(-20, (int)(Math.random()*50) + 300);
			setXVel(-getXVel());
			rightSpawn = false;
		}
		if(sd == null){
			String[] audio = {"audio/monster/zombie1.wav","audio/monster/zombie2.wav","audio/monster/zombie3.wav","audio/monster/zombiehurt1.wav","audio/monster/zombiehurt2.wav"};
			sd = new SoundDriver(audio);
		}
		health = 150;
		soundTimer = 300;
		hurtTimer = 0;
		moveTimer = 0;
		moveTime = 2;
		soundTime = moveTime * 100;
		animationDelay = 10;
		animationTimer = 0;
		frameStage = 1;
		facingRight = false;

		imgZombieRight = null;
		imgZombieLeft = null;

  			try {
		imgZombieRight = ImageIO.read(new File("images/zombie_game_Sprite_zombie1_right.png"));

			} catch (IOException e) {
		System.out.println(e);
		}

  			try {
		imgZombieLeft = ImageIO.read(new File("images/zombie_game_Sprite_zombie1_left.png"));

			} catch (IOException e) {
		System.out.println(e);
		}




	}


	public void move(int plyrX, int plyrY, boolean spawned)
	{
		moveTimer++;
		if(moveTimer > moveTime){
			if(spawned){
				if(plyrX < getXPos()){
					hitBox.translate(xvel,0);
				}if(plyrX > getXPos()){
					hitBox.translate(-xvel, 0);
				}if(plyrY > getYPos()){
					hitBox.translate(0, yvel);
				}if(plyrY < getYPos()){
					hitBox.translate(0, -yvel);
		}}else{
				if(plyrX < getXPos()){
					hitBox.translate(-xvel,0);
				}if(plyrX > getXPos()){
					hitBox.translate(xvel, 0);
				}if(plyrY > getYPos()){
					hitBox.translate(0, yvel);
				}if(plyrY < getYPos()){
					hitBox.translate(0, -yvel);
		}
		}
		soundTimer++;
		if(soundTimer > soundTime){
			sd.play((int)(Math.random() * 3));
			soundTimer = 0;
		}
			moveTimer = 0;


		}
	}
	public void setMoveTime(int t){
		moveTime = t;
	}

	public boolean getRightSpawned(){
		return rightSpawn;
	}
	public void hurt(int damage){
		hurtTimer++;
		setColor(col);
		if(hurtTimer > 5){
			if(damage <= 0) return;
			sd.play(2 + (int)(Math.random() * 3));
			setColor(Color.WHITE);
			hurtTimer = 0;
			health-=damage;
		}

	}

	public void drawActor(Graphics2D win) {
	animationTimer++;
	if(animationTimer > animationDelay){
	frameStage++;
	if(frameStage >= 11){
		frameStage = 1;
	}
	animationTimer = 0;
	}
	BufferedImage frame = null;
	if(!facingRight){
		frame = imgZombieRight.getSubimage(0,frameStage * 58, imgZombieRight.getWidth(), 57);
	} else{
		frame = imgZombieLeft.getSubimage(0,frameStage * 58, imgZombieLeft.getWidth(), 57);
	}

	win.drawImage(frame, getXPos(), getYPos(), null);

	}

	public void setDirection(int x){
		if(x < getXPos()){
			facingRight = true;
		}else{
			facingRight = false;
		}

	}
	public void setSoundTime(int x){
		soundTime = x;
	}

	public void setImageRight(BufferedImage img){
		imgZombieRight = img;
	}

	public void setImageLeft(BufferedImage img){
		imgZombieLeft = img;
	}

	public void setAnimationDelay(int x){
		animationDelay = x;
	}


	public int getHealth(){
		return health;
	}

	public void setHealth(int h){
		health = h;
	}


	public int getMoveTime(){
		return moveTime;
	}

}