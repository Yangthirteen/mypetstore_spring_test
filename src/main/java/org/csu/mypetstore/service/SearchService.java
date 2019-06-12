package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Product;

import java.util.List;

public interface SearchService {

    public List<Product> searchMessage(String keyword);
}
