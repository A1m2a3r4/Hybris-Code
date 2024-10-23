package org.training.core.job;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;

import org.training.core.daos.FindOldCartsDao;
import org.training.core.jalo.TrainingOldCartRemovalJob;
import org.training.core.model.TrainingOldCartRemovalJobModel;

import org.apache.log4j.Logger;

public class TrainingCartRemovalJob extends AbstractJobPerformable<TrainingOldCartRemovalJobModel> {

    @Autowired
    FindOldCartsDao findOldCartsDao;

    private static final Logger LOG = Logger.getLogger(TrainingCartRemovalJob.class);

    //final SearchResult<CartModel> searchResult =

    @Override
    public PerformResult perform(TrainingOldCartRemovalJobModel trainingOldCartRemovalJobModel) {
        final SearchResult<CartModel> searchResult = findOldCartsDao.findOldCarts(trainingOldCartRemovalJobModel.getHours());
        LOG.info("found" + searchResult.getCount() + " carts in the last " + trainingOldCartRemovalJobModel.getHours() + " hours.");
        searchResult.getResult().stream().forEach(this::removeOldCarts);
        LOG.info("Removed " + searchResult.getCount()+" carts");
        return new PerformResult(CronJobResult.SUCCESS , CronJobStatus.FINISHED);

    }

    private void removeOldCarts(CartModel cart) {
        LOG.info("Removing carts");
        modelService.remove(cart);
    }
}
