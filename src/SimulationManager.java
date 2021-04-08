import javax.swing.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimulationManager implements Runnable {
    public int timeLimit = 40; //max processing time
    public int maxProcessingTime = 5;
    public int minProcessingTime = 2;
    public int minArrivalTime = 2;
    public int maxArrivalTime = 10;
    public int numberOfServers = 3;
    public int numberOfClients = 200;
    public double avrageServiceTime=0;
    public double avrageWaitingTime=0;
    public int peakHour=0;
    public int nrClientsPeakHour=0;
    public JTextArea text;

    public FileWriter myWriter;
    private Scheduler scheduler;
    private List<Task> generatedTasks;

    public SimulationManager(int nrClients, int nrServers, int timeLimit, int minArrivalTime, int maxArrivalTime, int minProcessingTime, int maxProcessingTime, JTextArea text){
        this.numberOfServers=nrServers;
        this.numberOfClients=nrClients;
        this.timeLimit=timeLimit;
        this.minArrivalTime=minArrivalTime;
        this.maxArrivalTime=maxArrivalTime;
        this.minProcessingTime=minProcessingTime;
        this.maxProcessingTime=maxProcessingTime;
        this.text=text;
        generateNRandomTasks();
        scheduler=new Scheduler(numberOfServers,100);
    }

    public int randomArrivingTime(){
        int arrivalTime=(int)Math.floor(Math.random()*(maxArrivalTime-minArrivalTime+1)+minArrivalTime);
        return arrivalTime;
    }

    public int randomProcessingTime(){
        int processingTime=(int)Math.floor(Math.random()*(maxProcessingTime-minProcessingTime+1)+minProcessingTime);
        return processingTime;
    }

    private void generateNRandomTasks(){
        generatedTasks = Collections.synchronizedList(new ArrayList<Task>());
        int i=0;
        while(i<numberOfClients){
            int processingT=randomProcessingTime();
            Task task=new Task(i+1,randomArrivingTime(),processingT);
            generatedTasks.add(task);
            avrageServiceTime+=(double)processingT;
            i++;
        }
        avrageServiceTime=avrageServiceTime/(double)numberOfClients;
        Collections.sort(generatedTasks,null);
    }

    public void afisare(FileWriter myWriter){
        for(int i=0;i<generatedTasks.size();i++){
            try {
                myWriter.write(generatedTasks.get(i).toString()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void averageWaitig(){
        Server server=scheduler.getServers().get(0);
        for(Server s: scheduler.getServers()){
            if(s.getWaitingPeriod().intValue()< server.getWaitingPeriod().intValue() && scheduler.getMaxTasksPerServer()> s.getTasks().size()){
                server=s;
            }
        }
        if(scheduler.getMaxTasksPerServer()>server.getTasks().size())
            avrageWaitingTime=avrageWaitingTime+server.getWaitingPeriod().intValue();
    }

    public void calculatePeakHour(int currentTime){
        int nr=0;
        for(Server s: scheduler.getServers()){
            nr=nr+s.getTasks().size();
        }
        if(nr>nrClientsPeakHour) {
            nrClientsPeakHour=nr;
            peakHour = currentTime;
        }
    }

    public String timeAndWaitingClients(int currenttime){
        String s=new String("Time "+currenttime+"\n");
        s=s+"Waiting clients: ";
        for(Task t: generatedTasks){
            s=s+t.toString();
        }
        s=s+"\n";
        return s;
    }

    public void concatenare(int currentTime){
        String s=timeAndWaitingClients(currentTime);
        double aux=avrageWaitingTime;
        int nrServer=1;
        for(Server server: scheduler.getServers()){
            s=s+"Queue "+nrServer+": "+server.toString()+"\n";
            nrServer+=1;
        }
        if(currentTime==timeLimit-1){
            aux=aux/(double)numberOfClients;
            s=s+"Average Service Time is: "+avrageServiceTime+"\n"+"Average Waiting Time is: "+aux+"\n"+"Peak Hour is: "+peakHour;
        }
        text.setText(s);
    }

    @Override
    public void run() {
        try{ myWriter=new FileWriter("output.txt"); }catch (IOException e){ System.out.println("An error occured"); }
        afisare(myWriter);
        int currentTime=0;
        while (currentTime<timeLimit){
            for(int i=0; i<generatedTasks.size(); i++){
                if(generatedTasks.get(i).getArrivalTime()==currentTime){
                    averageWaitig();
                    calculatePeakHour(currentTime);
                    scheduler.dispatchTask(generatedTasks.get(i));
                    generatedTasks.remove(generatedTasks.get(i--));
                }
            }
            try{ myWriter.write("\n"+timeAndWaitingClients(currentTime));} catch (IOException e){}
            int nrServer=1;
            for(Server server: scheduler.getServers()){
                try{ myWriter.write("Queue "+nrServer+": "+server.toString()+"\n");} catch (IOException e){}
                nrServer+=1;
            }
            concatenare(currentTime);
            currentTime+=1;
            try{ Thread.sleep(1000); }catch (Exception e){ break; }
        }
        avrageWaitingTime=avrageWaitingTime/(double)numberOfClients;
        try{
            myWriter.write("Average Waiting Time is: "+avrageWaitingTime+"\n"+"Average Service Time is: "+avrageServiceTime+"\n"+"Peak Hour is: "+peakHour+"\n");
        } catch (IOException e){}
        try { myWriter.close(); } catch (IOException e) { e.printStackTrace(); }
    }
}
