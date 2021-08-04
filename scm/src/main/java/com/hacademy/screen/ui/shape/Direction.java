package com.hacademy.screen.ui.shape;

import java.awt.Cursor;

public enum Direction {
	UP(new Cursor(Cursor.N_RESIZE_CURSOR)), 
	UPLEFT(new Cursor(Cursor.NW_RESIZE_CURSOR)), 
	UPRIGHT(new Cursor(Cursor.NE_RESIZE_CURSOR)), 
	RIGHT(new Cursor(Cursor.E_RESIZE_CURSOR)), 
	LEFT(new Cursor(Cursor.W_RESIZE_CURSOR)), 
	DOWN(new Cursor(Cursor.S_RESIZE_CURSOR)), 
	DOWNLEFT(new Cursor(Cursor.SW_RESIZE_CURSOR)), 
	DOWNRIGHT(new Cursor(Cursor.SE_RESIZE_CURSOR)),
	DEFAULT(new Cursor(Cursor.DEFAULT_CURSOR));
	
	private Cursor cursor;
	private Direction(Cursor cursor) {
		this.cursor = cursor;
	}
	public Cursor getCursor() {
		return this.cursor;
	}
}
