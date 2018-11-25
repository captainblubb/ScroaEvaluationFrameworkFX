package control;


import algorithmns.croa.CROA;
import algorithmns.croa.equations.IEquation;
import algorithmns.croa.equations.Rastrigin;
import algorithmns.croa.equations.Rosenbrock;
import algorithmns.scroa.SCROA;
import gui.GUI;
import javafx.application.Platform;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args){


        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        GUI gui = new GUI();
        IEquation equation = new Rastrigin();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Runnable worker = new CROA(equation,gui,1,cyclicBarrier);
        Runnable worker2 = new SCROA(equation,gui,2,cyclicBarrier);
        executor.execute(worker2);
        executor.execute(worker);

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }

}
