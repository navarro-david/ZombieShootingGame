import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.Graphics2D;


public class PowerUp extends Actor{

	int value;
	BufferedImage imgPowerUp;

	public PowerUp()  {

		super(850, (int)(Math.random()*50) + 300, 25, 25, -1, 1, Color.PINK);

		if((int)(Math.random() * 2) == 1)
		{
			getHitBox().setLocation(-50, (int)(Math.random()*50) + 300);



		}
		value = 50;

		imgPowerUp = null;

  			try {
		imgPowerUp = ImageIO.read(new File("images/powerUp_crap.png"));

			} catch (IOException e) {
		System.out.println(e);
		}


	}

	public void drawActor(Graphics2D win){

		win.drawImage(imgPowerUp,getXPos(),getYPos(),null);
	}

	public void setImage(BufferedImage img){
		imgPowerUp = img;
	}

	public int getValue(){
		return value;
	}

	public void setValue(int v){
		value = v;
	}

	public void move(int x){
		getHitBox().translate(x,0);
	}
}