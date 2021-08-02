package com.hacademy.screen.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.hacademy.screen.ui.data.Multipoint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class Line extends Linear{
	private static final long serialVersionUID = 1L;
	
	protected int x1, x2, y1, y2;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawLine(x1, y1, x2, y2);
	}

	public void setPoints(Multipoint point) {
		int xmin = Math.min(point.getOldX(), point.getX());
		int ymin = Math.min(point.getOldY(), point.getY());
		this.x1 = point.getOldX() - xmin;
		this.y1 = point.getOldY() - ymin;
		this.x2 = point.getX() - xmin;
		this.y2 = point.getY() - ymin;
	}
	
}
