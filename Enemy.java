import java.awt.Color;
import java.awt.Graphics;

public class Enemy extends Entity
{
	int speed;
	Color color;
	boolean dead;
	
	public Enemy(int x, int y, int speed, Color color)
	{
		xPos = x * 50;
		yPos = y * 50;
		width = 45;
		height = 45;
		this.speed = speed;
		this.color = color;
		dead = false;
	}
	
	public void process(Player player)
	{
		xPos += xVel;
		yPos += yVel;
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
		for(int i = 0; i < 2; i++)
		{
			if(player.xPos > xPos && xVel + 1 <= speed)
			{
				xVel++;
			}
			if(player.xPos < xPos && xVel - 1 >= -speed)
			{
				xVel--;
			}
			if(player.yPos > yPos && yVel + 1 <= speed)
			{
				yVel++;
			}
			if(player.yPos < yPos && yVel - 1 >= -speed)
			{
				yVel--;
			}
		}
	}
	
	public void paint(Graphics g)
	{
		g.setColor(color);
		g.fillRect(xPos, yPos, width, height);
	}
}
