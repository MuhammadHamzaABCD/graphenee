package io.graphenee.pos.vaadin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid.SelectionMode;

import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxBillingItemBean;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxBillingSummaryListPanel extends AbstractEntityListPanel<GxBillingItemBean> {

	@Autowired
	GxBillingForm gxBillingForm;

	@Autowired
	GxPosDataService gxPosDataService;

	GxBillingBean gxBillingBean;

	public GxBillingSummaryListPanel() {
		super(GxBillingItemBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxBillingItemBean entity) {
		return false;
	}

	@Override
	protected boolean onDeleteEntity(GxBillingItemBean entity) {
		return false;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxBillingItemBean> fetchEntities() {
		if (gxBillingBean != null)
			return gxBillingBean.getGxProductBillingItemCollectionFault().getBeans().stream().collect(Collectors.toList());
		return Collections.emptyList();
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "productName", "price", "quantity", "totalAmount" };
	}

	@Override
	protected boolean shouldShowDeleteConfirmation() {
		return true;
	}

	@Override
	protected void postBuild() {
		super.postBuild();
		hideToolbar();
		entityGrid().setSelectionMode(SelectionMode.NONE);
	}

	@Override
	protected TRAbstractForm<GxBillingItemBean> editorForm() {
		return null;
	}

	public void initializaWithBean(GxBillingBean gxBillingBean) {
		this.gxBillingBean = gxBillingBean;
	}

}
