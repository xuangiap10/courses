package edu.mum.cs.cs425.junit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArrayReversor implements CommandLineRunner{
	@Autowired
	private ArrayFlattener arrFlattener;
	
	public int[] reverseArray(int[][] a_i) {

		int[] flatArray = arrFlattener.flattenArray(a_i);
		if(flatArray == null) return null;
		
		int i = 0, j = flatArray.length-1;
		while(i < j) {
			int tmp = flatArray[i];
			flatArray[i] = flatArray[j];
			flatArray[j] = tmp;
			++i; 
			--j;
		}
		return flatArray;
	}

	public static void main(String[] args) {
		SpringApplication.run(ArrayFlattener.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
