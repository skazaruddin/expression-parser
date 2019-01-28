package in.co.lifepartner.parser;

import java.util.List;
import java.util.Stack;

public class InfixToPrefixTransformer {

	public List<Token> transform(List<Token> tokens) {
		Stack<Token> opStack = new Stack<Token>();
		Stack<Token> outputQueue = new Stack<Token>();
		Stack<Token> wereValuesStack = new Stack<Token>();
		Stack<Integer> argStack = new Stack<Integer>();

		for (int i = 0; i < tokens.size(); i++) {
			Token token = tokens.get(i);
			switch (token.getType()) {
			case LITERAL:
				outputQueue.push(token);
				if(!wereValuesStack.isEmpty() ) {
					wereValuesStack.pop();
					wereValuesStack.push(new Token(TokenChar.TRUE, "true"));
				}
				break;
			case FUNCTION:
				opStack.push(token);
				argStack.push(0);
				Stack<Token> temwereValuesStack = new Stack<Token>();
				if(!wereValuesStack.isEmpty() ) {
					wereValuesStack.pop();
					temwereValuesStack.push(new Token(TokenChar.TRUE, "true"));
				}
				wereValuesStack.addAll(temwereValuesStack) ;
				wereValuesStack.push(new Token(TokenChar.FALSE, "false"));
				break;
			case FUNCTION_ARGS_SEPARATOR:
				while (!opStack.isEmpty() && !(opStack.peek().getType() == TokenChar.LEFT_PARENTHESIS)) {
					outputQueue.push(opStack.pop());
				}
				if(wereValuesStack.pop().type == TokenChar.TRUE) {
					 Integer argsc = argStack.pop();
					 argsc++;
					 argStack.push(argsc);
				}
				wereValuesStack.push(new Token(TokenChar.FALSE, "false"));
				break;
			case LEFT_PARENTHESIS:
				opStack.push(token);
				break;
			
			case OPERATOR:
				break;
			case RESULT:
				break;
			case RIGHT_PARENTHESIS:
				while (!opStack.isEmpty() && opStack.peek().getType()==TokenChar.OPERATOR && opStack.peek().getType() != TokenChar.LEFT_PARENTHESIS) {
					outputQueue.push(opStack.pop());
				}
				opStack.pop();
				// If the token at the top of the stack is a function token, pop it onto the
				// output queue.
				if (!opStack.empty() && opStack.peek().type == TokenChar.FUNCTION) {
					// outputQueue.push(opStack.pop());
					Token function = opStack.pop();
					Integer argsCount = argStack.pop();
					Token hasValues = wereValuesStack.pop();
					if (hasValues.getType() == TokenChar.TRUE) {
						argsCount++;
					}
					function.arguments = argsCount;
					outputQueue.push(function);
				}
				break;
			case VARIABLE:
				break;
			default:
				break;

			}
			System.out.println("-----------------");
			System.out.println("Token-" + token);
			System.out.println("OPSTACK:" + opStack);
			System.out.println("QUEUE:" + outputQueue);
			System.out.println("Were Values:" + wereValuesStack);
			System.out.println("Args:" + argStack);
		}
		while (!opStack.isEmpty()) {
			outputQueue.push(opStack.pop());

		}
		 System.out.println("-----------------");
		 outputQueue.forEach(e->{
		 System.out.println(e);
		 });
		return outputQueue;
	}

}
