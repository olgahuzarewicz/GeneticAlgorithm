import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.Vector;


public class Individual {
	 int id;
	 int fitness = 0;
	 int chromosomeLength = 8;
	 ArrayList<Integer> genes;
	 
	 Vector<int[]> genotype;
	 
	 public Individual(int size) {
		 genotype = new Vector<int[]>(size);		 
	 }

	 
	 public int getId() {
		return id;
	 }

	public void setId(int id) {
		this.id = id;
	}
	 
	void setGenesRandomly(int size){
		Random rn = new Random();
		for(int j=0; j < size; j++){
			 int[] chromosome = new int[chromosomeLength];
			 for (int i = 0; i < chromosomeLength; i++) {
		         chromosome[i] = Math.abs(rn.nextInt() % 2);
		     }
		     genotype.add(chromosome);
		 }
	}

	int calculateFitness(){
		 fitness=0;
		 String temp = "";
		 int decimalValue=-1;
		 genes = new ArrayList<>();	     
		 
		 for(int[] ind : genotype){
			 for(int i=0; i<chromosomeLength; i++){
				 temp=temp+ind[i];
			 }
			 int sign =  Integer.parseInt(temp.substring(0,1));
			 temp = temp.substring(1);
			 decimalValue = Integer.parseInt(temp, 2);
			 if(sign==1){
				 decimalValue *= -1;
			 }
			 genes.add(decimalValue);
             temp="";
		 }
		 
		 int x;
		 int y;
		 Set<Point> positiveSet = main.getPositiveSet();
		 int result = 0;
		 for (Point p : positiveSet) {
			    x = p.getX();
			    //System.out.println("Positive x: " + x);
			    y = p.getY();
			    //System.out.println("Positive y: " + y);
			    int counter = genes.size()-1;
			    
			    for(Integer gene : genes){
			    	if(counter>1){
			    		int power = (int) Math.pow(x, counter);
			    		result = result + (gene*power);
						counter--;
						//System.out.print("(" +gene + "x^" + counter + ")+");
			    	}
			    	else{
			    		result = result + gene;
			    		//System.out.print("(" + gene + ")\n");
			    	}
				 }
			    if(result>=y){
			    	fitness++;
			    	//System.out.println("It's ok");
			    }
			    result=0;
			    counter = genes.size()-1;
			    //System.out.println();
		 }
		 
		 Set<Point> negativeSet = main.getNegativeSet();
		 for (Point p : negativeSet) {
			    x = p.getX();
			    //System.out.println("Negative x: " + x);
			    y = p.getY();
			    //System.out.println("Negative y: " + y);
			    int counter = genes.size()-1;
			    
			    for(Integer gene : genes){
			    	if(counter>1){
			    		int power = (int) Math.pow(x, counter);
			    		result = result + (gene*power);						
			    		counter--;
			    		//System.out.print("(" +gene + "x^" + counter + ")+");
			    	}
			    	else{
			    		result = result + gene;
			    		//System.out.print("(" + gene + ")\n");
			    		//System.out.println("result: " + result);
			    	}
				 }
			    if(result<=y){
			    	fitness++;
			    	//System.out.println("It's ok");
			    }
			    result=0;
			    counter = genes.size()-1;
		 }
		 return fitness;
	 }
	
	 public int getFitness() {
		return fitness;
	}
}
