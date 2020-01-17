
package io.graphenee.core.model.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import io.graphenee.core.model.BeanCollectionFault;

public class GxBillingBean implements Serializable {

	private Integer oid;
	private String billNumber;
	private Timestamp billDate;
	private double discount = 0;
	private double totalBill = 0;
	private double tax = 0;
	private double totalPayable = 0;
	private double totalPaid = 0;

	private BeanCollectionFault<GxProductBean> gxProductBeanCollectionFault = BeanCollectionFault.emptyCollectionFault();

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

	public BeanCollectionFault<GxProductBean> getGxProductBeanCollectionFault() {
		return gxProductBeanCollectionFault;
	}

	public void setGxProductBeanCollectionFault(BeanCollectionFault<GxProductBean> gxProductBeanCollectionFault) {
		this.gxProductBeanCollectionFault = gxProductBeanCollectionFault;
	}

	public double calculatePayableAmout() {
		totalPayable = getTotalBill();
		double discountApplied = getDiscount() <= 1.0 ? totalPayable * getDiscount() : getDiscount();
		if (discountApplied < 0) {
			discountApplied = 0;
			setDiscount(0);
		}
		totalPayable -= discountApplied;

		double taxApplied = getTax() < 1.0 ? totalPayable * getTax() : getTax();
		if (taxApplied < 0) {
			taxApplied = 0;
			setTax(0);
		}
		totalPayable = totalPayable + taxApplied;
		return totalPayable;
	}

}
