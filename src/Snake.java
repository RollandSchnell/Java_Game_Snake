
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Snake implements ActionListener, KeyListener {


	public static Snake snake;

	public JFrame frame;

	public RenderPanel renderPanel;

	public Timer timer = new Timer(20, this);

	public ArrayList<Point> snakeParts = new ArrayList<Point>();

	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

	public int clock = 0, direction = DOWN, score, tailLength = 20, time;

	public Point head, bonus;

	public Random random;

	public boolean over = false, paused;

	public Dimension dim;
	
	int x = 100;
	
	public int timerdivider = 10;
	
	public Snake() {
	
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame = new JFrame("Snake");
		frame.setVisible(true);
		frame.setSize(805, 700);
		frame.setResizable(false);
		frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2 - frame.getHeight() / 2);
		frame.add(renderPanel = new RenderPanel());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(this);
		startGame();
	}

	public void startGame() {
	
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 3;
		clock = 10;
		direction = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		bonus = new Point(random.nextInt(79), random.nextInt(66));
		timer.start();
	}
	

	public int increaseSpeed(int x) {
		
		
		if(x <= 10)
		{
		return 10;
		} else return x;
	}
	
	public void setSpeed() {
		
		try {
			Thread.sleep(increaseSpeed(x));
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public void timerdividerSet() {
		
		if(x == 90) {
			timerdivider = 11;
		} else if(x == 80) {
			timerdivider = 13;
		} 
		if(x == 70) {
			timerdivider = 16;
		} else if(x == 60) {
			timerdivider = 20;
		} 
		if(x == 50) {
			timerdivider = 25;
		} else if(x == 40) {
			timerdivider = 31;
		} 
		if(x == 30) {
			timerdivider = 38;
		} else if(x == 20) {
			timerdivider = 46;
		} 
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		renderPanel.repaint();
		
		setSpeed();
		timerdividerSet();
	if (head != null && !over && !paused)
		{
		
			time++;

			snakeParts.add(new Point(head.x, head.y));

			
				
			if (direction == UP)
			{
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1))
				{
					head = new Point(head.x, head.y - 1);
				}
				else
				{
					over = true;

				}
			}

			if (direction == DOWN)
			{
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1))
				{
					head = new Point(head.x, head.y + 1);
				}
				else
				{
					over = true;
				}
			}

			if (direction == LEFT)
			{
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y))
				{
					head = new Point(head.x - 1, head.y);
				}
				else
				{
					over = true;
				}
			}

			if (direction == RIGHT)
			{
				if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y))
				{
					head = new Point(head.x + 1, head.y);
				}
				else
				{
					over = true;
				}
			}

			if (snakeParts.size() > tailLength)
			{
				snakeParts.remove(0);

			}

			if (bonus != null)
			{
				
				if (head.equals(bonus))
				{
					x-=10;
					score += 10;
					tailLength++;
					bonus.setLocation(random.nextInt(79), random.nextInt(66));
					
				}
			}
		}
	}

	public boolean noTailAt(int x, int y) {
	
		for (Point point : snakeParts)
		{
			if (point.equals(new Point(x, y)))
			{
				return false;
			}
		}
		return true;
	}

	

	@Override
	public void keyPressed(KeyEvent e) {
	
		int i = e.getKeyCode();

		if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT)
		{
			direction = LEFT;
		}

		if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT)
		{
			direction = RIGHT;
		}

		if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN)
		{
			direction = UP;
		}

		if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP)
		{
			direction = DOWN;
		}

		if (i == KeyEvent.VK_SPACE)
		{
			if (over)
			{
				x = 100;
				startGame();
			}
			else
			{
				paused = !paused;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
	}
	
	
	public static void main(String[] args) {
	
		snake = new Snake();
	}

}