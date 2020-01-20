package io.graphenee.pos.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.themes.ValoTheme;

import io.graphenee.core.model.ModificationListener;
import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxBillingItemBean;
import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.gx.theme.graphenee.GrapheneeTheme;
import io.graphenee.pos.api.GxPosDataService;
import io.graphenee.vaadin.AbstractEntityTablePanel;
import io.graphenee.vaadin.TRAbstractForm;
import io.graphenee.vaadin.converter.BeanFaultToBeanConverter;

@SuppressWarnings("serial")
@SpringComponent
@Scope("prototype")
public class GxBillingTablePanel extends AbstractEntityTablePanel<GxBillingItemBean> {

	@Autowired
	GxPosDataService posDataService;

	private GxBillingBean billingBean;

	private BeanItemContainer<GxProductBean> gxProductContainer = new BeanItemContainer<GxProductBean>(GxProductBean.class);

	private GxBillingTableDelegate<GxBillingBean> delegate = null;

	String totalAmount = "";

	public GxBillingTablePanel() {
		super(GxBillingItemBean.class);
	}

	@Override
	protected boolean onSaveEntity(GxBillingItemBean entity) {
		billingBean.getGxProductBillingItemCollectionFault().update(entity);
		return true;

	}

	@Override
	protected boolean onDeleteEntity(GxBillingItemBean entity) {
		billingBean.getGxProductBillingItemCollectionFault().remove(entity);
		return true;
	}

	@Override
	protected String panelCaption() {
		return null;
	}

	@Override
	protected List<GxBillingItemBean> fetchEntities() {
		return new ArrayList<>(billingBean.getGxProductBillingItemCollectionFault().getBeans());
	}

	@Override
	protected String[] visibleProperties() {
		return new String[] { "productFault", "quantity", "price", "totalAmount" };
	}

	@Override
	protected TRAbstractForm<GxBillingItemBean> editorForm() {
		return null;
	}

	public void initializeWithEntity(GxBillingBean bean) {
		entityTable().removeAllItems();
		this.billingBean = bean;
		buildFooter();
		gxProductContainer.removeAllItems();
		gxProductContainer.addAll(posDataService.findAllProduct());
		billingBean.getGxProductBillingItemCollectionFault().addModificationListener(new ModificationListener() {
			@Override
			public void onModification() {
				billingBean.calculatePayableAmout();
				if (delegate != null) {
					delegate.onUpdate();
				}
			}
		});
	}

	@Override
	protected void applyRendererForColumn(TableColumn column) {
		String id = column.getPropertyId();
		if (id.equals("productFault"))
			column.setHeader("Product Name");
		super.applyRendererForColumn(column);
	}

	@Override
	protected boolean isTableEditable() {
		return true;
	}

	@Override
	protected boolean isGridCellFilterEnabled() {
		return true;
	}

	@Override
	protected void postBuild() {
		setAddButtonVisibility(false);
		setEditButtonVisibility(false);
		setDeleteButtonVisibility(false);
	}

	@Override
	protected void addButtonsToToolbar(AbstractOrderedLayout toolbar) {
		MButton addBillItem = new MButton("Add Entry");
		addBillItem.addClickListener(clicked -> {
			GxBillingItemBean gxBillingItemBean = new GxBillingItemBean();
			billingBean.getGxProductBillingItemCollectionFault().update(gxBillingItemBean);
			refresh();
		});

		toolbar.addComponentAsFirst(addBillItem);
	}

	public String getTotalAmount() {
		return Double.toString(billingBean.getGxProductBillingItemCollectionFault().getBeans().stream().mapToDouble(GxBillingItemBean::getTotalAmount).sum());

	}

	@Override
	protected Field<?> propertyField(GxBillingItemBean itemId, String propertyId) {
		if (propertyId.equals("productFault")) {
			ComboBox cbx = new ComboBox();
			cbx.setWidth("200px");
			cbx.setRequired(true);
			cbx.setNullSelectionAllowed(false);
			cbx.setConverter(new BeanFaultToBeanConverter(GxProductBean.class));
			cbx.setStyleName(ValoTheme.COMBOBOX_BORDERLESS);
			cbx.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			cbx.setItemCaptionPropertyId("productName");
			cbx.setContainerDataSource(gxProductContainer);

			cbx.addValueChangeListener(listener -> {
				billingBean.getGxProductBillingItemCollectionFault().update(itemId);
				buildFooter();
				refresh();
			});

			return cbx;
		}
		if (propertyId.matches("(quantity)")) {
			MTextField textField = new MTextField();
			textField.setConverter(new StringToIntegerConverter());
			textField.setStyleName(GrapheneeTheme.STYLE_V_ALIGN_RIGHT);
			textField.addValueChangeListener(listener -> {
				billingBean.getGxProductBillingItemCollectionFault().update(itemId);
				buildFooter();
			});
			return textField;
		}

		return super.propertyField(itemId, propertyId);
	}

	public static interface GxBillingTableDelegate<GxBillingBean> {
		default void onUpdate() {

		}
	}

	private void buildFooter() {
		entityTable().setFooterVisible(true);
		entityTable().setColumnFooter("price", "Total Amount");
		entityTable().setColumnFooter("totalAmount", getTotalAmount());
	}

	public void setDelegate(GxBillingTableDelegate<GxBillingBean> delegate) {
		this.delegate = delegate;
	}

}
