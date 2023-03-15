package com.billdiary.utility;

public class Constants {
	
	public static final String DB_URL="jdbc:hsqldb:file:C:\\bill\\billdiarydb;create=true;shutdown=true;";
	public static final String DB_USERNAME="gajanan";
	public static final String DB_PASSWORD="gajanan";
	public static final String DB_DRIVER="org.hsqldb.jdbc.JDBCDriver";
	public static final String APPLICATION_TITLE="Welcome to Bill Diary version 1.0";
	public static final int WINDOW_WIDTH=1366;
	public static final int WINDOW_HEIGHT=695;
	public static final int POPUP_WINDOW_WIDTH=800;
	public static final int POPUP_WINDOW_HEIGHT=600;
	public static final int SEARCH_CUSTOMER_WIDTH=1000;
	public static final int SEARCH_CUSTOMER_HEIGHT=600;
	public static final int POPUP_UNIT_WINDOW_WIDTH=400;
	public static final int POPUP_UNIT_WINDOW_HEIGHT=350;
	public static final int INVOICE_WINDOW_WIDTH=1200;
	public static final int INVOICE_WINDOW_HEIGHT=650;
	
	public static final String DATE_FORMAT_MM_DD_YYYY = "MM-dd-yyyy";
	
	/**
	 * Constants for application use
	 */
	public static final int ZERO=0;
	public static final String ACTIVE="ACTIVE";
	public static final String INACTIVE="INACTIVE";
	
	/**
	 * Constants for alert, Dialogs, Popup messages
	 */
	public static final String INVOICE_TITLE="Invoice status";
	public static final String INVOICE_SUCCESSFULL_STATUS="Invoice SuccessFull!";
	public static final String INVOICE_UNSUCCESSFULL_STATUS="Invoice Unsucessfull!";
	public static final String INVOICE_SUCCESSFULL_PDF_STATUS="Invoice SuccessFull and PDF generated at";
	public static final String INVOICE_ERROR="Invoice ERROR";
	public static final String INVOICE_CUSTOMER_EMPTY="Customer Name and mobile no can not be empty";
	public static final String DEFAULT_CUSTOMER_NAME="XYZ";
	public static final String DEFAULT_CUSTOMER_ADDRESS="ABCD";
	public static final String DEFAULT_CUSTOMER_MOBILE_NO="1234567890";
	public static final String DATE_FORMAT="MM/dd/yyyy";
	
	/**
	 * Validation Messages
	 */
	public static final String ERROR_TITLE="Error";
	public static final String SUCCESS_TITLE="Success";
	public static final String ERROR_CUSTOMER_VALIDATION="Please insert necessary fields(*)";
	public static final String ERROR_COMMON_VALIDATION="Please insert necessary fields(*)";
	public static final String ERROR_CUSTOMER_FEILD_VALIDATION="Please insert proper data";
	public static final String ERROR_COMMON_WRONG="Something went wrong!";
	
	public static final String PRODUCT_TEMPLATE="PRODUCT_TEMPLATE";
	public static final String PRODUCT_QUANTITY_ERROR="PRODUCT_QUANTITY_ERROR";
	
	
	/**
	 * Add Unit Message
	 */
	public static final String ERROR_ADD_UNIT="Something Went wrong while adding Unit";
	public static final String ADD_UNIT="Unit Added!";
	public static final String ERROR_DELETE_UNIT="Something Went wrong while deleting Unit";
	public static final String DELETE_UNIT="Unit Deleted!";
	
	/**
	 * ManageInvoiceController messages
	 */
	public static final String INVALID_QUANTITY="Please select a valid quantity";
	public static final String INVALID_PRODUCT="Please select a Product";
	
	
	/**
	 * Pagination
	 */
	public static final int rowsPerPage=8;
	public static final String EMPTY_STRING = "";
	public static final String PRODUCT_UPLOAD_FAILURE = "Products upload failed";
	public static final String PRODUCT_UPLOAD_SUCCESS = "Products upload successful";
	public static final String PRODUCT_UPLOAD = "Products Upload";
	
	/**
	 * Barcode
	 */
	public static final int DEFAULT_BARCODE_WIDTH=140;
	public static final int DEFAULT_BARCODE_HEIGHT=50;
	public static final String DEFAULT_MANFACTURER_CODE = "12345";
	public static final String SUCCESS = "SUCCESS";
	public static final String BARCODE_GENERATION_FAILURE = "Barcode Generation Fail, Something went wrong";
	public static int theta = 2;
}
