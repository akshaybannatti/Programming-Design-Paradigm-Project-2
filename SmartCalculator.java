package calculator;

/**
 * Smart calculator which extends SimpleCalculator.
 * This does input patterns similar like the various old OS calculator.
 */
public class SmartCalculator extends AbstractCalculator {

  private final int lastOperand;
  private final char lastOperator;

  /**
   * Created SmartCalculator starting state.
   */
  public SmartCalculator() {
    super();
    this.lastOperand = 0;
    this.lastOperator = '\0';
  }

  /**
   * Private constructor for creating specific state inside the calculator.
   *
   * @param inputbuffer current value
   * @param memoryValue result kept
   * @param pendingoperator active operator
   * @param operatorActive true if is here
   * @param showingresult true if showing final result in calculator
   * @param lastOperand last operand for calculation
   * @param lastOperator last operator for calculation
   */
  private SmartCalculator(String inputbuffer, int memoryValue, char pendingoperator,
                          boolean operatorActive, boolean showingresult,
                          int lastOperand, char lastOperator) {
    super(inputbuffer, memoryValue, pendingoperator, operatorActive, showingresult);
    this.lastOperand = lastOperand;
    this.lastOperator = lastOperator;
  }

  @Override
  protected Calculator resetEngine() {
    return new SmartCalculator();
  }

  @Override
  protected Calculator copyWithState(String inputBuffer, int memoryValue, char pendingOperator,
                                     boolean operatorActive, boolean showingResult) {
    return new SmartCalculator(inputBuffer, memoryValue, pendingOperator, operatorActive,
        showingResult, this.lastOperand, this.lastOperator);
  }

  /**
   * Creates a copy for all the functions of calculator including last operation showed.
   *
   * @param inputBuffer for the buffer input
   * @param memoryValue new value for the memory
   * @param pendingOperator remaining operator
   * @param operatorActive active operator
   * @param showingResult result it shows.
   * @param lastOperand for repeated operands
   * @param lastOperator for repeated operations
   * @return SmartCalculator with a particular state.
   */
  private Calculator copywithfullstate(String inputBuffer, int memoryValue, char pendingOperator,
                                       boolean operatorActive, boolean showingResult,
                                       int lastOperand, char lastOperator) {
    return new SmartCalculator(inputBuffer, memoryValue, pendingOperator, operatorActive,
        showingResult, lastOperand, lastOperator);
  }

  @Override
  protected Calculator processDigit(char digit) {
    if (showingresult) {
      return new SmartCalculator(String.valueOf(digit), 0, '\0',
          false, false, 0, '\0');
    }
    if (operatorActive && inputbuffer.isEmpty()) {
      return copywithfullstate(String.valueOf(digit), memoryValue, pendingoperator,
          true, false,
          lastOperand, lastOperator);
    }
    String newBuffer = inputbuffer + digit;
    try {
      Integer.parseInt(newBuffer);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Number too large");
    }
    return copywithfullstate(newBuffer, memoryValue, pendingoperator, operatorActive,
        false,
        lastOperand, lastOperator);
  }

  /**
   * Operator input for the smart calculator features.
   *
   * @param symbol the operator symbol (+, -, *)
   * @return a new Calculator with updated state
   * @throws IllegalArgumentException if operator usage is invalid (except for leading +)
   */
  @Override
  protected Calculator processoperator(char symbol) {
    if (symbol == '+' && inputbuffer.isEmpty() && memoryValue == 0 && !operatorActive
        && !showingresult) {
      return this;
    }
    if (inputbuffer.isEmpty() && memoryValue == 0 && !operatorActive && !showingresult) {
      throw new IllegalArgumentException("Cannot start expression with this operator");
    }
    if (operatorActive && inputbuffer.isEmpty()) {
      return copywithfullstate("", memoryValue, symbol, true,
          false, lastOperand, lastOperator);
    }
    if (showingresult) {
      return new SmartCalculator("", memoryValue, symbol, true,
          false, 0, '\0');
    }
    if (operatorActive) {
      int rhs = getInputAsNumber();
      int outcome = calculate(memoryValue, rhs, pendingoperator);
      return new SmartCalculator("", outcome, symbol, true,
          false, 0, '\0');
    }
    int lhs = getInputAsNumber();
    return new SmartCalculator("", lhs, symbol, true,
        false, 0, '\0');
  }

  /**
   * Uses the equals input with smart features init.
   * Supports repeated equals and missing second operand.
   *
   * @return Calculator showing the calculated result
   * @throws IllegalArgumentException if it is invalid
   */
  @Override
  protected Calculator processequals() {
    if (showingresult && lastOperator != '\0') {
      int outcome = calculate(memoryValue, lastOperand, lastOperator);
      return copywithfullstate("", outcome, '\0',
          false, true, lastOperand, lastOperator);
    }
    if (showingresult) {
      return this;
    }
    if (!operatorActive) {
      throw new IllegalArgumentException("No operator specified");
    }
    int rhs;
    if (inputbuffer.isEmpty()) {
      rhs = memoryValue;
    } else {
      rhs = getInputAsNumber();
    }
    int outcome = calculate(memoryValue, rhs, pendingoperator);
    return copywithfullstate("", outcome, '\0', false,
        true, rhs, pendingoperator);
  }
}