package io.graphenee.pos.vaadin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.label.MLabel;

import com.vaadin.data.Property.ValueChangeNotifier;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;
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
		//		entityTable().addGeneratedColumn("Price", priceGeneratedCell());
		//		entityTable().addGeneratedColumn("Total Amount", totalAmountGeneratedCell());

	}

	private ColumnGenerator priceGeneratedCell() {
		return new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				BeanItem<GxBillingItemBean> beanItem = (BeanItem<GxBillingItemBean>) source.getItem(itemId);
				GxBillingItemBean bean = beanItem.getBean();
				MLabel price = new MLabel(formattedPrice(bean));
				((ValueChangeNotifier) beanItem.getItemProperty("productFault")).addValueChangeListener(event -> {
					price.setValue(formattedPrice(bean));
				});
				return price;
			}
		};

	}

	private ColumnGenerator totalAmountGeneratedCell() {
		return new Table.ColumnGenerator() {

			@Override
			public Object generateCell(Table source, Object itemId, Object columnId) {
				BeanItem<GxBillingItemBean> beanItem = (BeanItem<GxBillingItemBean>) source.getItem(itemId);
				GxBillingItemBean bean = beanItem.getBean();
				MLabel tottalAmount = new MLabel(formattedPrice(bean));
				((ValueChangeNotifier) beanItem.getItemProperty("productFault")).addValueChangeListener(event -> {
					tottalAmount.setValue(formattedTotalAmount(bean));
				});
				((ValueChangeNotifier) beanItem.getItemProperty("quantity")).addValueChangeListener(event -> {
					tottalAmount.setValue(formattedTotalAmount(bean));
				});
				return tottalAmount;
			}
		};

	}

	private String formattedTotalAmount(GxBillingItemBean bean) {
		return String.format("%1.2f", bean.getTotalAmount());
	}

	private String formattedPrice(GxBillingItemBean bean) {
		return String.format("%1.2f", bean.getPrice());
	}

	@Override
	protected void addButtonsToToolbar(AbstractOrderedLayout toolbar) {
		MButton addBillItem = new MButton("Add Entry");
		addBillItem.addClickListener(clicked -> {
			GxBillingItemBean gxBillingItemBean = new GxBillingItemBean();
			billingBean.getGxProductBillingItemCollectionFault().add(gxBillingItemBean);
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
			cbx.setInputPrompt("Select product");
			cbx.setSizeFull();
			cbx.setContainerDataSource(gxProductContainer);

			cbx.addValueChangeListener(listener -> {
				System.err.println(itemId.getTotalAmount());
				billingBean.getGxProductBillingItemCollectionFault().update(itemId);
				entityTable().refreshRowCache();
				refreshFooter();
			});

			return cbx;
		}
		if (propertyId.matches("(quantity)")) {
			MTextField textField = new MTextField();
			textField.setConverter(new StringToIntegerConverter());
			textField.setStyleName(GrapheneeTheme.STYLE_V_ALIGN_RIGHT);
			textField.setSizeFull();
			textField.addBlurListener(listener -> {
				billingBean.getGxProductBillingItemCollectionFault().update(itemId);
				refreshFooter();

			});
			return textField;
		}

		if (propertyId.matches("(price|totalAmount)")) {
			MTextField textField = new MTextField();
			textField.setConverter(new StringToDoubleConverter());
			textField.setStyleName(GrapheneeTheme.STYLE_V_ALIGN_RIGHT);
			textField.setSizeFull();
			textField.setEnabled(false);
			textField.addBlurListener(listener -> {
				billingBean.getGxProductBillingItemCollectionFault().update(itemId);
				refreshFooter();

			});
			return textField;
		}

		Field<?> field = super.propertyField(itemId, propertyId);
		return field;
	}

	public static interface GxBillingTableDelegate<GxBillingBean> {
		default void onUpdate() {

		}
	}

	private void buildFooter() {
		entityTable().setFooterVisible(true);
		entityTable().setColumnFooter("Price", "Total Amount");
		entityTable().setColumnFooter("Total Amount", getTotalAmount());
	}

	public void refreshFooter() {
		entityTable().setColumnFooter("Total Amount", getTotalAmount());
	}

	public void setDelegate(GxBillingTableDelegate<GxBillingBean> delegate) {
		this.delegate = delegate;
	}

	//	@Override
	//	protected void onCellValueChange(Object value, GxBillingItemBean entity, String propertyId) {
	//		if (entity.getQuantity() == 0 || entity.getQuantity() == null) {
	//			billingBean.getGxProductBillingItemCollectionFault().remove(entity);
	//		}
	//
	//	}

}
