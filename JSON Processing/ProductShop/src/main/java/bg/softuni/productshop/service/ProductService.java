package bg.softuni.productshop.service;

import java.io.IOException;

public interface ProductService {
    void seedProducts() throws IOException;
    boolean areProductsImported();

    void exportProductsInRange() throws IOException;

    void exportSuccessfullySoldProducts();
}
