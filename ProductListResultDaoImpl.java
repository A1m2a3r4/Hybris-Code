package org.training.core.daos;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.daos.impl.DefaultProductDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.SearchResult;

public class ProductListResultDaoImpl extends DefaultProductDao implements ProductListResultDao{
    public ProductListResultDaoImpl(String typecode) {
        super(typecode);
    }

    //Select {p:pk} from {product as p} where {p.code} like '1%'

    @Override
    public SearchResult<ProductModel> findProductByFirstLetter() {
        final StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Select {p:").append(ProductModel.PK).append("} ");
        stringBuilder.append("from {").append(ProductModel._TYPECODE).append(" as p ").append("} ");
        stringBuilder.append("where ").append("{p.code} like '1%'");

        final FlexibleSearchQuery query = new FlexibleSearchQuery(stringBuilder.toString());
        return  getFlexibleSearchService().search(query);
    }
}
