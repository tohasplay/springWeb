package com.demo.interactor;

import com.demo.dto.Order;
import com.demo.dto.builder.AutoPricedBuilder;
import com.demo.dto.builder.OrderBuilder;
import com.demo.dto.builder.OrderDirector;
import com.demo.exception.NotFoundException;
import com.demo.service.data.OrderDataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//DONE: reduce this

/**
 * @deprecated
 * reduced, integrated to other components
 */
@Deprecated
@Component
public class CustomerManager {

    private final OrderDataBaseAccess orderDataBaseAccess;

    @Autowired
    private CustomerManager(OrderDataBaseAccess orderDataBaseAccess) {
        this.orderDataBaseAccess = orderDataBaseAccess;
    }

    public Order makeOrder(long id, String text, String password) {
        if (!orderDataBaseAccess.verifyUser(id, password))
            throw new NotFoundException();
        OrderBuilder orderBuilder = new AutoPricedBuilder(text);
        OrderDirector orderDirector = new OrderDirector();
        orderDirector.setOrderBuilder(orderBuilder);
        orderDirector.constructOrder();
        orderDataBaseAccess.put(orderDirector.getOrder(), id);

        return orderDataBaseAccess.getLastOrder();
    }

//    public Node<Float, Integer> calculateTotal(long id) {
//        Node<Float, Integer> result = new Node<>(0f, 0);
//        for (Order o :
//                orderDataBaseAccess.getOrdersByCustomer(id)) {
//            result.first += o.getPrice();
//            result.second++;
//        }
//        return result;
//    }
}
