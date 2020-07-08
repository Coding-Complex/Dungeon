import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Player extends Entity
{
	int speed;
	boolean leftPressed;
	boolean upPressed;
	boolean rightPressed;
	boolean downPressed;
	Shot[] shots;
	boolean dead;
	int maxHealth;
	int health;
	int invincible;
	
	public Player(int xPos, int yPos, int xVel, int yVel)
	{
		this.speed = 12;
		width = 45;
		height = 45;
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		shots = new Shot[3];
		dead = false;
		maxHealth = 3;
		health = maxHealth;
		invincible = 0;
	}
	
	public void hurt()
	{
		if(invincible == 0)
		{
			health--;
			invincible = 15;
			if(health == 0)
			{
				dead = true;
			}
		}
	}
	
	public void process()
	{
		for(int i = 0; i < shots.length; i++)
		{
			if(shots[i] != null)
			{
				shots[i].process();
				if(shots[i].timer == 0)
				{
					shots[i] = null;
				}
			}
		}
		if(xVel > 0)
		{
			xVel--;
		}
		if(xVel < 0)
		{
			xVel++;
		}
		if(yVel > 0)
		{
			yVel--;
		}
		if(yVel < 0)
		{
			yVel++;
		}
		for(int i = 4; i > 0; i--)
		{
			if(leftPressed && xVel - 1 >= -speed)
			{
				xVel -= 1;
			}
			if(upPressed && yVel - 1 >= -speed)
			{
				yVel -= 1;
			}
			if(rightPressed && xVel + 1 <= speed)
			{
				xVel++;
			}
			if(downPressed && yVel + 1 <= speed)
			{
				yVel++;
			}
		}
	}
	
	public void move(int move)
	{
		if(move == 65)
		{
			leftPressed = true;
		}
		if(move == 87)
		{
			upPressed = true;
		}
		if(move == 68)
		{
			rightPressed = true;
		}
		if(move == 83)
		{
			downPressed = true;
		}
		if(move == 37)
		{
			int index = -1;
			for(int i = 0; i < shots.length; i++)
			{
				if(shots[i] == null)
				{
					index = i;
				}
			}
			if(index != -1)
			{
				shots[index] = new Shot(xPos + 15, yPos + 15, xVel - 20, yVel);
			}
		}
		if(move == 38)
		{
			int index = -1;
			for(int i = 0; i < shots.length; i++)
			{
				if(shots[i] == null)
				{
					index = i;
				}
			}
			if(index != -1)
			{
				shots[index] = new Shot(xPos + 15, yPos + 15, xVel, yVel - 20);
			}
		}
		if(move == 39)
		{
			int index = -1;
			for(int i = 0; i < shots.length; i++)
			{
				if(shots[i] == null)
				{
					index = i;
				}
			}
			if(index != -1)
			{
				shots[index] = new Shot(xPos + 15, yPos + 15, xVel + 20, yVel);
			}
		}
		if(move == 40)
		{
			int index = -1;
			for(int i = 0; i < shots.length; i++)
			{
				if(shots[i] == null)
				{
					index = i;
				}
			}
			if(index != -1)
			{
				shots[index] = new Shot(xPos + 15, yPos + 15, xVel, yVel + 20);
			}
		}
	}
	
	public void release(int release)
	{
		if(release == 65)
		{
			leftPressed = false;
		}
		if(release == 87)
		{
			upPressed = false;
		}
		if(release == 68)
		{
			rightPressed = false;
		}
		if(release == 83)
		{
			downPressed = false;
		}
	}

	public void paint(Graphics g) 
	{
		g.setColor(Color.black);
		if(invincible % 3 == 0)
		{
			g.fillRect(xPos, yPos, width, height);
		}
		g.setColor(Color.red);
		g.setFont(new Font("bruh", 10, 45));
		g.drawString(String.valueOf(health), xPos + 10, yPos + height - 5);
		for(int i = 0; i < shots.length; i++)
		{
			if(shots[i] != null)
			{
				shots[i].paint(g);
			}
		}
	}
}
