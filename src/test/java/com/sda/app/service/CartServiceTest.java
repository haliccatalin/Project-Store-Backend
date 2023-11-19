package com.sda.app.service;

import com.sda.app.entity.Cart;
import com.sda.app.entity.Item;
import com.sda.app.entity.User;
import com.sda.app.repository.CartRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllCarts() {
        // Mock data
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

        // Define the behavior of the repository mock
        when(cartRepository.findAll()).thenReturn(cartList);

        // Call the service method
        List<Cart> result = cartService.findAll();

        // Verify the result
        assertEquals(1, result.size());
        assertEquals("user", result.get(0).getUser().getUsername());
        assertEquals("coffee", result.get(0).getItems().get(0).getTitle());
    }

    @Test
    public void testGetOrderById() {
        // Mock data
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


        // Define the behavior of the repository mock
        when(cartRepository.findById(1)).thenReturn(Optional.of(cart));

        // Call the service method
        Optional<Cart> result = cartService.findById(1);

        // Verify the result
        assertTrue(result.isPresent());
        assertEquals("coffee", result.get().getItems().get(0).getTitle());
    }

    @Test
    public void testSaveOrder() {
        // Mock data
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

        // Call the service method
        cartService.createCart(cart);

        // Verify that the repository's save method was called
        verify(cartRepository, times(1)).save(cart);
    }

    @Test
    public void testDeleteOrder() {
        // Call the service method
        cartRepository.deleteById(1);

        // Verify that the repository's deleteById method was called
        verify(cartRepository, times(1)).deleteById(1);
    }
}
