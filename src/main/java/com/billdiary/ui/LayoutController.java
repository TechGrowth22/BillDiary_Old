package com.billdiary.ui;

import org.springframework.stereotype.Component;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


@Component
public class LayoutController {
	
	Stage stage;
	Scene scene;
//	int width=1200;
//	int hight=650;
	
	public void loadWindow(Parent root,String title,double width,double height)
	{
		stage = new Stage();
	    scene = new Scene(root, width-20, height-20); 
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
	}

}
