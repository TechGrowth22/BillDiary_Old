package com.billdiary.ui;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
///import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import com.billdiary.config.SpringFxmlLoader;
import com.billdiary.model.User;
import com.billdiary.screenResolution.ScreenController;
import com.billdiary.service.LoginService;
import com.billdiary.utility.Constants;
import com.billdiary.utility.URLS;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;


@Controller("LoginController")
public class LoginController {
	
	public static StackPane MainStage= new StackPane();
	
	final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	public LayoutController layoutController;
	
	@Autowired
	private LoginService loginService;

	@Autowired
	private User user;
	
	@FXML private Text actiontarget;
	@FXML private TextField textField;
	@FXML private PasswordField passwordField;
	private Parent home;
	
	@Autowired
	private ConfigurableApplicationContext context;
	
	@Autowired
	SpringFxmlLoader loader;

    
    @FXML protected void handleSignInButtonAction(ActionEvent event) throws IOException {
    	LOGGER.info("Inside the handleSignInButtonAction");
    	String userName=textField.getText();
    	String password=passwordField.getText();
    	
        /*Stage primaryStage=new Stage();
        primaryStage.setScene(new Scene(home, ScreenController.getScreenWidth(), ScreenController.getScreenHeight()));
        primaryStage.centerOnScreen();
        primaryStage.show();*/
        
    	if(userName!=null && password!=null)
    	{
    		user.setUserName(userName);
    		user.setPassword(password);
    		if(loginService.doLogin(user))
    		{
    			actiontarget.setText("Login Successfull");
    			((Node)(event.getSource())).getScene().getWindow().hide();
    	        home = (Parent) loader.load(URLS.HOME_PAGE);
    			layoutController.loadWindow(home, Constants.APPLICATION_TITLE,ScreenController.getScreenWidth(), ScreenController.getScreenHeight());
    		}
    		else {
    			actiontarget.setText("Login failed");
    		}	
    	}else
    	{
    		actiontarget.setText("UserName & Password cannot be valid");
    	}
    	LOGGER.info("Exit the  handleSignInButtonAction");
    }
 
	
    
	
}
