package com.hacademy.screen.ui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public abstract class Figure extends JPanel{
	private static final long serialVersionUID = 1L;
	protected Color color;
	
	protected Border emptyBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	protected Border dashBorder = BorderFactory.createDashedBorder(null, 5, 5);
	protected MouseEventHandler mouseHandler = new MouseEventHandler();
	
	public Figure() {
		addMouseMotionListener(mouseHandler);
		addMouseListener(mouseHandler);
		setBorder(emptyBorder);
		setOpaque(false);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(color);
	}
	
	private class MouseEventHandler extends MouseAdapter {
		private int x, y;
		private boolean drag;
		@Override
		public void mousePressed(MouseEvent e) {
			drag = true;
			x = e.getX(); 
			y = e.getY();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			drag = false;
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			Point p = getLocation();
			p.x += e.getX() - x;
			p.y += e.getY() - y;
			setLocation(p);
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(drag) return;
			setBorder(dashBorder);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if(drag) return;
			setBorder(emptyBorder);
		}
	}
	
}
