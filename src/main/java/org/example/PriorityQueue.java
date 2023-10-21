package org.example;

import java.util.ArrayList;

//example data to use
/*
*
1.5,0 q
2.1,0 d
2.3,0 q
4.3,0 q
5.1,2 q
5.2,1 q
5.5,0 q
5.6,0 q
7.1,0 d
11.0,0 q
* */

//will store all input data, will have a pop function that pops the next closest event
public class PriorityQueue {
    private ArrayList<InputPair> data;//data is already sorted so we dont need to sort it, not really a priority queue now
    private int counter=0;

    public PriorityQueue(){
        data = new ArrayList<>();
        //enter some random data
        data.add(new InputPair(1.5, 0, 'q'));
        data.add(new InputPair(2.1, 0, 'd'));
        data.add(new InputPair(2.3, 0, 'q'));
        data.add(new InputPair(4.3, 0, 'q'));
        data.add(new InputPair(5.1, 2, 'q'));
        data.add(new InputPair(5.2, 1, 'q'));
        data.add(new InputPair(5.5, 0, 'q'));
        data.add(new InputPair(5.6, 0, 'q'));
        data.add(new InputPair(7.1, 0, 'd'));
        data.add(new InputPair(11.0, 0, 'q'));

    }

    public InputPair pop(){

        try{
            if(counter < data.size()){
                return data.get(counter);
            }
            else{
                return null;
            }
        }
        finally{
            counter++;
         }
    }

    public InputPair peek(){
            return data.get(counter);
    }


}

