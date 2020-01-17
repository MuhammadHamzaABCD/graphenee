
package io.graphenee.core.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "gx_product_type")
@NamedQuery(name = "GxProductType.findAll", query = "select g from GxProductType g")
public class GxProductType extends io.graphenee.core.model.GxMappedSuperclass implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer oid;

	@Column(name = "type_name")
	private String typeName;

	@Column(name = "type_code")
	private String typeCode;

	@OneToMany(mappedBy = "gxProductType")
	private List<GxProduct> gxProducts = new ArrayList<>();

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public List<GxProduct> getGxProducts() {
		return gxProducts;
	}

	public void setGxProducts(List<GxProduct> gxProducts) {
		this.gxProducts = gxProducts;
	}

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

}
