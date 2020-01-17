package io.graphenee.core.model.api;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.graphenee.core.model.bean.GxAccountBean;
import io.graphenee.core.model.bean.GxAccountConfigurationBean;
import io.graphenee.core.model.bean.GxAccountTypeBean;
import io.graphenee.core.model.bean.GxBillingBean;
import io.graphenee.core.model.bean.GxProductBean;
import io.graphenee.core.model.bean.GxProductTypeBean;
import io.graphenee.core.model.bean.GxTransactionBean;
import io.graphenee.core.model.bean.GxVoucherBean;
import io.graphenee.core.model.entity.GxAccount;
import io.graphenee.core.model.entity.GxAccountBalance;
import io.graphenee.core.model.entity.GxAccountConfiguration;
import io.graphenee.core.model.entity.GxAccountType;
import io.graphenee.core.model.entity.GxBilling;
import io.graphenee.core.model.entity.GxProduct;
import io.graphenee.core.model.entity.GxProductType;
import io.graphenee.core.model.entity.GxTransaction;
import io.graphenee.core.model.entity.GxVoucher;
import io.graphenee.core.model.jpa.repository.GxAccountBalanceRepository;
import io.graphenee.core.model.jpa.repository.GxAccountConfigurationRepository;
import io.graphenee.core.model.jpa.repository.GxAccountRepository;
import io.graphenee.core.model.jpa.repository.GxAccountTypeRepository;
import io.graphenee.core.model.jpa.repository.GxBillingRepository;
import io.graphenee.core.model.jpa.repository.GxJournalVoucherRepository;
import io.graphenee.core.model.jpa.repository.GxNamespaceRepository;
import io.graphenee.core.model.jpa.repository.GxProductRepository;
import io.graphenee.core.model.jpa.repository.GxProductTypeRepository;
import io.graphenee.core.model.jpa.repository.GxTransactionRepository;
import io.graphenee.core.util.TRCalendarUtil;

@Service
public class GxEntityFactory {

	@Autowired
	GxAccountTypeRepository accountTypeRepository;

	@Autowired
	GxAccountRepository accountRepository;

	@Autowired
	GxNamespaceRepository namespaceRepository;

	@Autowired
	GxTransactionRepository transactionRepository;

	@Autowired
	GxJournalVoucherRepository voucherRepository;

	@Autowired
	GxAccountConfigurationRepository accountConfigurationRepository;

	@Autowired
	GxAccountBalanceRepository accountBalanceRepository;

	@Autowired
	GxProductTypeRepository gxProductTypeRepository;

	@Autowired
	GxProductRepository gxProductRepository;

	@Autowired
	GxBillingRepository gxBillingRepository;

	public GxAccountType makeGxAccountTypeEntity(GxAccountTypeBean bean) {
		GxAccountType entity = null;
		if (bean.getOid() != null)
			entity = accountTypeRepository.findOne(bean.getOid());
		else
			entity = new GxAccountType();
		entity.setTypeCode(bean.getTypeCode());
		entity.setTypeName(bean.getTypeName());
		entity.setAccountNumberSequence(bean.getAccountNumberSequence());
		return entity;
	}

	public GxAccount makeGxAccountEntity(GxAccountBean bean) {
		GxAccount entity = null;
		if (bean.getOid() != null)
			entity = accountRepository.findOne(bean.getOid());
		else
			entity = new GxAccount();
		entity.setAccountName(bean.getAccountName());
		entity.setAccountCode(bean.getAccountCode());

		if (bean.getGxNamespaceBeanFault() != null) {
			entity.setGxNamespace(namespaceRepository.findOne(bean.getGxNamespaceBeanFault().getOid()));
		}
		if (bean.getGxAccountTypeBeanFault() != null) {
			entity.setGxAccountType(accountTypeRepository.findOne(bean.getGxAccountTypeBeanFault().getOid()));
		}
		if (bean.getGxParentAccountBeanFault() != null) {
			entity.setGxParentAccount(accountRepository.findOne(bean.getGxParentAccountBeanFault().getOid()));
		}
		return entity;
	}

