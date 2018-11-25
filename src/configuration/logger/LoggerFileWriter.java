package configuration.logger;

import algorithmns.croa.models.Point;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class LoggerFileWriter implements ILogger{


    private Logger LOGGER;

    public LoggerFileWriter(String Name,int AlgorithmCounter){
        LOGGER = Logger.getLogger(Name);
        try{

            FileHandler fileLog = new FileHandler(Name+AlgorithmCounter+".log");
            fileLog.setFormatter(new SimpleFormatter());
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileLog);

        }catch (Exception exp){

            System.out.println("Couldnt init logger");
        }


    }

    @Override
    public void logInformation(String information) {
       LOGGER.info(information);
    }

    @Override
    public void logBestSolution(String algorithm, int iteration, Point solution, double PE) {
        LOGGER.info(algorithm+" "+iteration+" : Best solution :"+PE+" at:"+solution.toParseFormat());
    }

}
