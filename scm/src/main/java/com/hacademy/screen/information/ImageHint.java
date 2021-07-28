package com.hacademy.screen.information;

public enum ImageHint {
	PNG("png"), JPG("jpg"), GIF("gif");
	
	String hint;
	ImageHint(String hint){
		this.hint = hint;
	}
	@Override
	public String toString() {
		return hint;
	}
}
