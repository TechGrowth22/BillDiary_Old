package com.billdiary.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "product_category")
public class ProductCategoryEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4691795285642685081L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "category_id")
	long categoryId;
	
	@Column(name = "category_name")
	String categoryName;

	public long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	

}
