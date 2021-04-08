import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    private ArrayBlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;

    public Server(int maxTasksPerServer){
        this.tasks=new ArrayBlockingQueue<Task>(maxTasksPerServer);
        this.waitingPeriod=new AtomicInteger(0);
    }

    public void addTask(Task newTask){
        tasks.add(newTask);
        newTask.setFinishTime(newTask.getArrivalTime()+ newTask.getProcessingTime()+waitingPeriod.get());
        waitingPeriod.addAndGet(newTask.getProcessingTime());
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(1000);
                if(tasks.peek()!=null) {
                    waitingPeriod.decrementAndGet();
                    tasks.peek().setProcessingTime(tasks.peek().getProcessingTime() - 1);
                    if (tasks.peek().getProcessingTime() == 0) {
                        tasks.remove(tasks.peek());
                    }
                }
            }catch (Exception ex){
                break;
            }
        }
    }

    public ArrayBlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    @Override
    public String toString() {
       String s;
       s="";
       for(Task t: tasks){
           s+="("+t.getId()+","+t.getArrivalTime()+","+t.getProcessingTime()+") ";
       }
       return s;
    }
}
