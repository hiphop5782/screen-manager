package com.hacademy.screen.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;

import com.hacademy.screen.information.ScreenInformationReader;

public class GlassFrame extends JDialog{
	
	private BufferedImage img;
	private int imgBorder = 1;
	
	@Override
	public void paint(Graphics g) {
		if(img != null) {
			//배경 이미지
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
			
			//이미지 테두리
			if(imgBorder > 0) {
				Graphics2D g2d = (Graphics2D)g;
				g2d.setStroke(new BasicStroke(imgBorder));
				g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
			}
		}
	}
	
	public GlassFrame() {
		this(100);
	}
	
	public GlassFrame(int transparency) {
		setUndecorated(true);
		setTransparency(transparency);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void setTransparency(int percent) {
		if(percent < 0) percent = 0;
		if(percent > 100) percent = 100;
		int alpha = 255 - (int)(255 / 100.0 * percent);
		setBackground(new Color(0, 0, 0, alpha));
	}
	
	public static GlassFrameBuilder builder() {
		return new GlassFrameBuilder();
	}
	
	public static class GlassFrameBuilder {
		private GlassFrame frame = new GlassFrame();
		private GlassFrameBuilder(){}
		public GlassFrame build() {
			return build(false);
		}
		public GlassFrame build(boolean visible) {
			frame.setVisible(visible);
			return frame;
		}
		public GlassFrameBuilder fullscreen() {
			frame.setSize(ScreenInformationReader.getCursorLocatedDeviceResolution().getSize());
			return this;
		}
		public GlassFrameBuilder size(int width, int height) {
			frame.setSize(width, height);
			return this;
		}
		public GlassFrameBuilder transparent(int percent) {
			frame.setTransparency(percent);
			return this;
		}
		public GlassFrameBuilder backgroundImage() {
			return backgroundImage(0);
		}
		public GlassFrameBuilder backgroundImage(int imgBorder) {
			if(frame.getWidth() == 0 || frame.getHeight() == 0) {
				throw new RuntimeException("size를 먼저 설정하세요");
			}
			if(imgBorder < 0) {
				throw new IllegalArgumentException("테두리는 0 이상이어야 합니다");
			}
			
			frame.imgBorder = imgBorder;
			Rectangle rectangle = new Rectangle(frame.getLocation(), frame.getSize());
			BufferedImage image = ScreenInformationReader.capture(rectangle);
			frame.img = image;
			frame.repaint();
			return this;
		}
		public GlassFrameBuilder exitKey(int keyCode) {
			frame.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(keyCode == e.getExtendedKeyCode()) {
						frame.dispose();
					}
				}
			});
			return this;
		}
		public GlassFrameBuilder escEnable() {
			exitKey(KeyEvent.VK_ESCAPE);
			return this;
		}
		public GlassFrameBuilder draggable() {
			frame.addMouseMotionListener(new MouseMotionListener() {
				private int x, y;
				@Override
				public void mouseMoved(MouseEvent e) {
					x = e.getX();
					y = e.getY();
				}
				@Override
				public void mouseDragged(MouseEvent e) {
					Point p = frame.getLocation();
					p.x += e.getX() - x;
					p.y += e.getY() - y;
					frame.setLocation(p);
				}
			});
			return this;
		}
	}
}
