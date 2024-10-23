package org.training.core.daos;

import de.hybris.platform.commerceservices.order.dao.impl.DefaultCartEntryDao;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.mockito.internal.matchers.Find;
import org.springframework.beans.factory.annotation.Autowired;

public class FindOldCartDaoImpl implements FindOldCartsDao {

    private static final String HOUR= "hour";
    private static final String FILTER_TO_CREATION_TIME = "{cart.creationTime } > SYSDATE - INTERVAL '";
    private static final String CART_QUERY = "SELECT {cart.pk} from {Cart as cart}";
    private static final String WHERE_CLAUSE = "where";

    @Autowired
    FlexibleSearchService flexibleSearchService;

    // SELECT {cart.pk} from {Cart as cart} where {cart.creationTime } > SYSDATE - INTERVAL '1 ' HOUR

    @Override
    public SearchResult<CartModel> findOldCarts(int hours) {

        final StringBuilder stringBuilder = new StringBuilder(CART_QUERY);
        stringBuilder.append(WHERE_CLAUSE);
        stringBuilder.append(FILTER_TO_CREATION_TIME);
        stringBuilder.append(hours);
        stringBuilder.append("'HOUR ");

        final FlexibleSearchQuery query = new FlexibleSearchQuery(stringBuilder);
        return flexibleSearchService.search(query);
    }

}
