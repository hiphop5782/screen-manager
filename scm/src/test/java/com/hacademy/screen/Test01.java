package com.hacademy.screen;

import java.awt.GraphicsDevice;
import java.awt.Rectangle;

import com.hacademy.screen.information.Monitor;
import com.hacademy.screen.information.ScreenInformationReader;

public class Test01 {
	public static void main(String[] args) {
		for(GraphicsDevice device : ScreenInformationReader.getAllGraphicDevices()) {
			System.out.println(device);
		}
		
		for(Rectangle rectangle : ScreenInformationReader.getAllGraphicDevicesResolution()) {
			System.out.println(rectangle);
		}
		
		System.out.println("좌측 모니터 정보");
		System.out.println(ScreenInformationReader.getDevice(Monitor.FIRST));
		System.out.println(ScreenInformationReader.getDeviceResolution(Monitor.FIRST));
		
		System.out.println("기본 모니터 정보");
		System.out.println(ScreenInformationReader.getDefaultDevice());
		System.out.println(ScreenInformationReader.getDefaultDeviceResolution());
		
		System.out.println("커서가 위치한 모니터 정보");
		System.out.println(ScreenInformationReader.getCursorLocatedDevice());
		System.out.println(ScreenInformationReader.getCursorLocatedDeviceResolution());
		System.out.println(ScreenInformationReader.getCursorLocation());
	}
}
