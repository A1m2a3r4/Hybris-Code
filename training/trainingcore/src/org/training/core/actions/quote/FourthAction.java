package org.training.core.actions.quote;

import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import org.training.core.model.MyTrainingProcessModel;

public class FourthAction extends AbstractSimpleDecisionAction<MyTrainingProcessModel>{

    @Override
    public Transition executeAction(final MyTrainingProcessModel myTrainingProcessModel)  {

        if ( null != myTrainingProcessModel.getAttribute3() && null != myTrainingProcessModel.getAttribute2() &&
                myTrainingProcessModel.getAttribute3().equalsIgnoreCase(myTrainingProcessModel.getAttribute2())){
            return Transition.OK;
        }else{
            return Transition.NOK;
        }

    }
}
