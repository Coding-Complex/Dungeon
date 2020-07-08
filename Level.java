import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;

public class Level 
{
	Player player;
	Block[][] blocks;
	LinkedList<Enemy> enemies;
	boolean start;
	Load load;
	int side;
	boolean win;
	int score;
	Boss boss;
	
	public Level()
	{
		player = new Player(0, 0, 0, 0);
		blocks = new Block[16][12];
		enemies = new LinkedList<Enemy>();
		load = new Load(0, 0, 0, 0);
		win = false;
		side = 0;
		score = 0;
		boss = null;
	}
	
	public void process()
	{
		if(start)
		{
			if(player.dead)
			{
				start = false;
				score = 0;
			}
			if(player.invincible > 0)
			{
				player.invincible--;
			}
			player.process();
			
			//enemy collision
			for(int i = 0; i < enemies.size(); i++)
			{
				if(enemies.get(i).dead == false)
				{
					enemies.get(i).process(player);
					if(blockCollisionX(enemies.get(i)))
					{
						if(enemies.get(i).xVel > 0)
						{
							enemies.get(i).xVel = 0;
							while(!blockCollisionX(enemies.get(i)))
							{
								enemies.get(i).xPos++;
							}
							enemies.get(i).xPos--;
						}
						if(enemies.get(i).xVel < 0)
						{
							enemies.get(i).xVel = 0;
							while(!blockCollisionX(enemies.get(i)))
							{
								enemies.get(i).xPos--;
							}
							enemies.get(i).xPos++;
						}
					}
					
					if(blockCollisionY(enemies.get(i)))
					{
						if(enemies.get(i).yVel > 0)
						{
							enemies.get(i).yVel = 0;
							while(!blockCollisionY(enemies.get(i)))
							{
								enemies.get(i).yPos++;
							}
							enemies.get(i).yPos--;
						}
						if(enemies.get(i).yVel < 0)
						{
							enemies.get(i).yVel = 0;
							while(!blockCollisionY(enemies.get(i)))
							{
								enemies.get(i).yPos--;
							}
							enemies.get(i).yPos++;
						}
					}
				}
			}
			for(int i = 0; i < player.shots.length; i++)
			{
				if(player.shots[i] != null && blockCollisionX(player.shots[i]))
				{
					player.shots[i] = null;
				}
				if(player.shots[i] != null && blockCollisionY(player.shots[i]))
				{
					player.shots[i] = null;
				}
				for(int x = 0; x < enemies.size(); x++)
				{
					if(player.shots[i] != null && enemies.get(x).dead == false && collision(player.shots[i], enemies.get(x)))
					{
						enemies.get(x).dead = true;
					}
				}
				if(player.shots[i] != null && player.shots[i].timer == 0)
				{
					player.shots[i] = null;
				}
			}
			
			if(blockCollisionX(player))
			{
				if(player.xVel > 0)
				{
					player.xVel = 0;
					while(!blockCollisionX(player))
					{
						player.xPos++;
					}
					player.xPos--;
				}
				if(player.xVel < 0)
				{
					player.xVel = 0;
					while(!blockCollisionX(player))
					{
						player.xPos--;
					}
					player.xPos++;
				}
			}
			
			if(blockCollisionY(player))
			{
				if(player.yVel > 0)
				{
					player.yVel = 0;
					while(!blockCollisionY(player))
					{
						player.yPos++;
					}
					player.yPos--;
				}
				if(player.yVel < 0)
				{
					player.yVel = 0;
					while(!blockCollisionY(player))
					{
						player.yPos--;
					}
					player.yPos++;
				}
			}
			
			for(int i = 0; i < enemies.size(); i++)
			{
				if(enemies.get(i).dead == false && collision(enemies.get(i), player))
				{
					player.hurt();
				}
			}
			
			if(boss != null)
			{
				boss.process(player);
				for(int i = 0; i < player.shots.length; i++)
				{
					if(player.shots[i] != null && collision(boss, player.shots[i]))
					{
						boss.health--;
						player.shots[i] = null;
					}
					if(collision(boss, player))
					{
						player.hurt();
					}
					for(int x = 0; x < boss.parts.length; x++)
					{
						if(player.shots[i] != null && collision(boss.parts[x], player.shots[i]))
						{
							boss.health--;
							player.shots[i] = null;
						}
						if(collision(boss.parts[x], player))
						{
							player.hurt();
						}
					}
				}
				if(boss.health == 0)
				{
					boss = null;
				}
			}
			
			win = true;
			for(int i = 0; i < enemies.size(); i++)
			{
				if(!enemies.get(i).dead)
				{
					win = false;
				}
			}
			if(boss != null)
			{
				win = false;
			}
			if(win && collision(player, load))
			{
				if(score + 1 != (int)((score + 1) / 10) * 10)
				{
					genRandom();
				}
				else
				{
					genBoss();
				}
				score++;
				if(player.health < player.maxHealth)
				{
					player.health++;
				}
			}
			player.xPos += player.xVel;
			player.yPos += player.yVel;
		}
	}
	
