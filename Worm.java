import java.awt.Color;
import java.awt.Graphics;

public class Worm extends Boss
{
	int speed;
	WormSegment[] wormSegments;
	
	public Worm(int xPos, int yPos, int xVel, int yVel)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		speed = 10;
		width = 40;
		height = 40;
		wormSegments = new WormSegment[5];
		for(int i = 0; i < wormSegments.length; i++)
		{
			wormSegments[i] = new WormSegment(xPos + 5, yPos + 5, xVel, yVel);
		}
		health = 3;
		parts = (Entity[])wormSegments;
	}
	
	public void process(Player player)
	{
		for(int i = 0; i < 2; i++)
		{
			if(xPos > player.xPos && xVel - 1 >= -speed)
			{
				xVel--;
				if(Math.abs(xPos - player.xPos) > Math.abs(yPos - player.yPos) && xVel - 1 >= -speed)
				{
					xVel--;
				}
			}
			if(xPos < player.xPos && xVel + 1 <= speed)
			{
				xVel++;
				if(Math.abs(xPos - player.xPos) > Math.abs(yPos - player.yPos) && xVel + 1 <= speed)
				{
					xVel++;
				}
			}
			if(yPos > player.yPos && yVel - 1 >= -speed)
			{
				yVel--;
				if(Math.abs(xPos - player.xPos) < Math.abs(yPos - player.yPos) && yVel - 1 >= -speed)
				{
					yVel--;
				}
			}
			if(yPos < player.yPos && yVel + 1 <= speed)
			{
				yVel++;
				if(Math.abs(xPos - player.xPos) < Math.abs(yPos - player.yPos) && yVel + 1 <= speed)
				{
					yVel++;
				}
			}
		}
		
		if(xVel > 0)
		{
			xVel--;
		}
		if(yVel > 0)
		{
			yVel--;
		}
		if(xVel < 0)
		{
			xVel++;
		}
		if(yVel < 0)
		{
			yVel++;
		}
		xPos += xVel;
		yPos += yVel;
		wormSegments[0].newxVel = xVel;
		wormSegments[0].newyVel = yVel;
		for(int i = 1; i < wormSegments.length; i++)
		{
			wormSegments[i].newxVel = wormSegments[i - 1].xVel;
			wormSegments[i].newyVel = wormSegments[i - 1].yVel;
		}
		for(int i = 0; i < wormSegments.length; i++)
		{
			wormSegments[i].process();
		}
	}

	public void paint(Graphics g) 
	{
		for(int i = 0; i < wormSegments.length; i++)
		{
			wormSegments[i].paint(g, health);
		}
		if(health == 3)
		{
			g.setColor(new Color(0, 255, 200));
		}
		if(health == 2)
		{
			g.setColor(new Color(255, 255, 200));
		}
		if(health == 1)
		{
			g.setColor(new Color(255, 0, 200));
		}
		g.fillOval(xPos, yPos, 40, 40);
	}
}
