package poly.ex.food;

public class FoodMain1 {

    public static void main(String[] args) {
        Person person = new Person();

        MenuList pizza = MenuList.PIZZA;
        person.choiceMenu(pizza);

        MenuList chicken = MenuList.CHICKEN;
        person.choiceMenu(chicken);
    }
}
