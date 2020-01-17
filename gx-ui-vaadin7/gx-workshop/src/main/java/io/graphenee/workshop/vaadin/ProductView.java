package io.graphenee.workshop.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.navigator.MView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;

import io.graphenee.pos.vaadin.GxProductListPanel;
import io.graphenee.vaadin.AbstractDashboardPanel;

@SpringView(name = ProductView.VIEW_NAME)
@Scope("prototype")
public class ProductView extends AbstractDashboardPanel implements MView {

	public static final String VIEW_NAME = "gx-product";

	@Autowired
	GxProductListPanel productPanel;

	@Override
	public void enter(ViewChangeEvent event) {
		productPanel.refresh();
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
		return "Product";
	}

	@Override
	protected void postInitialize() {
		addComponent(productPanel.build());
	}

	@Override
	protected boolean shouldShowHeader() {
		return true;
	}
}