	public GxTransaction makeGxTransactionEntity(GxTransactionBean bean, Timestamp transactionDate) {
		GxTransaction entity = null;
		if (bean.getOid() != null)
			entity = transactionRepository.findOne(bean.getOid());
		else
			entity = new GxTransaction();

		entity.setAmount(bean.getAmount());

		entity.setTransactionDate(transactionDate);
		entity.setDescription(bean.getDescription());
		entity.setIsArchived(bean.getIsArchived());
		if (bean.getGxNamespaceBeanFault() != null) {
			entity.setGxNamespace(namespaceRepository.findOne(bean.getGxNamespaceBeanFault().getOid()));
		}
		if (bean.getGxAccountBeanFault() != null) {
			entity.setGxAccount(accountRepository.findOne(bean.getGxAccountBeanFault().getOid()));
		}

		return entity;
	}

	public GxVoucher makeGxVoucherEntity(GxVoucherBean bean) {
		GxVoucher entity = null;
		if (bean.getOid() != null)
			entity = voucherRepository.findOne(bean.getOid());
		else
			entity = new GxVoucher();
		entity.setVoucherDate(bean.getVoucherDate());
		if (bean.getVoucherNumber() == null) {
			GxAccountConfiguration configuration = accountConfigurationRepository.findTop1ByGxNamespaceOid(bean.getGxNamespaceBeanFault().getOid());
			if (configuration != null) {
				Integer lastVoucherNumber = configuration.getVoucherNumber();
				Integer maxVoucherNumber = voucherRepository.findMaxVoucherNumber();
				lastVoucherNumber = lastVoucherNumber.intValue() < maxVoucherNumber.intValue() ? maxVoucherNumber : lastVoucherNumber;
				Integer voucherNumber = lastVoucherNumber;
				++voucherNumber;

				entity.setVoucherNumber(voucherNumber.toString());

				configuration.setVoucherNumber(voucherNumber);
				accountConfigurationRepository.save(configuration);
			}
		} else {
			entity.setVoucherNumber(bean.getVoucherNumber());
		}
		entity.setDescription(bean.getDescription());
		if (bean.getGxNamespaceBeanFault() != null) {
			entity.setGxNamespace(namespaceRepository.findOne(bean.getGxNamespaceBeanFault().getOid()));
		}
		if (bean.getGxTransactionBeanCollectionFault().isModified()) {
			Set<Integer> oids = bean.getGxTransactionBeanCollectionFault().getBeansRemoved().stream().mapToInt(GxTransactionBean::getOid).boxed().collect(Collectors.toSet());
			for (Integer oid : oids) {
				entity.getGxTransactions().removeIf(t -> {
					return t.getOid().intValue() == oid;
				});
			}
			for (GxTransactionBean added : bean.getGxTransactionBeanCollectionFault().getBeansAdded()) {
				entity.getGxTransactions().add(makeGxTransactionEntity(added, bean.getVoucherDate()));
			}
			for (GxTransactionBean updated : bean.getGxTransactionBeanCollectionFault().getBeansUpdated()) {
				makeGxTransactionEntity(updated, bean.getVoucherDate());
			}
		}

		return entity;
	}

	public GxAccountConfiguration makeGxAccountConfigurationEntity(GxAccountConfigurationBean bean) {
		GxAccountConfiguration entity = null;
		if (bean.getOid() != null)
			entity = accountConfigurationRepository.findOne(bean.getOid());
		else
			entity = new GxAccountConfiguration();
		entity.setFiscalYearStart(bean.getFiscalYearStart());
		entity.setVoucherNumber(bean.getVoucherNumber());
		entity.setGxNamespace(namespaceRepository.findOne(bean.getGxNamespaceBeanFault().getOid()));
		return entity;
	}

