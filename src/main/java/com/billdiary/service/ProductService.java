package com.billdiary.service;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.billdiary.daoUtility.EntityTOModelMapper;
import com.billdiary.daoUtility.ModelTOEntityMapper;
import com.billdiary.entities.ProductCategoryEntity;
import com.billdiary.entities.ProductEntity;
import com.billdiary.entities.UnitEntity;
import com.billdiary.model.Product;
import com.billdiary.model.ProductColumns;
import com.billdiary.model.Unit;
import com.billdiary.repository.ProductCategoryRepository;
import com.billdiary.repository.ProductRepository;
import com.billdiary.repository.UnitRepository;
import com.billdiary.serviceImpl.CustomMappingStrategy;
import com.billdiary.utility.Constants;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import javafx.collections.ObservableList;

@Service
public class ProductService {
	
	final static Logger LOGGER = LoggerFactory.getLogger(ProductService.class); 
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UnitRepository unitRepository;
	
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	
	@Autowired
	EntityTOModelMapper entityTOModelMapper;
	
	@Autowired
	ModelTOEntityMapper modelTOEntityMapper;
	
	@Autowired
	CsvWriterService CsvWriterServiceImpl;
	
	@Autowired
	CsvReaderService csvReaderServiceImpl;
	
	@Value("${product.template.columns}")
	public List<String> product_table_columns;
	
	
	public List<Product> fetchProducts()
	{
		LOGGER.info("Entering in the fetchProducts");
		List<Product> productList=new ArrayList<>();
		List<ProductEntity> productEntityList=new ArrayList<>();
		try {
			productEntityList=productRepository.findAll();
		}catch(Exception e)
		{
			LOGGER.error(e.getMessage(),e);
		}
		productList=entityTOModelMapper.getProductModels(productEntityList);
		LOGGER.info("Exiting from the fetchProducts");
		return productList;
	}
	
	public Double getTotalInventoryCost() {
		Double totalInvestment = productRepository.getTotalInvestment();
		if(null == totalInvestment)
			return 0.0;
		return totalInvestment;
	}
	
	
	
	public boolean deleteProduct(int productId)
	{
		try {
			productRepository.deleteById(productId);
		}catch(Exception e)
		{
			LOGGER.error(e.getMessage(),e);
			return false;
		}
		return true;
	}
	public List<Product> saveProduct(ObservableList<Product> obproductList) {
		List<ProductEntity>  productEntityList = modelTOEntityMapper.getProdEntitiesFromObservableList(obproductList);
		List<ProductEntity> updatedProdEntities = new ArrayList<>();
		updatedProdEntities=productRepository.saveAll(productEntityList);
		List<Product> productList =new ArrayList<>();
		productList=entityTOModelMapper.getProductModels(updatedProdEntities);
		return productList;	
	}
	public boolean addProduct(Product prod) {
		boolean productAdded=false;
		ProductEntity prodEntity=modelTOEntityMapper.getProductEntity(prod);
		prodEntity=productRepository.save(prodEntity);
		productAdded=true;
		return productAdded;
		
	}
	
	public Product updateProduct(Product product) {
		Product updatedProduct=null;
		ProductEntity updatedProEnitity=null;
		ProductEntity proEntity=modelTOEntityMapper.getProductEntity(product);
		proEntity.setId(product.getProductId());
		updatedProEnitity=productRepository.save(proEntity);
		updatedProduct=entityTOModelMapper.getProductModel(updatedProEnitity);
		return updatedProduct;
	}
	public List<String> getCategoryList() {
		List<String> categoryList=new ArrayList<>();
		List<ProductCategoryEntity> categoryEntityList=productCategoryRepository.findAll();
		categoryList=entityTOModelMapper.getProductCategoryList(categoryEntityList);
		return categoryList;
	}
	public List<Unit> getUnitList() {
		List<Unit> unitList=new ArrayList<>();
		List<UnitEntity> unitEntityList=unitRepository.findAll();
		unitList=entityTOModelMapper.getUnitList(unitEntityList);
		return unitList;
	}
	public boolean addUnit(String unitName) {
		UnitEntity unitEntity=new UnitEntity();
		unitEntity.setName(unitName);
		unitEntity.setUnitId(0);
		UnitEntity updatedUnitEntity=unitRepository.save(unitEntity);
		boolean flag=null==updatedUnitEntity?false:true;
		return flag;
	}
	public boolean deleteUnit(long unitId) {
		boolean deleted=false;
		//deleted=unitDAO.deleteUnit(unitId);
		unitRepository.deleteById(unitId);
		deleted=true;
		return deleted;
	}
	
	
	/**
	 * To get no of products from the System.
	 */
	public long getProductCount() {
		long count=productRepository.count();
		return count;
	}
	public List<Product> getProducts(int pages, int index,int rowsPerPage) {
		LOGGER.info("Entering in the getProducts");
		List<Product> productList=new ArrayList<>();
		try {
			Page<ProductEntity> productEntities = productRepository.findAll(PageRequest.of(index, rowsPerPage));
			productEntities.forEach(productEntity->{
				productList.add(entityTOModelMapper.getProductModel(productEntity));
			});
		}catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("Exting from the getProducts");
		return productList;
	}
	public boolean checkProductCode(long code) {
		boolean flag=false;
		long checkProductCode=productRepository.countByProductCode(code);
		flag=checkProductCode==0?false:true;
		return flag;
	}
	public long getProductCode() {
		long prdCode=productRepository.getProductCode();
		
		return prdCode;
	}
	
