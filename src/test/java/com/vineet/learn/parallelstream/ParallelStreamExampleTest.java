package com.vineet.learn.parallelstream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.vineet.learn.util.DataSet;

public class ParallelStreamExampleTest {
	
	ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
	
	@Test
	void stringTransform() {
		List<String> inputList = DataSet.namesList();
		
		List<String> resultList = parallelStreamExample.stringTransform(inputList);
		
		assertEquals(inputList.size(), resultList.size());
		resultList.forEach(s -> {
			assertTrue(s.contains("-"));
		});
		
	}
	
	@Test
	void stringTransformToLowerCase() {
		List<String> inputList = DataSet.namesList();
		
		List<String> resultList = parallelStreamExample.stringTransformToLowerCase(inputList);
		
		assertEquals(inputList.size(), resultList.size());
		resultList.forEach(s -> {
			s.chars() //intStream
				.mapToObj(i -> (char) i)
				.peek(c -> { System.out.print(c); })
				.allMatch(c -> Character.isLowerCase(c));
		});
		
		
	}
	
	
	@ParameterizedTest
	@ValueSource(booleans = {true, false})
	void stringTransformDynamically(boolean isParallel) {
		List<String> inputList = DataSet.namesList();
		
		List<String> resultList = parallelStreamExample.stringTransformDynamically(inputList, isParallel);
		
		assertEquals(inputList.size(), resultList.size());
		resultList.forEach(s -> {
			assertTrue(s.contains("-"));
		});
		
	}

}
