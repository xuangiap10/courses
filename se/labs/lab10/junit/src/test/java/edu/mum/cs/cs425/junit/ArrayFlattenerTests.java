package edu.mum.cs.cs425.junit;

import static org.junit.Assert.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ArrayFlattenerTests {
	@Autowired
	private ArrayFlattener arrFlattener;

	@Test
	void testFlattenArray_input_2DArray() {
		int[][] a_i = {{1,3}, {0}, {4,5,9}};
		int[] a_o = arrFlattener.flattenArray(a_i);
		
		int[] a_expected = {1,3,0,4,5,9};
		
		assertArrayEquals(a_expected, a_o);
	}

	@Test
	void testFlattenArray_input_null() {
		int[][] a_i = null;
		int[] a_o = arrFlattener.flattenArray(a_i);;
		
		int[] a_expected = null;
		
		assertArrayEquals(a_expected, a_o);
	}
}
