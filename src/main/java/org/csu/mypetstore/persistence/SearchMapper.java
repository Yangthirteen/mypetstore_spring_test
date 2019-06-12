package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Product;

import java.util.List;

public interface SearchMapper {
    public List<Product> searchMessage(String keyword);
}
