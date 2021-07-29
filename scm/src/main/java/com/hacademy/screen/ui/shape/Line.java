package com.hacademy.screen.ui.shape;

import java.awt.Color;
import java.awt.Graphics2D;

import com.hacademy.screen.ui.data.Multipoint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

public class Line extends Linear{
	private static final long serialVersionUID = 1L;
	
	protected int x1, x2, y1, y2;
	
	@Override
	public void paint(Graphics2D pen) {
//		pen.drawLine(x1, y1, x2, y2);
		pen.fillRect(x1, y1, x2-x1, y2-y1);
		System.out.println(getBounds());
	}

	public void setPoints(Multipoint point) {
		this.x1 = point.getOldX();
		this.y1 = point.getOldY();
		this.x2 = point.getX();
		this.y2 = point.getY();
	}
	
}
