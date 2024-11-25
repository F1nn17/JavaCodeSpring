package com.shiraku.javacodespring.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shiraku.javacodespring.model.Customer;
import com.shiraku.javacodespring.model.Orders;
import com.shiraku.javacodespring.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OrdersControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateOrder() throws Exception {
        Orders order = new Orders();
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("john.doe@example.com");
        customer.setContactNumber("+1234567890");

        List<Product> products = new ArrayList<Product>();
        Product product1 = new Product();
        product1.setProductId(1L);
        product1.setDescription("This is a product");
        product1.setPrice(59.99);
        product1.setQuantityInStock(1);
        products.add(product1);

        order.setOrderId(1L);
        order.setProducts(products);
        order.setCustomer(customer);
        order.setOrderDate("01.01.0001");
        order.setShippingAddress("1234 Elm Street");
        order.setTotalPrice(59.99);
        order.setOrderStatus("Pending");

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.totalPrice").value(59.99))
                .andExpect(jsonPath("$.orderStatus").value("Pending"));
    }

    @Test
    public void testGetOrderById() throws Exception {
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").exists());
    }


}
