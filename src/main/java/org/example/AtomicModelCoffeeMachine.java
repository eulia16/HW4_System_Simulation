package org.example;

import com.sun.source.tree.ReturnTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class AtomicModelCoffeeMachine {

    public static final int COFFEE_VALUE=100, QUARTER_VALUE=25, NICKEL_VALUE = 5, DIME_VALUE=10;
    int value = 0, q=0, n=0, d=0;
    double elapsedTime = 0.0;
    private InputPair nextInternalEvent;
    private PriorityQueue queueOfData;

    public AtomicModelCoffeeMachine(){
        nextInternalEvent = InputPair.inputPairTimeInf();
        queueOfData = new PriorityQueue();

    }

    public void simulate() throws InsufficientFundsException {

        for(;;){
            //if the next event is external
            if(this.nextInternalEvent.continuousTime < queueOfData.peek().continuousTime){
                deltaExt(queueOfData.pop());
                //then call time advance
                double timeAdvance = timeAdvance();
                this.nextInternalEvent = new InputPair(timeAdvance, 0, '-');
            }
            //if the next event is internal
            else if(this.nextInternalEvent.continuousTime > queueOfData.peek().continuousTime){
                //lambda before delta
                lambda();
                deltaInt();
                double timeAdvance = timeAdvance();
                this.nextInternalEvent = new InputPair(timeAdvance, 0, '-');
            }
            //else we have a confluent event
            else {
                lambda();
                deltaCon(queueOfData.pop());
                double timeAdvance = timeAdvance();
                this.nextInternalEvent = new InputPair(timeAdvance, 0, '-');
            }

        }

    }


    public void deltaInt() throws InsufficientFundsException {
        int valueToBeDispersedInCoins = this.value % 100;
        //if the value is zero, the input was a mod of 100, nothing else to do
        if(valueToBeDispersedInCoins == 0)
            return;

        int numLoopsToAddForRespectiveLoops = 0;


        //for quarters
        int divideByQuarterValue = valueToBeDispersedInCoins / QUARTER_VALUE;

        if(divideByQuarterValue > this.q)
            numLoopsToAddForRespectiveLoops =  this.q;
        else
            numLoopsToAddForRespectiveLoops = divideByQuarterValue;

        if(this.q !=0 ) {
            valueToBeDispersedInCoins -= (numLoopsToAddForRespectiveLoops * QUARTER_VALUE);
            this.q -= (numLoopsToAddForRespectiveLoops * QUARTER_VALUE);
        }

        //for dimes
        int divideByDimeValue = valueToBeDispersedInCoins / DIME_VALUE;

        if(divideByDimeValue > this.d)
            numLoopsToAddForRespectiveLoops =  this.d;
        else
            numLoopsToAddForRespectiveLoops = divideByDimeValue;

        if(this.d !=0 ) {
            valueToBeDispersedInCoins -= (numLoopsToAddForRespectiveLoops * DIME_VALUE);
            this.d -= (numLoopsToAddForRespectiveLoops * DIME_VALUE);
        }

        //for nickels
        int divideByNickelValue = valueToBeDispersedInCoins / NICKEL_VALUE;

        if(divideByNickelValue > this.n)
            numLoopsToAddForRespectiveLoops =  this.n;
        else
            numLoopsToAddForRespectiveLoops = divideByNickelValue;

        if(this.n !=0 ) {
            valueToBeDispersedInCoins -= (numLoopsToAddForRespectiveLoops * NICKEL_VALUE);
            this.n -= (numLoopsToAddForRespectiveLoops * NICKEL_VALUE);

        }



        if(valueToBeDispersedInCoins != 0)
            throw new InsufficientFundsException("Insufficient Funds");

        //reset the value as well
        this.value = 0;


    }

    public void deltaCon(InputPair inputPair) throws InsufficientFundsException {
        if(this.nextInternalEvent.constantTime < inputPair.constantTime){
            deltaExt(inputPair);
            deltaInt();
        }
        //not sure if this is right, but we'll leave it rn
        else{
            deltaInt();
            deltaExt(inputPair);
        }

    }

    public void deltaExt(InputPair input){

        if(input.change == 'q'){
            q++;
            value += 25;
        }
        else if(input.change == 'n'){
            n++;
            value += 5;
        }
        else{
            d++;
            value += 10;
        }

    }

    //{coffee for every 100 cents in v} union {a suitable combination of q, n, d representing v % 100}.
    public void lambda() throws InsufficientFundsException {
        System.out.println("fuckmesilly input: Daddy;\n" +
                "        ReturnTree I'm a chode\"\n" +
                "    '" + " cough cough... idk what that was...anywaysss here is the number of coffee's you will receive " +
                 getNumCoffees());
        System.out.println("And here is the change you will receive: " + getChange());

    }

    public int getNumCoffees(){
        return (this.value % COFFEE_VALUE);
    }
    private List<Character> getNumQuarters(List<Character> ret, int numLoops){
        for(int i=0; i< numLoops; ++i){
            ret.add('q');
        }
        return ret;
    }
    private List<Character> getNumDimes(List<Character> ret, int numLoops){
        for(int i=0; i< numLoops; ++i){
            ret.add('d');
        }
        return ret;
    }
    private List<Character> getNumNickels(List<Character> ret, int numLoops){
        for(int i=0; i< numLoops; ++i){
            ret.add('n');
        }
        return ret;
    }


    public List<Character> getChange() throws InsufficientFundsException {

        int modulusOfValue = this.value % COFFEE_VALUE;
        List<Character> returnValues = new ArrayList<>();
        if(modulusOfValue == 0) {
            return returnValues;//if there is no change to output, return the empty list
        }
        else{
            int numLoopsToAddRespectiveCoins=0;

            int modDividedByQuarterValue = modulusOfValue / QUARTER_VALUE;
            //if the divided amt is larger than this.numQuarters, use this.numQuarters for the loop,
            //and decrement the total value as expected, else just use the modDividedByQuarterVlalue
            if(modDividedByQuarterValue > this.q)
                numLoopsToAddRespectiveCoins = this.q;
            else
                numLoopsToAddRespectiveCoins = modDividedByQuarterValue;

            if(this.q !=0 ) {
                returnValues = getNumQuarters(returnValues, numLoopsToAddRespectiveCoins);
                modulusOfValue -= (numLoopsToAddRespectiveCoins * QUARTER_VALUE);
                if (modulusOfValue == 0)
                    return returnValues;
            }


            int modDividedByDimeValue = modulusOfValue / DIME_VALUE;
            if(modDividedByDimeValue > this.d)
                numLoopsToAddRespectiveCoins = this.d;
            else
                numLoopsToAddRespectiveCoins = modDividedByDimeValue;

            if(this.d != 0) {
                returnValues = getNumDimes(returnValues, numLoopsToAddRespectiveCoins);
                modulusOfValue -= (numLoopsToAddRespectiveCoins * DIME_VALUE);
                if (modulusOfValue == 0)
                    return returnValues;
            }


            int modDividedByNickelValue = modulusOfValue / NICKEL_VALUE;
            if(modDividedByNickelValue > this.n)
                numLoopsToAddRespectiveCoins = this.n;
            else
                numLoopsToAddRespectiveCoins = modDividedByNickelValue;

            if(this.n !=0) {
                returnValues = getNumNickels(returnValues, numLoopsToAddRespectiveCoins);
                modulusOfValue -= (numLoopsToAddRespectiveCoins * NICKEL_VALUE);
                if (modulusOfValue == 0)
                    return returnValues;
            }


        }

        //if the modulus
        if(modulusOfValue != 0)
            throw new InsufficientFundsException("Insufficient Funds");

        return returnValues;

    }


    /**
     works on the current state of the machine
     */
    public double timeAdvance(){
        if(this.value > 0){
            return 2.0;
        }
        else{
            return Double.POSITIVE_INFINITY;
        }
    }




}
