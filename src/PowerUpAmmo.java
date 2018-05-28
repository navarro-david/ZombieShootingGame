import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import java.awt.Graphics2D;

public class PowerUpAmmo extends PowerUp{



	public PowerUpAmmo(){
		super();
		setValue(150);

		BufferedImage imgPowerUp = null;

				try {
		imgPowerUp = ImageIO.read(new File("images/powerUp_Ammo.png"));

			} catch (IOException e) {
		System.out.println(e);
		}

		setImage(imgPowerUp);
	}

	public void drawActor(Graphics2D win){

		win.drawImage(imgPowerUp,getXPos(),getYPos(),null);
	}
}