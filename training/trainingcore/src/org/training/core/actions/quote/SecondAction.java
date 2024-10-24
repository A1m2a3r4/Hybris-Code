package org.training.core.actions.quote;

import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import org.training.core.model.MyTrainingProcessModel;

public class SecondAction extends AbstractSimpleDecisionAction<MyTrainingProcessModel>{

    @Override
    public Transition executeAction(final MyTrainingProcessModel myTrainingProcessModel)  {

        if ( null != myTrainingProcessModel.getAttribute2() && null != myTrainingProcessModel.getAttribute3() &&
                myTrainingProcessModel.getAttribute2().equalsIgnoreCase(myTrainingProcessModel.getAttribute3())){
            return Transition.OK;
        }else{
            return Transition.NOK;
        }

    }
}
