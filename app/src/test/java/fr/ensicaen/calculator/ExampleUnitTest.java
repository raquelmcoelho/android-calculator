package fr.ensicaen.calculator;

  import org.junit.Test;

  import static org.junit.Assert.*;

  /**
   * Example local unit test, which will execute on the development machine (host).
   *
   * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
   */
  public class ExampleUnitTest {
      @Test
      public void addition_isCorrect() {
//          Expression e = new Expression("(1+3)(7-4)");
//          mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());
//          System.out.println("Res: " + e.getExpressionString() + " = " + e.calculate());

          assertEquals(4, 2 + 2);
      }
  }