package com.hacademy.screen;

import com.hacademy.screen.ui.GlassFrame;

public class Test02 {
	public static void main(String[] args) {
		GlassFrame frame = GlassFrame.builder()
				
													.fullscreen()
													.alwaysOnTop()
//													.size(300, 300)
													
													.transparent(60)
//													.backgroundImage(5)
													
//													.draggable()
													.escEnable()
													
													.mouseTracking()
													.mouseInvisible()
//													.areaSelectionMode(img->{
//														System.out.println("clear");
//													})
				
//													.staticSelectionMode()
//													.staticSelectionMode(img->System.out.println("clear"))
													
												.build(true);
	}
}
