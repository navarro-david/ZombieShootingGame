import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.Graphics2D;


public class PowerUpHealth extends PowerUp{

	public PowerUpHealth(){
		super();
		setValue(50);
		setColor(getColor().darker());

		BufferedImage imgPowerUp = null;

		String[] images = {"images/powerUp_crap.png","images/powerUp_food1.png","images/powerUp_food2.png","images/powerUp_food3.png","images/powerUp_food4.png","images/powerUp_food5.png"};

				try {
		imgPowerUp = ImageIO.read(new File(images[(int)(Math.random() * 5)]));

			} catch (IOException e) {
		System.out.println(e);
		}

		setImage(imgPowerUp);
	}

	public void drawActor(Graphics2D win){

		win.drawImage(imgPowerUp,getXPos(),getYPos(),null);
	}
}