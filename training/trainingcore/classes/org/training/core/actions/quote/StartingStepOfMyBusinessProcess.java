package org.training.core.actions.quote;

import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import org.training.core.model.MyTrainingProcessModel;

public class StartingStepOfMyBusinessProcess extends AbstractSimpleDecisionAction<MyTrainingProcessModel>{

    @Override
    public Transition executeAction(final MyTrainingProcessModel myTrainingProcessModel)  {

        if ( null != myTrainingProcessModel.getAttribute1() && null != myTrainingProcessModel.getAttribute2() &&
                myTrainingProcessModel.getAttribute1().equalsIgnoreCase(myTrainingProcessModel.getAttribute2())){
            return Transition.OK;
        }else{
            return Transition.NOK;
        }

    }
}
