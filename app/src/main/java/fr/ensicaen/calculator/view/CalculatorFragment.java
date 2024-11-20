package fr.ensicaen.calculator.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.mXparser;

import fr.ensicaen.calculator.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextView input;
    private String currentInput = "";
    private HorizontalScrollView scrollView;


    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculator, container, false);
        scrollView = view.findViewById(R.id.scrollInput);
        input = view.findViewById(R.id.input);
        setupNumberButtons(view);
        setupOperatorButtons(view);
        return view;
    }

    private void setupNumberButtons(View view) {
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
            view.findViewById(id).setOnClickListener(listener);
        }
    }

    private void updateInput() {
        input.setText(currentInput);
        scrollView.post(() -> scrollView.fullScroll(HorizontalScrollView.FOCUS_RIGHT));

    }

    private void setupOperatorButtons(View view) {
        view.findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
        view.findViewById(R.id.btnSubtract).setOnClickListener(v -> handleOperator("-"));
        view.findViewById(R.id.btnMultiply).setOnClickListener(v -> handleOperator("*"));
        view.findViewById(R.id.btnDivide).setOnClickListener(v -> handleOperator("/"));
        view.findViewById(R.id.btnOpenParentheses).setOnClickListener(v -> handleOperator("("));
        view.findViewById(R.id.btnCloseParentheses).setOnClickListener(v -> handleOperator(")"));

        view.findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult(view));
        view.findViewById(R.id.btnClear).setOnClickListener(v -> clear());
        view.findViewById(R.id.btnDelete).setOnClickListener(v -> deleteLastCharacter());
    }

    private void handleOperator(String op) {
        if (!currentInput.isEmpty() || "(".equals(op)) {
            currentInput += op;
            updateInput();
        }
    }

    private void calculateResult(View view) {
        if (!currentInput.isEmpty()) {
            Expression e = new Expression(currentInput);
            mXparser.consolePrintln("Res: " + e.getExpressionString() + " = " + e.calculate());
            if (Double.isNaN(e.calculate())) {
                Toast toast = new Toast(view.getContext());
                toast.setText(getString(R.string.math_error));
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.show();
            } else {
                currentInput = String.valueOf(e.calculate());
                updateInput();
            }
        }
    }

    private void clear() {
        currentInput = "";
        updateInput();
    }

    private void deleteLastCharacter() {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            updateInput();
        }
    }
}