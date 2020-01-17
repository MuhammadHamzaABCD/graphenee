package io.graphenee.pos.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxBillingListPanel extends AbstractEntityListPanel<GxBillingBean> {

	@Autowired
	GxBillingForm gxBillingForm;

	@Autowired
	GxPosDataService gxPosDataService;

	public GxBillingListPanel() {
		super(GxBillingBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxBillingBean entity) {
		gxPosDataService.createOrUpdate(entity);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(GxBillingBean entity) {
		gxPosDataService.delete(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxBillingBean> fetchEntities() {
		return gxPosDataService.findAllBilling();
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "billNumber", "totalBill", "tax", "discount", "totalPayable" };

	}

	@Override
	protected TRAbstractForm<GxBillingBean> editorForm() {
		return gxBillingForm;
	}

	@Override
	protected boolean shouldShowDeleteConfirmation() {
		return true;
	}

}
