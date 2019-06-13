package org.csu.mypetstore.contorller;

import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.SearchService;
import org.csu.mypetstore.service.impl.SearchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/catalog/search")
    public String search(@RequestParam("keyword") String keyword, Model model){
        //获取传值，搜索商品名称的关键字
        System.out.println(keyword);
        //查询
        //String search = "";
        try {
            List<Product> productList = searchService.searchMessage(keyword);
            model.addAttribute("productList",productList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //search = "123#123#12";
        //out.print(search);

        //if(productList==null) System.out.println("error");
        //else System.out.println(p);

        return "catalog/c_SearchProducts";
    }


    public static void main(String[] args) {
        SearchService searchService=new SearchServiceImpl();
        List<Product> productList = searchService.searchMessage("a");
        for (int i=0;i<productList.size();i++){
            System.out.println(productList.get(i).getName());
        }
    }
}
