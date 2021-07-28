package com.hacademy.screen.ui.shape;

import java.util.HashMap;
import java.util.Map;

import javax.swing.KeyStroke;

import com.hacademy.screen.ui.data.Keyboard;
import com.hacademy.screen.ui.data.Multipoint;

public class FigureFactory {
	
	private static Map<KeyStroke, Class<? extends Figure>> map = new HashMap<>();
	static {
		map.put(Keyboard.E, Line.class);
	}
	
	/**
	 * 키값에 따른 도형을 설정하는 메소드
	 * @param key 입력 키(KeyStroke)
	 * @Param param 위치정보
	 * @return 도형(Figure) 객체
	 * @throws 도형이 없을 경우 발생
	 */
	public static Figure create(KeyStroke key, Multipoint param) {
		try {
			Figure figure = map.get(key).getDeclaredConstructor().newInstance();
			if(figure instanceof Line) {
				Line line = Line.class.cast(figure);
				line.setX1(param.getOldX());
				line.setX2(param.getX());
				line.setY1(param.getOldY());
				line.setY2(param.getY());
			}
			return figure;
		}
		catch(Exception e) {
			return null;
		}
	}

	public static Figure refresh(Figure figure, Multipoint point) {
		if(figure instanceof Line) {
			Line line = (Line)figure;
			line.setX1(point.getOldX());
			line.setY1(point.getOldY());
			line.setX2(point.getX());
			line.setY2(point.getY());
		}
		return figure;
	}
	
}
