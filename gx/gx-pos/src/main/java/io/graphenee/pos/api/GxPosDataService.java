package io.graphenee.pos.api;

import java.util.List;

import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.core.model.bean.GxProductTypeBean;

public interface GxPosDataService {

	List<GxProductTypeBean> findAllProductType();

	List<GxProductBean> findAllProduct();

	GxProductTypeBean createOrUpdate(GxProductTypeBean bean);

	void delete(GxProductTypeBean bean);

	GxProductBean createOrUpdate(GxProductBean bean);

	void delete(GxProductBean bean);

	List<GxBillingBean> findAllBilling();

	GxBillingBean createOrUpdate(GxBillingBean bean);

	void delete(GxBillingBean bean);

	List<GxProductBean> findAllProductByBillingOid(GxBillingBean gxBillingBean);

	List<GxProductBean> findAllProductByNameOrCode(String productName, String productCode);

}
