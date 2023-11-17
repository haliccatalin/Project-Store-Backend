package com.sda.app.dto;

import com.sda.app.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private Integer id;
    private Integer userId;
    private List<Item> items;
}
