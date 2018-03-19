import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JFrame;

public class main extends JFrame {
	final static Set<Point> positiveSet = new HashSet<Point>();
	final static Set<Point> negativeSet = new HashSet<Point>();
	static int polynomialDegree = -1;
	static int sizeOfGeneration = 500;
	static int mutationRate = 10;
	static Generation generation = new Generation();
	static CartesianPlane cp;
	static Individual ind;
	
	public static void main(String[] args) throws FileNotFoundException, IOException{
		readInput();
		
		//Initialization of random population
		generation.generateRandomGeneration(polynomialDegree, sizeOfGeneration);
		
		int currentHighestFitness = generation.calculateFitness();	
		
		int terminateCondition = positiveSet.size()+negativeSet.size();
		
		ind = generation.fittestInd;
		cp.setPolynomial(ind);
		
		if(polynomialDegree==3 || polynomialDegree==4){
			while(currentHighestFitness<terminateCondition){
				generation.selection();
				
				generation.crossover();	
				
				generation.mutation(mutationRate);
				
				currentHighestFitness = generation.calculateFitness();
				
				ind = generation.fittestInd;
				cp.setPolynomial(ind);
			}
		}
		else{
			while(currentHighestFitness<(terminateCondition-8)){
				generation.selection();
				
				generation.crossover();	
				
				generation.mutation(mutationRate);
				
				currentHighestFitness = generation.calculateFitness();
				
				ind = generation.fittestInd;
				cp.setPolynomial(ind);
			}
		}
				
	}

	private static void readInput() throws IOException, FileNotFoundException{
		polynomialDegree=-1;
		Scanner reader = new Scanner(System.in);
		
		while(polynomialDegree < 2 || polynomialDegree > 5){
			System.out.println("Polynomial degree (2/3/4/5): ");
			try {
			    polynomialDegree = Integer.parseInt(reader.nextLine());
			}catch(NumberFormatException e) {
			}
		}
		polynomialDegree=polynomialDegree+1;
		
		cp = new CartesianPlane(positiveSet, negativeSet);
		JFrame window = new JFrame("Results presentation");
		window.add(cp);
		window.setSize(1600, 1000);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
		BufferedReader positiveFile;
		BufferedReader negativeFile;
		
		if(polynomialDegree==3 || polynomialDegree==4){
			positiveFile = new BufferedReader(new FileReader("src/positive.txt"));
			negativeFile = new BufferedReader(new FileReader("src/negative.txt"));
		}
		else{
			positiveFile = new BufferedReader(new FileReader("src/mixed_positive.txt"));
			negativeFile = new BufferedReader(new FileReader("src/mixed_negative.txt"));
		}
		
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = positiveFile.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        String[] line2 = line.split("\\s+");
		        int x = Integer.parseInt(line2[0]);
		        int y = Integer.parseInt(line2[1]);
		        Point p = new Point(x,y);
		        positiveSet.add(p);
		        line = positiveFile.readLine();
		    }
		} finally {
		    positiveFile.close();
		}
		
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = negativeFile.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        String[] line2 = line.split("\\s+");
		        int x = Integer.parseInt(line2[0]);
		        int y = Integer.parseInt(line2[1]);
		        Point p = new Point(x,y);
		        negativeSet.add(p);
		        line = negativeFile.readLine();
		    }
		} finally {
		    negativeFile.close();
		}
		reader.close();
	}
	
	public static Set<Point> getPositiveSet() {
		return positiveSet;
	}

	public static Set<Point> getNegativeSet() {
		return negativeSet;
	}
}
