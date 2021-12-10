package ru.step.store.services;

import ru.step.store.models.OrderElement;
import ru.step.store.repositories.OrderElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class OrderElementService {

    @Autowired
    private OrderElementRepository orderElementRepository;

    public BigInteger getSum() {
        BigInteger sum = new BigInteger("0");
        for (OrderElement orderElement : orderElementRepository.findAll()) {
            sum = sum.add(orderElement.getSum());
        }
        return sum;
    }
}
