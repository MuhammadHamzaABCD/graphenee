package io.graphenee.pos.vaadin;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.fields.MDateField;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.themes.ValoTheme;

import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.core.util.TRCalendarUtil;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.TRAbstractForm;
import io.graphenee.vaadin.converter.BeanCollectionFaultToSetConverter;
import io.graphenee.vaadin.converter.DateToTimestampConverter;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxBillingForm extends TRAbstractForm<GxBillingBean> {

	@Autowired
	GxPosDataService gxPosDataService;

	MTextField billNumber;
	MDateField billDate;
	MTextField discount;
	MTextField totalBill;
	MTextField tax;
	MTextField totalPayable;
	TwinColSelect gxProductBeanCollectionFault;
	MTextField searchBar;

	private BeanItemContainer<GxProductBean> productDataContainer;

	@Override
	protected Component getFormComponent() {
		FormLayout formLayoutLeft = new FormLayout();
		FormLayout formLayoutMiddle = new FormLayout();
		FormLayout formLayoutRight = new FormLayout();
		formLayoutLeft.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		formLayoutLeft.setSpacing(false);
		formLayoutLeft.setMargin(false);
		formLayoutMiddle.setStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		formLayoutMiddle.setSpacing(false);
		formLayoutMiddle.setMargin(false);

		billNumber = new MTextField("BillNumber");
		totalBill = new MTextField("Total Bill");
		totalBill.setConverter(StringToDoubleConverter.class);
		totalPayable = new MTextField("Total Payable");
		totalPayable.setConverter(StringToDoubleConverter.class);
		billDate = new MDateField("Date");
		billDate.setConverter(new DateToTimestampConverter());
		billDate.setDateFormat(TRCalendarUtil.dateFormatter.toPattern());
		discount = new MTextField("discount").withRequired(true);
		discount.setConverter(StringToDoubleConverter.class);
		tax = new MTextField("tax").withRequired(true);
		tax.setConverter(StringToDoubleConverter.class);
		MTextField searchBar = new MTextField("Search by name");
		gxProductBeanCollectionFault = new TwinColSelect();
		gxProductBeanCollectionFault.setSizeFull();
		gxProductBeanCollectionFault.setLeftColumnCaption("Available Products");
		gxProductBeanCollectionFault.setRightColumnCaption("Bill Items");
		productDataContainer = new BeanItemContainer<>(GxProductBean.class);
		gxProductBeanCollectionFault.setItemCaptionPropertyId("productName");
		gxProductBeanCollectionFault.setContainerDataSource(productDataContainer);
		gxProductBeanCollectionFault.setConverter(new BeanCollectionFaultToSetConverter());
		formLayoutLeft.addComponents(billNumber, billDate, totalBill);
		formLayoutMiddle.addComponents(discount, tax, totalPayable);
		MHorizontalLayout billingSummaryLayout = new MHorizontalLayout().withWidth("100%");
		MVerticalLayout productLayout = new MVerticalLayout().withHeight("100%");
		billingSummaryLayout.addComponents(formLayoutLeft, formLayoutMiddle);
		productLayout.addComponents(billingSummaryLayout, gxProductBeanCollectionFault);
		productLayout.setExpandRatio(gxProductBeanCollectionFault, 1);
		gxProductBeanCollectionFault.addValueChangeListener(listener -> {
			totalBill.setConvertedValue(getEntity().getGxProductBeanCollectionFault().getBeans().stream().mapToDouble(GxProductBean::getPrice).sum());
			getEntity().calculatePayableAmout();
			totalPayable.markAsDirty();
		});

		return productLayout;

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
		return "700px";
	}

	@Override
	protected String popupHeight() {
		return "500px";
	}

	@Override
	protected void postBinding(GxBillingBean entity) {
		super.postBinding(entity);
		billNumber.setValue(UUID.randomUUID().toString());
		billNumber.setEnabled(false);
		billDate.setValue(TRCalendarUtil.getCurrentTimeStamp());
		billDate.setEnabled(false);
		totalBill.setEnabled(false);
		totalPayable.setEnabled(false);

	}

	@Override
	protected void preBinding(GxBillingBean entity) {
		super.preBinding(entity);
		gxProductBeanCollectionFault.removeAllItems();
		productDataContainer.addAll(gxPosDataService.findAllProduct());

	}

}
