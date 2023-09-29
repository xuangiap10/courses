package edu.mum.cs.cs425.junit;

import static org.junit.Assert.assertArrayEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;


@SpringBootTest

class ArrayReversorTestCases {
	@Mock
	private ArrayFlattener arrFlattenerMock;

	@InjectMocks
	@Autowired
	private ArrayReversor arrReversor;

	@Test
	void testArrayReversor_input_2DArray() {
		
		int[][] input = { { 1, 3 }, { 0 }, { 4, 5, 9 } };
		when(arrFlattenerMock.flattenArray(input)).thenReturn(new int[]{1,3,0,4,5,9});
		int[] output = arrReversor.reverseArray(input);
		int[] expected = { 9, 5, 4, 0, 3, 1 };
		assertArrayEquals(expected, output);
	}

	@Test
	void testArrayReversor_input_null() {
		
		int[][] input = null;
		int[] output = arrReversor.reverseArray(input);
		int[] expected = null;
		assertArrayEquals(expected, output);
	}
}
