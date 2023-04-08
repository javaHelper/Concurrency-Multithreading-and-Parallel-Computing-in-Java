package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceUsingCompletableFutureTest {
    private ProductInfoService pis = new ProductInfoService();
    private ReviewService rs = new ReviewService();
    private InventoryService is = new InventoryService();
    private ProductServiceUsingCompletableFuture pscf = new ProductServiceUsingCompletableFuture(pis, rs, is);

    @Test
    void retrieveProductDetails() {
        String productId = "ABC123";
        startTimer();

        Product product = pscf.retrieveProductDetails(productId);
        System.out.println("product:  " + product);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_CF() {
        String productId = "ABC123";
        startTimer();

        CompletableFuture<Product> cfProduct = pscf.retrieveProductDetails_CF(productId);
        cfProduct
                .thenAccept((product -> {
                    assertNotNull(product);
                    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
                    assertNotNull(product.getReview());
                }))
                .join();
        timeTaken();
    }

    @Test
    void retrieveProductDetailsWithInventory() {
        String productId = "ABC123";
        startTimer();

        Product product = pscf.retrieveProductDetailsWithInventory(productId);
        System.out.println("product:  " + product);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetailsWithInventory_approach2() {
        String productId = "ABC123";
        startTimer();

        Product product = pscf.retrieveProductDetailsWithInventory_approach2(productId);
        System.out.println("product:  " + product);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(productOption -> {
            assertNotNull(productOption.getInventory());
        });
        assertNotNull(product.getReview());
    }
}