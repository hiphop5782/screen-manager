package com.hacademy.screen.ui.shape;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Circle extends Linear{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		int offset = thickness/2;
		g2d.drawOval(offset, offset, getWidth()-thickness, getHeight()-thickness);
	}
}
