package com.hacademy.screen.ui.shape;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Arrow extends Line{
	static enum Direction {
		FORWARD, REVERSE, BOTH
	}
	
	protected Direction direction;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		switch(direction) {
		case FORWARD: 
			paintForward(g2d);
			break;
		case REVERSE:
			paintBackward(g2d);
			break;
		case BOTH:
			paintForward(g2d);
			paintBackward(g2d);
			break;
		}
	}
	
	protected void paintForward(Graphics2D g2d) {
		
	}
	protected void paintBackward(Graphics2D g2d) {
		
	}
}
