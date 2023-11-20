package com.sda.app.controller;

import com.sda.app.entity.Cart;
import com.sda.app.entity.Item;
import com.sda.app.entity.User;
import com.sda.app.service.CartService;
import com.sda.app.service.ItemService;
import com.sda.app.service.UserService;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @MockBean
    private ItemService itemService;

    @Test
    public void testGetAllCart() throws Exception {
        User user = new User();
        user.setUsername("user");

        Item item = new Item();
        item.setTitle("coffee");

        List<Item> items = new ArrayList<>();
        items.add(item);

        Cart cart = new Cart();
        cart.setId(1);
        cart.setUser(user);
        cart.setItems(items);

        List<Cart> cartList = Arrays.asList(
                cart
        );


        Mockito.when(cartService.findAll()).thenReturn(cartList);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", IsCollectionWithSize.hasSize(1)))
                .andExpect(jsonPath("$.data.[0].id").value(1));

    }


}
