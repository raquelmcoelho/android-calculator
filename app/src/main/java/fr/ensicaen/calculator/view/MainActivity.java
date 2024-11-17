 package fr.ensicaen.calculator.view;

 import androidx.appcompat.app.AppCompatActivity;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.EditText;

 import fr.ensicaen.calculator.R;
 import fr.ensicaen.calculator.databinding.ActivityMainBinding;

 public class MainActivity extends AppCompatActivity {
     private EditText input;
     private String currentInput = "";
     private double firstNumber = 0;
     private String operator = "";
     ActivityMainBinding _activityMainBinding;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);

         _activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
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
             Button button = (Button) v;
             currentInput += button.getText().toString();
             input.setText(currentInput);
         };

         for (int id : numberButtonIds) {
             findViewById(id).setOnClickListener(listener);
         }
     }

     private void setupOperatorButtons() {
         findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
         findViewById(R.id.btnSubtract).setOnClickListener(v -> handleOperator("-"));
         findViewById(R.id.btnMultiply).setOnClickListener(v -> handleOperator("*"));
         findViewById(R.id.btnDivide).setOnClickListener(v -> handleOperator("/"));

         findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
         findViewById(R.id.btnClear).setOnClickListener(v -> clear());
         findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLastCharacter());
     }

     private void handleOperator(String op) {
         if (!currentInput.isEmpty()) {
             firstNumber = Double.parseDouble(currentInput);
             operator = op;
             currentInput += op;
             input.setText(currentInput);
         }
     }

     private void calculateResult() {
         if (!currentInput.isEmpty() && !operator.isEmpty()) {
             double secondNumber = Double.parseDouble(currentInput);
             double result = 0;

             switch (operator) {
                 case "+":
                     result = firstNumber + secondNumber;
                     break;
                 case "-":
                     result = firstNumber - secondNumber;
                     break;
                 case "*":
                     result = firstNumber * secondNumber;
                     break;
                 case "/":
                     if (secondNumber != 0) {
                         result = firstNumber / secondNumber;
                     } else {
                         input.setText("Error");
                         return;
                     }
                     break;
             }

             input.setText(String.valueOf(result));
             currentInput = String.valueOf(result);
             operator = "";
         }
     }

     private void clear() {
         currentInput = "";
         firstNumber = 0;
         operator = "";
         input.setText("");
     }

     private void deleteLastCharacter() {
         if (!currentInput.isEmpty()) {
             currentInput = currentInput.substring(0, currentInput.length() - 1);
             input.setText(currentInput);
         }
     }
 }