package io.graphenee.pos.vaadin;

import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.fields.MTextField;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.FormLayout;

import io.graphenee.core.model.bean.GxProductTypeBean;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxProductTypeForm extends TRAbstractForm<GxProductTypeBean> {

	MTextField typeName;

	@Override
	protected void addFieldsToForm(FormLayout form) {
		typeName = new MTextField("Type Name").withRequired(true);
		typeName.setMaxLength(50);

		form.addComponents(typeName);
	}

	@Override
	protected boolean eagerValidationEnabled() {
		return true;
	}

	@Override
	protected String formTitle() {
		return "Product Type Form";
	}

	@Override
	protected String popupWidth() {
		return "400px";
	}

	@Override
	protected String popupHeight() {
		return "180px";
	}

}
