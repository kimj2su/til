package poly.ex.food;

public enum MenuList {
    CHICKEN("치킨", menuName -> new Chicken()),
    PIZZA("피자", menuName -> new Pizza())
    ;
    private final String menuName;
    private final MenuFunction<String, Menu> menuFunction;

    MenuList(String menuName, MenuFunction<String, Menu> menuFunction) {
        this.menuName = menuName;
        this.menuFunction = menuFunction;
    }

    // private final MenuFunction<String, Menu> menuFunction = menuType -> switch (menuType) {
    //     case "치킨" -> new Chicken();
    //     case "피자" -> new Pizza();
    //     default -> throw new IllegalArgumentException("존재하지 않는 메뉴입니다.");
    // };
    // MenuType(String menuName) {
    //     this.menuName = menuName;
    // }

    public String getMenuName() {
        return menuName;
    }
    public Menu getMenu() {
        return menuFunction.apply(this.menuName);
    }
}
