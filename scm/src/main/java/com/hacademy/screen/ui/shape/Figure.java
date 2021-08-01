package com.hacademy.screen.ui.shape;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;

import lombok.Data;

public abstract class Figure extends JLabel{
	private static final long serialVersionUID = 1L;
	protected Color color;

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
	}
}
