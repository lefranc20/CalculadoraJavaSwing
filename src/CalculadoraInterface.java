import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculadoraInterface extends JFrame implements ActionListener {
    private JTextField display;
    private String operador = "";
    private double valor1 = 0;
    private boolean novaOperacao = true;

    public CalculadoraInterface() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display editável para permitir uso de teclas Delete, Backspace, Ctrl+C e Ctrl+V
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        // Filtro para controlar os caracteres válidos no display
        ((AbstractDocument) display.getDocument()).setDocumentFilter(new CalculadoraDocumentFilter());

        // Painel de botões
        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 4));

        String[] botoes = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };

        for (String texto : botoes) {
            JButton botao = new JButton(texto);
            botao.setFont(new Font("Arial", Font.BOLD, 18));
            botao.addActionListener(this);
            painel.add(botao);
        }

        add(painel, BorderLayout.CENTER);

        display.setFocusable(true);
        display.requestFocusInWindow();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        if (comando.equals("C")) {
            limparDisplay();
        } else if (comando.equals("=")) {
            calcularResultado();
        } else if (comando.equals("+") || comando.equals("-") || comando.equals("*") || comando.equals("/")) {
            definirOperador(comando);
        } else if (comando.equals(".")) {
            if (!display.getText().contains(".")) {
                adicionarAoDisplay(comando);
            }
        } else {
            adicionarAoDisplay(comando);
        }
    }

    private void adicionarAoDisplay(String texto) {
        if (novaOperacao) {
            display.setText(texto);
            novaOperacao = false;
        } else {
            display.setText(display.getText() + texto);
        }
    }

    private void definirOperador(String oper) {
        valor1 = Double.parseDouble(display.getText());
        operador = oper;
        display.setText(display.getText() + " " + operador + " ");
        novaOperacao = false;
    }

    private void calcularResultado() {
        String[] partes = display.getText().split(" ");
        if (partes.length < 3) return; // Verifica se há pelo menos uma operação para calcular

        double valor2 = Double.parseDouble(partes[2]);

        switch (operador) {
            case "+": valor1 += valor2; break;
            case "-": valor1 -= valor2; break;
            case "*": valor1 *= valor2; break;
            case "/": valor1 /= valor2; break;
        }
        
        display.setText(String.valueOf(valor1));
        novaOperacao = true;
    }

    private void limparDisplay() {
        display.setText("");
        valor1 = 0;
        operador = "";
        novaOperacao = true;
    }

    // Filtro para controlar o que pode ser digitado no JTextField
    class CalculadoraDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (isValidInput(string)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (isValidInput(text)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }

        private boolean isValidInput(String text) {
            return text.matches("[0-9\\.+\\-*/]*"); // Permite números, operadores e ponto
        }
    }
}
