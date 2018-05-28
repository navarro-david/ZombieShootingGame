import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.Color;
import java.util.Random;
import java.util.ArrayList;
import java.awt.geom.Line2D;
import java.awt.Rectangle;
import java.awt.Font;

public class GameCanvas extends GameDriver{

	static GameState gm;

	Hero player;
	Random rnd;
	Weapon gun;

	BufferedImage imgCrossHair;
	BufferedImage imgCrossHairRed;
	BufferedImage imgBG;
	BufferedImage imgSplash;
	BufferedImage imgGameOver;
	BufferedImage imgGui;
	BufferedImage imgHighScore;

	ArrayList<Monster> enemies;
	ArrayList<PowerUp> upgrades;
	Line2D.Double shot;

	int mouseX;
	int mouseY;
	int mouseClickedX;
	int mouseClickedY;
	int bgXPos;
	int moveSpeed;
	int killCount;
	int score;
	int spawnTime;
	int monsterTimer;
	int powerUpTimer;

	int displayResults = 0;

	Rectangle healthBar;

	boolean mousePressed;
	boolean haveAmmo;
	boolean titleScreenMusic;


	FileDriver fd;
	SoundDriver sd;


	public GameCanvas(){
		gm = GameState.TITLE_SCREEN;
		moveSpeed = 1;
		player = new Hero(400,300,50,50,moveSpeed,moveSpeed);
		gun = new Weapon();

		imgCrossHair = addImage("images/crosshair.png");
		imgCrossHairRed = addImage("images/crosshair_red.png");
		imgBG = addImage("images/background.png");
		imgSplash = addImage("images/ZombieApocalypseSplash.png");
		imgGameOver = addImage("images/ZombieApocalypseSplash_gameOver.png");
		imgGui = addImage("images/gui2.png");
		imgHighScore = addImage("images/ZombieApocalypseSplash_highScore.png");

		enemies = new ArrayList<Monster>();
		upgrades = new ArrayList<PowerUp>();
		shot = new Line2D.Double(player.getXPos(), player.getYPos(), mouseX, mouseY);

		mouseX = 400;
		mouseY = 300;
		mouseClickedX = 0;
		mouseClickedY = 0;
		bgXPos = (int)(Math.random() * -5000);

		spawnTime = 100;
		monsterTimer = 0;
		powerUpTimer = 0;
		killCount = 0;
		score = 0;

		healthBar = new Rectangle(10,30,player.getHealth(),20);

		mousePressed = false;
		haveAmmo = false;
		titleScreenMusic = true;


		String[] audio = {"audio/music/Kevin Macleod - SPAZZMATICA POLKA.wav","audio/music/The Whistler.wav","audio/music/ghosts.wav"};
		sd = new SoundDriver(audio);
		fd = new FileDriver();




	}
	public void mouseClicked(MouseEvent e){
  	}

	public void mouseMoved(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		mouseClickedX = player.getXPos();
		mouseClickedY = player.getYPos();
		mousePressed = false;
  	}

  	public void mousePressed(MouseEvent e){
  		mouseClickedX = e.getX();
		mouseClickedY = e.getY();
  		mousePressed = true;


  	}

  	public void mouseReleased(MouseEvent e){
  		mouseClickedX = player.getXPos();
		mouseClickedY = player.getYPos();
  		mousePressed = false;
  	}

  	public void mouseDragged(MouseEvent e){
  		mouseX = e.getX();
		mouseY = e.getY();
		mouseClickedX = e.getX();
		mouseClickedY = e.getY();
		mousePressed = true;
  	}
	/***************************************************************************************************************************************************/
	public void draw(Graphics2D win){
		if(titleScreenMusic){
			sd.loop(2);
			titleScreenMusic = false;
		}

		if(gm == GameState.TITLE_SCREEN){
				bgXPos -= 1;
			if (bgXPos <= -imgBG.getWidth()) {
				bgXPos = 0;
			}

			win.drawImage(imgBG, bgXPos, 0, null);
			win.drawImage(imgBG, bgXPos + imgBG.getWidth(), 0, null);
			win.drawImage(imgSplash,0,0,null);

			if(keys[11]){
				if((Math.random()*100) > 90){
					sd.loop(0);
				}else{
					sd.loop(1);
				}
				sd.stop(2);

				gm = GameState.GAME_SCREEN;
				bgXPos = (int)(Math.random() * -5000);
			}


		}
		else if(gm == GameState.GAME_SCREEN){
			drawAndMoveBG(win);
			shot.setLine(player.getXPos() + player.getHitBox().getWidth()/2, player.getYPos() + player.getHitBox().getHeight()/2, mouseClickedX, mouseClickedY);

			spawnPowerUps(win);
			spawnAndMoveMonsters(win);
			shootMonsters();
			hurtPlayer();
			win.setColor(Color.RED);

			player.move(keys);
			player.setDirection(mouseX);
			player.setMousePressed(mousePressed);
			player.setHaveAmmo(haveAmmo);
			player.drawActor(win);
			reset(win);



		}
		else if(gm == GameState.SCORE_SCREEN){
			setAndDisplayHighScore(win);
		}


	}
	/***************************************************************************************************************************************************/

