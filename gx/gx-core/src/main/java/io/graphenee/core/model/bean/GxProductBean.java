
package io.graphenee.core.model.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.graphenee.core.model.BeanFault;

public class GxProductBean implements Serializable {
	private static final Logger L = LoggerFactory.getLogger(GxAccessKeyBean.class);

	private Integer oid;

	private String productCode;

	private String productName;

	private String description;

	private double retailPrice = 0;

	private double price = 0;

	private String barCode;

	private BeanFault<Integer, GxProductTypeBean> productTypeBeanFault;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public BeanFault<Integer, GxProductTypeBean> getProductTypeBeanFault() {
		return productTypeBeanFault;
	}

	public void setProductTypeBeanFault(BeanFault<Integer, GxProductTypeBean> productTypeBeanFault) {
		this.productTypeBeanFault = productTypeBeanFault;
	}

	public String getProductType() {
		return productTypeBeanFault.getBean().getTypeName();
	}

}
