
public interface IReaderWriterMonitor {
	public void beginWrite() throws InterruptedException;
	public void endWrite();
	public void beginRead() throws InterruptedException;
	public void endRead();
}