	public void reset(Graphics2D win){
		if(mousePressed){
				win.drawImage(imgCrossHairRed, mouseX - (imgCrossHair.getWidth()/2), mouseY -  (imgCrossHair.getHeight()/2), null);
		}else{
				win.drawImage(imgCrossHair, mouseX - (imgCrossHair.getWidth()/2), mouseY -  (imgCrossHair.getHeight()/2), null);
		}
		if(!mousePressed && (keys[0]|| keys[1]|| keys[2] || keys[3])){
			mouseClickedX = player.getXPos();
			mouseClickedY = player.getYPos();
		}
		if(gun.getAmmo() <= 0){
			haveAmmo = false;
		}else{
			haveAmmo = true;
		}



		if(gun.getAmmo() < 0) gun.setAmmo(0);
		healthBar.setSize(player.getHealth(), 10);
		win.setColor(Color.RED);
		win.fill(healthBar);
		win.drawImage(imgGui,0,-1,null);
		win.setColor(Color.BLACK);
		win.drawString("Ammo: " + gun.getAmmo(), 15,29);
		win.drawString("Score: " + score, 670,40);
		difficultyIncrease();




	}
	public void resetGame(){
		if(!keys[11]) return;
		gm = GameState.GAME_SCREEN;

		enemies = null;
		upgrades = null;
		shot = null;
		healthBar = null;
		gun = null;

		enemies = new ArrayList<Monster>();
		upgrades = new ArrayList<PowerUp>();
		shot = new Line2D.Double(player.getXPos(), player.getYPos(), mouseX, mouseY);
		player.setHealth(300);
		gun = new Weapon();



		bgXPos = (int)(Math.random() * -5000);

		spawnTime = 100;
		monsterTimer = 0;
		powerUpTimer = 0;
		killCount = 0;
		score = 0;
		displayResults = 0;

		healthBar = new Rectangle(10,30,player.getHealth(),20);

		mousePressed = false;

	}

	public void setAndDisplayHighScore(Graphics2D win){


		if(displayResults == 0){

			win.drawImage(imgBG, bgXPos, 0, null);
			win.drawImage(imgGameOver, 0,0, null);
			String[] scores = fd.getStringArray("highScores.txt");
			System.out.println(scores[0]);

			int topScore = Integer.parseInt(scores[0]);

			win.setFont(new Font(Font.SERIF,0,20));

			win.setColor(Color.WHITE);

			if(score >= topScore){
				win.drawImage(imgHighScore,0,0,null);
				scores[0] = "" + score;
			}

			win.drawString("" + scores[0],405,240);
			win.drawString("" + score,405,260);

			fd.addToFile("highScores.txt",scores);
			displayResults = 1;
		}

		if(keys[11]){

			displayResults = 0;
			resetGame();
			//gm = GameState.TITLE_SCREEN;
			//System.out.println("SPACE PRESSED");
		}


	}

