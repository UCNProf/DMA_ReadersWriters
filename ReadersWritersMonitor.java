import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * If we consider access to a shared resource the objectives can be:
 * - Readers can access the shared resource only when there are no writers.
 * - Writers can access the shared resource only when there are no readers or writers.
 * - Only one thread can manipulate the state variables at a time.
 * 
 * */
public class ReadersWritersMonitor implements IReaderWriterMonitor {

	private int readersCount = 0;
	private int writersCount = 0;
	
	private int waitingReaders = 0;
	private int waitingWriters = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition canRead = lock.newCondition();
	private Condition canWrite = lock.newCondition(); 
	
	// wait until there are no active readers or writers
	@Override
	public void beginWrite() throws InterruptedException {		
		lock.lock();
		// A writer can enter if there are no other active writers and no readers are waiting 
		if(writersCount == 1 || readersCount > 0) {
			++waitingWriters;
			canWrite.wait();
			--waitingWriters;
		}
		
		writersCount = 1;
		lock.unlock();
	}
	
	// check out and wake a waiting writer or readers
	@Override
	public void endWrite() {
		lock.lock();
		writersCount = 0;
		
		// Checks to see if any readers are waiting
		if(waitingReaders > 0) {
			canRead.signal();
		}
		else {
			canWrite.signal();
		}
		lock.unlock();
	}

	// wait until there are no writers
	@Override
	public void beginRead() throws InterruptedException {
		lock.lock();
		// A reader can enter if there are no writers active or waiting, 
		// so we can have many readers active all at once
		if(writersCount == 1 || waitingWriters > 1) {
			++waitingReaders;
			canRead.wait();
			--waitingReaders;
		}
		
		++readersCount;
		canRead.signal();
		lock.unlock();
	}

	// check out and wake a waiting writer
	@Override
	public void endRead() {
		lock.lock();
		// When a reader finishes, if it was the last reader, it lets a writer in (if any is there).
		if(--readersCount == 0) {
			canWrite.signal();
		}
		lock.unlock();
	}

}
