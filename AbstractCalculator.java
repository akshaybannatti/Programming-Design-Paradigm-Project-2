package calculator;

/**
 * Abstract class.
 * Common functionality for calculator operations.
 * Functions immutability by returning new value.
 */
public abstract class AbstractCalculator implements Calculator {
  protected final String inputbuffer;
  protected final int memoryValue;
  protected final char pendingoperator;
  protected final boolean operatorActive;
  protected final boolean showingresult;

  /**
   * Constructor for making a calculator with a particular state.
   *
   * @param inputbuffer current input
   * @param memoryValue value stored result
   * @param pendingoperator active operator waiting for the one to come after this
   * @param operatorActive true if it has entered
   * @param showingresult true if it shows result
   */
  protected AbstractCalculator(String inputbuffer, int memoryValue, char pendingoperator,
                               boolean operatorActive, boolean showingresult) {
    this.inputbuffer = inputbuffer;
    this.memoryValue = memoryValue;
    this.pendingoperator = pendingoperator;
    this.operatorActive = operatorActive;
    this.showingresult = showingresult;
  }

  /**
   * Default constructor for starting initial.
   */
  protected AbstractCalculator() {
    this("", 0, '\0', false, false);
  }

  /**
   * To confirm and check for valid symbol.
   *
   * @param c to validate
   * @return true if it's valid
   */
  protected boolean isValidSymbol(char c) {
    return (c >= '0' && c <= '9') || c == '+' || c == '-' || c == '*' || c == '=' || c == 'C';
  }

  /**
   * To confirm digit is character, it checks.
   *
   * @param c check the character
   * @return true if it's a digit number
   */
  protected boolean isDigit(char c) {
    return c >= '0' && c <= '9';
  }

  /**
   * Checks the operator.
   *
   * @param c to check
   * @return true if is operator
   */
  protected boolean isOperator(char c) {
    return c == '+' || c == '-' || c == '*';
  }

  /**
   * Converts input to integer.
   *
   * @return the integer value
   * @throws IllegalArgumentException if the buffer cannot be done
   */
  protected int getInputAsNumber() {
    if (inputbuffer.isEmpty()) {
      return 0;
    }
    try {
      return Integer.parseInt(inputbuffer);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid number format");
    }
  }

  /**
   * Performs calculation.
   *
   * @param left left operator
   * @param right right operator
   * @param op operator
   * @return the result or 0
   */
  protected int calculate(int left, int right, char op) {
    try {
      long result;
      switch (op) {
        case '+':
          result = (long) left + right;
          break;
        case '-':
          result = (long) left - right;
          break;
        case '*':
          result = (long) left * right;
          break;
        default:
          throw new IllegalArgumentException("Invalid operator");
      }
      if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
        return 0;
      }
      return (int) result;
    } catch (Exception e) {
      return 0;
    }
  }

  @Override
  public Calculator input(char c) throws IllegalArgumentException {
    if (!isValidSymbol(c)) {
      throw new IllegalArgumentException("Invalid input character: " + c);
    }
    if (c == 'C') {
      return resetEngine();
    }
    if (isDigit(c)) {
      return processDigit(c);
    }
    if (isOperator(c)) {
      return processoperator(c);
    }
    if (c == '=') {
      return processequals();
    }
    throw new IllegalArgumentException("Unexpected input: " + c);
  }

  @Override
  public String getResult() {
    if (inputbuffer.isEmpty() && memoryValue == 0 && !operatorActive && !showingresult) {
      return "";
    }
    if (showingresult) {
      return String.valueOf(memoryValue);
    }
    if (operatorActive && inputbuffer.equals("0")) {
      return memoryValue + String.valueOf(pendingoperator);
    }
    if (operatorActive) {
      if (inputbuffer.isEmpty()) {
        return memoryValue + String.valueOf(pendingoperator);
      }
      return memoryValue + String.valueOf(pendingoperator) + inputbuffer;
    }
    return inputbuffer.isEmpty() ? String.valueOf(memoryValue) : inputbuffer;
  }

  /**
   * Digital input works here.
   *
   * @param digit digit to work
   * @return Calculator with updated state
   */
  protected Calculator processDigit(char digit) {
    if (showingresult) {
      return copyWithState(String.valueOf(digit), 0, '\0',
          false, false);
    }
    if (operatorActive && inputbuffer.equals("0")) {
      return copyWithState(String.valueOf(digit), memoryValue, pendingoperator, true,
          false);
    }
    String newBuffer = inputbuffer + digit;
    try {
      Integer.parseInt(newBuffer);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Number too large");
    }
    return copyWithState(newBuffer, memoryValue, pendingoperator, operatorActive,
        false);
  }

  /**
   * Abstract method to reset.
   *
   * @return Calculator with initial state
   */
  protected abstract Calculator resetEngine();

  /**
   * Abstract method to create a copy.
   *
   * @param inputBuffer buffer put here
   * @param memoryValue memory added
   * @param pendingOperator remaining operator
   * @param operatorActive operator active
   * @param showingResult result flag
   * @return A particular state in calculator
   */
  protected abstract Calculator copyWithState(String inputBuffer, int memoryValue,
                                              char pendingOperator, boolean operatorActive,
                                              boolean showingResult);

  /**
   * Abstract method is used here for the operator.
   *
   * @param symbol the operator symbol
   * @return Calculator with updated state.
   */
  protected abstract Calculator processoperator(char symbol);

  /**
   * Abstract method used for equal input used here.
   *
   * @return  New Calculator which give us the result
   */
  protected abstract Calculator processequals();
}