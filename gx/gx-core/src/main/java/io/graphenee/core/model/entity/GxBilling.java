
package io.graphenee.core.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gx_billing")
@NamedQuery(name = "GxBilling.findAll", query = "select g from GxBilling g")
public class GxBilling extends io.graphenee.core.model.GxMappedSuperclass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer oid;

	@Column(name = "bill_number")
	private String billNumber;

	@Column(name = "bill_date")
	private Timestamp billDate;

	@Column(name = "discount")
	private double discount;

	@Column(name = "total_bill")
	private double totalBill;

	@Column(name = "tax")
	private double tax;

	@Column(name = "total_payable")
	private double totalPayable;

	@Column(name = "total_paid")
	private double totalPaid;

	@OneToMany(mappedBy = "gxBilling", cascade = CascadeType.ALL)
	private List<GxBillingItem> gxBillingItems = new ArrayList<>();

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}

	public Timestamp getBillDate() {
		return billDate;
	}

	public void setBillDate(Timestamp billDate) {
		this.billDate = billDate;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getTotalBill() {
		return totalBill;
	}

	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getTotalPayable() {
		return totalPayable;
	}

	public void setTotalPayable(double totalPayable) {
		this.totalPayable = totalPayable;
	}

	public double getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(double totalPaid) {
		this.totalPaid = totalPaid;
	}

	public List<GxBillingItem> getGxBillingItems() {
		return gxBillingItems;
	}

	public void setGxBillingItems(List<GxBillingItem> gxBillingItems) {
		this.gxBillingItems = gxBillingItems;
	}

	public GxBillingItem addBillingItem(GxBillingItem gxBillingItem) {
		getGxBillingItems().add(gxBillingItem);
		gxBillingItem.setGxBilling(this);
		return gxBillingItem;
	}

	public GxBillingItem removeBillingItem(GxBillingItem gxBillingItem) {
		getGxBillingItems().remove(gxBillingItem);
		gxBillingItem.setGxBilling(null);
		return gxBillingItem;

	}

}
