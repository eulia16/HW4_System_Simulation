package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InsufficientFundsException, InterruptedException {

        AtomicModelCoffeeMachine coffeeMachine = new AtomicModelCoffeeMachine();
        coffeeMachine.simulate();
        System.out.println("test");

    }
}
