package io.graphenee.pos.vaadin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.vaadin.spring.annotation.SpringComponent;

import io.graphenee.core.model.bean.GxProductTypeBean;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxProductTypeListPanel extends AbstractEntityListPanel<GxProductTypeBean> {

	@Autowired
	GxProductTypeForm gxProductTypeForm;

	public GxProductTypeListPanel() {
		super(GxProductTypeBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxProductTypeBean entity) {
		return false;
	}

	@Override
	protected boolean onDeleteEntity(GxProductTypeBean entity) {
		return false;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxProductTypeBean> fetchEntities() {
		return null;
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "typeName" };
	}

	@Override
	protected TRAbstractForm<GxProductTypeBean> editorForm() {
		return gxProductTypeForm;
	}

}
