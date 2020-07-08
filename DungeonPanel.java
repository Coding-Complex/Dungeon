import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DungeonPanel extends JPanel implements KeyListener
{
	protected Level level;
	
	public DungeonPanel()
	{
		level = new Level();
		ActionListener actionListener = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				repaint();
				level.process();
			}
		};
		Timer timer = new Timer(35, actionListener);
		timer.start();
		addKeyListener(this);
		setFocusable(true);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		level.paint(g);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		
	}
	
	public void keyTyped(KeyEvent e) 
	{
		
	}
	
	public void keyPressed(KeyEvent e) 
	{
		level.move(e.getKeyCode());
	}
	
	public void keyReleased(KeyEvent e) 
	{
		level.release(e.getKeyCode());
	}
	
}
