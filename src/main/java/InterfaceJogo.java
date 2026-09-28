import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InterfaceJogo {
    private static int numeroSecreto;
    private static int tentativas;
    private static Clip clip;

    // Lista para guardar os números já jogados
    private static List<Integer> numerosUtilizados = new ArrayList<>();

    // Componentes visuais atualizáveis
    private static JLabel tentativasLabel;
    private static JLabel resultadoLabel;
    private static JLabel historicoLabel;
    private static JButton botaoAdivinhar;

    public static void main(String[] args) {
        // Tocar a música de fundo
        tocarMusicaDeFundo();

        // Gerar o número secreto inicial
        Random random = new Random();
        numeroSecreto = random.nextInt(100) + 1;
        tentativas = 0;

        // 1. Criar a janela principal (520x400 para dar espaço ao histórico)
        JFrame janela = new JFrame("Jogo da Adivinha - Filipa Martins");
        janela.setSize(520, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLocationRelativeTo(null);

        // Layout de grelha flexível
        janela.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.gridx = 0;

        // 2. Elementos visuais
        JLabel instrucaoLabel = new JLabel("Tente adivinhar o número entre 1 e 100:");
        instrucaoLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

        JTextField campoEntrada = new JTextField(10);
        campoEntrada.setFont(new Font("SansSerif", Font.PLAIN, 14));
        campoEntrada.setHorizontalAlignment(JTextField.CENTER);

        botaoAdivinhar = new JButton("Tentar");
        botaoAdivinhar.setFont(new Font("SansSerif", Font.BOLD, 13));

        resultadoLabel = new JLabel("Introduza um palpite e clique em Tentar.");
        resultadoLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        tentativasLabel = new JLabel("Tentativas realizadas: 0");
        tentativasLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        tentativasLabel.setForeground(Color.GRAY);

        // Novo rótulo para os números já utilizados
        historicoLabel = new JLabel("Números tentados: [ ]");
        historicoLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        historicoLabel.setForeground(new Color(100, 100, 100));

        // 3. Ação do Botão Tentar
        botaoAdivinhar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int chute = Integer.parseInt(campoEntrada.getText());

                    // Verifica se o número já foi jogado anteriormente
                    if (numerosUtilizados.contains(chute)) {
                        resultadoLabel.setText("⚠️ Já tentaste o número " + chute + "! Tenta outro.");
                        resultadoLabel.setForeground(Color.ORANGE.darker());
                    } else {
                        // Adiciona o novo palpite à lista
                        numerosUtilizados.add(chute);
                        tentativas++;

                        // Atualiza as etiquetas na interface
                        tentativasLabel.setText("Tentativas realizadas: " + tentativas);
                        historicoLabel.setText("Números tentados: " + numerosUtilizados.toString());

                        // Lógica de verificação do palpite
                        if (chute < numeroSecreto) {
                            resultadoLabel.setText("Muito baixo! Tente um número maior.");
                            resultadoLabel.setForeground(Color.BLUE);
                        } else if (chute > numeroSecreto) {
                            resultadoLabel.setText("Muito alto! Tente um número menor.");
                            resultadoLabel.setForeground(Color.RED);
                        } else {
                            resultadoLabel.setText("🎉 Parabéns! Acertou em " + tentativas + " tentativa(s)!");
                            resultadoLabel.setForeground(new Color(0, 128, 0));
                            botaoAdivinhar.setEnabled(false);
                            campoEntrada.setEnabled(false);
                        }
                    }
                } catch (NumberFormatException ex) {
                    resultadoLabel.setText("Por favor, digite apenas números inteiros!");
                    resultadoLabel.setForeground(Color.ORANGE.darker());
                }
                campoEntrada.setText("");
                campoEntrada.requestFocus();
            }
        });

        // 4. Adicionar elementos à janela
        gbc.gridy = 0;
        janela.add(instrucaoLabel, gbc);

        gbc.gridy = 1;
        janela.add(campoEntrada, gbc);

        gbc.gridy = 2;
        janela.add(botaoAdivinhar, gbc);

        gbc.gridy = 3;
        janela.add(resultadoLabel, gbc);

        gbc.gridy = 4;
        janela.add(tentativasLabel, gbc);

        gbc.gridy = 5;
        janela.add(historicoLabel, gbc); // Histórico na última linha

        // 5. Mostrar a janela
        janela.setVisible(true);
    }

    // Método para a música de fundo
    private static void tocarMusicaDeFundo() {
        try {
            InputStream audioSrc = InterfaceJogo.class.getResourceAsStream("/musica.wav");
            if (audioSrc == null) {
                System.out.println("Ficheiro musica.wav não encontrado na pasta resources!");
                return;
            }

            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            System.out.println("Erro ao reproduzir áudio: " + e.getMessage());
        }
    }
}