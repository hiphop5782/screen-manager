package com.hacademy.screen.information;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenInformationReader {
	private static Robot robot;
	static {
		try {
			robot = new Robot();
		}
		catch(Exception ex) {
			System.err.print("[ERROR]");
			System.err.println(ex.getMessage());
		}
	}
	
	/**
	 * 모든 그래픽 장치의 정보를 반환하는 기능
	 * @return 그래픽 장치 정보 배열
	 */
	public static GraphicsDevice[] getAllGraphicDevices() {
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		return env.getScreenDevices();
	}
	
	/**
	 * 모든 그래픽 장치 수를 반환하는 기능
	 * @return 그래픽 장치 수
	 */
	public static int getAllGraphicDevicesCount() {
		return getAllGraphicDevices().length;
	}
	
	/**
	 * 모든 그래픽 장치의 위치/크기 정보를 반환하는 기능
	 * @return Rectangle 정보 배열
	 */
	public static Rectangle[] getAllGraphicDevicesResolution() {
		GraphicsDevice[] devices = getAllGraphicDevices();
		Rectangle[] rectangles = new Rectangle[devices.length];
		for(int i=0; i < devices.length; i++) {
			rectangles[i] = devices[i].getDefaultConfiguration().getBounds();
		}
		return rectangles;
	}
	
	/**
	 * 특정 순서의 그래픽 장치 정보를 반환하는 기능
	 * @param monitor 그래픽 장치 번호 enum
	 * @return 그래픽 장치 정보
	 */
	public static GraphicsDevice getDevice(Monitor monitor) {
		GraphicsDevice[] devices = getAllGraphicDevices();
		if(devices.length < monitor.ordinal()) 
			throw new ArrayIndexOutOfBoundsException("존재하지 않는 모니터 번호 지정");
		return devices[monitor.ordinal()];
	}
	
	/**
	 * 특정 순서의 그래픽 장치 위치/크기 정보를 반환하는 기능
	 * @param monitor 그래픽 장치 번호 enum
	 * @return 그래픽 장치 Rectangle 정보
	 */
	public static Rectangle getDeviceResolution(Monitor monitor) {
		return getDevice(monitor).getDefaultConfiguration().getBounds();
	}
	
	/**
	 * 메인 그래픽 장치의 정보를 반환하는 기능
	 * @return 그래픽 장치 정보
	 */
	public static GraphicsDevice getDefaultDevice() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	}
	
	/**
	 * 메인 그래픽 장치의 위치/크기 정보를 반환하는 기능
	 * @return 그래픽 장치의 Rectangle 정보
	 */
	public static Rectangle getDefaultDeviceResolution() {
		return getDefaultDevice().getDefaultConfiguration().getBounds();
	}
	
	/**
	 * 현재 커서가 위치한 장치의 정보를 반환하는 기능
	 * @return 그래픽 장치의 정보
	 */
	public static GraphicsDevice getCursorLocatedDevice() {
		return MouseInfo.getPointerInfo().getDevice();
	}
	
	/**
	 * 현재 커서가 위치한 장치의 위치/크기 정보를 반환하는 기능
	 * @return 그래픽 장치의 Rectangle 정보
	 */
	public static Rectangle getCursorLocatedDeviceResolution() {
		return getCursorLocatedDevice().getDefaultConfiguration().getBounds();
	}
	
	/**
	 * 현재 커서의 위치 정보
	 * @return Point 객체 정보
	 */
	public static Point getCursorLocation() {
		return MouseInfo.getPointerInfo().getLocation();
	}
	
	/**
	 * 특정 영역을 캡쳐하는 기능
	 * @param rectangle 영역 정보 객체
	 * @return 캡쳐된 이미지 객체
	 */
	public static BufferedImage capture(Rectangle rectangle) {
		return robot.createScreenCapture(rectangle);
	}
	
	/**
	 * 지정한 모니터를 캡쳐하는 기능
	 * @param monitor 캡쳐할 모니터 Enum 객체
	 * @return 캡쳐된 이미지 객체
	 */
	public static BufferedImage getDeviceImage(Monitor monitor) {
		return robot.createScreenCapture(getDeviceResolution(monitor));
	}
	
	/**
	 * 기본 모니터를 캡쳐하는 기능
	 * @return 캡쳐된 기본 모니터 이미지 객체
	 */
	public static BufferedImage getDefaultDeviceImage() {
		return robot.createScreenCapture(getDefaultDeviceResolution());
	}
	
	/**
	 * 커서가 위치한 모니터를 캡쳐하는 기능
	 * @return 캡쳐된 기본 모니터 이미지 객체
	 */
	public static BufferedImage getCursorLocatedDeviceImage() {
		return robot.createScreenCapture(getCursorLocatedDeviceResolution());
	}
	
	/**
	 * 커서 반경을 캡쳐하는 기능
	 * @param width 캡쳐할 폭
	 * @param height 캡쳐할 높이
	 * @return 캡쳐된 이미지 객체
	 */
	public static BufferedImage getCursorSurroundImage(int width, int height) {
		Point p = getCursorLocation();
		int x = p.x - width / 2;
		int y = p.y - height / 2;
		Rectangle rectangle = new Rectangle(x, y, width, height);
		return capture(rectangle);
	}
	
	/**
	 * 커서 반경을 정사각형으로 캡쳐하는 기능
	 * @param radius 캡쳐 반경
	 * @return 캡쳐된 이미지 객체
	 */
	public static BufferedImage getCursorSurroundImage(int radius) {
		return getCursorSurroundImage(radius, radius);
	}
	
	/**
	 * 이미지를 지정된 타입의 바이트 배열로 반환하는 기능
	 * @param rectangle 캡쳐 영역
	 * @param hint 이미지 타입(PNG, JPG, GIF)
	 * @return 캡쳐된 이미지 바이트 데이터
	 * @throws IOException 변환 예외
	 */
	public static byte[] capture(Rectangle rectangle, ImageHint hint) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(capture(rectangle), hint.toString(), out);
		return out.toByteArray();
	}
	
	/**
	 * 특정 모니터 영역을 캡쳐하여 바이트 데이터로 반환하는 기능
	 * @param monitor 모니터 정보 enum 객체
	 * @param hint 이미지 타입(PNG, JPG, GIF)
	 * @return 캡쳐된 이미지 바이트 데이터
	 * @throws IOException 변환 예외
	 */
	public static byte[] getDeviceImageData(Monitor monitor, ImageHint hint) throws IOException {
		Rectangle rectangle = getDeviceResolution(monitor);
		return capture(rectangle, hint);
	}
	
	/**
	 * 커서가 위치한 모니터 캡쳐 이미지를 바이트 데이터로 반환하는 기능
	 * @param hint 이미지 타입(PNG, JPG, GIF)
	 * @return 캡쳐된 이미지 바이트 데이터
	 * @throws IOException 변환 예외
	 */
	public static byte[] getCursorLocatedDeviceImageData(ImageHint hint) throws IOException {
		Rectangle rectangle = getCursorLocatedDeviceResolution();
		return capture(rectangle, hint);
	}
	
	/**
	 * 기본 모니터 캡쳐 이미지를 바이트 데이터로 반환하는 기능
	 * @param hint 이미지 타입(PNG, JPG, GIF)
	 * @return 캡쳐된 이미지 바이트 데이터
	 * @throws IOException 변환 예외
	 */
	public static byte[] getDefaultDeviceImageData(ImageHint hint) throws IOException {
		Rectangle rectangle = getDefaultDeviceResolution();
		return capture(rectangle, hint);
	}
}
