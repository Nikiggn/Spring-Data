package bg.softuni.productshop.service.impls;

import bg.softuni.productshop.data.entities.Category;
import bg.softuni.productshop.data.entities.Product;
import bg.softuni.productshop.data.entities.User;
import bg.softuni.productshop.data.repositories.CategoryRepository;
import bg.softuni.productshop.data.repositories.ProductRepository;

import bg.softuni.productshop.data.repositories.UserRepository;
import bg.softuni.productshop.service.ProductService;
import bg.softuni.productshop.service.dtos.ProductSeedJsonDto;
import bg.softuni.productshop.service.dtos.export.ProductExportJsonDto;
import bg.softuni.productshop.utils.ValidatorUtilmpl;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_PATH = "src/main/resources/json/products.json";

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;
    private final ValidatorUtilmpl validatorUtl;
    private final Gson gson;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, ValidatorUtilmpl validatorUtl, Gson gson) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.validatorUtl = validatorUtl;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        String jsonString = String.join("", Files.readAllLines(Path.of(PRODUCT_PATH)));
        ProductSeedJsonDto[] seedJsonDtos = gson.fromJson(jsonString, ProductSeedJsonDto[].class);

        for (ProductSeedJsonDto seedJsonDto : seedJsonDtos) {
            if (!this.validatorUtl.isValid(seedJsonDto)) {
                System.out.println(this.validatorUtl.validate(seedJsonDtos)
                        .stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining()));

                continue;
            }

            User seller = getRandomSeller();
            User buyer = getRandomBuyer(seller);

            Product product = this.modelMapper.map(seedJsonDto, Product.class);
            product.setSeller(seller);
            product.setBuyer(buyer);
            product.setCategories(getRandomCategories());

            this.productRepository.saveAndFlush(product);
        }
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int count = ThreadLocalRandom.current().nextInt(1, 5);

        for (int i = 0; i < count; i++) {
            long randomCategoryId = ThreadLocalRandom.current().nextInt(1, (int) this.categoryRepository.count() + 1);
            categories.add(this.categoryRepository.findById(randomCategoryId).get());
        }

        return categories;
    }

    private User getRandomBuyer(User seller) {
        // Randomly for the buyer
        if (ThreadLocalRandom.current().nextBoolean()) { // 50% chance of assigning a buyer
            long buyerId = ThreadLocalRandom.current().nextInt(1, (int) this.userRepository.count() + 1);
            User buyer = this.userRepository.findById(buyerId)
                    .orElseThrow(() -> new IllegalStateException("No such user"));

            // Ensure buyer and seller are not the same person
            while (buyer.equals(seller)) {
                buyerId = ThreadLocalRandom.current().nextInt(1, (int) this.userRepository.count() + 1);
                buyer = this.userRepository.findById(buyerId)
                        .orElseThrow(() -> new IllegalStateException("No such user"));
            }

            return buyer;
        }

        return null;
    }

    private User getRandomSeller() {
        long sellerId = ThreadLocalRandom.current().nextInt(1, (int) this.userRepository.count() + 1);

        Optional<User> optionalUser = this.userRepository.findById(sellerId);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("No such user");
        }
        return optionalUser.get();
    }

    @Override
    public boolean areProductsImported() {
        return this.productRepository.count() > 0;
    }

    @Override
    public void exportProductsInRange() throws IOException {
        Set<Product> products = this.productRepository.findAllByPriceBetweenAndBuyerIsNull(
                BigDecimal.valueOf(500),
                BigDecimal.valueOf(1000));

        List<ProductExportJsonDto> dtos = products
                .stream()
                .filter(product -> product.getSeller().getFirstName() != null)
                .map(p -> {
                    ProductExportJsonDto mappedProduct = this.modelMapper.map(p, ProductExportJsonDto.class);
                    mappedProduct.setSeller(p.getSeller().getFirstName() + " " + p.getSeller().getLastName());
                    return mappedProduct;
                })
                .sorted(Comparator.comparing(ProductExportJsonDto::getPrice)) // Sort by price (low to high)
                .collect(Collectors.toList());

        String json = this.gson.toJson(dtos);
        FileWriter fileWriter = new FileWriter("src/main/resources/json/exports/products-in-range.json", false);

        fileWriter.write(json);
        fileWriter.close();
    }

    @Override
    public void exportSuccessfullySoldProducts() {

    }
}
