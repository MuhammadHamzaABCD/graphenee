package io.graphenee.workshop.vaadin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.navigator.MView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;

import io.graphenee.pos.vaadin.GxBillingListPanel;
import io.graphenee.vaadin.AbstractDashboardPanel;

@SpringView(name = BillingView.VIEW_NAME)
@Scope("prototype")
public class BillingView extends AbstractDashboardPanel implements MView {

	public static final String VIEW_NAME = "gx-billing";

	@Autowired
	GxBillingListPanel gxBillingPanel;

	@Override
	public void enter(ViewChangeEvent event) {
		gxBillingPanel.refresh();
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
		return "billing Panel";
	}

	@Override
	protected void postInitialize() {
		addComponent(gxBillingPanel.build());
	}

	@Override
	protected boolean shouldShowHeader() {
		return true;
	}
}
