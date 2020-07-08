import java.awt.Color;
import java.awt.Graphics;

public class Load extends Entity
{
	Color color;
	
	public Load(int xPos, int yPos, int width, int height)
	{
		this.xPos = xPos;
		this.yPos = yPos;
		this.width = width;
		this.height = height;
		color = Color.cyan;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(color);
		g.fillRect(xPos, yPos, width, height);
	}
}
