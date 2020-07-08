import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args)
	{
		JFrame mainWindow = new JFrame("Dungeon");
		mainWindow.setSize(817, 640);
		mainWindow.add(new DungeonPanel());
		mainWindow.setVisible(true);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}