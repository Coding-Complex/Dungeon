import java.awt.Color;
import java.awt.Graphics;

public class Shot extends Entity
{
	int timer;
	
	public Shot(int xPos, int yPos, int xVel, int yVel)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.xVel = xVel;
		this.yVel = yVel;
		width = 20;
		height = 20;
		timer = 30;
	}
	
	public void process()
	{
		xPos += xVel;
		yPos += yVel;
		timer--;
	}
	
	public void paint(Graphics g) 
	{
		g.setColor(Color.black);
		g.fillOval(xPos, yPos, width, height);
	}
}
