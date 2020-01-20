
package io.graphenee.core.model.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.graphenee.core.model.BeanCollectionFault;
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

	private BeanCollectionFault<GxBillingItemBean> gxProductBillingItemCollectionFault = BeanCollectionFault.emptyCollectionFault();

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
		if (productTypeBeanFault != null) {
			return productTypeBeanFault.getBean().getTypeName();
		} else
			return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GxProductBean other = (GxProductBean) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}

	public BeanCollectionFault<GxBillingItemBean> getGxProductBillingItemCollectionFault() {
		return gxProductBillingItemCollectionFault;
	}

	public void setGxProductBillingItemCollectionFault(BeanCollectionFault<GxBillingItemBean> gxProductBillingItemCollectionFault) {
		this.gxProductBillingItemCollectionFault = gxProductBillingItemCollectionFault;
	}

}
