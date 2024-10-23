package org.training.core.job;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.training.core.daos.ProductListResultDao;

public class ProductListJobPerformable extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = Logger.getLogger(ProductListJobPerformable.class);

    @Autowired
    ProductListResultDao productListResultDao;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        final SearchResult<ProductModel> searchResult = productListResultDao.findProductByFirstLetter();
        LOG.info("**** Printing products starting with 1");

        searchResult.getResult().stream().forEach(this:: printproductCodes);
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    protected  void printproductCodes(final ProductModel productmodel){
        LOG.info(productmodel.getCode() + " " + productmodel.getName());
    }
}
