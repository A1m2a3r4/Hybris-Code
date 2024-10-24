package org.training.core.actions.quote;

import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import org.training.core.model.MyTrainingProcessModel;

public class ThirdAction extends AbstractSimpleDecisionAction<MyTrainingProcessModel>{

    @Override
    public Transition executeAction(final MyTrainingProcessModel myTrainingProcessModel)  {

        if ( null != myTrainingProcessModel.getAttribute1() && null != myTrainingProcessModel.getAttribute3() &&
                myTrainingProcessModel.getAttribute1().equalsIgnoreCase(myTrainingProcessModel.getAttribute3())){
            return Transition.OK;
        }else{
            return Transition.NOK;
        }

    }
}
