package com.hacademy.screen.ui.shape;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class Linear extends Figure{
	private static final long serialVersionUID = 1L;
	protected int thickness;
	protected BasicStroke stroke;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if(stroke == null || stroke.getLineWidth() != thickness) {
			stroke = new BasicStroke(thickness);
		}
		
		//이 부분을 if에 넣으면 실행이 안됨. 매 드로잉마다 stroke를 새로 지정해야함
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(stroke);
	}
	
}
