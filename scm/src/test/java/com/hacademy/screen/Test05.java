package com.hacademy.screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.hacademy.screen.ui.shape.Line;

public class Test05 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 500, 500);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 0));
		frame.setLayout(null);
		frame.addKeyListener(new KeyAdapter() {
			@Override public void keyPressed(KeyEvent e) { 
				if(KeyEvent.VK_ESCAPE == e.getExtendedKeyCode()) {
					System.exit(0); 
				}
				
				for(Component c : frame.getComponents()) {
					int rx = (int)(Math.random() * 400);
					int ry = (int)(Math.random() * 400);
					int rw = (int)(Math.random() * 100);
					int rh = (int)(Math.random() * 100);
					c.setBounds(rx, ry, rw, rh);
					c.repaint();
				}
			}
		});
		frame.setVisible(true);
		
		for(int i=1; i <= 10; i++) {
			Line label = new Line() {
				@Override
				protected void paintComponent(Graphics g) {
					g.fillRect(0, 0, getWidth(), getHeight());
				}
			};
			int rx = (int)(Math.random() * 400);
			int ry = (int)(Math.random() * 400);
			int rw = (int)(Math.random() * 100);
			int rh = (int)(Math.random() * 100);
			label.setBounds(rx, ry, rw, rh);
			label.addMouseMotionListener(new MouseMotionListener() {
				int x, y;
				@Override
				public void mouseMoved(MouseEvent e) {
					x = e.getX();
					y = e.getY();
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					Point p = label.getLocation();
					System.out.println(p); 
					p.x += e.getX() - x;
					p.y += e.getY() - y;
					label.setLocation(p);
				}
			});
			frame.add(label);
		}
	}
}
