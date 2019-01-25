package in.co.sdrc.sdrcdatacollector.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Startup {

	public static void main(String[] args) {
		Map<String,String> questionMap = new HashMap<>();
		String expression = "lessThan(sum (2,3) , sum(5,6))";
//		String expression = "lessThan( 1 , sum(5,6,6,7,5))";
//		String expression = "if(selected(5,5), 1, 0)";
//		String expression = "if(5 > 5 , 1 , 0 )";
//		if(${f1q89} = ${f1q90}, 1 , 0 )
//		if(selected(5,5), 1, 0)
//		String expression = "mul(sum(2 , 1 , 0 ),1)";
//		String expression = "1 <= 2";
		
		Tokenizer tokenizer = new Tokenizer();
		 List<Token> tokens = tokenizer.tokenize(expression, questionMap);
		
		InfixToPrefixTransformer transformer = new InfixToPrefixTransformer();
		transformer.transform(tokens);
	}
	
	
}
