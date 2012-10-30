import java.applet.Applet;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class SpirographApplet extends Applet 
{
	private static final long serialVersionUID = 1L;

	private Timer timer;
	
	TextField field;
	
	Button addButton;
	Button clearButton;
	Button clearAddButton;
	Button animateButton;
	
	Canvas canvas;
	
	Point lastPoint = null;
	
	int xOffset = 100, yOffset = 100;
	
	Image doubleBuffer;
	Graphics doubleBufferGraphics;
	
	ArrayList<Spirograph> spirographs = new ArrayList<Spirograph>();
	
	boolean isAnimating = false;
	
	float animationOffset;
	
	@Override
	public void start()
	{
		final Graphics graphics;
		
		SetupUI();
		
		graphics = canvas.getGraphics();
		
		TimerTask task = new TimerTask() 
		{
			public void run()
			{
				paint(graphics);
			}
		};
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(task, 0, 16);
	}
	
	void SetupUI()
	{
		this.setLayout(null);
		
		field = new TextField("", 100);
		
		addButton = new Button("Add");
		clearButton = new Button("Clear");
		clearAddButton = new Button("Clear and Add");
		animateButton = new Button("Animate");
		
		canvas = new Canvas();
		
		field.setBounds(10, 10, 100, 20);
		
		addButton.setBounds(130, 10, 60, 20);
		clearButton.setBounds(200, 10, 60, 20);
		clearAddButton.setBounds(270, 10, 130, 20);
		animateButton.setBounds(410, 10, 60, 20);
		
		canvas.setBounds(10, 40, getWidth()-20, getHeight()-50);
		
		doubleBuffer = createImage(canvas.getWidth(), canvas.getHeight());
		doubleBufferGraphics = doubleBuffer.getGraphics();
		
		MouseMotionListener mouseListener = new MouseMotionListener()
		{
			@Override
			public void mouseMoved(MouseEvent e)
			{
				
			}

			@Override
			public void mouseDragged(MouseEvent e) 
			{
				onMouseDrag(e);
			}
		};
		
		MouseListener mouseClickListener = new MouseListener()
		{

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) 
			{
				onMouseUp();
			}
			
		};
		
		canvas.addMouseMotionListener(mouseListener);
		canvas.addMouseListener(mouseClickListener);
		
		ActionListener addListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				addButtonClick();
			}
		};
		
		ActionListener clearListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clearButtonClick();
			}
		};
		
		ActionListener clearAddListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				clearButtonClick();
				addButtonClick();
			}
		};
		
		ActionListener animateListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				animate();
			}
		};
		
		addButton.addActionListener(addListener);
		clearButton.addActionListener(clearListener);
		clearAddButton.addActionListener(clearAddListener);
		animateButton.addActionListener(animateListener);
		
		add(field);
		add(addButton);
		add(clearButton);
		add(clearAddButton);
		add(animateButton);
		add(canvas);
	}
	
	public void animate()
	{
		isAnimating = !isAnimating;
		
		if(isAnimating) animationOffset = 0;
	}
	
	public void onMouseUp()
	{
		lastPoint = null;
	}
	
	public void onMouseDrag(MouseEvent event)
	{
		if(lastPoint != null)
		{
			xOffset += event.getX()-lastPoint.x;
			yOffset += event.getY()-lastPoint.y;
			
			repaint();
		}
		lastPoint = event.getPoint();
	}
	
	public void clearButtonClick()
	{
		spirographs.clear();
	}
	
	public void addButtonClick()
	{
		String[] parts = field.getText().split(",");
		
		float R = 0,r = 0,O = 0;
		
		for(int i =0;i<parts.length && i < 3;i++)
		{
			float val = new Float(parts[i]);
			
			switch(i)
			{
				case 0:
					R = val;
					break;
				case 1:
					r = val;
					break;
				case 2:
					O = val;
					break;
			}
		}
		
		String colName = "black";
		Color col = Color.black;
		
		if(parts.length >= 4) colName = parts[3].replaceAll(" ", "");
		
		if(colName.equalsIgnoreCase("red")) col = Color.red;
		else if(colName.equalsIgnoreCase("blue")) col = Color.blue;
		else if(colName.equalsIgnoreCase("green")) col = Color.green;
		else if(colName.equalsIgnoreCase("yellow")) col = Color.yellow;
		
		spirographs.add(new Spirograph(R,r,O, col));
		
		repaint();
	}
	
	@Override
	public void update(Graphics graphics) 
    { 
         paint(canvas.getGraphics()); 
    } 
	
	public void paint(Graphics graphics)
	{
		doubleBufferGraphics.setColor(Color.white);
		doubleBufferGraphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		for(Spirograph spirograph : spirographs)
		{
			spirograph.draw(doubleBufferGraphics, xOffset, yOffset, animationOffset, isAnimating);
		}
		
		graphics.setColor(Color.white);
		graphics.drawImage(doubleBuffer, 0, 0, canvas);
	
		if(isAnimating)
		{
			animationOffset += 1f;
			if(animationOffset >= 1601f) animationOffset = 0f;
		}
	}
	
}
