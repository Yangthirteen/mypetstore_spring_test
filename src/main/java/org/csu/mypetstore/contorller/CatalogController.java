package org.csu.mypetstore.contorller;

import org.csu.mypetstore.domain.Account;
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
    private Account account=new Account();

    @Autowired
    private org.csu.mypetstore.domain.test test;

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/catalog/Main")
    public String viewMain(Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        return "catalog/c_Main";
    }

    @GetMapping("/catalog/viewCategory")
    public String viewCategory(@RequestParam("categoryId") String categoryId, Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        if (categoryId!=null){
            Category category=catalogService.getCategory(categoryId);
            List<Product> productList=catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);
        }
        return "catalog/c_Category";
    }


    @GetMapping("/catalog/viewProduct")
    public String viewProduct(@RequestParam("productId") String productId, Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        if (productId!=null){
            Product product=catalogService.getProduct(productId);
            List<Item> itemList=catalogService.getItemListByProduct(productId);

            model.addAttribute("product",product);
            model.addAttribute("itemList",itemList);
        }
        return "catalog/c_Product";
    }

    @GetMapping("/catalog/viewItem")
    public String viewItem(@RequestParam("itemId") String itemId, Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        if (itemId!=null){
            Item item=catalogService.getItem(itemId);
            Product product=catalogService.getProduct(item.getProduct().getProductId());
            model.addAttribute("item",item);
            model.addAttribute("product",product);
        }
        return "catalog/c_Item";
    }

    @GetMapping("/catalog/searchProduct")
    public String searchProduct(@RequestParam("keyword")String keyword,Model model){
        if (account==null)
            test.setAuthenticated(false);
        else test.setAuthenticated(true);
        model.addAttribute("authenticated",test.getAuthenticated());
        model.addAttribute("account",account);
        List<Product> productList=catalogService.searchProductList(keyword);

        model.addAttribute("productList",productList);

        return "catalog/c_SearchProducts";
    }
}
