package com.hacademy.screen.ui;

import java.awt.image.BufferedImage;

@FunctionalInterface
public interface CaptureListener {
	void capture(BufferedImage image);
}
