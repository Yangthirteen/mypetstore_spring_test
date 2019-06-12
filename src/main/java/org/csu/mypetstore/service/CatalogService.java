package org.csu.mypetstore.service;

import java.util.List;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public interface CatalogService {

    public List<Category> getCategoryList();

    public Category getCategory(String categoryId);

    public Product getProduct(String productId);

    public List<Product> getProductListByCategory(String categoryId);

    // TODO enable using more than one keyword
    public List<Product> searchProductList(String keyword);

    public List<Item> getItemListByProduct(String productId);

    public Item getItem(String itemId);

    public boolean isItemInStock(String itemId);
}