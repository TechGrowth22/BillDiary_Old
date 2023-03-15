package com.billdiary.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.billdiary.entities.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	List<ProductEntity> findAllLowStockProducts();
	
	@Modifying
	@Query("UPDATE ProductEntity product SET  product.stock=product.stock-:quantity WHERE product.id=:id")
	@Transactional
	public int updateProductStock(@Param("id") int id,@Param("quantity") double quantity);
	
	@Modifying
	@Query("UPDATE ProductEntity product SET  product.stock=product.stock+:quantity WHERE product.id=:id")
	@Transactional
	public int purchaseProductStock(@Param("id") int id,@Param("quantity") double quantity);
	
	@Query("SELECT SUM(product.stock * product.buy_price) FROM ProductEntity product")
	public Double getTotalInvestment();
	
	public long getProductCode();
	
	public long countByProductCode(long productCode);
	
	List<ProductEntity> findByIdAndName(int  Id, String name);
	
	
	List<ProductEntity> findByIdOrProductCode(int  Id, Long code);
}
