
package io.graphenee.core.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "gx_billing_item")
@NamedQuery(name = "GxBillingItem.findAll", query = "select g from GxBillingItem g")
public class GxBillingItem extends io.graphenee.core.model.GxMappedSuperclass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer oid;

	@Column(name = "discount")
	private double discount;

	@Column(name = "quantity")
	private Integer quantity;

	@ManyToOne
	@JoinColumn(name = "oid_billing")
	private GxBilling gxBilling;

	@ManyToOne
	@JoinColumn(name = "oid_product")
	private GxProduct gxProduct;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
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

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public GxProduct getGxProduct() {
		return gxProduct;
	}

	public void setGxProduct(GxProduct gxProduct) {
		this.gxProduct = gxProduct;
	}

}
