package com.hacademy.screen.ui.shape;

import java.awt.Graphics2D;

public class Rect extends Linear{

	@Override
	public void paint(Graphics2D pen) {
		pen.drawRect(getX(), getY(), getWidth(), getHeight());
	}
	
}
