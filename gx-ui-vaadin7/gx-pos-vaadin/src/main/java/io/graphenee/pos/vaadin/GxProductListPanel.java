package io.graphenee.pos.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxProductListPanel extends AbstractEntityListPanel<GxProductBean> {

	@Autowired
	GxProductForm gxProductForm;

	@Autowired
	GxPosDataService gxPosDataService;

	public GxProductListPanel() {
		super(GxProductBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxProductBean entity) {
		gxPosDataService.createOrUpdate(entity);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(GxProductBean entity) {
		gxPosDataService.delete(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxProductBean> fetchEntities() {
		return gxPosDataService.findAllProduct();
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "productCode", "productName", "description", "price", "productType" };

	}

	@Override
	protected TRAbstractForm<GxProductBean> editorForm() {
		return gxProductForm;
	}

	@Override
	protected boolean shouldShowDeleteConfirmation() {
		return true;
	}

}
