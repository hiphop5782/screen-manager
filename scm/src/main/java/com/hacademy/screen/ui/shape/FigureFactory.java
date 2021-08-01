package com.hacademy.screen.ui.shape;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.KeyStroke;

import com.hacademy.screen.ui.data.Keyboard;
import com.hacademy.screen.ui.data.Multipoint;

public class FigureFactory {
	
	private static Map<KeyStroke, Class<? extends Figure>> map = new HashMap<>();
	public static String find(KeyStroke keyStroke) {
		try {
			return map.get(keyStroke).getSimpleName();
		}
		catch(Exception e) {
			return null;
		}
	}
	static {
		map.put(Keyboard.E, Line.class);
		map.put(Keyboard.Q, Rect.class);
	}
	
	/**
	 * 키값에 따른 도형을 설정하는 메소드
	 * @param key 입력 키(KeyStroke)
	 * @Param param 위치정보
	 * @return 도형(Figure) 객체
	 * @throws 도형이 없을 경우 발생
	 */
	public static Figure create(KeyStroke key, Multipoint point) {
		try {
			Figure figure = map.get(key).getDeclaredConstructor().newInstance();
			Rectangle rectangle = convert(point);
			figure.setBounds(rectangle);
			
			figure.setBorder(BorderFactory.createLineBorder(Color.black, 5));
			
			if(figure instanceof Linear) {
				Linear linear = (Linear)figure;
				linear.thickness = 5;
				
				if(figure instanceof Line) {
					Line line = (Line)figure;
					line.setPoints(point);
				}
			}
			
			return figure;
		}
		catch(Exception e) {
			return null;
		}
	}

	public static Figure refresh(Figure figure, Multipoint point) {
		figure.setBounds(convert(point));
		if(figure instanceof Line) {
			Line line = (Line)figure;
			line.setPoints(point);
		}
		return figure;
	}
	
	public static Rectangle convert(Multipoint point) {
		int x = Math.min(point.getOldX(), point.getX());
		int y = Math.min(point.getOldY(), point.getY());
		int width = Math.abs(point.getOldX() - point.getX());
		int height= Math.abs(point.getOldY() - point.getY());
		return new Rectangle(x, y, width, height);
	}
	
}
