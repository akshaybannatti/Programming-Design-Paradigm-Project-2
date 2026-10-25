import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import calculator.Calculator;
import calculator.SmartCalculator;
import org.junit.Test;

/**
 * Tests for SmartCalculator.
 * Tests for smart features for equals, missing operands, and operator .
 */
public class SmartCalculatorTest extends CalculatorTest {

  @Override
  protected Calculator createCalculator() {
    return new SmartCalculator();
  }



  @Test
  public void testRepeatedEquals() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('2').input('4').input('=');
    assertEquals("56", calc.getResult());
    calc = calc.input('=');
    assertEquals("80", calc.getResult());
    calc = calc.input('=');
    assertEquals("104", calc.getResult());
  }



  @Test
  public void testMissingSecondOperand() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('=');
    assertEquals("64", calc.getResult());
  }



  @Test
  public void testMissingSecondOperandRepeated() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('=');
    assertEquals("64", calc.getResult());
    calc = calc.input('=');
    assertEquals("96", calc.getResult());
    calc = calc.input('=');
    assertEquals("128", calc.getResult());
  }



  @Test
  public void testConsecutiveOperators() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('-').input('2').input('4').input('=');
    assertEquals("8", calc.getResult());
  }



  @Test
  public void testLeadingPlus() {
    Calculator calc = createCalculator();
    calc = calc.input('+').input('3').input('2').input('-').input('2').input('4').input('=');
    assertEquals("8", calc.getResult());
  }



  @Test(expected = IllegalArgumentException.class)
  public void testLeadingMinusInvalid() {
    Calculator calc = createCalculator();
    calc.input('-');
  }



  @Test(expected = IllegalArgumentException.class)
  public void testLeadingMultiplyInvalid() {
    Calculator calc = createCalculator();
    calc.input('*');
  }



  @Test
  public void testRepeatedEqualsWithSubtraction() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('0').input('-').input('1').input('0').input('=');
    assertEquals("40", calc.getResult());
    calc = calc.input('=');
    assertEquals("30", calc.getResult());
    calc = calc.input('=');
    assertEquals("20", calc.getResult());
  }





  @Test
  public void testRepeatedEqualsWithMultiplication() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('*').input('2').input('=');
    assertEquals("10", calc.getResult());
    calc = calc.input('=');
    assertEquals("20", calc.getResult());
    calc = calc.input('=');
    assertEquals("40", calc.getResult());
  }




  @Test
  public void testMultipleOperatorReplacement() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('+').input('-').input('*')
        .input('5').input('=');
    assertEquals("50", calc.getResult());
  }




  @Test
  public void testLeadingPlusIgnored() {
    Calculator calc = createCalculator();
    calc = calc.input('+').input('5').input('+').input('3').input('=');
    assertEquals("8", calc.getResult());
  }




  @Test
  public void testConsecutiveOperatorsMultiple() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('+').input('-')
        .input('5').input('=');
    assertEquals("5", calc.getResult());
  }




  @Test
  public void testMissingOperandWithMultiplication() {
    Calculator calc = createCalculator();
    calc = calc.input('7').input('*').input('=');
    assertEquals("49", calc.getResult());
    calc = calc.input('=');
    assertEquals("343", calc.getResult());
  }




  @Test
  public void testChainedSmartOperations() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('+').input('5').input('=');
    assertEquals("15", calc.getResult());
    calc = calc.input('*').input('=');
    assertEquals("225", calc.getResult());
  }



  @Test
  public void testNewOperationAfterRepeatedEquals() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('+').input('3').input('=');
    assertEquals("8", calc.getResult());
    calc = calc.input('=');
    assertEquals("11", calc.getResult());
    calc = calc.input('+').input('2').input('=');
    assertEquals("13", calc.getResult());
  }




  @Test
  public void testDigitAfterResult() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('+').input('3').input('=');
    assertEquals("8", calc.getResult());
    calc = calc.input('2');
    assertEquals("2", calc.getResult());
  }




  @Test
  public void testComplexSmartSequence() {
    Calculator calc = createCalculator();
    calc = calc.input('+').input('1').input('0').input('+')
        .input('5').input('=');
    assertEquals("15", calc.getResult());
    calc = calc.input('=');
    assertEquals("20", calc.getResult());
    calc = calc.input('=');
    assertEquals("25", calc.getResult());
  }




  @Test
  public void testOperatorReplacementThenEquals() {
    Calculator calc = createCalculator();
    calc = calc.input('8').input('+').input('*').input('=');
    assertEquals("64", calc.getResult());
  }




  @Test
  public void testZeroWithSmartFeatures() {
    Calculator calc = createCalculator();
    calc = calc.input('0').input('+').input('=');
    assertEquals("0", calc.getResult());
    calc = calc.input('=');
    assertEquals("0", calc.getResult());
  }



  @Test
  public void testNegativeResultsWithRepeat() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('-').input('1').input('5').input('=');
    assertEquals("-5", calc.getResult());
    calc = calc.input('=');
    assertEquals("-20", calc.getResult());
  }


  @Test
  public void testLeadingPlusMultipleTimes() {
    Calculator calc = createCalculator();
    calc = calc.input('+').input('+').input('+').input('5');
    try {
      calc = calc.input('=');
      fail("Expected IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      e.getMessage();
    }
  }



  @Test
  public void testBackwardCompatibility() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('2').input('4')
        .input('-').input('1').input('0').input('=');
    assertEquals("46", calc.getResult());
  }



  @Test
  public void testClearWithSmartFeatures() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('+').input('=').input('C');
    assertEquals("", calc.getResult());
  }



  @Test
  public void testImmutabilityWithSmartFeatures() {
    Calculator calc1 = createCalculator();
    Calculator calc2 = calc1.input('5').input('+').input('=');
    assertEquals("10", calc2.getResult());
    Calculator calc3 = calc2.input('=');
    assertEquals("10", calc2.getResult());
    assertEquals("15", calc3.getResult());
  }




  @Test
  public void testOverflowWithRepeat() {
    Calculator calc = createCalculator();
    calc = calc.input('2').input('1').input('4').input('7').input('4').input('8')
        .input('3').input('6').input('4').input('7')
        .input('+').input('1').input('=');
    assertEquals("0", calc.getResult());
    calc = calc.input('=');
    assertEquals("1", calc.getResult());
  }


}

