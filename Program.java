
public class Program {

	public static void main(String[] args) {

		String sentence = "The rain in Spain falls mainly on the plain.";
	 	
		IReaderWriterMonitor monitor = new ReadersWritersMonitor();
		IMessage message = new Message();
		
		Writer writer = new Writer(monitor, message, sentence);
		Reader reader = new Reader(monitor, message, "Reader1");
		Reader reader2 = new Reader(monitor, message, "Reader2");
		
		writer.start();
		reader.start();
		reader2.start();
	}

}
