/**
 *
 */
package org.training.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.keygenerator.KeyGenerator;
import de.hybris.platform.servicelayer.model.ModelService;

import org.training.core.model.TrainingEmailProcessModel;


/**
 *
 */
public class TrainingEmailEventListener extends AbstractAcceleratorSiteEventListener<TrainingEmailEvent>
{

	private ModelService modelService;
	private BusinessProcessService businessProcessService;
	private KeyGenerator processCodeGenerator;

	/**
	 * @return the processCodeGenerator
	 */
	public KeyGenerator getProcessCodeGenerator()
	{
		return processCodeGenerator;
	}

	/**
	 * @param processCodeGenerator
	 *                                the processCodeGenerator to set
	 */
	public void setProcessCodeGenerator(final KeyGenerator processCodeGenerator)
	{
		this.processCodeGenerator = processCodeGenerator;
	}

	/**
	 * @return the businessProcessService
	 */
	public BusinessProcessService getBusinessProcessService()
	{
		return businessProcessService;
	}

	/**
	 * @param businessProcessService
	 *                                  the businessProcessService to set
	 */
	public void setBusinessProcessService(final BusinessProcessService businessProcessService)
	{
		this.businessProcessService = businessProcessService;
	}

	/**
	 * @return the modelService
	 */
	public ModelService getModelService()
	{
		return modelService;
	}

	/**
	 * @param modelService
	 *                        the modelService to set
	 */
	public void setModelService(final ModelService modelService)
	{
		this.modelService = modelService;
	}


	@Override
	protected void onSiteEvent(final TrainingEmailEvent event)
	{
		final TrainingEmailProcessModel trainingEmailProcessModel = (TrainingEmailProcessModel) getBusinessProcessService()
				.createProcess("trainingEmail-" + event.getCart().getCode() + "-" + processCodeGenerator.generate().toString(),
						"trainingEmailProcess");
		trainingEmailProcessModel.setCart(event.getCart());
		trainingEmailProcessModel.setCurrency(event.getCurrency());
		trainingEmailProcessModel.setLanguage(event.getLanguage());
		trainingEmailProcessModel.setStore(event.getBaseStore());
		trainingEmailProcessModel.setSite(event.getSite());
		getModelService().save(trainingEmailProcessModel);
		getBusinessProcessService().startProcess(trainingEmailProcessModel);
	}





	@Override
	protected SiteChannel getSiteChannelForEvent(final TrainingEmailEvent event)
	{
		// XXX Auto-generated method stub
		return null;
	}


}
