
package workshop3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class WorkShop3_nuevo {
    
    static int[] numbers;
    static LinkedList key;
    static LinkedList values;
    static long elapsedTime; //tomado de:https://stackify.com/heres-how-to-calculate-elapsed-time-in-java/
    static int comparation;
    
    public static void main(String[] args) throws FileNotFoundException {
        String FileName = "worstCase.txt";
        create(FileName);
        PrintWriter out = new PrintWriter(FileName);
        int repetition = 3;
        
        //para que aumente de tamaño
        for(int power = 4; power < 18; power++){
            int size = (int) Math.pow(2, power);
            double time = 0;
            comparation = 0;
            //para las repeticiones
            for(int i = 0; i < repetition; i++){
                createNumbers(size, 1);
                countDuplicates();
                time = (long) (time + elapsedTime);
            }
            //para sacar promedio de los valores
            time = time / repetition;
            comparation = comparation / repetition;
            
            String line = size + " " + comparation + " " + time;
            System.out.println("");
            System.out.println(line);
            System.out.println("");
            out.println(line);
        }
        out.close();
    }
    
    private static void createNumbers(int size, int caso){
        WorkShop3_nuevo.numbers = new int[size];
        Random rand = new Random();
        String msg = "writing %d random numbers ... ";
        System.out.printf(msg, size);
            
        for (int i = 0; i != size; ++i) {
            int number = -1;
            switch(caso){
                //best case
                case 1: 
                    number = 1;
                    break;
                //worst case
                case 2: 
                    number = i;
                    break;
                // average case
                case 3: 
                    number = rand.nextInt(size);
                    break;
            }
                WorkShop3_nuevo.numbers[i] = number;
        }
        System.out.println("done");
    }
        
    //code made by teacher Misael
    private static void create(String fname) {
    // creates a file with given name `filename'
        try {
            // creates a new File object
            File f = new File(fname);

            // creates the new file
            String msg = "creating file " + fname + "' ... ";
            System.out.println();
            System.out.printf("%s", msg);
            f.createNewFile();
            System.out.println("done");

        } catch (IOException err) {
            // complains if there is an Input/Output Error
            System.out.println("IO Error:");
            err.printStackTrace();
        }

        return;
    }

    private static void countDuplicates() {
        key = new LinkedList();
        values = new LinkedList();
        long start = System.nanoTime();
        for(int i = 0; i < numbers.length; i++){
            int index = WorkShop3_nuevo.getByValue(numbers[i]);
            //para saber si el nro esta en la lista
            if(index == -1){ //si no esta
                //agrega el nro a la lista key
                Node n = new Node(numbers[i]);
                key.insert(n);
                comparation = comparation + key.size();
                //agrega un uno a la lista values
                Node n2 = new Node(1);
                values.insert(n2);
            }else{//si esta
                //suma +1 en la misma posicion que esta el numero en key 
                values.set(index, values.getByIndex(index) + 1);
            }
        }
        
        long end = System.nanoTime();
        elapsedTime = end - start; 
    }
    
    //mismo metodo de la clase LinkedList pero modificado para obtener el nro de comparaciones
    public static int getByValue(int number){
        for(int i = 0; i < key.size(); i++){
            if(key.getByIndex(i) == number){
                comparation = comparation +  1;
                return i;
            }
        }
        return -1;
    }
}

class Node{
    private int value;
    private Node link;
    
    public Node(int dato){
        this.value = dato;
    }
    
    public int getValue(){
        return value;
    }
    
    public void setValue(int num){
        this.value = num;
    }
    
    public Node getLink(){
        return link;
    }
    
    public void setLink(Node a){
        this.link = a;
    }
}   

class LinkedList {
    
    private Node Ptr;
    private Node link;
    
    public LinkedList(){
        this.Ptr = null;
    }
    
    public void insert(Node n){
        if(Ptr == null){
            Ptr = n;
        }else{
            Node p = Ptr;
            while(p.getLink() != null){
                p = p.getLink();
            }
            p.setLink(n);
        } 
    }
    
    //get number by index
    public int getByIndex(int i){
        int index = 0;
        Node p = Ptr;
        while(p != null){
            if(index == i){
                return p.getValue();
            }
            p = p.getLink();
            index++;
        }
        return -1;
    }
    
    //get number by its value
    public int getByValue(int number){
        for(int i = 0; i < size(); i++){
            if(this.getByIndex(i) == number){
                return i;
            }
        }
        return -1;
    }
    
    public void set(int index, int num){
        Node p = Ptr;
        int i = 0;
        while(p != null){
            if(index == i){
                p.setValue(num);
            }
            p = p.getLink();
            i++;
        }
    }
    
    public int size(){
        int i = 0;
        Node p = Ptr;
        while(p != null){
            i++;
            p = p.getLink();
        }
        return i;
    }
    
}
