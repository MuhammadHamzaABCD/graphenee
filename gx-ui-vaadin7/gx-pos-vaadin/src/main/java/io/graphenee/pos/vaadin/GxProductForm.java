package io.graphenee.pos.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.fields.MTextArea;
import org.vaadin.viritin.fields.MTextField;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;

import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.core.model.bean.GxProductTypeBean;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.TRAbstractForm;
import io.graphenee.vaadin.converter.BeanFaultToBeanConverter;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxProductForm extends TRAbstractForm<GxProductBean> {

	@Autowired
	GxPosDataService gxPosDataService;

	MTextField productCode;
	MTextField productName;
	MTextArea description;
	MTextField retailPrice;
	MTextField price;
	MTextField barCode;
	ComboBox productTypeBeanFault;

	BeanItemContainer<GxProductTypeBean> productTypeBeanContainer = new BeanItemContainer<>(GxProductTypeBean.class);

	@Override
	protected void addFieldsToForm(FormLayout form) {
		productCode = new MTextField("Product Code").withRequired(true);
		productCode.setMaxLength(50);
		productName = new MTextField("Product Name").withRequired(true);
		productName.setMaxLength(200);
		description = new MTextArea("Description");
		description.setMaxLength(200);
		barCode = new MTextField("Bar Code");
		barCode.setMaxLength(200);
		price = new MTextField("price").withRequired(true);
		price.setConverter(StringToDoubleConverter.class);
		retailPrice = new MTextField("Retail Price").withRequired(true);
		retailPrice.setConverter(StringToDoubleConverter.class);
		productTypeBeanFault = new ComboBox("Select Type");
		productTypeBeanFault.setNullSelectionAllowed(true);
		productTypeBeanFault.setItemCaptionMode(ItemCaptionMode.PROPERTY);
		productTypeBeanFault.setItemCaptionPropertyId("typeName");
		productTypeBeanFault.setContainerDataSource(productTypeBeanContainer);
		productTypeBeanFault.setConverter(new BeanFaultToBeanConverter(GxProductTypeBean.class));

		form.addComponents(productCode, productName, description, retailPrice, price, productTypeBeanFault, barCode);
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
		return "600px";
	}

	@Override
	protected String popupHeight() {
		return "400px";
	}

	@Override
	protected void preBinding(GxProductBean entity) {
		super.preBinding(entity);
		productTypeBeanContainer.addAll(gxPosDataService.findAllProductType());
	}

}
