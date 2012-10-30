import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


public class Spirograph 
{
	float largeRadius = 10;
	float smallRadius = 5;
	float penOffset = 1;
	float rotationOffset = 0;
	
	Color col = Color.black;
	
	ArrayList<Integer> xCoords = new ArrayList<Integer>();
	ArrayList<Integer> yCoords = new ArrayList<Integer>();
	
	public Spirograph(float LargeRadius, float SmallRadius, float PenOffset, Color Col)
	{
		largeRadius = LargeRadius;
		smallRadius = SmallRadius;
		penOffset = PenOffset;
		rotationOffset = 0f;
		
		if(Col != null) col = Col;
		
		calculateVectors();
	}
	
	void calculateVectors()
	{
		rotationOffset = 0;
		
		xCoords.clear();
		yCoords.clear();
		
		for(int i =0;i<1601;i++)
		{
			xCoords.add((int)Math.round((largeRadius+smallRadius)*Math.cos(rotationOffset) - (smallRadius+penOffset)*Math.cos(((largeRadius+smallRadius)/smallRadius)*rotationOffset)));
			yCoords.add((int)Math.round((largeRadius+smallRadius)*Math.sin(rotationOffset) - (smallRadius+penOffset)*Math.sin(((largeRadius+smallRadius)/smallRadius)*rotationOffset)));
			
			rotationOffset+= Math.PI/800f;
		}
	}
	
	public void draw(Graphics graphics, int xOffset, int yOffset, float animationOffset, boolean isAnimating)
	{
		graphics.setColor(col);
		for(int i =0;i<xCoords.size() && i < yCoords.size() && (i < Math.floor(animationOffset) | !isAnimating);i++)
		{
			if(i == yCoords.size() - 1 || i == xCoords.size() - 1) graphics.drawLine(xCoords.get(i) + xOffset, yCoords.get(i) + yOffset, xCoords.get(0) + xOffset, yCoords.get(0) + yOffset);
			else graphics.drawLine(xCoords.get(i) + xOffset, yCoords.get(i) + yOffset, xCoords.get(i+1) + xOffset, yCoords.get(i+1) + yOffset);
		}
		if(isAnimating)
		{
			int offsetFloor = (int)Math.floor(animationOffset);
			float amount = animationOffset-offsetFloor;
			
			if(offsetFloor < xCoords.size()-1 && amount > 0)
			{
				int nextVec = offsetFloor+1;
				if(nextVec >= xCoords.size()) nextVec = 0;
			
				int x = (int)xCoords.get(offsetFloor);
				int y = (int)yCoords.get(offsetFloor);
				int newX = (int)Lerp(x, xCoords.get(nextVec), amount);
				int newY = (int)Lerp(yCoords.get(offsetFloor), yCoords.get(nextVec), amount);
			
				graphics.drawLine(x + xOffset, y + yOffset, newX + xOffset, newY + yOffset);
			}
		}
	}
	
	float Lerp(float from, float to, float amount)
	{
		return (to-from)*amount + from;
	}
}