	public void hurtPlayer(){
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getHitBox().intersects(player.getHitBox())){
				player.hurt();
			}
		}
		if(player.getHealth() <= 0) gm = GameState.SCORE_SCREEN;
	}




  	public void spawnAndMoveMonsters(Graphics2D win){
  		monsterTimer++;
  		if(monsterTimer > spawnTime && enemies.size() < 10){
  			score+= 10;
  			monsterTimer = 0;
  			Monster enemy = new Monster();
  			int type = (int)(Math.random() * 100);

  			enemies.add(whichMonsterType(type));
  		} for(int i = 0; i < enemies.size(); i++){

			if (keys[2] && player.getXPos() < 220 && bgXPos < 0) {
				enemies.get(i).getHitBox().setLocation(enemies.get(i).getXPos() + moveSpeed,enemies.get(i).getYPos());
			}if (keys[3] && player.getXPos() > 515 && bgXPos > -(imgBG.getWidth() - 800)) {
				enemies.get(i).getHitBox().setLocation(enemies.get(i).getXPos() - moveSpeed,enemies.get(i).getYPos());
			}
  			enemies.get(i).move(player.getXPos(), player.getYPos(),enemies.get(i).getRightSpawned());
  			enemies.get(i).setDirection(player.getXPos());
  			enemies.get(i).drawActor(win);

  			if(enemies.get(i).getXVel() > 0 && enemies.get(i).getXPos() < -150){
  				enemies.remove(i);
  				i--;
  			}else if(enemies.get(i).getXVel() < 0 && enemies.get(i).getXPos() > 950){
  				enemies.remove(i);
  				i--;
  			}
  		}

  	}

  	public Monster whichMonsterType(int type){
  		Monster mon = new Monster();
  		if(type < 40){
  				mon = new Zombie();
  		}else if(type < 60 ){
  				mon = new ZombieFast();
  		}
  		else if(type < 80){
  				mon = new ZombieFat();
  		}
  		return mon;
  	}

  	public void shootMonsters(){
  		if(!mousePressed) return;
  		ArrayList<Monster> targets = new ArrayList<Monster>();


  		for(int i = 0; i < enemies.size(); i++){
  			if(shot.intersects(enemies.get(i).getHitBox())){
				targets.add(enemies.get(i));

  			}

  		}
  		if(targets.size() == 0){
  			return;
  		}
  		Monster closest = targets.get(0);
  		double dist = getDistance(player.getXPos(), player.getYPos(), closest.getXPos(), closest.getYPos());;
  		for(int i = 1; i < targets.size(); i++){
  			double mon = getDistance(player.getXPos(), player.getYPos(), targets.get(i).getXPos(), targets.get(i).getYPos());
  			if(dist > mon){
  				closest = targets.get(i);
  				dist = mon;
  			}
  		}
  		hurtAndKillMonster(closest);
  	}

  	public void hurtAndKillMonster(Monster target){

  		target.hurt(gun.shoot(target.getXPos(), player.getYPos()));

  		for(int i = 0; i < enemies.size(); i++){
  			if(enemies.get(i).getHealth() < 0){
  				enemies.remove(i);
  				killCount++;
  				score+=100;

  				i--;
  			}
  		}
  	}

  	public void  spawnPowerUps(Graphics2D win){
  		powerUpTimer++;
  		if(powerUpTimer > spawnTime * 5 && upgrades.size() < 5){
  			powerUpTimer = 0;
  			upgrades.add(whichPowerUpType((int)(Math.random() * 100)));

		}
		if(upgrades.size() > 5){
			upgrades.remove(0);
		}
		for(int i = 0; i < upgrades.size(); i++){
			if (keys[2] && player.getXPos() < 220 && bgXPos < 0) {
			upgrades.get(i).move(moveSpeed);

			}if (keys[3] && player.getXPos() > 515 && bgXPos > -(imgBG.getWidth() - 800)) {
			upgrades.get(i).move(-moveSpeed);
			}
			upgrades.get(i).drawActor(win);
			if(upgrades.get(i).getHitBox().intersects(player.getHitBox())){

				score+=50;

				if(upgrades.get(i) instanceof PowerUpAmmo){
					gun.reloadAmmo(upgrades.get(i).getValue());

				}else{
					player.healHealth(upgrades.get(i).getValue());

				}

				upgrades.remove(i);
				i--;

			}

		}

  	}

  	public PowerUp whichPowerUpType(int type){
  		PowerUp upgrade = new PowerUp();
  		if(type < 25){
  			upgrade = new PowerUpHealth();
  		}else{
  			upgrade = new PowerUpAmmo();
  		}
  		return upgrade;
  	}

	public void drawAndMoveBG(Graphics2D win){
		if (keys[2] && player.getXPos() < 220 && bgXPos < 0) {
			bgXPos+=moveSpeed;

		}if (keys[3] && player.getXPos() > 515 && bgXPos > -(imgBG.getWidth() - 800)) {
			bgXPos-=moveSpeed;
		}
		win.drawImage(imgBG, bgXPos, 0, null);
	}

	public void difficultyIncrease(){
		if(killCount == 60){
			spawnTime = 40;
			//System.out.println("Difficulty Changed to Spawn Time " + spawnTime);
		}
		else if(killCount == 40){
			spawnTime = 60;
			//System.out.println("Difficulty Changed to Spawn Time " + spawnTime);
		}
		else if(killCount == 20){
			spawnTime = 80;
			//System.out.println("Difficulty Changed to Spawn Time " + spawnTime);
		}
	}

	public double getDistance(int x1, int y1, int x2, int y2){
		return Math.sqrt((x2 - x1)*(x2 - x1) + (y2 - y1)*(y2 - y1));
	}


	}

