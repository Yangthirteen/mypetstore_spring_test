package org.csu.mypetstore.contorller;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/catalog/Main")
    public String viewMain(){
        return "catalog/Main";
    }

    @GetMapping("/catalog/viewCategory")
    public String viewCategory(@RequestParam("categoryId") String categoryId, Model model){
        if (categoryId!=null){
            Category category=catalogService.getCategory(categoryId);
            List<Product> productList=catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);
        }
        return "catalog/Category";
    }


    @GetMapping("/catalog/viewProduct")
    public String viewProduct(@RequestParam("productId") String productId, Model model){
        if (productId!=null){
            Product product=catalogService.getProduct(productId);
            List<Item> itemList=catalogService.getItemListByProduct(productId);
            model.addAttribute("product",product);
            model.addAttribute("itemList",itemList);
        }
        return "catalog/Product";
    }

    @GetMapping("/catalog/viewItem")
    public String viewItem(@RequestParam("itemId") String itemId, Model model){
        if (itemId!=null){
            Item item=catalogService.getItem(itemId);
            model.addAttribute("item",item);
        }
        return "catalog/Item";
    }

    @GetMapping("/catalog/searchProduct")
    public String searchProduct(@RequestParam("keyword")String keyword,Model model){
        List<Product> productList=catalogService.searchProductList(keyword);

        model.addAttribute("productList",productList);

        return "catalog/SearchProducts";
    }
}
