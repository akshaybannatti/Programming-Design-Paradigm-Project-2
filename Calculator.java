package calculator;

/**
 * Created an interface for the Calculator which will take the input
 * It will take input one at a time for calculator as characters.
 * getResult will get us the answer.
 *
 */
public interface Calculator {
  /**
   * Single character inputs and returns updated calculator.
   *
   * @param c the input character
   * @return new calculatorException.
   * @throws IllegalArgumentException creates invalid sequence
   */

  Calculator input(char c)
      throws IllegalArgumentException;

  /**
   * Returns the display info.
   *
   * @return the value in display
   */
  String getResult();
}
