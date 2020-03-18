
public class Writer extends Thread {

	private IReaderWriterMonitor monitor;
	private IMessage message;
	private String sentence;	
	
	public Writer(IReaderWriterMonitor monitor, IMessage message, String sentence) {
		super("Writer");

		this.monitor = monitor;
		this.message = message;
		this.sentence = sentence;
	}

	@Override
	public void run() {

		try {
			
			String[] words = sentence.split(" ");
			
			for(int i = 0; i < words.length; i++) {
			
				Thread.sleep((long) (Math.random()*3001));
				
				monitor.beginWrite();
				String word = words[i];
				message.write(word);		
				System.out.println(getName() + " wrote: "+ word);
				monitor.endWrite();
			}			
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
