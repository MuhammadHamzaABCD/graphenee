package io.graphenee.accounting.api;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import io.graphenee.core.model.bean.GxAccountBean;
import io.graphenee.core.model.bean.GxAccountConfigurationBean;
import io.graphenee.core.model.bean.GxAccountTypeBean;
import io.graphenee.core.model.bean.GxBalanceSheetBean;
import io.graphenee.core.model.bean.GxGeneralLedgerBean;
import io.graphenee.core.model.bean.GxIncomeStatementBean;
import io.graphenee.core.model.bean.GxNamespaceBean;
import io.graphenee.core.model.bean.GxTransactionBean;
import io.graphenee.core.model.bean.GxTrialBalanceBean;
import io.graphenee.core.model.bean.GxVoucherBean;

public interface GxAccountingDataService {

	List<GxAccountTypeBean> findAllAccountTypes();

	GxAccountTypeBean createOrUpdate(GxAccountTypeBean bean);

	void delete(GxAccountTypeBean bean);

	GxAccountBean createOrUpdate(GxAccountBean bean);

	List<GxAccountBean> findAllAccounts();

	List<GxAccountBean> findAllAccountsByNamespace(GxNamespaceBean namespaceBean);

	List<GxAccountBean> findAllAccountsByAccountType(GxAccountTypeBean bean);

	List<GxAccountBean> findAllAccountsByNamespaceAndAccountType(GxNamespaceBean namespaceBean, GxAccountTypeBean bean);

	void delete(GxAccountBean bean);

	GxAccountBean findByAccountNumberAndNamespace(Integer accountCode, GxNamespaceBean namespaceBean);

	GxAccountBean findByAccountNumber(Integer accountCode);

	List<GxTransactionBean> findAllTransactionsOrderByDateAsc();

	List<GxTransactionBean> findAllTransactionsByNamespaceOrderByDateAsc(GxNamespaceBean namespaceBean);

	List<GxVoucherBean> findAllVouchersOrderByVoucherDateAsc();

	List<GxVoucherBean> findAllVouchersByNamespaceOrderByVoucherDateAsc(GxNamespaceBean namespaceBean);

	GxVoucherBean createOrUpdate(GxVoucherBean bean);

	void delete(GxVoucherBean bean);

	List<GxGeneralLedgerBean> findAllByAccountAndNamespaceAndDateRangeOrderByTransactionDateAsc(GxAccountBean accountBean, GxNamespaceBean namespaceBean, Timestamp fromDate,
			Timestamp toDate);

	Map<String, List<GxGeneralLedgerBean>> findAllByNamespaceAndDateRangeOrderByTransactionDateAsc(GxNamespaceBean namespaceBean, Timestamp fromDate, Timestamp toDate);

	Double findAccountBalanceByAccountAndDateIsBefore(GxAccountBean accountBean, Timestamp date);

	Double findAccountBalanceByAccountAndChildAccountsAndDateIsBefore(List<Integer> oids, Timestamp date);

	List<GxTrialBalanceBean> findAllByMonthAndYearAndNamespace(Timestamp date, GxNamespaceBean namespaceBean);

	List<GxGeneralLedgerBean> findAllByAccountAndChildAccountsAndNamespaceAndDateRangeOrderByTransactionDateAsc(GxAccountBean selectedAccount, GxNamespaceBean namespaceBean,
			Timestamp fromDate, Timestamp toDate);

	GxAccountConfigurationBean findAccountConfigurationByNamespace(GxNamespaceBean namespaceBean);

	GxAccountConfigurationBean createOrUpdate(GxAccountConfigurationBean bean);

	void closeYear(List<GxAccountBean> accounts, GxNamespaceBean namespaceBean);

	List<GxBalanceSheetBean> findBalanceSheetByDateAndNamespace(Timestamp toDate, GxNamespaceBean namespaceBean);

	Double findNetIncomeByDateAndNamespace(Timestamp toDate, GxNamespaceBean namespaceBean);

	List<GxIncomeStatementBean> findIncomeStatementByDateAndNamespace(Timestamp toDate, GxNamespaceBean namespaceBean);

	List<Integer> findTransactionYearByNamespace(GxNamespaceBean namespaceBean);
}
