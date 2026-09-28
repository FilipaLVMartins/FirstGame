import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int numeroSecreto = random.nextInt(100) + 1;
        int tentativas = 0;
        int palpite = 0;

        System.out.println("=================================");
        System.out.println("  Bem-vindo ao Jogo de Adivinhar! ");
        System.out.println("=================================");
        System.out.println("Tente adivinhar o número entre 1 e 100.\n");

        do{
            System.out.print("Digite o seu palpite: ");
            palpite = scanner.nextInt();
            tentativas++;

            if(palpite < numeroSecreto){
                System.out.println("Muito baixo! Tente um número maior.\n");
            }
            else if(palpite > numeroSecreto){
                System.out.println("Muito alto! Tente um número menor. \n");
            }
            else{
                System.out.println("\n PARABÉNS!! ACERTASTE <333  " + "Número Secreto: " + numeroSecreto);
                System.out.println("\n No total utilizaste: " + tentativas + "tentativas!");
            }
        } while (palpite != numeroSecreto);
        scanner.close();
        }
}

