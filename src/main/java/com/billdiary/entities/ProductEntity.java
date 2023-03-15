package com.billdiary.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product")
@NamedQueries({
	@NamedQuery(
            name = "ProductEntity.findAllLowStockProducts",
            query = "SELECT product FROM ProductEntity product WHERE product.stock < product.lowStockThershold"
            ),
	@NamedQuery(
            name = "ProductEntity.getProductCode",
            query = "select coalesce(max(product.productCode),100) from ProductEntity product"
            )
})
public class ProductEntity implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "product_id")
	int id;
	
	@Column(name = "product_code")
	long productCode;
	
	@Column(name = "product_name")
	String name; 
	
	@Column(name="product_category")
	String productCategory;
	
	@Column(name="product_hsncode")
	String productHSNCode;

	@Column(name = "description")
	String description;
	
	@Column(name = "buy_price")
	double buy_price;
	
	@Column(name = "sell_price")
	double sell_price;
	
	@Column(name = "sell_GST")
	String sellGST;
	
	@Column(name = "sell_GST_percentage")
	double sellGSTPercentage;
	
	@Column(name = "buy_GST")
	String buyGST;
	
	@Column(name = "buy_GST_percentage")
	double buyGSTPercentage;
	
	@Column(name = "discount")
	double discount; 
	
	@Column(name = "stock")
	double stock;
	
	@Column(name="lowStock_thershold")
	Double lowStockThershold;
	
	@OneToOne
	UnitEntity unitEntity;
	
	@OneToMany(mappedBy="productEntity")
    private List<SoldProductsEntity> SoldProducts;
	
	
	public long getProductCode() {
		return productCode;
	}
	public void setProductCode(long productCode) {
		this.productCode = productCode;
	}
	public UnitEntity getUnitEntity() {
		return unitEntity;
	}
	public void setUnitEntity(UnitEntity unitEntity) {
		this.unitEntity = unitEntity;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getStock() {
		return stock;
	}
	public void setStock(double stock) {
		this.stock = stock;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	
	public String getProductHSNCode() {
		return productHSNCode;
	}
	public void setProductHSNCode(String productHSNCode) {
		this.productHSNCode = productHSNCode;
	}

	public double getLowStockThershold() {
		return lowStockThershold;
	}
	public void setLowStockThershold(Double lowStockThershold) {
		this.lowStockThershold = lowStockThershold;
	}
	public double getBuy_price() {
		return buy_price;
	}
	public void setBuy_price(double buy_price) {
		this.buy_price = buy_price;
	}
	public double getSell_price() {
		return sell_price;
	}
	public void setSell_price(double sell_price) {
		this.sell_price = sell_price;
	}
	public String getSellGST() {
		return sellGST;
	}
	public void setSellGST(String sellGST) {
		this.sellGST = sellGST;
	}
	public double getSellGSTPercentage() {
		return sellGSTPercentage;
	}
	public void setSellGSTPercentage(double sellGSTPercentage) {
		this.sellGSTPercentage = sellGSTPercentage;
	}
	public String getBuyGST() {
		return buyGST;
	}
	public void setBuyGST(String buyGST) {
		this.buyGST = buyGST;
	}
	public double getBuyGSTPercentage() {
		return buyGSTPercentage;
	}
	public void setBuyGSTPercentage(double buyGSTPercentage) {
		this.buyGSTPercentage = buyGSTPercentage;
	}
	public List<SoldProductsEntity> getSoldProducts() {
		return SoldProducts;
	}
	public void setSoldProducts(List<SoldProductsEntity> soldProducts) {
		SoldProducts = soldProducts;
	}
	
	

}