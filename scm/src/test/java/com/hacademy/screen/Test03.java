package com.hacademy.screen;

import com.hacademy.screen.ui.GlassFrame;

public class Test03 {
	public static void main(String[] args) {
		GlassFrame.builder()
							.dynamicDrawingMode()
//							.staticDrawingMode()
						.build(true);
	}
}
