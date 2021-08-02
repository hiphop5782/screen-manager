package com.hacademy.screen.ui.shape;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Text extends Figure{
	private static final long serialVersionUID = 1L;
	
	protected String text;
	protected JTextArea textarea = new JTextArea("hello world");
	protected JScrollPane scroll = new JScrollPane(textarea);
	protected JLabel label = new JLabel("hello world");
	protected ComponentListener componentListener = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			fit();
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
	protected MouseListener mouseListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			changeToEditor();
			fit();
		}
	};
	
	public Text() {
		setOpaque(true);
		add(scroll);
		addComponentListener(componentListener);
		textarea.addKeyListener(keyListener);
		label.addMouseListener(mouseListener);
	}
	
	public void changeToLabel() {
		label.setText(textarea.getText());
		remove(scroll);//textarea 제거
		add(label);//label 추가
		repaint();
		revalidate();
		SwingUtilities.getRoot(label).requestFocus();//Frame으로 focus 전환
	}
	
	public void changeToEditor() {
		textarea.setText(label.getText());
		remove(label);
		add(textarea);
		repaint();
		revalidate();
		textarea.requestFocus();
	}
	
	public void fit() {
		label.setSize(getSize());
		scroll.setSize(getSize());
		textarea.setSize(scroll.getSize());
	}
}
