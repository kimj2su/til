package com.example.mongodbpractice.stores;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.mongodbpractice.utils.AcceptanceTest;
import java.util.List;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Disabled
class RestaurantsServiceTest extends AcceptanceTest {

  @Autowired
  private RestaurantsService restaurantsService;

  @Autowired
  private OrderService orderService;

  @BeforeEach
  void before() {
    restaurantsService.createRestaurant(createRestaurant(1, "American Steak House", List.of("filet", "sirloin"), List.of("beer", "wine")));
    restaurantsService.createRestaurant(createRestaurant(2, "Honest John Pizza", List.of("cheese pizza", "pepperoni pizza"), List.of("soda")));
    orderService.createOrder(createOrders(1, "filet", "American Steak House", null));
    orderService.createOrder(createOrders(2, "cheese pizza", "Honest John Pizza", "lemonade"));
    orderService.createOrder(createOrders(3, "cheese pizza", "Honest John Pizza", "soda"));
  }


  @DisplayName("basicLookup")
  @Test
  void lookup() {
    // given : 선행조건 기술

    // when : 기능 수행
    List<RestaurantsOrdersLookupDto> restaurantsOrders = restaurantsService.aggregateRestaurantsOrdersLookup();

    // then : 결과 확인
    assertThat(restaurantsOrders.stream().map(RestaurantsOrdersLookupDto::restaurants))
        .hasSize(2)
        .extracting("id", "name", "food", "beverages")
        .contains(
            Tuple.tuple(1, "American Steak House", List.of("filet", "sirloin"), List.of("beer", "wine")),
            Tuple.tuple(2, "Honest John Pizza", List.of("cheese pizza", "pepperoni pizza"), List.of("soda"))
        );

    assertThat(restaurantsOrders.stream().flatMap(x -> x.orders().stream()))
        .hasSize(3)
        .extracting("id", "item", "restaurant_name", "drink")
        .contains(
            Tuple.tuple(1, "filet", "American Steak House", null),
            Tuple.tuple(2, "cheese pizza", "Honest John Pizza", "lemonade"),
            Tuple.tuple(3, "cheese pizza", "Honest John Pizza", "soda")
        );
  }

  @DisplayName("unwindLookup")
  @Test
  void unwindLookup() {
    // given : 선행조건 기술

    // when : 기능 수행
    List<RestaurantsOrdersLookupDto> restaurantsOrders = restaurantsService.aggregateRestaurantsOrdersUnwindLookup();

    // then : 결과 확인
    assertThat(restaurantsOrders.stream().map(RestaurantsOrdersLookupDto::restaurants))
        .hasSize(1)
        .extracting("id", "name", "food", "beverages")
        .contains(
            Tuple.tuple(2, "Honest John Pizza", List.of("cheese pizza", "pepperoni pizza"), List.of("soda"))
        );

    assertThat(restaurantsOrders.stream().flatMap(x -> x.orders().stream()))
        .hasSize(1)
        .extracting("id", "item", "restaurant_name", "drink")
        .contains(
            Tuple.tuple(3, "cheese pizza", "Honest John Pizza", "soda")
        );
  }

  @DisplayName("joinConditionsandSubqueriesonaJoinedCollection")
  @Test
  void joinConditionsandSubqueriesonaJoinedCollection() {
    // given : 선행조건 기술

    // when : 기능 수행
    List<RestaurantsOrdersLookupDto> restaurantsOrders = restaurantsService.joinConditionsandSubqueriesonaJoinedCollection();

    // then : 결과 확인
    assertThat(restaurantsOrders.stream().map(RestaurantsOrdersLookupDto::restaurants))
        .hasSize(2)
        .extracting("id", "name", "food", "beverages")
        .contains(
            Tuple.tuple(1, "American Steak House", List.of("filet", "sirloin"), List.of("beer", "wine")),
            Tuple.tuple(2, "Honest John Pizza", List.of("cheese pizza", "pepperoni pizza"), List.of("soda"))
        );

    assertThat(restaurantsOrders.stream().flatMap(x -> x.orders().stream()))
        .hasSize(1)
        .extracting("id", "item", "restaurant_name", "drink")
        .contains(
            Tuple.tuple(3, "cheese pizza", "Honest John Pizza", "soda")
        );
  }

  @DisplayName("correlatedSubqueries")
  @Test
  void correlatedSubqueries() {
    // given : 선행조건 기술

    // when : 기능 수행
    List<RestaurantsOrdersLookupDto> restaurantsOrders = restaurantsService.aggregateRestaurantsOrdersCorrelatedSubqueries();

    // then : 결과 확인
    assertThat(restaurantsOrders.stream().map(RestaurantsOrdersLookupDto::restaurants))
        .hasSize(2)
        .extracting("id", "name", "food", "beverages")
        .contains(
            Tuple.tuple(1, "American Steak House", List.of("filet", "sirloin"), List.of("beer", "wine")),
            Tuple.tuple(2, "Honest John Pizza", List.of("cheese pizza", "pepperoni pizza"), List.of("soda"))
        );

    assertThat(restaurantsOrders.stream().flatMap(x -> x.orders().stream()))
        .hasSize(1)
        .extracting("id", "item", "restaurant_name", "drink")
        .contains(
            Tuple.tuple(3, "cheese pizza", "Honest John Pizza", "soda")
        );
  }

  private Restaurants createRestaurant(int id, String name, List<String> food, List<String> beverages) {
    return Restaurants.create(id, name, food, beverages);
  }

  private Orders createOrders(int id, String item, String restaurant_name, String drink) {
    return Orders.create(id, item, restaurant_name, drink);
  }
}
