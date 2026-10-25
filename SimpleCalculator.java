package calculator;

/**
 * Simple calculator for basic arithmetic operations.
 * This calculator has 32-bit integers and does one character at a time.
 * It does addition subtraction and multiply
 */
public class SimpleCalculator extends AbstractCalculator {

  /**
   * Creates a new SimpleCalculator in initial state.
   */
  public SimpleCalculator() {
    super();
  }

  /**
   * Private constructor for creating a calculator with specific state.
   *
   * @param inputbuffer current user input
   * @param memoryValue stored result
   * @param pendingoperator active operator
   * @param operatorActive true if operator has been entered
   * @param showingresult true if showing computed result
   */
  private SimpleCalculator(String inputbuffer, int memoryValue, char pendingoperator,
                           boolean operatorActive, boolean showingresult) {
    super(inputbuffer, memoryValue, pendingoperator, operatorActive, showingresult);
  }

  @Override
  protected Calculator resetEngine() {
    return new SimpleCalculator();
  }

  @Override
  protected Calculator copyWithState(String inputBuffer, int memoryValue, char pendingOperator,
                                     boolean operatorActive, boolean showingResult) {
    return new SimpleCalculator(inputBuffer, memoryValue, pendingOperator, operatorActive,
        showingResult);
  }

  /**
   * Processes an operator input.
   *
   * @param symbol the operator symbol (+, -, *)
   * @return a new Calculator with updated state
   * @throws IllegalArgumentException if operator usage is invalid
   */
  @Override
  protected Calculator processoperator(char symbol) {
    if (inputbuffer.isEmpty() && memoryValue == 0 && !operatorActive && !showingresult) {
      throw new IllegalArgumentException("Cannot start expression with operator");
    }
    if (operatorActive && inputbuffer.isEmpty()) {
      throw new IllegalArgumentException("Two consecutive operators are not allowed");
    }
    if (showingresult) {
      return new SimpleCalculator("", memoryValue, symbol, true,
          false);
    }
    if (operatorActive) {
      int rhs = getInputAsNumber();
      int outcome = calculate(memoryValue, rhs, pendingoperator);
      return new SimpleCalculator("", outcome, symbol, true,
          false);
    }
    int lhs = getInputAsNumber();
    return new SimpleCalculator("", lhs, symbol, true, false);
  }

  /**
   * Gives the equals input to give result and display it.
   *
   * @return Calculated result
   * @throws IllegalArgumentException if incomplete
   */
  @Override
  protected Calculator processequals() {
    if (showingresult) {
      return this;
    }
    if (!operatorActive) {
      throw new IllegalArgumentException("No operator specified");
    }
    if (inputbuffer.isEmpty()) {
      throw new IllegalArgumentException("Second operand missing");
    }
    int rhs = getInputAsNumber();
    int outcome = calculate(memoryValue, rhs, pendingoperator);
    return new SimpleCalculator("", outcome, '\0',
        false, true);
  }
}