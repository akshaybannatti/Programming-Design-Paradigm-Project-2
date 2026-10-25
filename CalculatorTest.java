import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import calculator.Calculator;
import calculator.SimpleCalculator;
import calculator.SmartCalculator;
import org.junit.Test;

/**
 * Tests for abstract class for calculator.
 * It extends this class tests here.
 */
public abstract class CalculatorTest {

  /**
   * To create the calculator instance to test.
   *
   * @return a new instance state in calculator
   */
  protected abstract Calculator createCalculator();

  @Test
  public void testInitialState() {
    Calculator calc = createCalculator();
    assertEquals("", calc.getResult());
  }



  @Test
  public void testSingleDigit() {
    Calculator calc = createCalculator();
    calc = calc.input('5');
    assertEquals("5", calc.getResult());
  }



  @Test
  public void testMultipleDigits() {
    Calculator calc = createCalculator();
    calc = calc.input('3');
    assertEquals("3", calc.getResult());
    calc = calc.input('2');
    assertEquals("32", calc.getResult());
  }



  @Test
  public void testSimpleAddition() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('2').input('4').input('=');
    assertEquals("56", calc.getResult());
  }


  @Test
  public void testSimpleSubtraction() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('0').input('-').input('2').input('0').input('=');
    assertEquals("30", calc.getResult());
  }



  @Test
  public void testSimpleMultiplication() {
    Calculator calc = createCalculator();
    calc = calc.input('6').input('*').input('7').input('=');
    assertEquals("42", calc.getResult());
  }



  @Test
  public void testChainedOperations() {
    Calculator calc = createCalculator();
    calc = calc.input('3').input('2').input('+').input('2').input('4')
        .input('-').input('1').input('0').input('=');
    assertEquals("46", calc.getResult());
  }



  @Test
  public void testResultDisplay() {
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
    calc = calc.input('=');
    assertEquals("56", calc.getResult());
  }



  @Test
  public void testClearOperation() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('5').input('+').input('3').input('C');
    assertEquals("", calc.getResult());
  }



  @Test
  public void testNegativeResult() {
    Calculator calc = createCalculator();
    calc = calc.input('1').input('0').input('-').input('2').input('0').input('=');
    assertEquals("-10", calc.getResult());
  }



  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCharacter() {
    Calculator calc = createCalculator();
    calc.input('a');
  }



  @Test(expected = IllegalArgumentException.class)
  public void testStartWithMultiply() {
    Calculator calc = createCalculator();
    calc.input('*');
  }



  @Test(expected = IllegalArgumentException.class)
  public void testStartWithMinus() {
    Calculator calc = createCalculator();
    calc.input('-');
  }



  @Test
  public void testOperandOverflow() {
    Calculator calc = createCalculator();
    try {
      calc = calc.input('9').input('9').input('9').input('9').input('9')
          .input('9').input('9').input('9').input('9').input('9').input('9');
      fail("Expected IllegalArgumentException for operand overflow");
    } catch (IllegalArgumentException e) {
      e.getMessage();
    }
  }



  @Test
  public void testResultOverflow() {
    Calculator calc = createCalculator();
    calc = calc.input('2').input('1').input('4').input('7').input('4').input('8')
        .input('3').input('6').input('4').input('7')
        .input('+').input('1').input('=');
    assertEquals("0", calc.getResult());
  }



  @Test
  public void testContinueAfterResult() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('+').input('3').input('=');
    assertEquals("8", calc.getResult());
    calc = calc.input('-').input('2').input('=');
    assertEquals("6", calc.getResult());
  }



  @Test
  public void testZeroOperand() {
    Calculator calc = createCalculator();
    calc = calc.input('0').input('+').input('5').input('=');
    assertEquals("5", calc.getResult());
  }



  @Test
  public void testMultiplicationByZero() {
    Calculator calc = createCalculator();
    calc = calc.input('5').input('*').input('0').input('=');
    assertEquals("0", calc.getResult());
  }

  @Test
  public void testLargeNumbers() {
    Calculator calc = createCalculator();
    calc = calc.input('9').input('9').input('9').input('9').input('9')
        .input('+').input('1').input('=');
    assertEquals("100000", calc.getResult());
  }



}