import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.io.*;
import javax.imageio.*;

public class Hero extends Actor {

  BufferedImage playerTopRight,playerTopLeft,playerBottomRight,playerBottomLeft;
  int health, hurtTimer, moveTimer, frameStageTop, frameStageBottom, animationDelay, animationTimer;
  boolean facingRight, isMoving, mousePressed, haveAmmo;
  SoundDriver sd;

public Hero(int x, int y, int w, int h, int xv, int yv)  {
	super(x, y, w, h, xv, yv, Color.BLUE);
	health = 300;
	hurtTimer = 0;
	moveTimer = 0;
	frameStageTop = 1;
	frameStageBottom = 1;
	animationDelay = 10;
	animationTimer = 0;
	String[] audio = {"audio/hurt.wav"};
	sd = new SoundDriver(audio);

	isMoving = false;
	facingRight = false;
	haveAmmo = false;

	playerTopRight = null;
	playerTopLeft = null;
	playerBottomRight = null;
	playerBottomLeft = null;

  	try {
		playerTopRight = ImageIO.read(new File("images/zombie_game_Sprite_player_right.png"));

	} catch (IOException e) {
		System.out.println(e);
	}
	try {
		playerTopLeft = ImageIO.read(new File("images/zombie_game_Sprite_player_left.png"));

	} catch (IOException e) {
		System.out.println(e);
	}
	try {
		playerBottomRight = ImageIO.read(new File("images/zombie_game_Sprite_player_bottom_right.png"));

	} catch (IOException e) {
		System.out.println(e);
	}
	try {
		playerBottomLeft = ImageIO.read(new File("images/zombie_game_Sprite_player_bottom_left.png"));

	} catch (IOException e) {
		System.out.println(e);
	}

}

public void move(boolean[] keys) {
	moveTimer++;
	if(moveTimer < 1) return;
	moveTimer = 0;
	if(keys[0] && getYPos() > 250) {
		hitBox.translate(0, -yvel);
	}if (keys[1]  && getYPos() < 350) {
		hitBox.translate(0, yvel);
	}if (keys[2] && getXPos() > 210) {
		hitBox.translate(-xvel, 0);
	}if (keys[3] && getXPos() < 525) {
		hitBox.translate(xvel, 0);
	}
	isMoving = true;
	if(!(keys[0]|| keys[1]|| keys[2] || keys[3])){
		isMoving = false;
	}


}

public void hurt(){
		hurtTimer++;
		setColor(col);
		if(hurtTimer > 50){
			hurtTimer = 0;
			health-=5;
			sd.play(0);
		}
		if(health <= 0){
			health = 0;
		}

	}



public void drawActor(Graphics2D win){
	animationTimer++;
	if(animationTimer > animationDelay){
		frameStageTop++;
		frameStageBottom++;
		animationTimer = 0;
	}
	BufferedImage frameTop = null;
	BufferedImage frameBottom = null;

	if(mousePressed && haveAmmo){
		if(frameStageTop >= 4){
			frameStageTop = 1;
		}
		if(facingRight){
			frameTop = playerTopRight.getSubimage(0,(10*59) + (frameStageTop*59),playerTopRight.getWidth(),59);
		}else{
			frameTop = playerTopLeft.getSubimage(0,(10*59) + (frameStageTop*59),playerTopLeft.getWidth(),59);
		}
	}else{
		if(frameStageTop >= 5){
			frameStageTop = 1;
		}

		if(facingRight){
			frameTop = playerTopRight.getSubimage(0,(frameStageTop*59),playerTopRight.getWidth(),59);
		}else{
			frameTop = playerTopLeft.getSubimage(0,(frameStageTop*59),playerTopLeft.getWidth(),59);
		}
	}
	if(!isMoving){
		if(frameStageTop >= 3){
			frameStageBottom = 1;
		}
		if(frameStageTop*27 > playerBottomRight.getHeight()){
			frameStageBottom = 1;
		}
		if(facingRight){
			frameBottom = playerBottomRight.getSubimage(0,frameStageBottom*27 ,playerBottomRight.getWidth(),27);
		}else{
			frameBottom = playerBottomLeft.getSubimage(0,frameStageBottom*27 ,playerBottomRight.getWidth(),27);
		}
	}else{
		if(frameStageBottom >= 20){

			frameStageBottom = 1;
		}
		if(facingRight){
			frameBottom = playerBottomRight.getSubimage(0, (3*27) + frameStageBottom*27, playerBottomRight.getWidth(), 27);
		}else{
			frameBottom = playerBottomLeft.getSubimage(0,(3*27) + frameStageBottom*27, playerBottomLeft.getWidth(), 27);
		}

	}
	win.drawImage(frameBottom,getXPos(),getYPos()+frameBottom.getHeight() + 10,null);

	if(!facingRight){
		win.drawImage(frameTop,getXPos() - frameTop.getWidth()/2,getYPos(),null);
	}else{
		win.drawImage(frameTop,getXPos(),getYPos(),null);
	}


}

public void setDirection(int x){
	if(x < getXPos()){
		facingRight = false;
	}else{
		facingRight = true;
	}
}
public void setMousePressed(boolean b){
	mousePressed = b;
}

public void setHaveAmmo(boolean b){
	haveAmmo = b;
}

public int getHealth(){
	return health;
}

public void healHealth(int h){
	health = health + h;
	if(health > 300){
		health = 300;
	}
}

public void setHealth(int h){
	health = h;
}




}