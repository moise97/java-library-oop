package com.example.inventory.service;

import com.example.inventory.exception.ResourceNotFoundException;
import com.example.inventory.model.Product;
import com.example.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void whenAddProduct_thenRepositorySaveIsCalledExactlyOnce() {
        Product product = new Product("Widget", new BigDecimal("9.99"), 50);
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
        assertThat(result).isEqualTo(product);
    }

    @Test
    void whenDeleteProductWithUnknownId_thenThrowsResourceNotFoundException() {
        Long unknownId = 99L;
        when(productRepository.existsById(unknownId)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(unknownId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Product not found with id: " + unknownId);

        verify(productRepository, never()).deleteById(any());
    }
}
