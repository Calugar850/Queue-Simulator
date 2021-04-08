import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {
    View view;
    SimulationManager simulationManager;
    Thread t;

    public Controller(View v){
        this.view = v;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedButton=0;
        if(view.getButton1().isSelected()) selectedButton=1;
        String clienti=view.getTextField1().getText();
        String cozi=view.getTextField2().getText();
        String intervalMax=view.getTextField3().getText();
        String tArrivalMin=view.getTextField4().getText();
        String tArrivalMax=view.getTextField5().getText();
        String tServiceMin=view.getTextField6().getText();
        String tServiceMax=view.getTextField7().getText();
        int nrClienti=Integer.parseInt(clienti);
        int nrCozi=Integer.parseInt(cozi);
        int timeMax=Integer.parseInt(intervalMax);
        int timpMinSosire=Integer.parseInt(tArrivalMin);
        int timpMaxSosire=Integer.parseInt(tArrivalMax);
        int timeMinServire=Integer.parseInt(tServiceMin);
        int timeMaxServire=Integer.parseInt(tServiceMax);
        if (selectedButton==1){
                simulationManager=new SimulationManager(nrClienti,nrCozi,timeMax,timpMinSosire,timpMaxSosire,timeMinServire,timeMaxServire, view.getText());
                t=new Thread(simulationManager);
                t.start();
        }
    }
}
