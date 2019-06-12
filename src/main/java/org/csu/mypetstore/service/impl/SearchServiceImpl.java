package org.csu.mypetstore.service.impl;


import org.csu.mypetstore.persistence.SearchMapper;
import org.csu.mypetstore.service.SearchService;
import org.springframework.stereotype.Service;

@Service
public class SearchServiceImpl implements SearchService {

    private SearchMapper searchMapper;

    @Override
    public String searchMessage(String keyword) {
        return searchMapper.searchMessage("%"+keyword+"%");
    }
}