	public List<Product> bulkUpdateProducts(List<Product> products ) {
		List<ProductEntity> productEntities=modelTOEntityMapper.getProdEntities(products);
		productEntities=productRepository.saveAll(productEntities);
		List<Product> updatedProducts=entityTOModelMapper.getProductModels(productEntities);
		return updatedProducts;
		
	}
	
	/**
	 * Updating the stock of product after purchasing the product
	 * @param id
	 * @param quantity
	 * @return Product
	 */
	public int purchaseProductStock(int id,double quantity) {
		int stockUpdated=productRepository.purchaseProductStock(id,quantity);
		return stockUpdated;
	}
	public int updateProductStock(int id,double quantity) {
		int result=productRepository.updateProductStock(id,quantity);
		return result;
	}


	public List<Product> getLowStockProducts() {
		List<Product> productList=new ArrayList<>();
		List<ProductEntity> productEntityList=productRepository.findAllLowStockProducts();
		productList=entityTOModelMapper.getProductModels(productEntityList);
		return productList;
	}
	
	
	public String createProductTemplate() {
		LOGGER.info("Entering into the createProductTemplate");
		StringBuilder message = new StringBuilder("");
		File templateDir = new File(System.getProperty("user.dir")+File.separator+"templates");
		if(!templateDir.exists()) {
			templateDir.mkdirs();
    	}
        final File outputFile = new File(templateDir.getAbsolutePath()+File.separator+"Products_Template"+ ".csv");
        
        try {
        	final CustomMappingStrategy<ProductColumns> mappingStrategy = new CustomMappingStrategy<>();
        	mappingStrategy.setHeader(product_table_columns.toArray(new String[0]));
        	mappingStrategy.setType(ProductColumns.class);
        	
			String filepath= CsvWriterServiceImpl.writeCsvFromBean(outputFile.toPath(), getHeaderOfProductColumnsCsv(),mappingStrategy);
			message.append("Product template has been created "+ filepath);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(),e);
			message.append("Something went wrong, Product template is not created");
		}
        LOGGER.info("Exting from the createProductTemplate");
        return message.toString();
	}
	
	public List<ProductColumns> getHeaderOfProductColumnsCsv(){ 
	
		List<ProductColumns> listProductColumns =new ArrayList<ProductColumns>();
		ProductColumns productColumns= new ProductColumns();
		productColumns.setProduct_id("product_id");
		productColumns.setProductCode("productCode");
		productColumns.setProductName("productName");
		productColumns.setProductCategory("productCategory");
		productColumns.setProductHSNCode("productHsncode");
		productColumns.setDescription("description");
		productColumns.setBuyPrice("buyPrice");
		productColumns.setBuyPriceGST("buyPriceGST");
		productColumns.setBuyPriceGSTPercentage("buyPriceGSTPercentage");
		productColumns.setSellPrice("sellPrice");
		productColumns.setSellPriceGST("sellPriceGST");
		productColumns.setSellPriceGSTPercentage("sellPriceGSTPercentage");
		productColumns.setDiscount("discount");
		productColumns.setStock("stock");
		productColumns.setLowStockThershold("lowStockThershold");
		productColumns.setUnitEntity("unitEntity");
		listProductColumns.add(productColumns);		
		return listProductColumns;

	}
	
	public String uploadProducts(Path path) {
		 LOGGER.info("Entering into the uploadProducts");
		try {
			ColumnPositionMappingStrategy mappingStrategy = new ColumnPositionMappingStrategy();
	    	mappingStrategy.setType(ProductColumns.class);
	    	mappingStrategy.setColumnMapping(product_table_columns.toArray(new String[0]));
			List<ProductColumns> list =csvReaderServiceImpl.buildCsvToBean(path, mappingStrategy);
			LOGGER.info(list.toString());
			list.remove(0);
			
			List<ProductEntity> prodlist= modelTOEntityMapper.getProductEntityFromProductColumns(list);
			for(ProductEntity productEntity:prodlist) {
				List<ProductEntity> exitingProduct = productRepository.findByIdOrProductCode(productEntity.getId(), productEntity.getProductCode());
				if(null != exitingProduct && exitingProduct.size() > 0) {
					productEntity.setId(exitingProduct.get(0).getId());
					productRepository.save(productEntity);
				}else {
					productRepository.save(productEntity);
				}
			}
			
		}catch(Exception e) {
			 LOGGER.error(e.getMessage(),e);
			 return Constants.PRODUCT_UPLOAD_FAILURE;
		}
		 LOGGER.info("Exting from the uploadProducts");
		return Constants.PRODUCT_UPLOAD_SUCCESS;
	}

	public List<ProductEntity> getAllProducts() {
		return productRepository.findAll();
	}
	
	
	public String getBarcodeFromProduct(ProductEntity product) {
		String baseBarcode = "89012345";
		String fourDigit = String.format("%04d",product.getProductCode());
		
		String barcodewithoutChecksum = baseBarcode.concat(fourDigit);  
		StringBuffer buffer = new StringBuffer();
		buffer.append(barcodewithoutChecksum);
		buffer.append(calculateCheckSum(barcodewithoutChecksum));
		return buffer.toString();
		
	}
	

	public int calculateCheckSum(String barcode) {
		char[] digits = barcode.toCharArray();
		int oddSum = 0;
		int evenSum = 0;
		for(int i = 0 ; i< digits.length ; i++) {
			if(i%2 == 0) {
				evenSum =evenSum + Character.getNumericValue(digits[i]);
			}else {
				oddSum = oddSum + Character.getNumericValue(digits[i]);
			}
		}
		
		int checksum = (10 -((3*oddSum + evenSum )%10))%10;
		return checksum;
	}
	
	
}
