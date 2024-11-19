 package fr.ensicaen.calculator.view;

 import static java.lang.Math.max;

 import androidx.appcompat.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.TextView;
 import org.mariuszgromada.math.mxparser.*;

 import com.google.android.material.button.MaterialButton;

 import fr.ensicaen.calculator.R;

 public class MainActivity extends AppCompatActivity {
     private TextView input;
     private TextView input;
     private String currentInput = "";
     private double firstNumber = 0;
     private String operator = "";

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_main);

         input = findViewById(R.id.input);
         setupNumberButtons();
         setupOperatorButtons();
     }

     private void setupNumberButtons() {
         int[] numberButtonIds = {
             R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
             R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
         };

         View.OnClickListener listener = v -> {
             MaterialButton button = (MaterialButton) v;
             System.out.println(button.getText());
             currentInput += button.getText().toString();
             updateInput();

         };

         for (int id : numberButtonIds) {
             findViewById(id).setOnClickListener(listener);
         }
     }

     private void updateInput() {
         int beginIndex = max(currentInput.length()-10, 0);
         input.setText(currentInput.substring(beginIndex));

     }

     private void setupOperatorButtons() {
         findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
         findViewById(R.id.btnSubtract).setOnClickListener(v -> handleOperator("-"));
         findViewById(R.id.btnMultiply).setOnClickListener(v -> handleOperator("*"));
         findViewById(R.id.btnDivide).setOnClickListener(v -> handleOperator("/"));
         findViewById(R.id.btnOpenParentheses).setOnClickListener(v -> handleOperator("("));
         findViewById(R.id.btnCloseParentheses).setOnClickListener(v -> handleOperator(")"));

         findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
         findViewById(R.id.btnClear).setOnClickListener(v -> clear());
         findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLastCharacter());
     }

     // TODO: open parenthesis logic
     private void handleOperator(String op) {
         if (!currentInput.isEmpty() || "(".equals(op)) {
             currentInput += op;
             updateInput();
         }
     }


     private void calculateResult() {
         if (!currentInput.isEmpty()) {
            Expression e = new Expression(currentInput);
            mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());
            currentInput = String.valueOf(e.calculate());
            updateInput();
         }
     }

     private void clear() {
         currentInput="";
         updateInput();
     }

     private void deleteLastCharacter() {
         if (!currentInput.isEmpty()) {
             currentInput = currentInput.substring(0, currentInput.length() - 1);
             updateInput();
         }
     }
 }