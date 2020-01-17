package io.graphenee.pos.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

import io.graphenee.core.model.bean.GxProductTypeBean;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxProductTypeListPanel extends AbstractEntityListPanel<GxProductTypeBean> {

	@Autowired
	GxProductTypeForm gxProductTypeForm;

	@Autowired
	GxPosDataService gxPosDataService;

	public GxProductTypeListPanel() {
		super(GxProductTypeBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxProductTypeBean entity) {
		gxPosDataService.createOrUpdate(entity);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(GxProductTypeBean entity) {
		gxPosDataService.delete(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxProductTypeBean> fetchEntities() {
		return gxPosDataService.findAllProductType();
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "typeName", "typeCode" };
	}

	@Override
	protected TRAbstractForm<GxProductTypeBean> editorForm() {
		return gxProductTypeForm;
	}

	@Override
	protected boolean shouldShowDeleteConfirmation() {
		return true;
	}

}
