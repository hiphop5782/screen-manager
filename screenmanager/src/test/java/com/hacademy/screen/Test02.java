package com.hacademy.screen;

import com.hacademy.screen.ui.GlassFrame;

public class Test02 {
	public static void main(String[] args) {
		GlassFrame frame = GlassFrame.builder()
				
//													.fullscreen()
													.size(300, 300)
													
//													.transparent(30)
													.backgroundImage(20)
													
													.draggable()
													.escEnable()
													.build(true);
	}
}
