import java.awt.Color;
import java.awt.Graphics;

public class WormSegment extends Entity
{
	int newxVel;
	int newyVel;
	
	public WormSegment(int xPos, int yPos, int xVel, int yVel)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		this.newxVel = xVel;
		this.newyVel = yVel;
		width = 30;
		height = 30;
	}
	
	public void process()
	{
		xPos += xVel;
		yPos += yVel;
		xVel = newxVel;
		yVel = newyVel;
	}

	public void paint(Graphics g, int health) 
	{
		if(health == 3)
		{
			g.setColor(new Color(0, 255, 0));
		}
		if(health == 2)
		{
			g.setColor(new Color(255, 255, 0));
		}
		if(health == 1)
		{
			g.setColor(new Color(255, 0, 0));
		}
		g.fillOval(xPos, yPos, 30, 30);
	}
}
