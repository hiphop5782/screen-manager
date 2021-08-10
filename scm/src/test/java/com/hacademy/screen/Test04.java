package com.hacademy.screen;

import com.hacademy.screen.ui.data.Keyboard;
import com.hacademy.screen.ui.data.Multipoint;
import com.hacademy.screen.ui.shape.Figure;
import com.hacademy.screen.ui.shape.FigureFactory;

public class Test04 {
	public static void main(String[] args) {
		Multipoint point = new Multipoint(100, 100, 200, 200);
		Figure figure = FigureFactory.create(Keyboard.E, point);
		System.out.println(figure);
	}
}
