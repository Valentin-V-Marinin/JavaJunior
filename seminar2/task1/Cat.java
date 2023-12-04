package seminar2.task1;

public class Cat  extends Animal{

    private String breed;

    public Cat(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }

    public void treeClimbing(){
        System.out.println("Cat is jumping on a tree and stay there to control situation.");
    }

    public void foodBegging(){
        System.out.println("Cat's first rule: you should ask a food even you aren't hungry.");
    }

    @Override
    public String toString() {
        return "Cat {" +
                "breed='" + breed + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
