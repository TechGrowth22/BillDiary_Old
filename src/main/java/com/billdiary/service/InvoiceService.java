package com.billdiary.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.billdiary.daoUtility.ModelTOEntityMapper;
import com.billdiary.entities.InvoiceEntity;
import com.billdiary.entities.ShopEntity;
import com.billdiary.model.Customer;
import com.billdiary.model.Invoice;
import com.billdiary.model.Product;
import com.billdiary.repository.InvoiceRepository;
import com.billdiary.utility.Constants;
import com.billdiary.utility.PdfGenaratorUtil;

import javafx.collections.ObservableList;

@Service
public class InvoiceService {

	final static Logger LOGGER = LoggerFactory.getLogger(InvoiceService.class);

	@Autowired
	PdfGenaratorUtil pdfGenaratorUtil;

	@Autowired
	ShopService shopService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	InvoiceRepository invoiceRepository;

	@Autowired
	ModelTOEntityMapper modelTOEntityMapper;

	public boolean save(Invoice inv) {
		InvoiceEntity invEntity = new InvoiceEntity();
		invEntity = modelTOEntityMapper.getInvoiceEntity(inv);
		InvoiceEntity savedInvEntity = invoiceRepository.save(invEntity);
		if (null != savedInvEntity)
			return true;
		else
			return false;
	}

	public String generateInvoiceNO() {
		String invoiceNo = null;
		invoiceNo = String.valueOf(invoiceRepository.generateInvoiceNO() + 1);
		return invoiceNo;
	}
	
	public Double getTotalInvoicesAmount() {
		
		Double d = invoiceRepository.totalInvoicesAmount();
		if(null == d)
			return 0.0;
		return d;
	}

	public boolean GenerateInvoicePDF(ObservableList<Product> data, Map<String, String> valueMap) throws Exception {
		boolean invoicePDFGenerated = false;
		LOGGER.info("Entering into GenerateInvoicePDF");
		Map<String, Object> templateData = new HashMap<String, Object>();

		List<ShopEntity> shoplist = shopService.getShopDetails();
		if (!shoplist.isEmpty()) {
			ShopEntity shop = shoplist.get(0);
			templateData.put("company_name", shop.getShopName());
			templateData.put("company_address", shop.getAddress());
			templateData.put("company_city_zip_state",
					shop.getCity() + "-" + shop.getPincode() + ", " + shop.getState());
			templateData.put("company_phone_fax", "Fax:-");
			templateData.put("company_email_web", shop.getEmailId() + ", Web: " + shop.getWebsite());
		} else {
			templateData.put("company_name", "XYZ Ltd.");
			templateData.put("company_address", "Near Old Market");
			templateData.put("company_city_zip_state", "baramati-413102, Maharashtra");
			templateData.put("company_phone_fax", "Fax:-");
			templateData.put("company_email_web", "xyz@gmail.com, Web- www.xyz.com");
		}

		templateData.put("issue_date_label", "Invoice");
		templateData.put("invoice_number", "ID: " + valueMap.get("invNO"));
		templateData.put("issue_date",
				Constants.EMPTY_STRING == valueMap.get("invIssueDate") ? LocalDate.now().toString()
						: valueMap.get("invIssueDate"));
		templateData.put("invoice_title", "Invoice");

		Customer customer = getCustomer(trimCustomerID(valueMap.get("invCustName")));

		templateData.put("bill_to_label", "Bill To");
		templateData.put("client_name", customer.getCustomerName());
		templateData.put("client_address", null == customer.getAddress() ? "" : customer.getAddress());
		StringBuffer buff = new StringBuffer();
		buff.append(null == customer.getCity() ? "" : customer.getCity() + "-");
		buff.append(null == customer.getZipCode() ? "" : customer.getZipCode() + ", ");
		buff.append(null == customer.getState() ? "" : customer.getState());
		templateData.put("client_city_zip_state", buff.toString());
		templateData.put("client_phone_fax", customer.getMobile_no() + ", Fax: -");
		templateData.put("client_email", customer.getEmailID());
		templateData.put("client_other", customer.getAddAdditionalInfo());

		// net days label
		templateData.put("net_term_label", "NET-");
		templateData.put("due_date_label", "Due On");

		templateData.put("net_term", "5 Days");
		templateData.put("due_date", valueMap.get("invDueDate"));
		// data.put("po_number","001");

		templateData.put("currency_label", "*All prices are in");
		templateData.put("currency", "INR");

		templateData.put("item_row_number_label", "S. No");
		templateData.put("item_description_label", "ITEM");
		templateData.put("item_rate_label", "Rate(Rs)");
		templateData.put("item_quantity_label", "Quantity");
		templateData.put("item_value_label", "value");
		templateData.put("item_discount_label", "DISCOUNT%");
		templateData.put("item_tax_label", "GST%");
		templateData.put("item_line_total_label", "SUB-TOTAL");

		List<Product> productList = data.subList(0, data.size());
		templateData.put("productList", productList);

		templateData.put("amount_subtotal_label", "Total");
		templateData.put("amount_subtotal", valueMap.get("totalAmount"));

		templateData.put("taxable_amount_label", "Taxable Amount");
		templateData.put("taxable_amount", valueMap.get("taxableAmt"));

		templateData.put("tax_name_SGST", "SGST");
		templateData.put("SGST", valueMap.get("totalSGST"));

		templateData.put("tax_name_CGST", "CGST");
		templateData.put("CGST", valueMap.get("totalCGST"));

		templateData.put("amount_total_label", "Final Amt");
		templateData.put("amount_total", valueMap.get("finalAmount"));

		templateData.put("amount_paid_label", "Paid ");
		templateData.put("amount_paid", valueMap.get("paidAmount"));

		templateData.put("amount_due_label", "Due Amount");
		templateData.put("amount_due", valueMap.get("amountDue"));

		templateData.put("terms_label", "Terms & Conditions*");
		templateData.put("terms","Thank you very much! \n In case of return a product, Product should return before 5 days");
		templateData.put("payment_type", "Payment Type");
		templateData.put("payment_method", "cash");
		LOGGER.info("Generated Invoive PDF stated ");
		pdfGenaratorUtil.createPdf("InvoiceTemplate", templateData, valueMap.get("invNO"));
		invoicePDFGenerated = true;
		LOGGER.info("Exiting from GenerateInvoicePDF");
		return invoicePDFGenerated;
	}

	public Customer getCustomer(String custID) {
		Customer cust = customerService.getCustomerById(custID);
		return cust;
	}

	public String trimCustomerID(String displayName) {
		String custID = "0";
		if (null != displayName) {
			int i = displayName.indexOf(' ');
			custID = displayName.substring(0, i);
		}
		return custID;
	}

}
