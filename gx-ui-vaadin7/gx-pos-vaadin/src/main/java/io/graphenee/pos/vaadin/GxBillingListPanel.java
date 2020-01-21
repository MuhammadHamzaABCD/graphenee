package io.graphenee.pos.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.ui.MNotification;

import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.util.TRCalendarUtil;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.AbstractEntityListPanel;
import io.graphenee.vaadin.TRAbstractForm;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxBillingListPanel extends AbstractEntityListPanel<GxBillingBean> {

	@Autowired
	GxBillingForm gxBillingForm;

	@Autowired
	GxPosDataService gxPosDataService;

	@Autowired
	GxBillingSummaryForm gxBillingSummaryForm;

	public static final int DRAFT = 1;
	public static final int PAID = 2;

	private Integer fetchMode = DRAFT;

	private MButton collectButton;

	public GxBillingListPanel() {
		super(GxBillingBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxBillingBean entity) {
		entity.setBillDate(TRCalendarUtil.getCurrentTimeStamp());
		gxPosDataService.createOrUpdate(entity);
		return true;
	}

	@Override
	protected boolean onDeleteEntity(GxBillingBean entity) {
		gxPosDataService.delete(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxBillingBean> fetchEntities() {
		switch (fetchMode) {
		case DRAFT:
			return gxPosDataService.findAllByIsPaid(false);
		case PAID:
			return gxPosDataService.findAllByIsPaid(true);
		}
		return gxPosDataService.findAllBilling();

	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "billNumber", "billDateAndTime", "totalBill", "tax", "discount", "totalPayable", "totalPaid" };

	}

	@Override
	protected TRAbstractForm<GxBillingBean> editorForm() {
		return gxBillingForm;
	}

	@Override
	protected boolean shouldShowDeleteConfirmation() {
		return true;
	}

	@Override
	protected void onGridItemClicked(GxBillingBean item) {
		if (fetchMode == DRAFT)
			super.onGridItemClicked(item);
		else {
			gxBillingSummaryForm.setEntity(GxBillingBean.class, item);
			gxBillingSummaryForm.build();
			gxBillingSummaryForm.openInModalPopup();
		}
	}

	@Override
	protected boolean isGridCellFilterEnabled() {
		return true;
	}

	@Override
	protected void addButtonsToToolbar(AbstractOrderedLayout toolbar) {
		super.addButtonsToToolbar(toolbar);
		collectButton = new MButton(FontAwesome.PAPER_PLANE, "pay", event -> {
			List<GxBillingBean> beans = new ArrayList<GxBillingBean>(entityGrid().getSelectedRowsWithType());
			ConfirmDialog.show(UI.getCurrent(), "Are you sure to pay selected bills?", e -> {
				if (e.isConfirmed()) {
					for (GxBillingBean bean : beans)
						gxPosDataService.collectBill(bean);
					MNotification.tray(entityGrid().getSelectedRowsWithType().size() + " bill paid successfully");
				}
				refresh();
			});
		}).withStyleName(ValoTheme.BUTTON_PRIMARY);

		toolbar.addComponents(collectButton);

	}

	@Override
	protected void addButtonsToSecondaryToolbar(AbstractOrderedLayout toolbar) {
		MButton draftButton = new MButton("Drafts");
		MButton paidButton = new MButton("Paid");

		CssLayout draftSentLayout = new CssLayout(draftButton, paidButton);
		draftSentLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		draftButton.addClickListener(event -> {
			showToolbar();
			draftButton.setEnabled(false);
			paidButton.setEnabled(true);
			fetchMode = DRAFT;
			refresh();
		});

		paidButton.addClickListener(event -> {
			hideToolbar();
			draftButton.setEnabled(true);
			paidButton.setEnabled(false);
			fetchMode = PAID;
			refresh();
		});

		draftButton.setEnabled(false);
		paidButton.setEnabled(true);
		toolbar.addComponents(draftSentLayout);
	}

}