	public boolean collision(Entity one, Entity two)
	{
		if(one.xPos + one.width < two.xPos || one.xPos > two.xPos + two.width || one.yPos + one.height < two.yPos || one.yPos > two.yPos + two.height)
		{
			return false;
		}
		return true;
	}
	
	public boolean blockCollisionX(Entity entity)
	{
		if(entity.xPos + entity.xVel < 0 || entity.xPos + entity.xVel + entity.width >= 800 || entity.yPos < 0 || entity.yPos + entity.height >= 600)
		{
			return true;
		}
		if(blocks[(entity.xPos + entity.xVel + entity.width) / 50][(entity.yPos + entity.height) / 50] != null)
		{
			return true;
		}
		if(blocks[(entity.xPos + entity.xVel) / 50][(entity.yPos + entity.height) / 50] != null)
		{
			return true;
		}
		if(blocks[(entity.xPos + entity.xVel + entity.width) / 50][(entity.yPos) / 50] != null)
		{
			return true;
		}
		if(blocks[(entity.xPos + entity.xVel) / 50][(entity.yPos) / 50] != null)
		{
			return true;
		}
		return false;
	}
	
	public boolean blockCollisionY(Entity entity)
	{
		if(entity.xPos < 0 || entity.xPos+ entity.width >= 800 || entity.yPos + entity.yVel < 0 || entity.yPos + entity.yVel + entity.height >= 600)
		{
			return true;
		}
		if(blocks[(entity.xPos + entity.width) / 50][(entity.yPos + entity.yVel + entity.height) / 50] != null)
		{
			return true;
		}
		if(blocks[(entity.xPos) / 50][(entity.yPos + entity.yVel + entity.height) / 50] != null)
		{
			return true;
		}
		if(blocks[(entity.xPos + entity.width) / 50][(entity.yPos + entity.yVel) / 50] != null)
		{
			return true;
		}
		if(blocks[(entity.xPos) / 50][(entity.yPos + entity.yVel) / 50] != null)
		{
			return true;
		}
		return false;
	}
	
	public void move(int move)
	{
		player.move(move);
		if(move == 32 && !start)
		{
			start = true;
			player = new Player(377, 0, 0, 0);
			genRandom();
		}
	}
	
	public void release(int release)
	{
		player.release(release);
	}

	public void paint(Graphics g)
	{
		if(start)
		{
			player.paint(g);
			
			if(boss != null)
			{
				boss.paint(g);
			}
			
			g.setColor(Color.black);
			g.setFont(new Font("bruh", 10, 20));
			g.drawString(("Score: " + score), 10, 20);
			for(int i = 0; i < enemies.size(); i++)
			{
				if(!enemies.get(i).dead)
				{
					enemies.get(i).paint(g);
				}
			}
			for(int x = 0; x < blocks.length; x++)
			{
				for(int y = 0; y < blocks[0].length; y++)
				{
					if(blocks[x][y] != null)
					{
						blocks[x][y].paint(g);
					}
				}
			}
			if(win)
			{
				load.paint(g);
			}
		}
		else
		{
			g.setFont(new Font("bruh", 10, 45));
			g.drawString("Press the Space Bar to Start", 100, 100);
		}
	}
	
	public void genBoss()
	{
		load.xPos = 358;
		load.yPos = 0;
		load.width = 100;
		load.height = 10;
		
		player.yPos = 600 - player.height;
		player.xPos = 400 - (player.width / 2);
		
		int bosses = 1;
		int result = (int) (Math.random() * bosses);
		
		if(result == 0)
		{
			bossOne();
		}
	}
	
	public void bossOne()
	{
		blocks = new Block[16][12];
		player.shots = new Shot[3];
		boss = new Worm(380, 280, 0, 0);
	}
	
