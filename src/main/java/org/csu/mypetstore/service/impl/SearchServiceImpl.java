package org.csu.mypetstore.service.impl;


import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.SearchMapper;
import org.csu.mypetstore.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchMapper searchMapper;

    @Override
    public List<Product> searchMessage(String keyword) {
        return searchMapper.searchMessage(keyword);
    }
}
