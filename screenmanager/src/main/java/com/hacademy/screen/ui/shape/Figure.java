package com.hacademy.screen.ui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import lombok.Data;

@Data
public abstract class Figure {
	protected Color color;
	protected boolean complete;
	public void draw(Graphics g) {
		g.setColor(color);
		paint((Graphics2D)g);
		complete = true;
	}
	public abstract void paint(Graphics2D pen);
}
