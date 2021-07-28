package com.hacademy.screen.ui.shape;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Linear extends Figure{

	protected int thickness;
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		Graphics2D pen = (Graphics2D)g;
		pen.setStroke(new BasicStroke(thickness));
		paint(pen);
	}
	
}
