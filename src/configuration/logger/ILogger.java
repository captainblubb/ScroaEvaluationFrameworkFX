package configuration.logger;

import algorithmns.croa.models.Point;

public interface ILogger {
    void logInformation(String information);
    void logBestSolution(String algorithm, int iteration, Point solution, double PE);
}
