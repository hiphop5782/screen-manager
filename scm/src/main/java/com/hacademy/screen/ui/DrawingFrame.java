package com.hacademy.screen.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.hacademy.screen.information.ScreenInformationReader;
import com.hacademy.screen.ui.data.Keyboard;
import com.hacademy.screen.ui.data.Multipoint;
import com.hacademy.screen.ui.shape.Figure;
import com.hacademy.screen.ui.shape.FigureFactory;

public class DrawingFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	
	private DrawingFrame() {
		keyHandler = this.new KeyHandler();
		mouseHandler = this.new MouseHandler();
		
		setLayout(null);
		addKeyListener(keyHandler);
		addMouseListener(mouseHandler);
		addMouseMotionListener(mouseHandler);
		addMouseWheelListener(mouseHandler);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}
	
	public void close() {
		dispose();
	}
	
	public static DrawingFrameBuilder builder() {
		return new DrawingFrameBuilder();
	}
	
	public static class DrawingFrameBuilder {
		private DrawingFrame frame = new DrawingFrame();
		public DrawingFrameBuilder () {
			frame.setUndecorated(true);
			frame.setAlwaysOnTop(true);
			frame.setBounds(ScreenInformationReader.getCursorLocatedDeviceResolution());
			frame.setResizable(false);
		}
		public DrawingFrame build() {
			return frame;
		}
		public DrawingFrame build(boolean visible) {
			frame.setVisible(visible);
			return frame;
		}
	}
	
	public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
		private Multipoint point = new Multipoint();
		private Figure temp;
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			point.setX(e.getX());
			point.setY(e.getY());
			temp = FigureFactory.refresh(temp, point);
			temp.repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			point.setOldX(e.getX());
			point.setOldY(e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			point.setOldX(e.getX());
			point.setOldY(e.getY());
			point.setX(e.getX());
			point.setY(e.getY());
			temp = FigureFactory.create(Keyboard.T, point);
//			temp = FigureFactory.create(Keyboard.X, point);
			getContentPane().add(temp);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			point.setX(e.getX());
			point.setY(e.getY());
			temp = null;
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}
		
	}
	
	public class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getExtendedKeyCode()) {
			case KeyEvent.VK_ESCAPE: 
				close(); break;
			}
		}
	}
}