package com.hacademy.screen.ui;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import com.hacademy.screen.information.ScreenInformationReader;
import com.hacademy.screen.ui.data.Multipoint;
import com.hacademy.screen.ui.shape.Figure;
import com.hacademy.screen.ui.shape.FigureFactory;

import lombok.ToString;

public class GlassFrame extends JDialog{
	private static final long serialVersionUID = 1L;
	private BufferedImage backgroundImage;
	private int imgBorder = 1;
	private boolean fullscreen;
	private MouseTracker tracker;
	
	private int alpha;
	private Color transparentColor;
	private Color tempColor = new Color(0, 0, 0, 0);
	private int tempBorder = 2;
	private ProcessType mode = ProcessType.DEFAULT;
	private Rectangle area;
	private CaptureListener captureListener;
	
	private Font font = new Font("", Font.PLAIN, 20);
	private DrawPainter drawPainter;
	private BufferedImage backupImage;
	
	@Override
	public void paint(Graphics g) {
//		BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
//		image.getGraphics().drawLine(10, 10, 500, 500);
//		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		g.setFont(font);
		
		Graphics2D g2d = (Graphics2D)g;
		
		switch(mode) {
		case AREA_SELECTION:
		case STATIC_SELECTION:
		case MOUSE_TRACKING:
			if(backgroundImage != null) {
				//배경 이미지
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				
				//이미지 테두리
				if(imgBorder > 0) {
					g2d.setStroke(new BasicStroke(imgBorder));
					g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
				}
			}
			
			if(tracker != null) {
				g2d.setComposite(AlphaComposite.Src);
				g2d.setColor(transparentColor);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				g2d.setStroke(new BasicStroke(tempBorder));
				g2d.setColor(Color.black);
				g2d.drawLine(tracker.x, 0, tracker.x, getHeight());
				g2d.drawLine(0, tracker.y, getWidth(), tracker.y);
				g2d.setColor(Color.white);
				g2d.drawString("("+tracker.x+","+tracker.y+")", tracker.x+tempBorder*2, tracker.y-tempBorder*4);
				
				if(tracker.drag) {
					//임시 영역 테두리
					g2d.setColor(Color.black);
					g2d.drawRect(tracker.getLeft(), tracker.getTop(), tracker.getWidth(), tracker.getHeight());
					
					//임시 영역
					g2d.setColor(tempColor);
					g2d.fillRect(
							tracker.getLeft() + tempBorder / 2, 
							tracker.getTop() + tempBorder / 2, 
							tracker.getWidth() - tempBorder, 
							tracker.getHeight() - tempBorder
					);
					
					//영역 크기 글자 출력
					int height = g2d.getFontMetrics().getHeight();
					g2d.setColor(Color.black);
					g2d.drawString(tracker.getWidth()+"x"+tracker.getHeight(), tracker.getLeft()+tempBorder*4, tracker.getTop()+height+tempBorder*2);
				}
			}
			break;
		case DRAWING:
			//배경이 있다면 표시(static drawing)
			if(backgroundImage != null) {
				//배경 이미지
				g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
				
				//이미지 테두리
				if(imgBorder > 0) {
					g2d.setStroke(new BasicStroke(imgBorder));
					g2d.drawRect(0, 0, getWidth()-1, getHeight()-1);
				}
			}
			
			if(drawPainter.tempFigure != null) {
				drawPainter.tempFigure.draw(g2d);
			}
				
		default:
		}
		
	}
	
	public GlassFrame() {
		this(100);
	}
	
	public GlassFrame(int transparency) {
		setUndecorated(true);
		setTransparency(transparency);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}
	
	public void setTransparency(int percent) {
		if(percent < 0) percent = 0;
		if(percent > 100) percent = 100;
		this.alpha = 255 - (int)(255 / 100.0 * percent);
		this.transparentColor = new Color(0, 0, 0, alpha);
		this.setBackground(this.transparentColor);
	}
	
