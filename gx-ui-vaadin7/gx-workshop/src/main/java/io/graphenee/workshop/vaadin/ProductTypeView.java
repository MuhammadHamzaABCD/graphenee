package io.graphenee.workshop.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.navigator.MView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;

import io.graphenee.pos.vaadin.GxProductTypeListPanel;
import io.graphenee.vaadin.AbstractDashboardPanel;

@SpringView(name = ProductTypeView.VIEW_NAME)
@Scope("prototype")
public class ProductTypeView extends AbstractDashboardPanel implements MView {

	public static final String VIEW_NAME = "gx-product-type";

	@Autowired
	GxProductTypeListPanel productTypePanel;

	@Override
	public void enter(ViewChangeEvent event) {
		productTypePanel.refresh();
	}

	@Override
	public boolean beforeViewChange(ViewChangeEvent event) {
		return false;
	}

	@Override
	public void afterViewChange(ViewChangeEvent event) {

	}

	@Override
	protected String panelTitle() {
		return "Product Type";
	}

	@Override
	protected void postInitialize() {
		addComponent(productTypePanel.build());
	}

	@Override
	protected boolean shouldShowHeader() {
		return true;
	}
}
