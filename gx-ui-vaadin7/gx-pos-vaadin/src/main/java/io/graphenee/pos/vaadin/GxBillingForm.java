package io.graphenee.pos.vaadin;

import java.util.Collection;
import java.util.Collections;
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
import com.vaadin.ui.themes.ValoTheme;

import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.core.util.TRCalendarUtil;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.pos.vaadin.GxBillingTablePanel.GxBillingTableDelegate;
import io.graphenee.vaadin.TRAbstractForm;
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

	Collection<GxProductBean> selectedBeans = Collections.EMPTY_LIST;

	private BeanItemContainer<GxProductBean> productDataContainer;

	@Autowired
	GxBillingTablePanel gxBillingTablePanel;

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
		formLayoutLeft.addComponents(billNumber, billDate, totalBill);
		formLayoutMiddle.addComponents(discount, tax, totalPayable);
		MHorizontalLayout billingSummaryLayout = new MHorizontalLayout().withWidth("100%");
		MVerticalLayout productLayout = new MVerticalLayout().withHeight("100%");
		productLayout.setSizeFull();
		billingSummaryLayout.addComponents(formLayoutLeft, formLayoutMiddle);
		gxBillingTablePanel.setDelegate(new GxBillingTableDelegate<GxBillingBean>() {
			@Override
			public void onUpdate() {
				GxBillingTableDelegate.super.onUpdate();
				totalBill.markAsDirty();
				totalPayable.markAsDirty();
			}
		});
		productLayout.addComponents(billingSummaryLayout, gxBillingTablePanel.build());
		productLayout.setExpandRatio(gxBillingTablePanel, 1);

		tax.addTextChangeListener(value -> {
			getEntity().calculatePayableAmout();
			totalPayable.markAsDirty();
		});

		discount.addTextChangeListener(value -> {
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
		return "Billing Form";
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
	protected void preBinding(GxBillingBean entity) {
		super.preBinding(entity);
	}

	@Override
	protected void postBinding(GxBillingBean entity) {
		super.postBinding(entity);
		gxBillingTablePanel.initializeWithEntity(entity);
		gxBillingTablePanel.refresh();
		billNumber.setValue(UUID.randomUUID().toString());
		billNumber.setEnabled(false);
		billDate.setValue(TRCalendarUtil.getCurrentTimeStamp());
		billDate.setEnabled(false);
		totalBill.setEnabled(false);
		totalPayable.setEnabled(false);
	}

}
