import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        // Display
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        // Configura o KeyListener para capturar teclas de operação e números
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char keyChar = e.getKeyChar();
                if (Character.isDigit(keyChar) || keyChar == '.') {
                    adicionarAoDisplay(String.valueOf(keyChar));
                } else if (keyChar == '+' || keyChar == '-' || keyChar == '*' || keyChar == '/') {
                    definirOperador(String.valueOf(keyChar));
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Enter para "="
                    calcularResultado();
                } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    apagarUltimoCaractere();
                } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) { // Esc para "C"
                    limparDisplay();
                }
            }
        });

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

        setFocusable(true);
        requestFocusInWindow();
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

    private void apagarUltimoCaractere() {
        String texto = display.getText();
        if (!texto.isEmpty()) {
            display.setText(texto.substring(0, texto.length() - 1));
        }
    }
}
