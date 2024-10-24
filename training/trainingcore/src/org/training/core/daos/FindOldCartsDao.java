package org.training.core.daos;

import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.servicelayer.search.SearchResult;

public interface FindOldCartsDao {
    public SearchResult<CartModel> findOldCarts(int hours);
}
