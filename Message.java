
public class Message implements IMessage {

	private String msg = ""; 
	
	@Override
	public void write(String word) {

		msg += " "+ word;
	}

	@Override
	public String read() {

		return msg;
	}
}
