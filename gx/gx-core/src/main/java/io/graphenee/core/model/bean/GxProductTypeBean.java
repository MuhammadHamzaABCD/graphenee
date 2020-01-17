
package io.graphenee.core.model.bean;

import java.io.Serializable;

import io.graphenee.core.model.BeanCollectionFault;
import io.graphenee.core.model.entity.GxProduct;

public class GxProductTypeBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer oid;

	private String typeName;

	private String typeCode;

	private BeanCollectionFault<GxProduct> productCollectionFault = BeanCollectionFault.emptyCollectionFault();

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BeanCollectionFault<GxProduct> getProductCollectionFault() {
		return productCollectionFault;
	}

	public void setProductCollectionFault(BeanCollectionFault<GxProduct> productCollectionFault) {
		this.productCollectionFault = productCollectionFault;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
