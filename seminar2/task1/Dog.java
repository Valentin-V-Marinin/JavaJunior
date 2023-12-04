package seminar2.task1;

public class Dog extends Animal{

    private double weight;


    public Dog(String name, int age, double weight) {
        super(name, age);
        this.weight = weight;
    }

    @Override
    public void makeSound() {
        System.out.println("bow-bow!");
    }

    public void dogTracking(){
        System.out.println("Dog searches the track.");
    }

    public void dogHunting(){
        System.out.println("Dog brings a trophy to a hunter.");
    }

    @Override
    public String toString() {
        return "Dog {" +
                "weight=" + weight +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
