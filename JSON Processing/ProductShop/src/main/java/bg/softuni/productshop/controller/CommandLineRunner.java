package bg.softuni.productshop.controller;

import bg.softuni.productshop.service.CategoryService;
import bg.softuni.productshop.service.ProductService;
import bg.softuni.productshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {
    private final UserService userService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Autowired
    public CommandLineRunner(UserService userService, ProductService productService, CategoryService categoryService) {
        this.userService = userService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        //seedData();

        // this.productService.exportProductsInRange();
        this.productService.exportSuccessfullySoldProducts();
    }

    private void seedData() throws IOException {
        if (!this.userService.areUsersImported()){
            this.userService.seedUsers();
        }

        if (!this.categoryService.areCategoriesImported()){
            this.categoryService.seedCategories();
        }

        if (!this.productService.areProductsImported()){
            this.productService.seedProducts();
        }
    }
}
