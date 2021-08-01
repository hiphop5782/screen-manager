package com.hacademy.screen.ui.shape;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Linear extends Figure{

	protected int thickness;
	protected BasicStroke stroke;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(stroke == null || stroke.getLineWidth() != thickness) {
			stroke = new BasicStroke(thickness);
			Graphics2D g2d = (Graphics2D)g;
			g2d.setStroke(stroke);
		}
	}
	
}