	public void genRandom()
	{
		int levels = 3;
		int result = (int) (Math.random() * levels);
		int lastSide = side;
		boss = null;
		while(lastSide == side)
		{
			side = (int) (Math.random() * 4);
		}
		if(side == 0)
		{
			load.xPos = 0;
			load.yPos = 270;
			load.width = 10;
			load.height = 100;
		}
		if(side == 1)
		{
			load.xPos = 358;
			load.yPos = 0;
			load.width = 100;
			load.height = 10;
		}
		if(side == 2)
		{
			load.xPos = 790;
			load.yPos = 270;
			load.width = 10;
			load.height = 100;
		}
		if(side == 3)
		{
			load.xPos = 358;
			load.yPos = 590;
			load.width = 100;
			load.height = 10;
		}
		if(lastSide == 0)
		{
			player.xPos = 799 - player.width;
			player.yPos = 300 - (player.height / 2);
		}
		if(lastSide == 1)
		{
			player.yPos = 599 - player.height;
			player.xPos = 400 - (player.width / 2);
		}
		if(lastSide == 2)
		{
			player.xPos = 1;
			player.yPos = 300 - (player.height / 2);
		}
		if(lastSide == 3)
		{
			player.yPos = 1;
			player.xPos = 400 - (player.width / 2);
		}
		win = false;
		
		//result = -1;// testing
		if(result == -1)
		{
			genZero();
		}
		if(result == 0)
		{
			genOne(lastSide);
		}
		if(result == 1)
		{
			genTwo(lastSide);
		}
		if(result == 2)
		{
			genThree(lastSide);
		}
	}
	
	public void genZero()
	{
		blocks = new Block[16][12];
		player.shots = new Shot[3];
		player.xPos = 400 - (player.width / 2);
		player.yPos = 599 - player.height;
		for(int x = 0; x < blocks.length; x++)
		{
			for(int y = 0; y < blocks[0].length; y++)
			{
				if((int) (Math.random() * 5) == 0)
				{
					blocks[x][y] = new Block(Color.gray, x, y);
				}
			}
		}
	}
	
	public void genOne(int side)
	{
		blocks = new Block[16][12];
		player.shots = new Shot[3];
		for(int i = 2; i < blocks[0].length - 2; i++)
		{
			blocks[3][i] = new Block(Color.gray, 3, i);
		}
		for(int i = 2; i < blocks[0].length - 2; i++)
		{
			blocks[12][i] = new Block(Color.gray, 12, i);
		}
		enemies = new LinkedList<Enemy>();
		regEnemySpawn(side);
	}
	
	public void genTwo(int side)
	{
		blocks = new Block[16][12];
		player.shots = new Shot[3];
		for(int x = 5; x < 11; x++)
		{
			for(int y = 3; y < 9; y++)
			{
				blocks[x][y] = new Block(Color.gray, x, y);
			}
		}
		enemies = new LinkedList<Enemy>();
		regEnemySpawn(side);
	}
	
	public void genThree(int side)
	{
		blocks = new Block[16][12];
		player.shots = new Shot[3];
		for(int i = 1; i < blocks[0].length - 1; i += 2)
		{
			blocks[2][i] = new Block(Color.gray, 2, i);
		}
		for(int i = 1; i < blocks[0].length - 1; i += 2)
		{
			blocks[5][i] = new Block(Color.gray, 5, i);
		}
		for(int i = 1; i < blocks[0].length - 1; i += 2)
		{
			blocks[10][i] = new Block(Color.gray, 10, i);
		}
		for(int i = 1; i < blocks[0].length - 1; i += 2)
		{
			blocks[13][i] = new Block(Color.gray, 13, i);
		}
		enemies = new LinkedList<Enemy>();
		regEnemySpawn(side);
	}
	
	public void regEnemySpawn(int side)
	{
		if(side == 0)
		{
			enemies.add(new Enemy(0, 5, 9, Color.green));
			enemies.add(new Enemy(0, 11, 9, Color.green));
			enemies.add(new Enemy(0, 0, 9, Color.green));
		}
		if(side == 1)
		{
			enemies.add(new Enemy(7, 0, 9, Color.green));
			enemies.add(new Enemy(15, 0, 9, Color.green));
			enemies.add(new Enemy(0, 0, 9, Color.green));
		}
		if(side == 2)
		{
			enemies.add(new Enemy(15, 5, 9, Color.green));
			enemies.add(new Enemy(15, 11, 9, Color.green));
			enemies.add(new Enemy(15, 0, 9, Color.green));
		}
		if(side == 3)
		{
			enemies.add(new Enemy(7, 11, 9, Color.green));
			enemies.add(new Enemy(15, 11, 9, Color.green));
			enemies.add(new Enemy(0, 11, 9, Color.green));
		}
	}
}
