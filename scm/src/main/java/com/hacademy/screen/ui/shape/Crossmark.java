package com.hacademy.screen.ui.shape;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Crossmark extends Linear{
	private static final long serialVersionUID = 1L;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawLine(0, 0, getWidth(), getHeight());
		g2d.drawLine(getWidth(), 0, 0, getHeight());
	}
}
