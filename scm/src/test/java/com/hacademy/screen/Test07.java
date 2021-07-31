package com.hacademy.screen;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import com.hacademy.screen.ui.data.Keyboard;
import com.hacademy.screen.ui.data.Multipoint;
import com.hacademy.screen.ui.shape.Figure;
import com.hacademy.screen.ui.shape.FigureFactory;

public class Test07 {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(0, 0, 700, 700);
		frame.setUndecorated(true);
		frame.setBackground(new Color(0, 0, 0, 50));
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
		
		frame.addMouseListener(new MouseAdapter() {
			int x, y;
			@Override
			public void mousePressed(MouseEvent e) {
				x = e.getX();
				y = e.getY();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int nx = e.getX(), ny = e.getY();
				Figure label = FigureFactory.create(Keyboard.E, new Multipoint());
//				JLabel label = new JLabel("");
				label.setBorder(BorderFactory.createLineBorder(Color.black, 5));
				label.setBounds(x, y, nx-x, ny-y);
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
		});
		
		frame.setVisible(true);
		
	}
}
