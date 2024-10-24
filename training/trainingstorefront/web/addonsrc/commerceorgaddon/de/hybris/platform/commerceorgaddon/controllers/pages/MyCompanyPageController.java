/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company. All rights reserved.
 */
package de.hybris.platform.commerceorgaddon.controllers.pages;

import de.hybris.platform.acceleratorstorefrontcommons.breadcrumb.Breadcrumb;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.ThirdPartyConstants;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.pages.AbstractSearchPageController;
import de.hybris.platform.acceleratorstorefrontcommons.controllers.util.GlobalMessages;
import de.hybris.platform.b2b.constants.B2BConstants;
import de.hybris.platform.b2b.enums.B2BPeriodRange;
import de.hybris.platform.b2b.enums.B2BPermissionTypeEnum;
import de.hybris.platform.b2bapprovalprocessfacades.company.B2BApprovalProcessFacade;
import de.hybris.platform.b2bapprovalprocessfacades.company.B2BApproverFacade;
import de.hybris.platform.b2bapprovalprocessfacades.company.B2BPermissionFacade;
import de.hybris.platform.b2bapprovalprocessfacades.company.data.B2BPermissionData;
import de.hybris.platform.b2bapprovalprocessfacades.company.data.B2BPermissionTypeData;
import de.hybris.platform.b2bcommercefacades.company.B2BBudgetFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BCostCenterFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUnitFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUserFacade;
import de.hybris.platform.b2bcommercefacades.company.B2BUserGroupFacade;
import de.hybris.platform.b2bcommercefacades.company.data.B2BBudgetData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BCostCenterData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BSelectionData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitData;
import de.hybris.platform.b2bcommercefacades.company.data.B2BUnitNodeData;
import de.hybris.platform.cms2.exceptions.CMSItemNotFoundException;
import de.hybris.platform.cms2.model.pages.ContentPageModel;
import de.hybris.platform.commercefacades.customer.CustomerFacade;
import de.hybris.platform.commercefacades.order.CheckoutFacade;
import de.hybris.platform.commercefacades.storesession.StoreSessionFacade;
import de.hybris.platform.commercefacades.storesession.data.CurrencyData;
import de.hybris.platform.commercefacades.user.UserFacade;
import de.hybris.platform.commercefacades.user.data.CustomerData;
import de.hybris.platform.commerceorgaddon.breadcrumb.impl.MyCompanyBreadcrumbBuilder;
import de.hybris.platform.commerceorgaddon.controllers.ControllerConstants;
import de.hybris.platform.commerceorgaddon.forms.B2BBudgetForm;
import de.hybris.platform.commerceorgaddon.forms.B2BCostCenterForm;
import de.hybris.platform.commerceorgaddon.forms.B2BCustomerForm;
import de.hybris.platform.commerceorgaddon.forms.B2BPermissionForm;
import de.hybris.platform.commerceorgaddon.forms.validation.B2BBudgetFormValidator;
import de.hybris.platform.commerceorgaddon.forms.validation.B2BPermissionFormValidator;
import de.hybris.platform.servicelayer.exceptions.ModelSavingException;
import de.hybris.platform.servicelayer.i18n.FormatFactory;
import de.hybris.platform.servicelayer.interceptor.InterceptorException;
import de.hybris.platform.servicelayer.interceptor.impl.UniqueAttributesInterceptor;
import de.hybris.platform.store.services.BaseStoreService;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Controller for organization management.
 */
@Controller
public class MyCompanyPageController extends AbstractSearchPageController
{

