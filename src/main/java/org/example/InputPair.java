package org.example;
//this could have been a record as it's the same structure, i'm just too lazy to convert it now :(
public final class InputPair {
     final double continuousTime;
     final int constantTime;
     final char change;

     public InputPair(double d, int c, char change){
         this.continuousTime = d;
         this.constantTime = c;
         this.change = change;
     }


    public double getContinuousTime() {
        return continuousTime;
    }

    public int getConstantTime() {
        return constantTime;
    }

    public char getChange() {
        return change;
    }

    public static InputPair inputPairTimeInf(){
         return new InputPair(Double.POSITIVE_INFINITY, 0, '-');
    }

}
