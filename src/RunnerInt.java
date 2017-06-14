import by.gsu.epamlab.factory.ResultFactory;

public class RunnerInt {
    public static void main(String[] args) {
        final String SOURCE_NAME = "results";
        ResultFactory resultFactory = new ResultFactory();
        RunnerLogic.execute(resultFactory, SOURCE_NAME);
    }
}