	protected static final String MY_COMPANY_CMS_PAGE = "my-company";
	protected static final String ORGANIZATION_MANAGEMENT_CMS_PAGE = "organizationManagement";
	protected static final String MANAGE_UNITS_CMS_PAGE = "manageUnits";
	protected static final String MANAGE_USERGROUPS_CMS_PAGE = "manageUsergroups";
	protected static final String MANAGE_USER_DETAILS_URL = "/my-company/organization-management/manage-users/details?user=%s";
	protected static final String MANAGE_UNIT_DETAILS_URL = "/my-company/organization-management/manage-units/details/?unit=%s";
	protected static final String MANAGE_USERGROUP_DETAILS_URL = "/my-company/organization-management/manage-usergroups/details?usergroup=%s";
	protected static final String MANAGE_COST_CENTER_BASE_URL = "/my-company/organization-management/manage-costcenters";
	protected static final String MANAGE_COSTCENTERS_EDIT_URL = "/my-company/organization-management/manage-costcenters/edit/?costCenterCode=%s";
	protected static final String MANAGE_COSTCENTERS_VIEW_URL = "/my-company/organization-management/manage-costcenters/view/?costCenterCode=%s";
	protected static final String MANAGE_COSTCENTERS_SELECTBUDGET_URL = "/my-company/organization-management/manage-costcenters/selectBudget/?costCenterCode=%s";
	protected static final String MANAGE_PERMISSIONS_VIEW_URL = "/my-company/organization-management/manage-permissions/view/?permissionCode=%s";
	protected static final String MANAGE_USERGROUPS_BASE_URL = "/my-company/organization-management/manage-usergroups";
	protected static final String MANAGE_PERMISSIONS_EDIT_URL = "/my-company/organization-management/manage-permissions/edit/?permissionCode=%s";
	protected static final String REDIRECT_TO_UNIT_DETAILS = REDIRECT_PREFIX + MANAGE_UNIT_DETAILS_URL;
	protected static final String REDIRECT_TO_COSTCENTER_DETAILS = REDIRECT_PREFIX + MANAGE_COSTCENTERS_VIEW_URL;
	protected static final String REDIRECT_TO_USER_DETAILS = REDIRECT_PREFIX + MANAGE_USER_DETAILS_URL;
	protected static final String REDIRECT_TO_BUDGET_DETAILS = REDIRECT_PREFIX
			+ "/my-company/organization-management/manage-budgets/view/?budgetCode=%s";
	protected static final String REDIRECT_TO_PERMISSION_DETAILS = REDIRECT_PREFIX + MANAGE_PERMISSIONS_VIEW_URL;
	protected static final String REDIRECT_TO_USERGROUP_DETAILS = REDIRECT_PREFIX + MANAGE_USERGROUP_DETAILS_URL;
	protected static final String REDIRECT_TO_USER_GROUPS_PAGE = REDIRECT_PREFIX + MANAGE_USERGROUPS_BASE_URL;
	private static final Logger LOG = Logger.getLogger(MyCompanyPageController.class);
	protected static final String SINGLE_WHITEPSACE = " ";
	protected static final String MANAGE_UNITS_BASE_URL = "/my-company/organization-management/manage-units";
	protected static final String ADD_COSTCENTER_URL = "/my-company/organization-management/manage-costcenters/add";
	protected static final String EDIT_COSTCENTER_URL = "/my-company/organization-management/manage-costcenters/update";
	private static final String FORM_GLOBAL_ERROR = "form.global.error";
	private static final String TITLE_DATA = "titleData";
	private static final int DEFAULT_SEARCH_PAGE_SIZE = 9;
	private static final String BREADCRUMBS = "breadcrumbs";
	private static final String B2B_CUSTOMER_FORM = "b2BCustomerForm";
	private static final String ROLES = "roles";

	@Resource(name = "checkoutFacade")
	protected CheckoutFacade checkoutFacade;

	@Resource(name = "storeSessionFacade")
	protected StoreSessionFacade storeSessionFacade;

	@Resource(name = "customerFacade")
	protected CustomerFacade customerFacade;

	@Resource(name = "b2bApprovalProcessFacade")
	protected B2BApprovalProcessFacade b2bApprovalProcessFacade;

	@Resource(name = "b2bUserFacade")
	protected B2BUserFacade b2bUserFacade;

	@Resource(name = "b2bUnitFacade")
	protected B2BUnitFacade b2bUnitFacade;

	@Resource(name = "b2bPermissionFacade")
	protected B2BPermissionFacade b2bPermissionFacade;

	@Resource(name = "costCenterFacade")
	protected B2BCostCenterFacade b2bCostCenterFacade;

	@Resource(name = "budgetFacade")
	protected B2BBudgetFacade budgetFacade;

	@Resource(name = "b2bUserGroupFacade")
	protected B2BUserGroupFacade b2bUserGroupFacade;

	@Resource(name = "myCompanyBreadcrumbBuilder")
	protected MyCompanyBreadcrumbBuilder myCompanyBreadcrumbBuilder;

	@Resource(name = "b2BPermissionFormValidator")
	protected B2BPermissionFormValidator b2BPermissionFormValidator;

	@Resource(name = "b2BBudgetFormValidator")
	protected B2BBudgetFormValidator b2BBudgetFormValidator;

	@Resource(name = "formatFactory")
	protected FormatFactory formatFactory;

	@Resource(name = "userFacade")
	private UserFacade userFacade;

	@Resource(name = "b2bApproverFacade")
	protected B2BApproverFacade b2bApproverFacade;

	@Resource(name = "baseStoreService")
	protected BaseStoreService baseStoreService;


	@Override
	protected UserFacade getUserFacade()
	{
		return userFacade;
	}

	@ModelAttribute("b2bUnits")
	public List<SelectOption> getB2BUnits()
	{
		return populateSelectBoxForString(b2bUnitFacade.getAllActiveUnitsOfOrganization());
	}

	@ModelAttribute("b2bCostCenterCurrencies")
	public List<SelectOption> getAllCostCenters()
	{
		return populateCurrencySelectBox(storeSessionFacade.getAllCurrencies());
	}

