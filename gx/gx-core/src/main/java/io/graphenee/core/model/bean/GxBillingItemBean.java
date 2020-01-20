
package io.graphenee.core.model.bean;

import java.io.Serializable;
import java.util.UUID;

import io.graphenee.core.model.BeanFault;
import io.graphenee.core.model.entity.GxBilling;

public class GxBillingItemBean implements Serializable {

	private UUID uid = UUID.randomUUID();
	private Integer oid;
	private double discount = 0;
	private Integer quantity = 1;
	private GxBilling gxBilling;
	private double totalAmount = 0;

	private BeanFault<Integer, GxProductBean> productFault;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public GxBilling getGxBilling() {
		return gxBilling;
	}

	public void setGxBilling(GxBilling gxBilling) {
		this.gxBilling = gxBilling;
	}

	public BeanFault<Integer, GxProductBean> getProductFault() {
		return productFault;
	}

	public void setProductFault(BeanFault<Integer, GxProductBean> productFault) {
		this.productFault = productFault;
	}

	public String getProductName() {
		if (productFault != null) {
			return productFault.getBean().getProductName();
		}
		return null;
	}

	public String getProductCode() {
		if (productFault != null) {
			return productFault.getBean().getProductCode();
		}
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
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
		GxBillingItemBean other = (GxBillingItemBean) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		return true;
	}

	public double getTotalAmount() {
		if (getProductFault() != null) {
			return totalAmount = getQuantity() * getProductFault().getBean().getPrice();
		}
		return 0;
	}

	public double getPrice() {
		if (getProductFault() != null) {
			return getProductFault().getBean().getPrice();
		}
		return 0;
	}

}
