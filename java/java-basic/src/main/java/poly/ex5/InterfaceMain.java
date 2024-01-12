package poly.ex5;

public class InterfaceMain {

    public static void main(String[] args) {
        InterfaceAnimal[] animals = new InterfaceAnimal[3];
        animals[0] = new Cat();
        animals[1] = new Dog();
        animals[2] = new Cow();

        for (InterfaceAnimal animal : animals) {
            animal.sound();
            animal.move();
        }

        // 다형성을 이용한 방법
        // InterfaceAnimal animal = new Cat();
        Cat cat = new Cat();
        Dog dog = new Dog();
        Cow cow = new Cow();

        soundAnimal(cat);
        soundAnimal(dog);
        soundAnimal(cow);
    }

    private static void soundAnimal(InterfaceAnimal animal) {
        System.out.println("동물 소리 테스트 시작");
        animal.sound();
        System.out.println("동물 소리 테스트 종료");
    }
}
