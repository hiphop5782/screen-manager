package com.hacademy.screen.ui.shape;

import java.awt.Graphics2D;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Line extends Linear{

	protected int x1, y1, x2, y2;
	
	@Override
	public void paint(Graphics2D pen) {
		pen.drawLine(x1, y1, x2, y2);
	}
	
}
