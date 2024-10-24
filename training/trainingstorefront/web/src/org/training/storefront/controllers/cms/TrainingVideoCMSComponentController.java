package org.training.storefront.controllers.cms;

import de.hybris.platform.acceleratorstorefrontcommons.controllers.cms.AbstractCMSComponentController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.training.core.jalo.TrainingVideoComponent;
import org.training.core.model.components.TrainingVideoComponentModel;
import org.training.storefront.controllers.ControllerConstants;

import javax.servlet.http.HttpServletRequest;


@Controller("TrainingVideoCMSComponentController")
@RequestMapping(value = ControllerConstants.Actions.Cms.TrainingVideoCmsCompoent)
public class TrainingVideoCMSComponentController extends AbstractCMSComponentController<TrainingVideoComponentModel> {
    @Override
    protected void fillModel(HttpServletRequest request, Model model, TrainingVideoComponentModel component) {
        model.addAttribute( "autoPlay", component.getAutoPlay() ? 1:0 );
        model.addAttribute("showControls", component.getShowControls() ? 1:0 );
        model.addAttribute("height", component.getHeight() );
        model.addAttribute("videoId" , component.getVideoId());
        model.addAttribute("width", component.getWidth());
    }

    @Override
    protected String getView(TrainingVideoComponentModel component) {
        return null;
    }
}
