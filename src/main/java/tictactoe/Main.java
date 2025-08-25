package tictactoe;

import java.util.Scanner;

public class Main {

    public static void mapaDePosicoes() {
        System.out.println("Mapa de posições:");
        System.out.println("1 | 2 | 3");
        System.out.println("4 | 5 | 6");
        System.out.println("7 | 8 | 9");
        System.out.println();
    }

    public static int[] criarBoard() {
        return new int[9];
    }

    public static void exibirBoard(int[] board) {
        for (int i = 0; i < board.length; i++) {
            switch (board[i]) {
                case 0 -> System.out.print("-");
                case 1 -> System.out.print("X");
                case 2 -> System.out.print("O");
                default -> {
                }
            }
            if (i == 0 || i == 1 || i == 3 || i == 4 || i == 6 || i == 7) {
                System.out.print(" | ");
            }
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    public static boolean fazerJogada(int[] board, int posicao, int jogador) {
        if (posicao < 1 || posicao > 9 || board[posicao - 1] != 0) {
            System.out.println("Posição inválida ou já ocupada.");
            return false;
        }
        board[posicao - 1] = jogador;
        return true;
    }

    public static int melhorMovimento(int[] board) {
        int pontuacao = 0, maiorPontuacao = Integer.MIN_VALUE, melhorMovimento = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                System.out.println("Analisando posição " + (i + 1));
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
                System.out.println("Venceu na posição " + movimento);
                return 10 - movimentosFeitos;
            }
            case 'P' -> {
                System.out.println("Perdeu na posição " + movimento);
                return 10 + movimentosFeitos;
            }
            case 'E' -> {
                System.out.println("Empatou na posição " + movimento);
                return 0;
            }
            case 'N' -> {
                int melhor = 0;
                for (int i = 0; i < board.length; i++) {
                    if (jogadorAtual) {
                        if (board[i] == 0) {
                            board[i] = 1;
                            System.out.println("foi para a posição " + movimento);
                            int tentativa = previsaoMovimento(board, movimento, false, movimentosFeitos + 1);
                            board[i] = 0;
                            if (tentativa < melhor) {
                                melhor = tentativa;
                            }
                        }
                    } else {
                        if (board[i] == 0) {
                            board[i] = 2;
                            System.out.println("foi para a posição " + movimento);
                            int tentativa = previsaoMovimento(board, movimento, true, movimentosFeitos + 1);
                            board[i] = 0;
                            if (tentativa > melhor) {
                                melhor = tentativa;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    public static char verificarVencedor(int[] board) {
        int[][] combinacoesVitoria = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Linhas
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Colunas
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonais
        };

        for (int[] combinacao : combinacoesVitoria) {
            if (board[combinacao[0]] != 0 &&
                    board[combinacao[0]] == board[combinacao[1]] &&
                    board[combinacao[0]] == board[combinacao[2]]) {
                if (board[combinacao[0]] == 1)
                    return 'P';
                else
                    return 'C';
            }
        }
        for (int valor : board) {
            if (valor == 0) {
                return 'N'; // Jogo não terminou
            }
        }
        return 'E';
    }

    public static void mostrarVencedor(char vencedor) {
        switch (vencedor) {
            case 'P' -> {
                System.out.println("O jogador venceu!");
            }
            case 'C' -> System.out.println("O computador venceu!");
            case 'E' -> System.out.println("Empate!");
            default -> {
            }
        }
    }

    public static void main(String[] args) {
        int jogador = 1;
        int computador = 2;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Jogo da Velha!");
        mapaDePosicoes();
        boolean playerAtual; // true para jogador, false para computador
        int[] board = criarBoard();

        while (true) {
            System.out.println("Deseja começar o jogo? (s/n)");
            String resposta = scanner.nextLine().toLowerCase();
            if (resposta.equals("n")) {
                playerAtual = false;
                break;
            } else if (resposta.equals("s")) {
                playerAtual = true;
                break;
            } else {
                System.out.println("Resposta inválida. Por favor, responda com 's' ou 'n'.");
            }
        }
        while (true) {

            if (verificarVencedor(board) == 'P' || verificarVencedor(board) == 'C') {
                mostrarVencedor(verificarVencedor(board));
                return;
            }
            if (playerAtual) {
                System.out.println("Sua vez! Escolha uma posição (1-9):");
                int posicao;
                posicao = scanner.nextInt();
                fazerJogada(board, posicao, jogador);
                exibirBoard(board);
                playerAtual = false;
            } else {
                System.out.println("Vez do computador:");
                int movimentoComputador = melhorMovimento(board);
                fazerJogada(board, (movimentoComputador + 1), computador);
                System.out.println("Computador escolheu a posição " + (movimentoComputador + 1));
                exibirBoard(board);
                playerAtual = true;
            }
        }
    }

}