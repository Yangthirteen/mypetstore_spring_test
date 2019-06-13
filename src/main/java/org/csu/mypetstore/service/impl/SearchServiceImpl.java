package org.csu.mypetstore.service.impl;


import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.SearchMapper;
import org.csu.mypetstore.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public List<Product> searchMessage(String keyword) {
        return searchMapper.searchMessage("%"+keyword+"%");
    }


    public static void main(String[] args) {
        SearchService searchService=new SearchServiceImpl();
        List<Product> productList = searchService.searchMessage("a");
        for (int i=0;i<productList.size();i++){
            System.out.println(productList.get(i).getName());
        }
    }

}
