package poly.ex.food;

public class Person {

    // private Menu menu;
    //
    // public void setMenu(Menu menu) {
    //     this.menu = menu;
    // }
    //
    // public void choiceMenu(String menuName) {
    //     System.out.println("음식을 선택합니다.");
    //     menu.selected(menuName);
    // }

    public void choiceMenu(MenuList menuList) {
        System.out.println("음식을 선택합니다.");
        Menu menu = menuList.getMenu();
        menu.selected(menuList.getMenuName());
    }
}
