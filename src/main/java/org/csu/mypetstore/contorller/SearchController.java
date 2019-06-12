package org.csu.mypetstore.contorller;

import org.csu.mypetstore.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/catalog/search")
    public void search(@RequestParam("keyword") String keyword, Model model){
        //获取传值，搜索商品名称的关键字
        System.out.println(keyword);
        //查询
        String search = "";
        try {
            search = searchService.searchMessage(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //search = "123#123#12";
        //out.print(search);
        model.addAttribute("search",search);
        if(search==null) System.out.println("error");
        else System.out.println(search);
    }

}
