package unibs.dii.taskscheduling.model;

import com.leansoft.bigqueue.BigQueueImpl;
import unibs.dii.taskscheduling.services.BooleanConverter;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Random;

public class BufferedBigQueue implements Closeable {
    private final LinkedList<boolean[]> buffer;
    private final BigQueueImpl bigQueue;
    private final long bufferSize=500000;
    private final BooleanConverter booleanConverter;
    boolean canEnqueueToBuffer=true;

    public BufferedBigQueue() throws IOException {
        Path path = Files.createTempDirectory("queue-");
        bigQueue = new BigQueueImpl(path.toString(), String.valueOf(new Random().nextGaussian()));
        buffer=new LinkedList<>();
        booleanConverter=new BooleanConverter();
    }

    @Override
    public void close() throws IOException {
        if(bigQueue!=null)
            bigQueue.close();
    }

    public void enqueue(boolean[] booleans) throws IOException {
        if(canEnqueueToBuffer){
            buffer.addLast(booleans);
            if(buffer.size()>bufferSize)
                canEnqueueToBuffer=false;
        }
        else
            bigQueue.enqueue(booleanConverter.toByteArray(booleans));
    }

    public boolean isEmpty(){
        return buffer.isEmpty()&&bigQueue.isEmpty();
    }

    public boolean[] dequeue() throws IOException {
        if(buffer.isEmpty())
            loadBuffer();
        return buffer.pollFirst();
    }

    private void loadBuffer() throws IOException {
        while(!bigQueue.isEmpty()||buffer.size()<bufferSize){
            final boolean[] booleans = booleanConverter.fromByteArray(bigQueue.dequeue());
            buffer.addLast(booleans);
        }
    }

    public long size() {
        return buffer.size()+bigQueue.size();
    }
}
