package com.example.demo.services;

import com.example.demo.models.Cart;
import com.example.demo.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public BigInteger getSum() {
        BigInteger sum = new BigInteger("0");
        for (Cart cart : cartRepository.findAll()) {
            sum = sum.add(cart.getSum());
        }
        return sum;
    }
}
