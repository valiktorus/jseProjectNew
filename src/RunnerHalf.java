import by.gsu.epamlab.factory.HalfResultFactory;
import by.gsu.epamlab.factory.ResultFactory;

public class RunnerHalf {
    public static void main(String[] args) {
        final String SOURCE_NAME = "results3";
        ResultFactory resultFactory = new HalfResultFactory();
        RunnerLogic.execute(resultFactory,SOURCE_NAME);
    }
}
