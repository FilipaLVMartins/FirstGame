import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class InterfaceJogo {
    private static int numeroSecreto;
    private static int tentativas;

    public static void main(String[] args) {
        // Gerar o número secreto inicial
        Random random = new Random();
        numeroSecreto = random.nextInt(100) + 1;
        tentativas = 0;

        // 1. Criar a janela principal (JFrame)
        JFrame janela = new JFrame("Jogo da Adivinhação");
        janela.setSize(400, 200);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new FlowLayout());
        janela.setLocationRelativeTo(null); // Centraliza a janela no ecrã

        // 2. Criar os elementos visuais
        JLabel instrucaoLabel = new JLabel("Tente adivinhar o número entre 1 e 100:");
        JTextField campoEntrada = new JTextField(10);
        JButton botaoAdivinhar = new JButton("Tentar");
        JLabel resultadoLabel = new JLabel("Introduza um palpite e clique em Tentar.");

        // 3. Adicionar ação ao botão
        botaoAdivinhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int chute = Integer.parseInt(campoEntrada.getText());
                    tentativas++;

                    if (chute < numeroSecreto) {
                        resultadoLabel.setText("Muito baixo! Tente um número maior.");
                    } else if (chute > numeroSecreto) {
                        resultadoLabel.setText("Muito alto! Tente um número menor.");
                    } else {
                        resultadoLabel.setText("🎉 Acertou em " + tentativas + " tentativa(s)!");
                        botaoAdivinhar.setEnabled(false); // Desativa o botão ao ganhar
                    }
                } catch (NumberFormatException ex) {
                    resultadoLabel.setText("Por favor, digite apenas números inteiros!");
                }
                campoEntrada.setText(""); // Limpa o campo de texto
            }
        });

        // 4. Adicionar os elementos à janela
        janela.add(instrucaoLabel);
        janela.add(campoEntrada);
        janela.add(botaoAdivinhar);
        janela.add(resultadoLabel);

        // 5. Tornar a janela visível
        janela.setVisible(true);
    }
}

