package org.csu.mypetstore.service.impl;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.CategoryMapper;
import org.csu.mypetstore.persistence.ItemMapper;
import org.csu.mypetstore.persistence.ProductMapper;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Category> getCategoryList() {
        return categoryMapper.getCategoryList();
    }

    @Override
    public Category getCategory(String categoryId) {
        return categoryMapper.getCategory(categoryId);
    }

    @Override
    public Product getProduct(String productId) {
        return productMapper.getProduct(productId);
    }

    @Override
    public List<Product> getProductListByCategory(String categoryId) {
        return productMapper.getProductListByCategory(categoryId);
    }

    @Override
    public List<Product> searchProductList(String keyword) {
        return productMapper.searchProductList(keyword);
    }

    @Override
    public List<Item> getItemListByProduct(String productId) {
        return itemMapper.getItemListByProduct(productId);
    }

    @Override
    public Item getItem(String itemId) {
        return itemMapper.getItem(itemId);
    }

    @Override
    public boolean isItemInStock(String itemId) {
        return false;
    }
}
