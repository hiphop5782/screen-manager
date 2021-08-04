package com.hacademy.screen.ui.shape;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Text extends Figure{
	private static final long serialVersionUID = 1L;
	
	protected String text;
	protected JTextArea textarea = new JTextArea();
	protected JScrollPane scroll = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	protected JLabel label = new JLabel();
	protected CardLayout cardLayout = new CardLayout();
	protected Font font = new Font("", Font.PLAIN, 20);
	private String header = "<html><body><pre>";
	private String footer = "</pre></body></html>";
	
	protected ComponentListener componentListener = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			resizeAllComponent();
		}
	};
	protected KeyListener keyListener = new KeyAdapter() {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getExtendedKeyCode() == KeyEvent.VK_ESCAPE) {
				changeToLabel();
			}
		}
	};
	protected MouseAdapter mouseListener = new MouseAdapter() {
		private int oldX, oldY;
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getSource() == label) {
				changeToEditor();
				resizeAllComponent();
				return;
			}
		}
		public void mouseEntered(MouseEvent e) {
			focus();
		}
		public void mouseExited(MouseEvent e) {
			blur();
		}
		public void mousePressed(MouseEvent e) {
			if(e.getSource() == label) {
				oldX = e.getX();
				oldY = e.getY();
			}
		}
		public void mouseDragged(MouseEvent e) {
			if(e.getSource() == label) {
				move(oldX, oldY, e.getX(), e.getY());
			}
		}
	};
	
	public Text() {
		setOpaque(true);
		setLayout(cardLayout);
		
		add(scroll, "editor");
		add(label, "label");
		addComponentListener(componentListener);
		
		textarea.addKeyListener(keyListener);
		textarea.addMouseListener(mouseListener);
		textarea.setLineWrap(true);
		
		label.addMouseListener(mouseListener);
		label.addMouseMotionListener(mouseListener);
		label.setVerticalAlignment(JLabel.TOP);
		label.setVerticalTextPosition(JLabel.TOP);
		
		textarea.setFont(font);
		label.setFont(font);
	}
	
	public void changeToLabel() {
		String value = textarea.getText();
		StringBuffer buffer = new StringBuffer();
		buffer.append(header);
		buffer.append(value);
		buffer.append(footer);
		label.setText(buffer.toString());
		cardLayout.show(this, "label");
		SwingUtilities.getRoot(label).requestFocus();//Frame으로 focus 전환
	}
	
	public void changeToEditor() {
		String value = label.getText();
		textarea.setText(value.substring(header.length(), value.length() - footer.length()));
		cardLayout.show(this, "editor");
		textarea.requestFocus();
	}
	
	public void resizeAllComponent() {
		label.setBounds(1, 1, getWidth() - 2, getHeight() - 2);
		scroll.setBounds(1, 1, getWidth() - 2, getHeight() - 2);
		textarea.setSize(scroll.getSize());
	}
}
