package com.hacademy.screen.ui.shape;

import java.awt.Graphics;

public class Rect extends Linear{

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
	}
	
}
