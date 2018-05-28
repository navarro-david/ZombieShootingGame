public class Weapon{

	int damage;
	int ammo;
	int rangeX;
	int shotTimer;
	int shotTime;
	SoundDriver sd;

	public Weapon(){
		damage = 10;
		ammo = 500;
		rangeX = 9001;
		shotTimer = 0;
		shotTime = 10;
		String[] audio = {"audio/weapon/mp.wav"};
		sd = new SoundDriver(audio);

	}

	public int shoot(int monsterXPos, int playerXPos){
		shotTimer++;
		
		if(shotTimer < shotTime) return 0;
		shotTimer = 0;
                ammo--;

		if(ammo > 0) sd.play(0);
		if(ammo <= 0){
			ammo = 0;
			return 0;
		}else if(Math.abs(monsterXPos - playerXPos) < rangeX){
			//ammo--;
			return damage;

			}

		return 0;

	}

	public void reloadAmmo(int a){
		ammo = ammo + a;

	}

	public void setAmmo(int a){
		ammo = a;
	}

	public int getAmmo(){
		return ammo;
	}

	public void setDamage(int d){
		damage = d;
	}

	public void setRangeX(int x){
		rangeX = x;
	}

	public int getDamage(){
		return damage;
	}

	public int getRangeX(){
		return rangeX;
	}


}