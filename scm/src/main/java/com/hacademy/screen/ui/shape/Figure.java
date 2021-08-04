package com.hacademy.screen.ui.shape;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
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
		private int oldX, oldY;
		private int mouseX, mouseY;
		private boolean drag;
		private boolean edge;
		private Rectangle rect;
		private Direction direction;
		private int offset = 5;
		@Override
		public void mousePressed(MouseEvent e) {
			drag = true;
			edge = isEdge(e.getX(), e.getY(), offset); 
			oldX = e.getX(); 
			oldY = e.getY();
			
			if(edge) {
				mouseX = e.getX();
				mouseY = e.getY();
				rect = getBounds();
				direction = getDirection(e.getX(), e.getY(), offset);
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			drag = false;
			edge = false;
			rect = null;
			direction = null;
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if(edge) {
				resize(mouseX, mouseY, e.getX(), e.getY(), direction);
				mouseX = e.getX();
				mouseY = e.getY();
			}
			else{
				move(oldX, oldY, e.getX(), e.getY());
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			if(drag) return;
			focus();
		}
		@Override
		public void mouseExited(MouseEvent e) {
			if(drag) return;
			blur();
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			int offset = 5;
			if(isEdge(e.getX(), e.getY(), offset)) {
				Direction direction = getDirection(e.getX(), e.getY(), offset);
				setCursor(direction.getCursor());
			}
			else {
				setCursor(Direction.DEFAULT.getCursor());
			}
		}
		
		private boolean isEdge(int x, int y, int offset) {
			return (x < offset || x > getWidth() - offset) || (y < offset || y > getHeight() - offset);
		}
		
		private Direction getDirection(int x, int y, int offset) {
			if(x < offset) {//좌
				if(y < offset) return Direction.UPLEFT;
				else if(y > getHeight() - offset) return Direction.DOWNLEFT;
				else return Direction.LEFT;
			}
			else if(x > getWidth() - offset) {//우
				if(y < offset) return Direction.UPRIGHT;
				else if(y > getHeight() - offset) return Direction.DOWNRIGHT;
				else return Direction.RIGHT;
			}
			else {
				if(y < offset) return Direction.UP;
				else if(y > getHeight() - offset) return Direction.DOWN;
			}
			return null;
		}
	}
	
	protected void focus() {
		setBorder(dashBorder);
	}

	protected void blur() {
		setBorder(emptyBorder);
	}
	
	protected void move(int oldX, int oldY, int x, int y) {
		Point p = getLocation();
		p.x += x - oldX;
		p.y += y - oldY;
		setLocation(p);
	}
	
	protected synchronized void resize(int oldX, int oldY, int x, int y, Direction direction) {
		Rectangle rect = mouseHandler.rect;
		if(rect == null) return;
		
		int xgap = x - oldX;
		int ygap = y - oldY;
		//xgap과 ygap이 +-가 번갈아서 나와 흔들림 현상이 발생
		System.out.println("xgap = " + xgap + " , ygap = " + ygap);
		
		switch(direction) {
		case UP: 					resizeUp(ygap); break;
		case UPLEFT:			resizeUp(y); resizeLeft(x); break;
		case UPRIGHT:		resizeUp(y); resizeRight(x); break;
		case DOWN: 			resizeDown(y); break;
		case DOWNLEFT:	resizeDown(y); resizeLeft(x); break;
		case DOWNRIGHT:	resizeDown(y); resizeRight(x); break;
		case LEFT:				resizeLeft(x); break;
		case RIGHT:			resizeRight(x); break;
		}
		setBounds(rect);
	}
	
	protected synchronized void resizeUp(int ygap) {
		if(mouseHandler.rect == null || ygap == 0) return;
		mouseHandler.rect.y += ygap;
		mouseHandler.rect.height -= ygap;
	}
	
	protected synchronized void resizeDown(int ygap) {
		if(mouseHandler.rect == null) return;
		mouseHandler.rect.height += ygap;
	}
	
	protected synchronized void resizeLeft(int xgap) {
		if(mouseHandler.rect == null) return;
		mouseHandler.rect.x -= xgap;
		mouseHandler.rect.width += xgap;
	}
	
	protected synchronized void resizeRight(int xgap) {
		if(mouseHandler.rect == null) return;
		mouseHandler.rect.width += xgap;
	}
	
}
