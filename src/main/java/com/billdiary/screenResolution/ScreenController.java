package com.billdiary.screenResolution;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javafx.stage.Screen;

public class ScreenController {

	//static GraphicsDevice graphicDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	static javafx.geometry.Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
	public static double getScreenWidth() {
		double width = visualBounds.getWidth();
		return width;
	}
	public static double getScreenHeight() {
		double height =  visualBounds.getHeight();
		return height-25;
	}
	
}