	@ModelAttribute("b2bPeriodRanges")
	public List<SelectOption> getB2BPeriodRanges()
	{
		final List<String> periodRanges = new ArrayList<String>();
		for (final B2BPeriodRange range : B2BPeriodRange.values())
		{
			periodRanges.add(range.toString());
		}
		return populateSelectBoxForString(periodRanges);
	}

	@ModelAttribute("b2bPermissionTypes")
	public List<SelectOption> getB2BPermissionTypes()
	{
		final List<SelectOption> b2bPermissionTypeList = new ArrayList<SelectOption>();
		final List<B2BPermissionTypeData> permissionTypeDatalist = (List<B2BPermissionTypeData>) b2bPermissionFacade
				.getB2BPermissionTypes();
		for (final B2BPermissionTypeData b2bPermissionType : permissionTypeDatalist)
		{
			final SelectOption selectOption = new SelectOption(b2bPermissionType.getCode(), b2bPermissionType.getName());
			b2bPermissionTypeList.add(selectOption);
		}

		return b2bPermissionTypeList;
	}


	@InitBinder
	protected void initBinder(final ServletRequestDataBinder binder)
	{
		final DateFormat dateFormat = new SimpleDateFormat(
				getMessageSource().getMessage("text.store.dateformat", null, getI18nService().getCurrentLocale()));
		final CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@ModelAttribute("b2bStore")
	public String getCurrentB2BStore()
	{
		return baseStoreService.getCurrentBaseStore() == null ? StringUtils.EMPTY
				: baseStoreService.getCurrentBaseStore().getName();
	}

	protected String unitDetails(final String unit, final Model model) throws CMSItemNotFoundException
	{
		final ContentPageModel manageUnitsPage = getContentPageForLabelOrId(MANAGE_UNITS_CMS_PAGE);
		storeCmsPageInModel(model, manageUnitsPage);
		setUpMetaDataForContentPage(model, manageUnitsPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUnitsDetailsBreadcrumbs(unit);
		model.addAttribute(BREADCRUMBS, breadcrumbs);

		B2BUnitData unitData = b2bUnitFacade.getUnitForUid(unit);
		if (unitData == null)
		{
			unitData = new B2BUnitData();
			GlobalMessages.addErrorMessage(model, "b2bunit.notfound");
		}
		else if (!unitData.isActive())
		{
			GlobalMessages.addInfoMessage(model, "b2bunit.disabled.infomsg");
		}

		model.addAttribute("unit", unitData);
		model.addAttribute("user", customerFacade.getCurrentCustomer());
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUnitDetailsPage;
	}

	protected String addCostCenter(final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute("b2BCostCenterForm"))
		{
			final B2BCostCenterForm b2BCostCenterForm = new B2BCostCenterForm();
			b2BCostCenterForm.setParentB2BUnit(b2bUnitFacade.getParentUnit().getUid());
			model.addAttribute(b2BCostCenterForm);
		}
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageCostCenterBreadCrumbs();
		breadcrumbs.add(new Breadcrumb(ADD_COSTCENTER_URL,
				getMessageSource().getMessage("text.company.costCenter.addPage", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyCostCenterAddPage;
	}

	protected String saveCostCenter(@Valid final B2BCostCenterForm b2BCostCenterForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasFieldErrors())
		{
			model.addAttribute(b2BCostCenterForm);
			return addCostCenter(model);
		}
		final B2BCostCenterData b2BCostCenterData = populateB2BCostCenterDataFromForm(b2BCostCenterForm);
		try
		{
			b2bCostCenterFacade.addCostCenter(b2BCostCenterData);
		}
		catch (final RuntimeException e)
		{
			LOG.warn("Exception while saving the cost center details " + e);
			model.addAttribute(b2BCostCenterForm);
			bindingResult.rejectValue("code", "text.company.costCenter.code.exists.error.title");
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			return addCostCenter(model);
		}
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageCostCenterBreadCrumbs();
		breadcrumbs.add(new Breadcrumb(ADD_COSTCENTER_URL,
				getMessageSource().getMessage("text.company.costCenter.addPage", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.company.costCenter.create.success");
		return String.format(REDIRECT_TO_COSTCENTER_DETAILS, urlEncode(b2BCostCenterData.getCode()));
	}

	protected String viewCostCenterDetails(final String costCenterCode, final Model model) throws CMSItemNotFoundException
	{
		model.addAttribute("b2bCostCenter", b2bCostCenterFacade.getCostCenterDataForCode(costCenterCode));
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageCostCenterBreadCrumbs();
		myCompanyBreadcrumbBuilder.addViewCostCenterBreadCrumbs(breadcrumbs, costCenterCode);
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyCostCenterViewPage;
	}

	protected String editCostCenterDetails(final String costCenterCode, final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute("b2BCostCenterForm"))
		{
			final B2BCostCenterData b2BCostCenterData = b2bCostCenterFacade.getCostCenterDataForCode(costCenterCode);
			final B2BCostCenterForm b2BCostCenterform = new B2BCostCenterForm();
			b2BCostCenterform.setOriginalCode(costCenterCode);
			b2BCostCenterform.setCode(b2BCostCenterData.getCode());
			b2BCostCenterform.setCurrency(b2BCostCenterData.getCurrency().getIsocode());
			b2BCostCenterform.setName(b2BCostCenterData.getName());
			b2BCostCenterform.setParentB2BUnit(b2BCostCenterData.getUnit().getUid());
			model.addAttribute(b2BCostCenterform);
		}
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageCostCenterBreadCrumbs();
		myCompanyBreadcrumbBuilder.addViewCostCenterBreadCrumbs(breadcrumbs, costCenterCode);
		breadcrumbs.add(new Breadcrumb(String.format(MANAGE_COSTCENTERS_EDIT_URL, urlEncode(costCenterCode)),
				getMessageSource().getMessage("text.company.costCenter.edit.title", null, getI18nService().getCurrentLocale()),
				null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyCostCenterAddPage;
	}

	protected String updateCostCenterDetails(final B2BCostCenterForm b2BCostCenterForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			model.addAttribute(b2BCostCenterForm);
			return editCostCenterDetails(b2BCostCenterForm.getOriginalCode(), model);
		}
		final B2BCostCenterData b2BCostCenterData = populateB2BCostCenterDataFromForm(b2BCostCenterForm);

		try
		{
			b2bCostCenterFacade.updateCostCenter(b2BCostCenterData);
		}
		catch (final RuntimeException e)
		{
			LOG.warn("Exception while saving the cost center details " + e);
			model.addAttribute(b2BCostCenterForm);
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			return editCostCenterDetails(b2BCostCenterData.getOriginalCode(), model);
		}
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER,
				"text.company.costCenter.update.success");
		return String.format(REDIRECT_TO_COSTCENTER_DETAILS, urlEncode(b2BCostCenterData.getCode()));
	}

	protected String createUser(final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute(B2B_CUSTOMER_FORM))
		{
			final B2BCustomerForm b2bCustomerForm = new B2BCustomerForm();
			b2bCustomerForm.setParentB2BUnit(b2bUnitFacade.getParentUnit().getUid());

			// Add the b2bcustomergroup role by default
			b2bCustomerForm.setRoles(Collections.singletonList("b2bcustomergroup"));

			model.addAttribute(b2bCustomerForm);
		}
		model.addAttribute(TITLE_DATA, getUserFacade().getTitles());
		model.addAttribute(ROLES, populateRolesCheckBoxes(b2bUserGroupFacade.getUserGroups()));

		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserBreadcrumb();
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users/create", getMessageSource()
				.getMessage("text.company.organizationManagement.createuser", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserAddEditFormPage;
	}

	protected String createUser(final B2BCustomerForm b2BCustomerForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{

		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			model.addAttribute(b2BCustomerForm);
			return createUser(model);
		}

		final CustomerData b2bCustomerData = new CustomerData();
		b2bCustomerData.setTitleCode(b2BCustomerForm.getTitleCode());
		b2bCustomerData.setFirstName(b2BCustomerForm.getFirstName());
		b2bCustomerData.setLastName(b2BCustomerForm.getLastName());
		b2bCustomerData.setEmail(b2BCustomerForm.getEmail());
		b2bCustomerData.setDisplayUid(b2BCustomerForm.getEmail());
		b2bCustomerData.setUnit(b2bUnitFacade.getUnitForUid(b2BCustomerForm.getParentB2BUnit()));
		b2bCustomerData.setRoles(b2BCustomerForm.getRoles());
		model.addAttribute(b2BCustomerForm);
		model.addAttribute(TITLE_DATA, getUserFacade().getTitles());
		model.addAttribute(ROLES, populateRolesCheckBoxes(b2bUserGroupFacade.getUserGroups()));

		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.getBreadcrumbs(null);
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/",
				getMessageSource().getMessage("text.company.organizationManagement", null, getI18nService().getCurrentLocale()),
				null));
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-user",
				getMessageSource().getMessage("text.company.manageUsers", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);

		try
		{
			b2bUserFacade.updateCustomer(b2bCustomerData);
			b2bCustomerData.setUid(b2BCustomerForm.getEmail().toLowerCase(Locale.getDefault()));
			b2BCustomerForm.setUid(b2bCustomerData.getUid());
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.added");
		}
		catch (final ModelSavingException e)
		{
			if (e.getCause() instanceof InterceptorException
					&& ((InterceptorException) e.getCause()).getInterceptor().getClass().equals(UniqueAttributesInterceptor.class))
			{
				LOG.error("The uid of the model being stored already exists, could not save. Edit user instead.");

				bindingResult.rejectValue("email", "text.manageuser.error.email.exists.title");
				GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
				model.addAttribute(B2B_CUSTOMER_FORM, b2BCustomerForm);
				return editUser(b2BCustomerForm.getEmail(), model);
			}
			else
			{
				LOG.error("Failed to create user.");
			}
		}
		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(b2bCustomerData.getUid()));
	}

	public String editUser(final String user, final Model model) throws CMSItemNotFoundException
	{
		List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);

		final CustomerData customerData = b2bUserFacade.getCustomerForUid(user);
		final String userId = StringUtils.isNotBlank(customerData.getCustomerId()) ? customerData.getCustomerId() : user;

		if (!model.containsAttribute(B2B_CUSTOMER_FORM))
		{
			final B2BCustomerForm b2bCustomerForm = new B2BCustomerForm();
			b2bCustomerForm.setUid(customerData.getUid());
			b2bCustomerForm.setTitleCode(customerData.getTitleCode());
			b2bCustomerForm.setFirstName(customerData.getFirstName());
			b2bCustomerForm.setLastName(customerData.getLastName());
			b2bCustomerForm.setEmail(customerData.getDisplayUid());
			b2bCustomerForm.setParentB2BUnit(b2bUserFacade.getParentUnitForCustomer(customerData.getUid()).getUid());
			b2bCustomerForm.setActive(customerData.isActive());
			b2bCustomerForm.setApproverGroups(customerData.getApproverGroups());
			b2bCustomerForm.setApprovers(customerData.getApprovers());
			b2bCustomerForm.setRoles(customerData.getRoles());
			model.addAttribute(b2bCustomerForm);
			model.addAttribute("userId", userId);
		}

		model.addAttribute(TITLE_DATA, getUserFacade().getTitles());
		model.addAttribute(ROLES, populateRolesCheckBoxes(b2bUserGroupFacade.getUserGroups()));

		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);

		if (model.containsAttribute("newUserEdit"))
		{
			breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserBreadcrumb();
			breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-users/create", getMessageSource()
					.getMessage("text.company.organizationManagement.createuser", null, getI18nService().getCurrentLocale()), null));
		}
		else
		{
			breadcrumbs.add(
					new Breadcrumb(String.format("/my-company/organization-management/manage-users/edit?user=%s", urlEncode(userId)),
							getMessageSource().getMessage("text.company.manageusers.edit", new Object[]
							{ user }, "Edit {0} User", getI18nService().getCurrentLocale()), null));
		}
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserAddEditFormPage;
	}

	protected String editUser(final String user, final B2BCustomerForm b2BCustomerForm, final BindingResult bindingResult,
			final Model model, final RedirectAttributes redirectModel) throws CMSItemNotFoundException
	{
		if (bindingResult.hasErrors())
		{
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			model.addAttribute(b2BCustomerForm);
			return editUser(b2BCustomerForm.getUid(), model);
		}

		// A B2B Admin should not be able to downgrade their roles, they must at lest belong to B2B Administrator role
		if (customerFacade.getCurrentCustomer().getUid().equals(b2BCustomerForm.getUid()))
		{
			final Collection<String> roles = b2BCustomerForm.getRoles() != null ? b2BCustomerForm.getRoles()
					: new ArrayList<String>();
			if (!roles.contains(B2BConstants.B2BADMINGROUP))
			{
				GlobalMessages.addErrorMessage(model, "form.b2bcustomer.adminrole.error");
				roles.add(B2BConstants.B2BADMINGROUP);
				b2BCustomerForm.setRoles(roles);
				model.addAttribute(b2BCustomerForm);
				return editUser(b2BCustomerForm.getUid(), model);
			}
			else
			{
				// A session user can't modify their own parent unit.
				final B2BUnitData parentUnit = b2bUnitFacade.getParentUnit();
				if (!parentUnit.getUid().equals(b2BCustomerForm.getParentB2BUnit()))
				{
					GlobalMessages.addErrorMessage(model, "form.b2bcustomer.parentunit.error");
					b2BCustomerForm.setParentB2BUnit(parentUnit.getUid());
					model.addAttribute(b2BCustomerForm);
					return editUser(b2BCustomerForm.getUid(), model);
				}
			}
		}

		final CustomerData b2bCustomerData = new CustomerData();
		b2bCustomerData.setUid(b2BCustomerForm.getUid());
		b2bCustomerData.setTitleCode(b2BCustomerForm.getTitleCode());
		b2bCustomerData.setFirstName(b2BCustomerForm.getFirstName());
		b2bCustomerData.setLastName(b2BCustomerForm.getLastName());
		b2bCustomerData.setEmail(b2BCustomerForm.getEmail());
		b2bCustomerData.setDisplayUid(b2BCustomerForm.getEmail());
		b2bCustomerData.setUnit(b2bUnitFacade.getUnitForUid(b2BCustomerForm.getParentB2BUnit()));
		b2bCustomerData
				.setRoles(b2BCustomerForm.getRoles() != null ? b2BCustomerForm.getRoles() : Collections.<String> emptyList());
		model.addAttribute(b2BCustomerForm);
		model.addAttribute(TITLE_DATA, getUserFacade().getTitles());
		model.addAttribute(ROLES, populateRolesCheckBoxes(b2bUserGroupFacade.getUserGroups()));

		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		model.addAttribute(BREADCRUMBS, breadcrumbs);

		try
		{
			b2bUserFacade.updateCustomer(b2bCustomerData);
			b2bCustomerData.setUid(b2BCustomerForm.getEmail().toLowerCase(Locale.getDefault()));
			GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.user.edited");
		}
		catch (final ModelSavingException e)
		{
			LOG.error("Failed to edit user. Email already exists.");
			bindingResult.rejectValue("email", "text.manageuser.error.email.exists.title");
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			model.addAttribute(B2B_CUSTOMER_FORM, b2BCustomerForm);
			return editUser(b2BCustomerForm.getUid(), model);

		}

		final String updateUid = b2bCustomerData.getUid();
		final CustomerData customerData = b2bUserFacade.getCustomerForUid(updateUid);
		final String userId = StringUtils.isNotBlank(customerData.getCustomerId()) ? customerData.getCustomerId() : updateUid;

		return String.format(REDIRECT_TO_USER_DETAILS, urlEncode(userId));
	}

	protected String manageUserDetail(final String user, final Model model) throws CMSItemNotFoundException
	{
		final CustomerData customerData = b2bUserFacade.getCustomerForUid(user);
		model.addAttribute("customerData", customerData);
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManageUserDetailsBreadcrumb(user);
		model.addAttribute(BREADCRUMBS, breadcrumbs);

		if (!customerData.getUnit().isActive())
		{
			GlobalMessages.addInfoMessage(model, "text.parentunit.disabled.warning");
		}
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManageUserDetailPage;
	}


	/**
	 * Data class used to hold a drop down select option value. Holds the code identifier as well as the display name.
	 */
	public static class SelectOption
	{
		private final String code;
		private final String name;

		public SelectOption(final String code, final String name)
		{
			this.code = code;
			this.name = name;
		}

		public String getCode()
		{
			return code;
		}

		public String getName()
		{
			return name;
		}
	}

	protected B2BCostCenterData populateB2BCostCenterDataFromForm(final B2BCostCenterForm b2BCostCenterForm)
	{
		final B2BCostCenterData b2BCostCenterData = new B2BCostCenterData();
		b2BCostCenterData.setOriginalCode(b2BCostCenterForm.getOriginalCode());
		b2BCostCenterData.setCode(b2BCostCenterForm.getCode());
		b2BCostCenterData.setName(b2BCostCenterForm.getName());
		final CurrencyData currencyData = new CurrencyData();
		currencyData.setIsocode(b2BCostCenterForm.getCurrency());
		b2BCostCenterData.setCurrency(currencyData);
		b2BCostCenterData.setUnit(b2bUnitFacade.getUnitForUid(b2BCostCenterForm.getParentB2BUnit()));

		return b2BCostCenterData;
	}

	protected B2BBudgetData populateB2BBudgetDataFromForm(final B2BBudgetForm b2BBudgetForm) throws ParseException
	{
		final B2BBudgetData b2BBudgetData = new B2BBudgetData();
		b2BBudgetData.setOriginalCode(b2BBudgetForm.getOriginalCode());
		b2BBudgetData.setCode(b2BBudgetForm.getCode());
		b2BBudgetData.setName(b2BBudgetForm.getName());
		b2BBudgetData.setUnit(b2bUnitFacade.getUnitForUid(b2BBudgetForm.getParentB2BUnit()));
		final CurrencyData currencyData = new CurrencyData();
		currencyData.setIsocode(b2BBudgetForm.getCurrency());
		b2BBudgetData.setCurrency(currencyData);
		b2BBudgetData.setStartDate(b2BBudgetForm.getStartDate());
		b2BBudgetData.setEndDate(b2BBudgetForm.getEndDate());
		b2BBudgetData
				.setBudget(BigDecimal.valueOf(formatFactory.createNumberFormat().parse(b2BBudgetForm.getBudget()).doubleValue()));

		return b2BBudgetData;
	}

	protected B2BPermissionData populateB2BPermissionDataFromForm(final B2BPermissionForm b2BPermissionForm) throws ParseException
	{
		final B2BPermissionData b2BPermissionData = new B2BPermissionData();
		b2BPermissionData.setOriginalCode(b2BPermissionForm.getOriginalCode());
		final String permissionCode = b2BPermissionForm.getCode();
		if (StringUtils.isNotEmpty(permissionCode))
		{
			b2BPermissionData.setCode(permissionCode);
		}
		else
		{
			b2BPermissionData.setCode(assignPermissionName(b2BPermissionForm));
		}
		final B2BPermissionTypeData b2BPermissionTypeData = b2BPermissionForm.getB2BPermissionTypeData();
		b2BPermissionData.setB2BPermissionTypeData(b2BPermissionTypeData);
		final CurrencyData currencyData = new CurrencyData();
		currencyData.setIsocode(b2BPermissionForm.getCurrency());
		b2BPermissionData.setCurrency(currencyData);

		b2BPermissionData.setUnit(b2bUnitFacade.getUnitForUid(b2BPermissionForm.getParentUnitName()));
		final String permissionTimespan = b2BPermissionForm.getTimeSpan();
		if (StringUtils.isNotEmpty(permissionTimespan))
		{
			b2BPermissionData.setPeriodRange(B2BPeriodRange.valueOf(b2BPermissionForm.getTimeSpan()));
		}
		final String monetaryValue = b2BPermissionForm.getValue();
		if (StringUtils.isNotEmpty(monetaryValue))
		{
			b2BPermissionData.setValue(Double.valueOf(formatFactory.createNumberFormat().parse(monetaryValue).doubleValue()));
		}
		return b2BPermissionData;
	}

	protected String assignPermissionName(final B2BPermissionForm b2BPermissionForm)
	{
		final StringBuilder permissionCode = new StringBuilder();
		final String permissionType = b2BPermissionForm.getB2BPermissionTypeData().getCode();

		if (!B2BPermissionTypeEnum.B2BBUDGETEXCEEDEDPERMISSION.equals(B2BPermissionTypeEnum.valueOf(permissionType)))
		{
			final String currency = b2BPermissionForm.getCurrency();
			permissionCode.append((StringUtils.isNotEmpty(currency) ? currency : ""));
			permissionCode.append(SINGLE_WHITEPSACE);
			permissionCode.append(b2BPermissionForm.getValue());
			permissionCode.append(SINGLE_WHITEPSACE);
			permissionCode.append(getMessageSource().getMessage("text.company.managePermissions.assignName.per", null,
					getI18nService().getCurrentLocale()));
			permissionCode.append(SINGLE_WHITEPSACE);
			final String timespan = b2BPermissionForm.getTimeSpan();
			permissionCode.append((StringUtils.isNotEmpty(timespan) ? timespan
					: getMessageSource().getMessage("text.company.managePermissions.assignName.order", null,
							getI18nService().getCurrentLocale())));
		}
		else
		{
			permissionCode.append(getMessageSource().getMessage("text.company.managePermissions.assignName.budget", null,
					getI18nService().getCurrentLocale()));
			permissionCode.append(SINGLE_WHITEPSACE);
			permissionCode.append(b2BPermissionForm.getParentUnitName());
		}

		b2BPermissionForm.setCode(permissionCode.toString());
		return permissionCode.toString();
	}

	public String editPermission(final String permissionCode, final Model model) throws CMSItemNotFoundException
	{
		if (!model.containsAttribute("b2BPermissionForm"))
		{
			final B2BPermissionData b2BPermissionData = b2bPermissionFacade.getPermissionDetails(permissionCode);
			final B2BPermissionForm b2BPermissionForm = new B2BPermissionForm();
			b2BPermissionForm.setCode(b2BPermissionData.getCode());
			b2BPermissionForm.setOriginalCode(b2BPermissionData.getCode());
			b2BPermissionForm.setParentUnitName(b2BPermissionData.getUnit().getUid());
			b2BPermissionForm.setPermissionType(b2BPermissionData.getB2BPermissionTypeData().getName());
			b2BPermissionForm.setB2BPermissionTypeData(b2BPermissionData.getB2BPermissionTypeData());
			if (!(B2BPermissionTypeEnum.B2BBUDGETEXCEEDEDPERMISSION
					.equals(B2BPermissionTypeEnum.valueOf(b2BPermissionData.getB2BPermissionTypeData().getCode()))))
			{
				b2BPermissionForm.setTimeSpan(b2BPermissionData.getTimeSpan());
				b2BPermissionForm.setValue(formatFactory.createNumberFormat().format(b2BPermissionData.getValue()));
				b2BPermissionForm.setCurrency(b2BPermissionData.getCurrency().getIsocode());
			}

			model.addAttribute(b2BPermissionForm);
		}
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManagePermissionsBreadcrumb();
		myCompanyBreadcrumbBuilder.addViewPermissionBreadCrumbs(breadcrumbs, permissionCode);
		breadcrumbs.add(new Breadcrumb(String.format(MANAGE_PERMISSIONS_EDIT_URL, urlEncode(permissionCode)),
				getMessageSource().getMessage("text.company.managePermissions.edit.page", new Object[]
				{ permissionCode }, "Edit Order Threshold {0}", getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);
		model.addAttribute(ThirdPartyConstants.SeoRobots.META_ROBOTS, ThirdPartyConstants.SeoRobots.NOINDEX_NOFOLLOW);
		return ControllerConstants.Views.Pages.MyCompany.MyCompanyManagePermissionsEditPage;
	}

	public String editPermission(final B2BPermissionForm b2BPermissionForm, final BindingResult bindingResult, final Model model,
			final RedirectAttributes redirectModel) throws CMSItemNotFoundException, ParseException
	{
		b2BPermissionFormValidator.validate(b2BPermissionForm, bindingResult);
		if (bindingResult.hasErrors())
		{
			model.addAttribute(b2BPermissionForm);
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			return editPermission(b2BPermissionForm.getOriginalCode(), model);
		}

		final B2BPermissionData b2BPermissionData = populateB2BPermissionDataFromForm(b2BPermissionForm);
		try
		{
			b2bPermissionFacade.updatePermissionDetails(b2BPermissionData);
		}
		catch (final RuntimeException e)
		{
			LOG.warn("Exception while saving the permission details " + e);
			model.addAttribute(b2BPermissionForm);
			GlobalMessages.addErrorMessage(model, FORM_GLOBAL_ERROR);
			bindingResult.rejectValue("code", "text.company.managePermissions.code.exists.error.title");
			return editPermission(b2BPermissionForm.getOriginalCode(), model);
		}
		final ContentPageModel organizationManagementPage = getContentPageForLabelOrId(ORGANIZATION_MANAGEMENT_CMS_PAGE);
		storeCmsPageInModel(model, organizationManagementPage);
		setUpMetaDataForContentPage(model, organizationManagementPage);
		final List<Breadcrumb> breadcrumbs = myCompanyBreadcrumbBuilder.createManagePermissionsBreadcrumb();
		breadcrumbs.add(new Breadcrumb("/my-company/organization-management/manage-budgets/update",
				getMessageSource().getMessage("text.company.budget.editPage", null, getI18nService().getCurrentLocale()), null));
		model.addAttribute(BREADCRUMBS, breadcrumbs);

		GlobalMessages.addFlashMessage(redirectModel, GlobalMessages.CONF_MESSAGES_HOLDER, "text.confirmation.permission.updated");
		return String.format(REDIRECT_TO_PERMISSION_DETAILS, urlEncode(b2BPermissionData.getCode()));
	}



	protected boolean checkEndDateIsBeforeStartDateForBudget(final B2BBudgetForm b2BBudgetForm)
	{
		final Date startDate = b2BBudgetForm.getStartDate();
		final Date endDate = b2BBudgetForm.getEndDate();
		return endDate.before(startDate);
	}

	protected List<SelectOption> populateCurrencySelectBox(final Collection<CurrencyData> currencies)
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();
		for (final CurrencyData currency : currencies)
		{
			selectOptions.add(new SelectOption(currency.getIsocode(), currency.getIsocode()));
		}

		return selectOptions;
	}

	protected List<SelectOption> populateSelectBoxForString(final List<String> listOfStrings)
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();
		for (final String string : listOfStrings)
		{
			selectOptions.add(new SelectOption(string, string));
		}

		return selectOptions;
	}

	protected List<SelectOption> populateRolesCheckBoxes(final List<String> roles)
	{
		final List<SelectOption> selectBoxList = new ArrayList<SelectOption>();
		for (final String data : roles)
		{
			selectBoxList.add(new SelectOption(data, getMessageSource().getMessage(String.format("b2busergroup.%s.name", data), null,
					getI18nService().getCurrentLocale())));
		}

		return selectBoxList;
	}

	protected List<SelectOption> getBranchSelectOptions(final List<B2BUnitNodeData> branchNodes)
	{
		final List<SelectOption> selectOptions = new ArrayList<SelectOption>();

		for (final B2BUnitNodeData b2bUnitNode : branchNodes)
		{
			if (b2bUnitNode.isActive())
			{
				selectOptions.add(new SelectOption(b2bUnitNode.getId(), b2bUnitNode.getName()));
			}
		}

		return selectOptions;
	}

	protected B2BSelectionData populateDisplayNamesForRoles(final B2BSelectionData b2BSelectionData)
	{
		final List<String> roles = b2BSelectionData.getRoles();
		final List<String> displayRoles = new ArrayList<String>(roles.size());
		for (final String role : roles)
		{
			displayRoles.add(
					getMessageSource().getMessage("b2busergroup." + role + ".name", null, role, getI18nService().getCurrentLocale()));
		}
		b2BSelectionData.setDisplayRoles(displayRoles);
		return b2BSelectionData;
	}

	protected String getCancelUrl(final String url, final String contextPath, final String param)
	{
		return String.format(contextPath + url, urlEncode(param));
	}

	@Override
	protected int getSearchPageSize()
	{
		return getSiteConfigService().getInt("commerceorgaddon.search.pageSize", DEFAULT_SEARCH_PAGE_SIZE);
	}

}
