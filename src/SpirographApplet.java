import java.applet.Applet;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class SpirographApplet extends Applet 
{
	private static final long serialVersionUID = 1L;

	private Timer timer;
	
	float R = 10, r = 5, O = 1;
	float t = 0;
	
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();
	
	TextField field;
	Button button;
	Canvas canvas;
	
	@Override
	public void start()
	{
		final Graphics graphics = getGraphics();
		
		TimerTask task = new TimerTask() 
		{
			public void run()
			{
				paint(graphics);
			}
		};
	
		CalcPos();
		
		SetupUI();
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(task, 0, 16);
	}
	
	void SetupUI()
	{
		this.setLayout(null);
		
		field = new TextField("", 100);
		button = new Button("Draw");
		canvas = new Canvas();
		
		field.setBounds(10, 10, 100, 20);
		button.setBounds(130, 10, 60, 20);
		canvas.setBounds(10, 40, getWidth()-20, getHeight()-50);
		
		ActionListener buttonListener = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				buttonClick();
			}
		};
		
		button.addActionListener(buttonListener);
		
		add(field);
		add(button);
		add(canvas);
	}
	
	public void buttonClick()
	{
		String[] parts = field.getText().split(",");
		
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
		
		CalcPos();
		
		canvas.getGraphics().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}
	
	public void paint(Graphics graphics)
	{
		for(int i =0;i<xCoords.size() && i < yCoords.size();i++)
		{
			if(i == yCoords.size() - 1 || i == xCoords.size() - 1) canvas.getGraphics().drawLine(xCoords.get(i) + (int)(R+O+r), yCoords.get(i) + (int)(R+O+r), xCoords.get(0) + (int)(R+O+r), yCoords.get(0) + (int)(R+O+r));
			else canvas.getGraphics().drawLine(xCoords.get(i) + (int)(R+O+r), yCoords.get(i) + (int)(R+O+r), xCoords.get(i+1) + (int)(R+O+r), yCoords.get(i+1) + (int)(R+O+r));
		}
	}
	
	void CalcPos()
	{
		t = 0;
		
		xCoords.clear();
		yCoords.clear();
		
		for(int i =0;i<200;i++)
		{
			xCoords.add((int)Math.round((R+r)*Math.cos(t) - (r+O)*Math.cos(((R+r)/r)*t)));
			yCoords.add((int)Math.round((R+r)*Math.sin(t) - (r+O)*Math.sin(((R+r)/r)*t)));
			t+= Math.PI/50f;
		}
	}
}