	public void setInvisibleCursor() {
		BufferedImage base = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(0, 0), "pen");
		this.setCursor(cursor);
	}
	
	public static final int MAX_CURSOR_SIZE = 20, MIN_CURSOR_SIZE = 3;
	public void setCircleCursor(Color color, int size) {
		if(size > MAX_CURSOR_SIZE || size < MIN_CURSOR_SIZE) {
			return;
		}
		
		BufferedImage base = new BufferedImage(MAX_CURSOR_SIZE, MAX_CURSOR_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics pen = base.getGraphics();
		pen.setColor(color);
		pen.fillOval(0, 0, size, size);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(size/2, size/2), "pen");
		this.setCursor(cursor);
	}
	
	public void setTextCursor(Color color) {
		int size = 15;
		
		BufferedImage base = new BufferedImage(MAX_CURSOR_SIZE, MAX_CURSOR_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics pen = base.getGraphics();
		pen.setColor(color);
		
		//텍스트 커서 모양 만들기
		int center = MAX_CURSOR_SIZE / 2;
		int xBegin = center - size / 4;
		int xEnd = center + size / 4;
		int yBegin = MAX_CURSOR_SIZE - size;
		int yEnd = MAX_CURSOR_SIZE-1;
		pen.drawLine(xBegin, yBegin, xEnd, yBegin);
		pen.drawLine(center, yBegin, center, yEnd);
		pen.drawLine(xBegin, yEnd, xEnd, yEnd);
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(base, new Point(size/2, size/2), "pen");
		setCursor(cursor);
	}
	
	public static GlassFrameBuilder builder() {
		return new GlassFrameBuilder();
	}
	
	public static class GlassFrameBuilder {
		private GlassFrame frame = new GlassFrame();
		private boolean sizeFlag;
		private GlassFrameBuilder(){}
		public GlassFrame build() {
			return build(false);
		}
		public GlassFrame build(boolean visible) {
			frame.setVisible(visible);
			return frame;
		}
		public GlassFrameBuilder fullscreen() {
			sizeFlag = true;
			frame.fullscreen = true;
			frame.setBounds(ScreenInformationReader.getCursorLocatedDeviceResolution());
			return this;
		}
		public GlassFrameBuilder location(int x, int y) {
			frame.setLocation(x, y);
			return this;
		}
		public GlassFrameBuilder location(Point p) {
			return location(p.x, p.y);
		}
		public GlassFrameBuilder size(int width, int height) {
			sizeFlag = true;
			if(!frame.fullscreen) { 
				frame.setSize(width, height);
			}
			return this;
		}
		public GlassFrameBuilder size(Dimension dimension) {
			sizeFlag = true;
			return size(dimension.width, dimension.height);
		}
		public GlassFrameBuilder bound(Rectangle rectangle) {
			sizeFlag = true;
			frame.setBounds(rectangle);
			return this;
		}
		public GlassFrameBuilder transparent() {
			return transparent(100);
		}
		public GlassFrameBuilder transparent(int percent) {
			frame.setTransparency(percent);
			return this;
		}
		public GlassFrameBuilder backgroundImage() {
			return backgroundImage(1);
		}
		public GlassFrameBuilder backgroundImage(int imgBorder) {
			if(!sizeFlag) {
				throw new RuntimeException("size를 먼저 설정하세요");
			}
			if(imgBorder < 0) {
				throw new IllegalArgumentException("테두리는 0 이상이어야 합니다");
			}
			
			frame.imgBorder = imgBorder;
			Rectangle rectangle = new Rectangle(frame.getLocation(), frame.getSize());
			BufferedImage image = ScreenInformationReader.capture(rectangle);
			frame.backgroundImage = image;
			frame.repaint();
			return this;
		}
		public GlassFrameBuilder exitKey(int keyCode) {
			frame.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if(keyCode == e.getExtendedKeyCode()) {
						frame.close();
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
		public GlassFrameBuilder alwaysOnTop() {
			frame.setAlwaysOnTop(true);
			return this;
		}
		public GlassFrameBuilder mouseTracking() {
			frame.mode = ProcessType.MOUSE_TRACKING;
			if(frame.tracker == null) {
				frame.tracker = frame.new MouseTracker();
				frame.addMouseMotionListener(frame.tracker);
				frame.addMouseListener(frame.tracker);
			}
			return this;
		}
		public GlassFrameBuilder mouseInvisible() {
			frame.setInvisibleCursor();
			return this;
		}
		public GlassFrameBuilder areaSelectionMode(CaptureListener listener) {
			frame.mode = ProcessType.AREA_SELECTION;
			frame.captureListener = listener;
			return this.fullscreen()
								.alwaysOnTop()
								.transparent(50)
								.escEnable()
								.mouseTracking()
							.mouseInvisible();
		}
		public GlassFrameBuilder staticSelectionMode() {
			return staticSelectionMode(null);
		}
		public GlassFrameBuilder staticSelectionMode(CaptureListener listener) {
			frame.mode = ProcessType.STATIC_SELECTION;
			frame.captureListener = listener;
			return this.fullscreen()
									.alwaysOnTop()
									.transparent(50)
									.escEnable()
									.mouseTracking()
								.mouseInvisible();
		}
		public GlassFrameBuilder dynamicDrawingMode() {
			frame.mode = ProcessType.DRAWING;
			frame.setCircleCursor(Color.black, 5);
			frame.drawPainter = frame.new DrawPainter();
			frame.addKeyListener(frame.drawPainter);
			frame.addMouseListener(frame.drawPainter);
			frame.addMouseMotionListener(frame.drawPainter);
			frame.addMouseWheelListener(frame.drawPainter);
			return this.fullscreen().alwaysOnTop().transparent();
		}
		public GlassFrameBuilder staticDrawingMode() {
			frame.mode = ProcessType.DRAWING;
			frame.setCircleCursor(Color.black, 5);
			frame.drawPainter = frame.new DrawPainter();
			frame.addKeyListener(frame.drawPainter);
			frame.addMouseListener(frame.drawPainter);
			frame.addMouseMotionListener(frame.drawPainter);
			frame.addMouseWheelListener(frame.drawPainter);
			return this.fullscreen().alwaysOnTop().backgroundImage();
		}
	}
	
	private TimerTask refreshTask = new TimerTask() {
		@Override
		public void run() {
			repaint();
		}
	};
	
	public void close() {
		switch(mode) {
			case STATIC_SELECTION:
				GlassFrame.builder()
										.bound(area)
										.backgroundImage()
										.draggable()
										.escEnable()
									.build(true);
			case AREA_SELECTION:
				if(captureListener != null) {
					BufferedImage image = ScreenInformationReader.capture(area);
					captureListener.capture(image);
				}
				break;
			case DRAWING:
			default:
		}
		if(tracker != null)
			tracker.cancel();
		if(drawPainter != null)
			drawPainter.cancel();
		super.dispose();
	}
	
	@ToString(of = {"oldX", "oldY", "x", "y", "drag"})
	class MouseTracker extends MouseAdapter implements MouseMotionListener{
		private int oldX, oldY;
		private int x, y;
		public boolean drag;
		private Timer timer = new Timer();
		
		public MouseTracker() {
			timer.scheduleAtFixedRate(refreshTask, 0, 1000/24);
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			x = e.getX();
			y = e.getY();
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			oldX = x = e.getX();
			oldY = y = e.getY();
			drag = true;
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			drag = false;
			area = getRectangle();
			
			switch(mode) {
			case STATIC_SELECTION:
			case AREA_SELECTION:
				close();
			default:
			}
		}
		
		public void cancel() {
			timer.cancel();
		}
		
		public int getLeft() {
			return Math.min(oldX, x);
		}
		
		public int getTop() {
			return Math.min(oldY, y);
		}
		
		public int getWidth() {
			return Math.max(oldX, x) - Math.min(oldX, x);
		}
		
		public int getHeight() {
			return Math.max(oldY, y) - Math.min(oldY, y);
		}
		
		public Dimension getDimension() {
			return new Dimension(getWidth(), getHeight());
		}
		
		public Rectangle getRectangle() {
			return new Rectangle(getLeft(), getTop(), getWidth(), getHeight());
		}
		
	}

	class DrawPainter implements KeyListener, MouseMotionListener, MouseWheelListener, MouseListener{
		private Multipoint point = new Multipoint();
		private boolean enter, focus;
		private Timer timer = new Timer();
		private List<Figure> figureList = new ArrayList<>();
		private Figure tempFigure;
		private KeyStroke pressKey;
		private Set<Integer> pressKeyStorage = new HashSet<>();
		
		public DrawPainter() {
//			timer.scheduleAtFixedRate(refreshTask, 0, 1000/24);
		}
		@Override
		public void mouseClicked(MouseEvent e) {}
		@Override
		public void mousePressed(MouseEvent e) {
			focus = true;
			point.setOldX(e.getX());
			point.setOldY(e.getY());
			point.setX(e.getX());
			point.setY(e.getY());
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			point.setX(e.getX());
			point.setY(e.getY());
			
			//키보드 값을 이용한 도형 추가
			if(pressKey != null) {
				Figure figure = FigureFactory.create(pressKey, point);
				System.out.println(figure);
				if(figure != null) {
					figureList.add(figure);
					add(figure);
					System.out.println(figureList.contains(figure)+ " , " + figureList.size() + " , " + GlassFrame.this.getComponentCount());
				}
			}
			else {
				
			}
			
			focus = false;
		}
		@Override
		public void mouseEntered(MouseEvent e) {
			enter = true;
		}
		@Override
		public void mouseExited(MouseEvent e) {
			enter = false;
		}
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			point.setX(e.getX());
			point.setY(e.getY());
			
//			if(tempFigure == null) {
//				tempFigure = FigureFactory.create(pressKey, point);
//			}
//			else {
//				tempFigure = FigureFactory.refresh(tempFigure, point);
//			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			point.setX(e.getX());
			point.setY(e.getY());
			
			if(tempFigure != null) {
				tempFigure = null;
			}
		}
		@Override
		public void keyTyped(KeyEvent e) {
			
		}
		@Override
		public void keyPressed(KeyEvent e) {
			pressKeyStorage.add(e.getKeyCode());
			
			if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
				if(focus) {
					pressKey = null;
				}
				else {
					close();
				}
			}
			else{
				pressKey = KeyStroke.getKeyStroke(e.getExtendedKeyCode(), e.getModifiers());
				String figureName = FigureFactory.find(pressKey);
				//이후에 어떻게 할지 고민해야됨(커서에 표시 등)
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			pressKeyStorage.remove(e.getKeyCode());
			if(pressKeyStorage.isEmpty()) {
				pressKey = null;
			}
		}
		
		public void cancel() {
//			timer.cancel();
		}
	}
	
}
