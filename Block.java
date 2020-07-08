import java.awt.Color;
import java.awt.Graphics;

public class Block 
{
	Color color;
	int x;
	int y;
	
	public Block(Color color, int x, int y)
	{
		this.color = color;
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g)
	{
		g.setColor(color);
		g.fillRect(x * 50, y * 50, 50, 50);
	}
}