package com.mycompany.builderpatterndemo;

import java.util.*;

// Interfaces
interface Item {
    String name();
    Packing packing();
    float price();
}

interface Packing {
    String pack();
}

// Packing Types
class Wrapper implements Packing {
    public String pack() {
        return "Wrapper";
    }
}

class Bottle implements Packing {
    public String pack() {
        return "Bottle";
    }
}

class Cup implements Packing {
    public String pack() {
        return "Cup";
    }
}

// Abstract Classes
abstract class Burger implements Item {
    public Packing packing() {
        return new Wrapper();
    }

    public abstract float price();
}

abstract class ColdDrink implements Item {
    public Packing packing() {
        return new Bottle();
    }

    public abstract float price();
}

abstract class HotDrink implements Item {
    public Packing packing() {
        return new Cup();
    }

    public abstract float price();
}

// Concrete Burgers
class VegBurger extends Burger {
    public float price() {
        return 25.0f;
    }

    public String name() {
        return "Veg Burger";
    }
}

class ChickenBurger extends Burger {
    public float price() {
        return 50.5f;
    }

    public String name() {
        return "Chicken Burger";
    }
}

// Concrete Cold Drinks
class Coke extends ColdDrink {
    public float price() {
        return 30.0f;
    }

    public String name() {
        return "Coke";
    }
}

class Pepsi extends ColdDrink {
    public float price() {
        return 35.0f;
    }

    public String name() {
        return "Pepsi";
    }
}

class DietCoke extends ColdDrink {
    public float price() {
        return 25.0f;
    }

    public String name() {
        return "Diet Coke";
    }
}

class DietPepsi extends ColdDrink {
    public float price() {
        return 28.0f;
    }

    public String name() {
        return "Diet Pepsi";
    }
}

// Base Tea
class Tea extends HotDrink {
    public float price() {
        return 20.0f;
    }

    public String name() {
        return "Plain Tea";
    }
}

// Decorator for Tea
abstract class TeaDecorator extends HotDrink {
    protected HotDrink decoratedTea;

    public TeaDecorator(HotDrink tea) {
        this.decoratedTea = tea;
    }

    public Packing packing() {
        return decoratedTea.packing();
    }

    public String name() {
        return decoratedTea.name();
    }
}

class Sweetness extends TeaDecorator {
    private String type;
    private int spoons;

    public Sweetness(HotDrink tea, String type, int spoons) {
        super(tea);
        this.type = type;
        this.spoons = spoons;
    }

    public float price() {
        return decoratedTea.price() + (spoons * 2.0f);
    }

    public String name() {
        return decoratedTea.name() + " + Sweetness (" + spoons + " spoons of " + type + ")";
    }
}

class Cream extends TeaDecorator {
    public Cream(HotDrink tea) {
        super(tea);
    }

    public float price() {
        return decoratedTea.price() + 5.0f;
    }

    public String name() {
        return decoratedTea.name() + " + Cream";
    }
}

// Meal Class
class Meal {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) {
        items.add(item);
    }

    public float getCost() {
        float cost = 0;
        for (Item item : items) {
            cost += item.price();
        }
        return cost;
    }

    public void showItems() {
        for (Item item : items) {
            System.out.println("Item: " + item.name() + ", Packing: " + item.packing().pack() + ", Price: " + item.price());
        }
    }
}

// Builder Class
class MealBuilder {
    public Meal prepareCustomMeal(Scanner sc) {
        Meal meal = new Meal();

        System.out.println("Choose Burger: 1. Veg  2. Chicken  3. None");
        int burgerChoice = sc.nextInt();
        if (burgerChoice == 1) meal.addItem(new VegBurger());
        else if (burgerChoice == 2) meal.addItem(new ChickenBurger());

        System.out.println("Choose Drink: 1. Coke 2. Pepsi 3. Diet Coke 4. Diet Pepsi 5. Tea 6. None");
        int drinkChoice = sc.nextInt();
        switch (drinkChoice) {
            case 1: meal.addItem(new Coke()); break;
            case 2: meal.addItem(new Pepsi()); break;
            case 3: meal.addItem(new DietCoke()); break;
            case 4: meal.addItem(new DietPepsi()); break;
            case 5:
                HotDrink tea = new Tea();
                System.out.print("Add Sweetness? (yes/no): ");
                String addSweet = sc.next();
                if (addSweet.equalsIgnoreCase("yes")) {
                    System.out.print("Sweetener type (Sugar/Stevia/etc): ");
                    String sweetType = sc.next();
                    System.out.print("Number of spoons: ");
                    int spoons = sc.nextInt();
                    tea = new Sweetness(tea, sweetType, spoons);
                }
                System.out.print("Add Cream? (yes/no): ");
                String addCream = sc.next();
                if (addCream.equalsIgnoreCase("yes")) {
                    tea = new Cream(tea);
                }
                meal.addItem(tea);
                break;
        }

        return meal;
    }
}

// Main Class
public class BuilderPatternDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MealBuilder builder = new MealBuilder();

        System.out.println("Welcome to Custom Meal Builder!");
        Meal customMeal = builder.prepareCustomMeal(sc);

        System.out.println("\nYour Meal:");
        customMeal.showItems();
        System.out.println("Total Cost: " + customMeal.getCost());

        sc.close();
    }
}
