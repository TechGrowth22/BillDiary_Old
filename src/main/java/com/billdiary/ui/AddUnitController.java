package com.billdiary.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.billdiary.javafxUtility.Popup;
import com.billdiary.model.Unit;
import com.billdiary.service.ProductService;
import com.billdiary.utility.Constants;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.LongStringConverter;

@Controller("AddUnitController")
public class AddUnitController implements Initializable {

	final static Logger LOGGER = LoggerFactory.getLogger(AddUnitController.class); 
	
	@Autowired
	ProductService productService;
	
	@Autowired
	AddProductController addProductController;
	
	@FXML
	TextField addUnitName;
	
	@FXML
	private TableView<Unit> unitTable;
	@FXML TableColumn<Unit,Long>unitId;
	@FXML TableColumn<Unit,String>unitName;
	private ObservableList<Unit> data = FXCollections.observableArrayList();
	private List<Unit> unitList=new ArrayList<>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		unitId.setCellFactory(TextFieldTableCell.<Unit,Long>forTableColumn(new LongStringConverter()));
		try {
			unitTable.setItems(data);
			populate(retrieveData());
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private List<Unit> retrieveData() {
		try {
			if (unitList.isEmpty()) {
				unitList = productService.getUnitList();
			}
			return unitList;
		} catch (Exception e) {
			LOGGER.error("AddUnitController"+e);
		}
		return new ArrayList<Unit>();
	}
	
	private void populate(final List<Unit> units) {
		if (data.isEmpty()) {
			units.forEach(unit->{
				data.add(unit);
				long unitId=unit.getUnitId();
				unit.getDelete().setOnAction(e -> deleteButtonClickedThroughHyperlink(unitId));
			});
		}

	}
	
	private void deleteButtonClickedThroughHyperlink(long unitId) {
		if(productService.deleteUnit(unitId)) {
			getRefreshedTable();
			Popup.showAlert(Constants.SUCCESS_TITLE,Constants.DELETE_UNIT,AlertType.INFORMATION);
		}else {
			Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_DELETE_UNIT,AlertType.ERROR);
		}
	}

	@FXML
	public void addUnit() {
		if(!addUnitName.getText().isEmpty()) {
			if(productService.addUnit(addUnitName.getText())) {
				getRefreshedTable();
				Popup.showAlert(Constants.SUCCESS_TITLE,Constants.ADD_UNIT,AlertType.INFORMATION);
			}else {
				Popup.showErrorAlert(Constants.ERROR_TITLE,Constants.ERROR_ADD_UNIT,AlertType.ERROR);
			}
		}
	}

	public void getRefreshedTable()
	{
		unitList.clear();
		data.clear();
		unitTable.setItems(data);
		populate(retrieveData());
		addProductController.getUnitList();
	}
	
}