	public GxAccountBalance makeGxAccountBalanceEntity(GxAccountBean accountBean, GxAccountConfigurationBean accountConfigurationBean) {

		Timestamp fiscalYearEnd = accountConfigurationBean.getFiscalYearEnd();

		GxAccountBalance accountBalance = new GxAccountBalance();
		accountBalance.setGxAccount(accountRepository.findOne(accountBean.getOid()));
		accountBalance.setGxNamespace(namespaceRepository.findOne(accountBean.getGxNamespaceBeanFault().getOid()));

		List<Integer> oids = accountBean.getAllChildAccounts().stream().mapToInt(GxAccountBean::getOid).boxed().collect(Collectors.toList());
		oids.add(accountBean.getOid());

		Double closingBalance = transactionRepository.findBalanceByAccountAndChildAccountsAndDateIsBefore(oids, fiscalYearEnd);
		if (closingBalance == null)
			closingBalance = 0.0;
		accountBalance.setClosingBalance(closingBalance);
		accountBalance.setFiscalYear(TRCalendarUtil.getYear(fiscalYearEnd));
		return accountBalance;
	}

	public GxProduct makeGxProductEntity(GxProductBean gxProductBean) {
		GxProduct gxProduct = null;
		if (gxProductBean.getOid() != null)
			gxProduct = gxProductRepository.findOne(gxProductBean.getOid());
		else
			gxProduct = new GxProduct();

		gxProduct.setBarCode(gxProductBean.getBarCode());
		gxProduct.setDescription(gxProductBean.getDescription());
		gxProduct.setOid(gxProductBean.getOid());
		gxProduct.setPrice(gxProductBean.getPrice());
		gxProduct.setProductCode(gxProductBean.getProductCode());
		gxProduct.setRetailPrice(gxProductBean.getRetailPrice());
		gxProduct.setProductName(gxProductBean.getProductName());
		gxProduct.setGxProductType(gxProductTypeRepository.findOne(gxProductBean.getProductTypeBeanFault().getOid()));

		return gxProduct;

	}

	public GxProductType makeGxProductTypeEntity(GxProductTypeBean gxProductTypeBean) {
		GxProductType gxProductType = null;
		if (gxProductTypeBean.getOid() != null)
			gxProductType = gxProductTypeRepository.findOne(gxProductTypeBean.getOid());
		else
			gxProductType = new GxProductType();

		gxProductType.setOid(gxProductTypeBean.getOid());
		gxProductType.setTypeName(gxProductTypeBean.getTypeName());
		gxProductType.setTypeCode(gxProductTypeBean.getTypeCode());
		return gxProductType;

	}

	public GxBilling makeGxBillingEntity(GxBillingBean bean) {
		GxBilling gxBilling = null;
		if (bean.getOid() != null)
			gxBilling = gxBillingRepository.findOne(gxBilling.getOid());
		else
			gxBilling = new GxBilling();
		gxBilling.setOid(bean.getOid());
		gxBilling.setBillDate(bean.getBillDate());
		gxBilling.setBillNumber(bean.getBillNumber());
		gxBilling.setTax(bean.getTax());
		gxBilling.setDiscount(bean.getDiscount());
		gxBilling.setTotalBill(bean.getTotalBill());
		gxBilling.setTotalPaid(bean.getTotalPaid());
		gxBilling.setTotalPayable(bean.getTotalPayable());
		if (bean.getGxProductBeanCollectionFault().isModified()) {
			Set<Integer> oids = bean.getGxProductBeanCollectionFault().getBeansRemoved().stream().mapToInt(GxProductBean::getOid).boxed().collect(Collectors.toSet());
			for (Integer oid : oids) {
				gxBilling.getGxProducts().removeIf(t -> {
					return t.getOid().intValue() == oid;
				});
			}
			for (GxProductBean added : bean.getGxProductBeanCollectionFault().getBeansAdded()) {
				gxBilling.getGxProducts().add(makeGxProductEntity(added));
			}
			for (GxProductBean updated : bean.getGxProductBeanCollectionFault().getBeansUpdated()) {
				makeGxProductEntity(updated);
			}
		}

		return gxBilling;
	}

}
