package in.co.lifepartner.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tokenizer {
	List<String> numberBuffer = new ArrayList<>();
	List<String> letterBuffer = new ArrayList<>();

	public List<Token> tokenize(String expression, Map<String, String> questionMap) {

		List<Token> tokens = new ArrayList<>();

		expression = expression.replaceAll(" ", "");
		String[] chs = expression.split("");

		for (int i = 0; i < chs.length; i++) {
			String ch = chs[i];
			switch (ch) {
			case "<":
			case ">":
			case "=":
			case "+":
			case "-":
			case "*":
			case "/":
			case "%":
				tokens = this.clearNumberBuffer(tokens);
				tokens = this.clearLetterBuffer(tokens);
				String ch1 = chs[i + 1];
				if (ch1.equals("=")) {
					tokens.add(new Token(TokenChar.OPERATOR, ch + ch1));
					i = i+1;
				} else {
					tokens.add(new Token(TokenChar.OPERATOR, ch));
				}
				break;
			default:
				if (ch.matches("[a-zA-z]")) {
					if (this.numberBuffer.size() > 0) {
						tokens = this.clearNumberBuffer(tokens);
						tokens.add(new Token(TokenChar.OPERATOR, "*"));
					}
					this.letterBuffer.add(ch);
				} else if (ch.matches("[0-9]")) {
					this.numberBuffer.add(ch);
				} else if (ch.matches("[.]")) {
					this.numberBuffer.add(ch);
				} else if (ch.matches("[(]")) {

					if (letterBuffer.size() > 0) {
						tokens.add(new Token(TokenChar.FUNCTION,
								letterBuffer.stream().map(x -> x.toString()).collect(Collectors.joining(""))));
						this.letterBuffer = new ArrayList<>();
					} else if (this.numberBuffer.size() > 0) {
						tokens = this.clearNumberBuffer(tokens);
						tokens.add(new Token(TokenChar.OPERATOR, "*"));
					}
					tokens.add(new Token(TokenChar.LEFT_PARENTHESIS, ch));

				} else if (ch.matches("[)]")) {
					tokens = this.clearLetterBuffer(tokens);
					tokens = this.clearNumberBuffer(tokens);
					tokens.add(new Token(TokenChar.RIGHT_PARENTHESIS, ch));
				} else if (ch.matches("[,]")) {
					tokens = this.clearNumberBuffer(tokens);
					tokens = this.clearLetterBuffer(tokens);
					tokens.add(new Token(TokenChar.FUNCTION_ARGS_SEPARATOR, ch));

				}
			}
		}

		if (this.numberBuffer.size() > 0) {
			tokens = this.clearNumberBuffer(tokens);
		}
		if (this.letterBuffer.size() > 0) {
			tokens = this.clearLetterBuffer(tokens);
		}

//		System.out.println("Tokens::"+ tokens.stream().map(x -> x.getValue().toString()).collect(Collectors.joining(",")));
		tokens.forEach(t->{
			System.out.println(t);
		});
		
		return tokens;

	}

	public List<Token> clearNumberBuffer(List<Token> tokens) {
		  if (this.numberBuffer.size() > 0) {
		      tokens.add(
		        new Token(TokenChar.LITERAL, this.numberBuffer.stream().map(x->x.toString()).collect(Collectors.joining(""))));
		      numberBuffer  = new ArrayList<>();
		    }
		  return tokens;
	}

	public List<Token>  clearLetterBuffer(List<Token> tokens) {
		 	Integer l = this.letterBuffer.size();
		    for (int i = 0; i < l; i++) {
		    	tokens.add(new Token(TokenChar.VARIABLE, this.letterBuffer.get(i)));
		      if (i < l - 1) {
		    	  tokens.add(new Token(TokenChar.OPERATOR, "*"));
		      }
		    }
		    this.letterBuffer = new ArrayList<>();
		    return tokens;
	}
	
//	 pushIntoNumberBuffer(numbers: String[]) {
//		    if (numbers.length > 0) {
//		      numbers.forEach(num => {
//		        this.numberBuffer.push(num);
//		      });
//		    }
//		  }
}
