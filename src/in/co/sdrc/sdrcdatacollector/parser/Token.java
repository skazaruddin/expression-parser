package in.co.sdrc.sdrcdatacollector.parser;

import java.util.HashMap;
import java.util.Map;

public class Token {

	public String value;
	public TokenChar type;
	public Integer arguments = 0;

	Map<String, String> associativityMap = new HashMap<String, String>();
	Map<String, Integer> precedenceMap = new HashMap<String, Integer>();

	Token(TokenChar type, String value) {
		this.type = type;
		this.value = value;

		this.associativityMap.put("^", "right");
		this.associativityMap.put("%", "left");
		this.associativityMap.put("*", "left");
		this.associativityMap.put("/", "left");
		this.associativityMap.put("+", "left");
		this.associativityMap.put("-", "left");
		this.associativityMap.put("<", "left");
		this.associativityMap.put("<=", "left");
		this.associativityMap.put(">", "left");
		this.associativityMap.put(">=", "left");
		this.associativityMap.put("=", "left");

		this.precedenceMap.put("^", 5);
		this.precedenceMap.put("%", 4);
		this.precedenceMap.put("*", 3);
		this.precedenceMap.put("/", 3);
		this.precedenceMap.put("+", 2);
		this.precedenceMap.put("-", 2);

	}

	public Integer precedence() {
		return this.precedenceMap.get(this.type.toString());
	}

	public String associativity() {
		return this.associativityMap.get(this.type.toString());
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public TokenChar getType() {
		return type;
	}

	public void setType(TokenChar type) {
		this.type = type;
	}
	
	

	public Integer getArguments() {
		return arguments;
	}

	public void setArguments(Integer arguments) {
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		return "Token [type=" + type +  ", arguments=" + arguments +", value=" + value  + "]";
	}

	
}
