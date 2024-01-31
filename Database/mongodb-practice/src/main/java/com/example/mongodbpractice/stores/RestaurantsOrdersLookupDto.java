package com.example.mongodbpractice.stores;

import java.util.List;
import org.springframework.data.mongodb.core.mapping.Unwrapped;

public record RestaurantsOrdersLookupDto(
    @Unwrapped.Nullable
    Restaurants restaurants,
    List<Orders> orders
) {}
