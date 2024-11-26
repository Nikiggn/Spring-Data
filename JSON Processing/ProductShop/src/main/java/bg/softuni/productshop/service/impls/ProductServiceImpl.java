package bg.softuni.productshop.service.impls;

import bg.softuni.productshop.data.entities.User;
import bg.softuni.productshop.data.repositories.ProductRepository;

import bg.softuni.productshop.data.repositories.UserRepository;
import bg.softuni.productshop.service.ProductService;
import bg.softuni.productshop.service.dtos.ProductSeedJsonDto;
import bg.softuni.productshop.utils.ValidatorUtilmpl;
import com.google.gson.Gson;
import jakarta.validation.ConstraintViolation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PRODUCT_PATH = "src/main/resources/files/products.json";

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final ValidatorUtilmpl validatorUtl;
    private final Gson gson;

    public ProductServiceImpl(ProductRepository productRepository, UserRepository userRepository, ModelMapper modelMapper, ValidatorUtilmpl validatorUtl, Gson gson) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.validatorUtl = validatorUtl;
        this.gson = gson;
    }

    @Override
    public void seedProducts() throws IOException {
        //
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

            User seller = getSeller();
            User buyer = getBuyer(seller);




        }
    }

    private User getBuyer(User seller) {
        // Randomly for the buyer
        User buyer = null;

        if (ThreadLocalRandom.current().nextBoolean()) { // 50% chance of assigning a buyer
            long buyerId = ThreadLocalRandom.current().nextInt(1, (int) this.userRepository.count() + 1);
            buyer = this.userRepository.findById(buyerId)
                    .orElseThrow(() -> new IllegalStateException("No such user"));

            // Ensure buyer and seller are not the same person
            while (buyer.equals(seller)) {
                buyerId = ThreadLocalRandom.current().nextInt(1, (int) this.userRepository.count() + 1);
                buyer = this.userRepository.findById(buyerId)
                        .orElseThrow(() -> new IllegalStateException("No such user"));
            }
        }

        return buyer;
    }

    private User getSeller() {
        long sellerId = ThreadLocalRandom.current().nextInt(1, (int) this.userRepository.count() + 1);

        Optional<User> optionalUser = this.userRepository.findById(sellerId);
        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("No such user");
        }
        return optionalUser.get();
    }

    @Override
    public void areProductsImported() {

    }
}
