package configuration.equations;

import algorithmns.croa.models.Point;
import org.junit.*;
import static org.junit.Assert.assertArrayEquals;

public class RosenbrockTest {

    Rosenbrock rosenbrock;
    Point Root;

    @Before
    public void setUp() throws Exception {
        rosenbrock = new Rosenbrock();
        Root = new Point(0,0);
    }

    @Test
    public void calculateValue() throws Exception {
        double[] expected = {0.0};
        double[] actual = {rosenbrock.calculateValue(Root)};

        assertArrayEquals(expected, actual,0.000);
    }

    @Test
    public void calculateGrade() {

        double[] expected = {0.0,0.0};
        double[] actual = {rosenbrock.calculateGrade(Root).x,rosenbrock.calculateGrade(Root).y};

        assertArrayEquals(expected, actual,0.000);

    }
}