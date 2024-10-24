package org.training.core.event;

import de.hybris.platform.acceleratorservices.site.AbstractAcceleratorSiteEventListener;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.commerceservices.enums.SiteChannel;
import de.hybris.platform.core.model.order.OrderModel;
import de.hybris.platform.order.events.SubmitOrderEvent;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.util.ServicesUtil;
import org.training.core.model.TrainingEmailProcessModel;
import de.hybris.platform.core.model.order.AbstractOrderModel;

public class TrainingEmailEventListener extends AbstractAcceleratorSiteEventListener<TrainingEmailEvent>{

    private ModelService modelService;
    private BusinessProcessService businessProcessService;


    protected BusinessProcessService getBusinessProcessService() {return businessProcessService;}

    public void setBusinessProcessService(final BusinessProcessService businessProcessService){
        this.businessProcessService = businessProcessService;
    }

    protected ModelService getModelService() {return modelService;}

    public void setModelService(final ModelService modelService){
        this.modelService = modelService;
    }



    protected void onSiteEvent(final TrainingEmailEvent event){
        final TrainingEmailProcessModel trainingEmailProcessModel = getBusinessProcessService().createProcess("trainingEmail-" + event.getCart().getCode() + "-" + System.currentTimeMillis() ,"trainingEmailProcess");
        trainingEmailProcessModel.setCart(event.getCart());
        trainingEmailProcessModel.setCurrency(event.getCurrency());
        trainingEmailProcessModel.setStore(event.getBaseStore());
        trainingEmailProcessModel.setSite(event.getSite());
        trainingEmailProcessModel.setLanguage(event.getLanguage());

        getModelService().save(trainingEmailProcessModel);
        getBusinessProcessService().startProcess(trainingEmailProcessModel);


    }

    protected SiteChannel getSiteChannelForEvent(final TrainingEmailEvent event)
    {
//        final BaseSiteModel site = order.getSite();
//        ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
//        return site.getChannel();

        final BaseSiteModel site = event.getSite();
        ServicesUtil.validateParameterNotNullStandardMessage("event.order.site", site);
        return site.getChannel();
    }




}
