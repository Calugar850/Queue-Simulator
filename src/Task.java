public class Task implements Comparable<Task> {
    private int id;
    private int arrivalTime;
    private int processingTime;
    private int finishTime;

    public Task(int id, int arrivalTime, int processingTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        return "(" + id +
                "," + arrivalTime +
                "," + processingTime+
                ") ";
    }

    @Override
    public int compareTo(Task task) {
       return this.arrivalTime-task.getArrivalTime();
    }
}
