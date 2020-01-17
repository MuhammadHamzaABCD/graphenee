package io.graphenee.pos.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.graphenee.core.model.api.GxBeanFactory;
import io.graphenee.core.model.api.GxEntityFactory;
import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.core.model.bean.GxProductTypeBean;
import io.graphenee.core.model.entity.GxBilling;
import io.graphenee.core.model.entity.GxProduct;
import io.graphenee.core.model.entity.GxProductType;
import io.graphenee.core.model.jpa.repository.GxBillingRepository;
import io.graphenee.core.model.jpa.repository.GxProductRepository;
import io.graphenee.core.model.jpa.repository.GxProductTypeRepository;
import io.graphenee.pos.api.GxPosDataService;

@Transactional
@Service
public class GxPosDataServiceImpl implements GxPosDataService {

	@Autowired
	GxBeanFactory gxBeanFactory;

	@Autowired
	GxEntityFactory entityFactory;

	@Autowired
	GxProductTypeRepository gxProductTypeRepository;

	@Autowired
	GxProductRepository gxProductRepository;

	@Autowired
	GxBillingRepository gxBillingRepository;

	@Override
	public List<GxProductTypeBean> findAllProductType() {

		return gxBeanFactory.makeGxProductTypeBean(gxProductTypeRepository.findAll());

	}

	@Override
	public List<GxProductBean> findAllProduct() {
		return gxBeanFactory.makeGxProductBean(gxProductRepository.findAll());
	}

	@Override
	public GxProductTypeBean createOrUpdate(GxProductTypeBean bean) {
		GxProductType entity = entityFactory.makeGxProductTypeEntity(bean);
		gxProductTypeRepository.save(entity);
		bean.setOid(entity.getOid());
		return bean;
	}

	@Override
	public void delete(GxProductTypeBean bean) {
		gxProductTypeRepository.deleteById(bean.getOid());
	}

	@Override
	public GxProductBean createOrUpdate(GxProductBean bean) {
		GxProduct entity = entityFactory.makeGxProductEntity(bean);
		gxProductRepository.save(entity);
		bean.setOid(entity.getOid());
		return bean;
	}

	@Override
	public void delete(GxProductBean bean) {
		gxProductTypeRepository.deleteById(bean.getOid());
	}

	@Override
	public List<GxBillingBean> findAllBilling() {
		return gxBeanFactory.makeGxBillingBean(gxBillingRepository.findAll());
	}

	@Override
	public GxBillingBean createOrUpdate(GxBillingBean bean) {
		GxBilling entity = entityFactory.makeGxBillingEntity(bean);
		gxBillingRepository.save(entity);
		bean.setOid(entity.getOid());
		return bean;
	}

	@Override
	public void delete(GxBillingBean bean) {
		gxBillingRepository.deleteById(bean.getOid());
	}

}
