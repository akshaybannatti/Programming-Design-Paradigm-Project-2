import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import calculator.Calculator;
import calculator.SimpleCalculator;
import org.junit.Test;

/**
 * Test suite for SimpleCalculator implementation.
 * Tests specific behaviors of the simple calculator.
 */
public class SimpleCalculatorTest extends CalculatorTest {

  @Override
  protected Calculator createCalculator() {
    return new SimpleCalculator();
  }

  @Test
  public void testMultipleEqualsReturnSameResult() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('2').input('4').input('=');
    assertEquals("56", calc.getResult());
    calc = calc.input('=');
    assertEquals("56", calc.getResult());
    calc = calc.input('=').input('=');
    assertEquals("56", calc.getResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTwoConsecutiveOperators() {
    Calculator calc = createCalculator();
    calc.input('5').input('+').input('-');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingSecondOperand() {
    Calculator calc = createCalculator();
    calc.input('5').input('+').input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMissingOperator() {
    Calculator calc = createCalculator();
    calc.input('5').input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartWithEquals() {
    Calculator calc = createCalculator();
    calc.input('=');
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOperatorWithoutFirstOperand() {
    Calculator calc = createCalculator();
    calc.input('+');
  }

  @Test
  public void testComplexChain() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('+').input('5')
        .input('*').input('2').input('=');
    assertEquals("30", calc.getResult());
  }

  @Test
  public void testProgressiveDisplay() {
    Calculator calc = createCalculator();
    calc = calc.input('3');
    assertEquals("3", calc.getResult());
    calc = calc.input('2');
    assertEquals("32", calc.getResult());
    calc = calc.input('+');
    assertEquals("32+", calc.getResult());
    calc = calc.input('2');
    assertEquals("32+2", calc.getResult());
    calc = calc.input('4');
    assertEquals("32+24", calc.getResult());
    calc = calc.input('-');
    assertEquals("56-", calc.getResult());
    calc = calc.input('1');
    assertEquals("56-1", calc.getResult());
    calc = calc.input('0');
    assertEquals("56-10", calc.getResult());
    calc = calc.input('=');
    assertEquals("46", calc.getResult());
  }

  @Test
  public void testClearMidOperation() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('+').input('3').input('C');
    assertEquals("", calc.getResult());
    calc = calc.input('1').input('0').input('-').input('5').input('=');
    assertEquals("5", calc.getResult());
  }

  @Test
  public void testImmutability() {
    Calculator calc1 = createCalculator();
    Calculator calc2 = calc1.input('5');
    assertEquals("", calc1.getResult());
    assertEquals("5", calc2.getResult());
    Calculator calc3 = calc2.input('+');
    assertEquals("5", calc2.getResult());
    assertEquals("5+", calc3.getResult());
  }


  @Test
  public void testSubtractionToNegative() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('-').input('1').input('0').input('=');
    assertEquals("-5", calc.getResult());
  }



  @Test
  public void testMultipleOperationsSequence() {
    Calculator calc = createCalculator();
    calc = calc.input('2').input('+').input('3').input('=');
    assertEquals("5", calc.getResult());
    calc = calc.input('*').input('4').input('=');
    assertEquals("20", calc.getResult());
    calc = calc.input('-').input('5').input('=');
    assertEquals("15", calc.getResult());
  }


  @Test
  public void testSingleDigitOperations() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('+').input('3').input('=');
    assertEquals("8", calc.getResult());
  }



  @Test
  public void testZeroResult() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('-').input('5').input('=');
    assertEquals("0", calc.getResult());
  }


  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSequenceOperatorFirst() {
    Calculator calc = createCalculator();
    calc.input('*').input('5');
  }



  @Test
  public void testLongNumber() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('2').input('3').input('4').input('5')
        .input('+').input('5').input('=');
    assertEquals("12350", calc.getResult());
  }



  @Test
  public void testNegativeOverflow() {
    Calculator calc = createCalculator();
    calc = calc.input('2').input('1').input('4').input('7').input('4').input('8')
        .input('3').input('6').input('4').input('7')
        .input('*').input('2').input('=');
    assertEquals("0", calc.getResult());
  }



  @Test
  public void testMultiplicationOverflow() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('0').input('0').input('0')
        .input('0').input('0').input('0')
        .input('*').input('1').input('0').input('0').input('0')
        .input('0').input('0').input('0').input('=');
    assertEquals("0", calc.getResult());
  }



}