package com.billdiary.ui;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.billdiary.entities.ShopEntity;

import com.billdiary.service.ShopService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

@Controller("ShopController")
@Service
public class ShopController  {

	@FXML TextField ShopName;
	@FXML TextField Email;
	@FXML TextField Website;
	@FXML TextField Address;
	@FXML TextField City;
	@FXML TextField State;
	@FXML TextField Pincode;
	@FXML TextField GSTIN;
	@FXML TextField Phone;
	@FXML TextField Country;
	@Autowired
	private ShopService shopService;
	List<ShopEntity> shop = new ArrayList<>();
	
	@FXML public void SaveShopDetails(ActionEvent event)
	{
		ShopEntity addShopDetails = new ShopEntity();
		addShopDetails.setShopName(ShopName.getText());
		addShopDetails.setAddress(Address.getText());
		addShopDetails.setCity(City.getText());
		addShopDetails.setCountry(Country.getText());
		addShopDetails.setEmailId(Email.getText());
		addShopDetails.setPhone(Phone.getText());
		addShopDetails.setGSTIN(GSTIN.getText());
		addShopDetails.setWebsite(Website.getText());
		addShopDetails.setPincode(Pincode.getText());
		addShopDetails.setState(State.getText());
		addShopDetails.setLogo(Logo.getText());
		if(shop.size()==0)
		{
			shopService.saveShopDetails(addShopDetails);
			((Node)(event.getSource())).getScene().getWindow().hide();
		}
		else
		{
			shopService.editShopDetails(addShopDetails);
			((Node)(event.getSource())).getScene().getWindow().hide();
		}
		
	}
	@FXML public void CloseWindow(ActionEvent event)
	{
		((Node)(event.getSource())).getScene().getWindow().hide();
	}
	public void getShopDetails() {
		
		shop=shopService.getShopDetails();
		if(shop.size()!=0)
		{
			for(ShopEntity e:shop)
			{
				System.out.println(e.getShopName());
				ShopName.setText(e.getShopName());
				ShopName.setDisable(true);
				Address.setText(e.getAddress());
				Address.setDisable(true);
				City.setText(e.getCity());
				City.setDisable(true);
				Country.setText(e.getCountry());
				Country.setDisable(true);
				Email.setText(e.getEmailId());
				Email.setDisable(true);
				Phone.setText(e.getPhone());
				Phone.setDisable(true);
				GSTIN.setText(e.getGSTIN());
				GSTIN.setDisable(true);
				Website.setText(e.getWebsite());
				Website.setDisable(true);
				Pincode.setText(e.getPincode());
				Pincode.setDisable(true);
				State.setText(e.getState());
				State.setDisable(true);
				Logo.setText(e.getLogo());
				Logo.setDisable(true);
			}
		}	
		
	}
	
	@FXML public void EditDetails()
	{
		ShopName.setDisable(false);
		Address.setDisable(false);
		City.setDisable(false);
		Country.setDisable(false);
		Email.setDisable(false);
		Phone.setDisable(false);
		GSTIN.setDisable(false);
		Website.setDisable(false);
		Pincode.setDisable(false);
		State.setDisable(false);
		Logo.setDisable(false);
	}
	
	@FXML TextField Logo;
	@FXML public void browseLogo() throws IOException {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		if(selectedFile!=null)
		{
			
				File first = new File(selectedFile.getAbsolutePath());
				//File dir =new File("C:\\bill\\Logos");
				//URL dirURL = getClass().getResource("/logo");
				File logoDir = new File("/logo");
				if(!logoDir.exists())
				{
					logoDir.mkdir();
				}
				//File second = new File("C:\\bill\\Logos\\userName.png");
				URL url = getClass().getResource("/logo/ShopLogo.png");
				File logoFile = new File(url.getPath());
				if(!logoFile.exists())
				{
					logoFile.createNewFile();	
				}
				
				/*FileUtils.copyFileToDirectory(first,second);*/
				copyFile(first,logoFile);
				System.out.println(true);
				Logo.setText(logoFile.getName());
		}
		else
		{
			System.out.println(false);
			
		}
	}
	
	private  void copyFile(File srcFile, File destFile) throws IOException 
    {
            InputStream oInStream = new FileInputStream(srcFile);
            OutputStream oOutStream = new FileOutputStream(destFile);

            // Transfer bytes from in to out
            byte[] oBytes = new byte[1024];
            int nLength;
            BufferedInputStream oBuffInputStream = 
                            new BufferedInputStream( oInStream );
            while ((nLength = oBuffInputStream.read(oBytes)) > 0) 
            {
                oOutStream.write(oBytes, 0, nLength);
            }
            oInStream.close();
            oOutStream.close();
    }

}
