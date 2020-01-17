
package io.graphenee.core.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "gx_product")
@NamedQuery(name = "GxProduct.findAll", query = "select g from GxProduct g")
public class GxProduct extends io.graphenee.core.model.GxMappedSuperclass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer oid;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "description")
	private String description;

	@Column(name = "retail_price")
	private double retailPrice;

	@Column(name = "price")
	private double price;

	@Column(name = "bar_code")
	private String barCode;

	@ManyToMany(mappedBy = "gxProducts")
	private List<GxBilling> gxBillings = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "oid_product_type")
	private GxProductType gxProductType;

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

	public GxProductType getGxProductType() {
		return gxProductType;
	}

	public void setGxProductType(GxProductType gxProductType) {
		this.gxProductType = gxProductType;
	}

}
