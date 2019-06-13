package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Product;

import java.util.List;

public interface SearchMapper {
    List<Product> searchMessage(String keyword);
}
