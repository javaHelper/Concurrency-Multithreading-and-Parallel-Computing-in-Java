package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {

    PriceValidatorService priceValidatorService = new PriceValidatorService();

    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void parallelism() {
        // parallelism =  no of cores -1
        System.out.println("parallelism : " +ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    void no_of_cores() {
        System.out.println("no of cores : " +Runtime.getRuntime().availableProcessors());
    }

    @Test
    void checkout_6_items() {
        Cart cart = DataSet.createCart(6);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.SUCCESS, checkoutResponse.getCheckoutStatus());
        assertTrue(checkoutResponse.getFinalRate()>0);
    }

    @Test
    void checkout_12_items() {
        //-Djava.util.concurrent.ForkJoinPool.common.parallelism=100
        //  System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

        Cart cart = DataSet.createCart(12);
        System.out.println("size of the cart  : "+ cart.getCartItemList().size());

        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);
        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_modify_parallelism() {
        // -Djava.util.concurrent.ForkJoinPool.common.parallelism=100
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");

        Cart cart = DataSet.createCart(105);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }

    @Test
    void checkout_25_items() {
        Cart cart = DataSet.createCart(25);
        CheckoutResponse checkoutResponse = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkoutResponse.getCheckoutStatus());
    }
}