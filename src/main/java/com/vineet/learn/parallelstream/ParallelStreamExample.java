package com.vineet.learn.parallelstream;

import static com.vineet.learn.util.CommonUtil.*;
import static com.vineet.learn.util.LoggerUtil.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelStreamExample {
	
	public List<String> stringTransform(List<String> namesList){
        return namesList
                .parallelStream()
                .map(this::transform)
                .collect(Collectors.toList());
    }
	
	public List<String> stringTransformToLowerCase(List<String> namesList){
        return namesList
                .parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
	
	public List<String> stringTransformDynamically(List<String> namesList, boolean isParallel){
        
		Stream<String> inputStream = namesList.stream();
		
		if(isParallel)
			inputStream.parallel();
		
		return inputStream
                .map(this::transform)
                .collect(Collectors.toList());
    }
	
	private String transform(String name) {
        delay(500);
        return name.length() + " - " + name;
    }
	
	
	public static void main(String[] args) {
		List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");
        log("namesList : " + namesList);
        startTimer();
        ParallelStreamExample parallelismExample = new ParallelStreamExample();
        List<String> resultList = parallelismExample.stringTransform(namesList);
        timeTaken();
        log("resultList : " + resultList);
	}

}
