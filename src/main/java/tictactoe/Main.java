package tictactoe;

public class Main {

    public static int[] criarBoard() {
        int[] board = new int[9];
        return board;
    }
    public static void exibirBoard(int[] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print(board[i] + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }

    public static int[] fazerJogada(int[] board, int posicao, int jogador) {
        if (posicao < 0 || posicao >= board.length || board[posicao] != 0) {
            throw new IllegalArgumentException("Posição inválida ou já ocupada.");
        }
        board[posicao] = jogador;
        return board;
    }

    public static void main(String[] args) {
        int []jogo = criarBoard();
        exibirBoard(jogo);
        System.out.println();
        jogo = fazerJogada(jogo, 0, 1);
        exibirBoard(jogo);
    }
}