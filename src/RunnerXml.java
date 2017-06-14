import by.gsu.epamlab.factory.DecimalResultFactory;
import by.gsu.epamlab.factory.ResultFactory;

public class RunnerXml {
    public static void main(String[] args) {
      final String SOURCE_NAME = "results";
        ResultFactory resultFactory = new DecimalResultFactory();
        RunnerLogic.execute(resultFactory, SOURCE_NAME);
    }
}
