package org.training.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import org.training.core.model.TrainingEmailProcessModel;

public class TrainingEmailContext extends AbstractEmailContext<TrainingEmailProcessModel> {

    public void init(final TrainingEmailProcessModel processModel, final EmailPageModel emailPageModel){
        super.init(processModel, emailPageModel);
        put(EMAIL, getCustomerEmailResolutionService().getEmailForCustomer(getCustomer(processModel)));
        put(DISPLAY_NAME, getCustomer(processModel).getDisplayName());
    }

    @Override
    protected BaseSiteModel getSite(TrainingEmailProcessModel businessProcessModel) {
        return businessProcessModel.getCart().getSite();
    }

    @Override
    protected CustomerModel getCustomer(TrainingEmailProcessModel businessProcessModel) {
        return businessProcessModel.getCustomer();
    }

    @Override
    protected LanguageModel getEmailLanguage(TrainingEmailProcessModel businessProcessModel) {
        return businessProcessModel.getCart().getUser().getSessionLanguage();
    }
}
