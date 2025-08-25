package tictactoe;

import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void mapaDePosicoes() {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║     MAPA DE POSIÇÕES       ║");
        System.out.println("╚════════════════════════════╝");
        System.out.println("   |   ");
        System.out.println("   |   ");
        System.out.println("  \\|/ ");
        System.out.println("   V   \n");
        System.out.println("  1 | 2 | 3");
        System.out.println("  4 | 5 | 6");
        System.out.println("  7 | 8 | 9");
        System.out.println("══════════════════════════════\n");
    }

    public static int[] criarBoard() {
        return new int[9];
    }

    public static void exibirBoard(int[] board) {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║     TABULEIRO ATUAL        ║");
        System.out.println("╚════════════════════════════╝");

        for (int i = 0; i < board.length; i++) {
            switch (board[i]) {
                case 0 -> System.out.print((i + 1));
                case 1 -> System.out.print("X");
                case 2 -> System.out.print("O");
            }
            if (i % 3 != 2) {
                System.out.print(" | ");
            } else {
                System.out.println();
            }
        }

        System.out.println("══════════════════════════════\n");
    }

    public static boolean fazerJogada(int[] board, int posicao, int jogador) {
        if (posicao < 1 || posicao > 9) {
            System.out.println("Número fora do intervalo. Escolha uma posição entre 1 e 9.");
            return false;
        }

        if (board[posicao - 1] != 0) {
            System.out.println("Ops! Essa posição já está ocupada. Tente outra.");
            return false;
        }

        board[posicao - 1] = jogador;
        return true;
    }

    public static int obterPosicaoJogador(Scanner scanner) {
        while (true) {
            System.out.println("Escolha uma posição de (1-9):");
            String entrada = scanner.nextLine();
            if (entrada.matches("[1-9]")) {
                return Integer.parseInt(entrada);
            } else {
                System.out.println("Entrada inválida. Digite um número de 1 a 9.");
            }
        }
    }

    public static int melhorMovimento(int[] board) {
        int pontuacao, maiorPontuacao = Integer.MIN_VALUE, melhorMovimento = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                board[i] = 2;
                pontuacao = previsaoMovimento(board, i, true, 0);
                board[i] = 0;
                if (pontuacao > maiorPontuacao) {
                    maiorPontuacao = pontuacao;
                    melhorMovimento = i;
                }
            }
        }
        return melhorMovimento;
    }

    public static int previsaoMovimento(int[] board, int movimento, boolean jogadorAtual, int movimentosFeitos) {
        switch (verificarVencedor(board)) {
            case 'C' -> {
                return 10 - movimentosFeitos;
            }
            case 'P' -> {
                return -10 + movimentosFeitos;
            }
            case 'E' -> {
                return 0;
            }
            case 'N' -> {
                int melhor = jogadorAtual ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                for (int i = 0; i < board.length; i++) {
                    if (board[i] == 0) {
                        board[i] = jogadorAtual ? 1 : 2;
                        int tentativa = previsaoMovimento(board, movimento, !jogadorAtual, movimentosFeitos + 1);
                        board[i] = 0;
                        if (jogadorAtual && tentativa < melhor) melhor = tentativa;
                        if (!jogadorAtual && tentativa > melhor) melhor = tentativa;
                    }
                }
                return melhor;
            }
        }
        return 0;
    }

    public static char verificarVencedor(int[] board) {
        int[][] combinacoesVitoria = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] c : combinacoesVitoria) {
            if (board[c[0]] != 0 && board[c[0]] == board[c[1]] && board[c[0]] == board[c[2]]) {
                return board[c[0]] == 1 ? 'P' : 'C';
            }
        }

        for (int valor : board) {
            if (valor == 0) return 'N';
        }

        return 'E';
    }

    public static void mostrarVencedor(char vencedor) {
        System.out.println("╔════════════════════════════╗");
        System.out.println("║       RESULTADO FINAL      ║");
        System.out.println("╚════════════════════════════╝");
        switch (vencedor) {
            case 'P' -> System.out.println("O jogador venceu!");
            case 'C' -> System.out.println("O computador venceu!");
            case 'E' -> System.out.println("Empate!");
        }
        System.out.println("══════════════════════════════\n");
    }

    public static void main(String[] args) {
        int jogador = 1, computador = 2;
        Scanner scanner = new Scanner(System.in);
        int vitorias = 0, derrotas = 0, empates = 0;

        System.out.println("╔═════════════════════════════════════╗");
        System.out.println("║     BEM-VINDO AO JOGO DA VELHA!     ║");
        System.out.println("╚═════════════════════════════════════╝\n");
        mapaDePosicoes();

        boolean continuarJogando = true;

        while (continuarJogando) {
            int[] board = criarBoard();
            boolean playerAtual;

            while (true) {
                System.out.println("╔════════════════════════════════════════════════════╗");
                System.out.println("║ Deseja iniciar jogando? (s/n)                      ║");
                System.out.println("╚════════════════════════════════════════════════════╝");
                String resposta = scanner.nextLine().trim().toLowerCase();

                if (resposta.equals("s")) {
                    playerAtual = true;
                    break;
                } else if (resposta.equals("n")) {
                    int movimentoComputador = new Random().nextInt(9);
                    fazerJogada(board, movimentoComputador + 1, computador);
                    System.out.println("Computador escolheu a posição " + (movimentoComputador + 1));
                    playerAtual = false;
                    break;
                } else {
                    System.out.println("Entrada inválida. Digite 's' para sim ou 'n' para não.");
                }
            }

            exibirBoard(board);

            while (true) {
                char resultado = verificarVencedor(board);
                if (resultado != 'N') {
                    mostrarVencedor(resultado);

                    switch (resultado) {
                        case 'P' -> vitorias++;
                        case 'C' -> derrotas++;
                        case 'E' -> empates++;
                    }

                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║         PLACAR FINAL       ║");
                    System.out.println("╚════════════════════════════╝");
                    System.out.println("Vitórias: " + vitorias);
                    System.out.println("Derrotas: " + derrotas);
                    System.out.println("Empates : " + empates);
                    System.out.println("══════════════════════════════\n");

                    while (true) {
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║ Deseja jogar novamente? (s/n) ║");
                        System.out.println("╚════════════════════════════╝");
                        String resposta = scanner.nextLine().trim().toLowerCase();

                        if (resposta.equals("s")) {
                            System.out.println("\nReiniciando partida...\n");
                            break;
                        } else if (resposta.equals("n")) {
                            System.out.println("╔════════════════════════════╗");
                            System.out.println("║     OBRIGADO POR JOGAR!    ║");
                            System.out.println("║    Até a próxima partida!  ║");
                            System.out.println("╚════════════════════════════╝");
                            continuarJogando = false;
                            return;
                        } else {
                            System.out.println("Entrada inválida. Digite 's' para sim ou 'n' para não.");
                        }
                    }
                    break;
                }

                if (playerAtual) {
                    while (true) {
                        System.out.println("╔════════════════════════════╗");
                        System.out.println("║       SUA VEZ DE JOGAR     ║");
                        System.out.println("╚════════════════════════════╝");

                        int posicao = obterPosicaoJogador(scanner);

                        boolean jogadaValida = fazerJogada(board, posicao, jogador);
                        if (jogadaValida) {
                            exibirBoard(board);
                            playerAtual = false;
                            break;
                        }
                    }
                } else {
                    System.out.println("╔════════════════════════════╗");
                    System.out.println("║     VEZ DO COMPUTADOR      ║");
                    System.out.println("╚════════════════════════════╝");
                    int movimentoComputador = melhorMovimento(board);
                    fazerJogada(board, movimentoComputador + 1, computador);
                    System.out.println("Computador escolheu a posição " + (movimentoComputador + 1));
                    exibirBoard(board);
                    playerAtual = true;
                }
            }
        }
    }
}
