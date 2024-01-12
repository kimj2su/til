package poly.ex.food;

@FunctionalInterface
public interface MenuFunction<String, Menu> {
    Menu apply(String menuType);
}
