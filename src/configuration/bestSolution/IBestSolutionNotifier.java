package configuration.bestSolution;

public interface IBestSolutionNotifier {

    void register(IBestSolutionListener bestSolutionListener);
    void unregister(IBestSolutionListener bestSolutionListener);
    void notifyBestSolutionChanged();

}
