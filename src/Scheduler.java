import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers=0;
    private int maxTasksPerServer=0;

    public Scheduler(int nrServers, int nrTasks){
        this.maxNoServers=nrServers;
        this.maxTasksPerServer=nrTasks;
        servers= Collections.synchronizedList(new ArrayList<Server>());
        for(int i=0;i<nrServers;i++){
            Server s=new Server(100);
            servers.add(s);
            Thread thread=new Thread(servers.get(i));
            thread.start();
        }
    }

    public void dispatchTask(Task t){
        int waitingTime=99;
        int min=0;
        for(int i=0;i<maxNoServers;i++){
            AtomicInteger aux=servers.get(i).getWaitingPeriod();
            int aux2= aux.get();
            if( aux2 < waitingTime){
                waitingTime=aux2;
                min=i;
            }
            if(waitingTime==0)
                break;
        }
        servers.get(min).addTask(t);
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(ArrayList<Server> servers) {
        this.servers = servers;
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }

    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public void setMaxTasksPerServer(int maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
    }
}
