package poly.ex.food;

public class Chicken implements Menu {
    @Override
    public void selected(String menu) {
        System.out.println(menu + "를 선택했습니다.");
    }
}
