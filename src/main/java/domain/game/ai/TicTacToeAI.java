package domain.game.ai;

import support.abstracts.AbstractAI;

public class TicTacToeAI extends AbstractAI {
    final int maximisingPlayerPiece = 1;
    final int minimisingPlayerPiece = -1;
    final int boardWidth = 3;
    final int maxScore = 100;

    public Integer evaluateBoard(Integer[] board) {
        Integer[] pieces = new Integer[]{minimisingPlayerPiece, maximisingPlayerPiece};

        for (int playerPiece : pieces) {
            int diaFCount = 0;
            int diaBCount = 0;

            for (int i = 0; i < boardWidth; i++) {
                int horCount = 0;
                int verCount = 0;

                for (int j = 0; j < boardWidth; j++) {
                    // Horizontal check
                    if (board[i + j * boardWidth] == playerPiece) {
                        horCount += 1;
                    }
                    // Vertical check
                    if (board[j + i * boardWidth] == playerPiece) {
                        verCount += 1;
                    }
                }
                // Diagonal backwards check
                if (board[i * (boardWidth + 1)] == playerPiece) {
                    diaBCount += 1;
                }
                // Diagonal forwards check
                if (board[(i + 1) * (boardWidth - 1)] == playerPiece) {
                    diaFCount += 1;
                }
                if (horCount == boardWidth || verCount == boardWidth || diaBCount == boardWidth || diaFCount == boardWidth) {
                    // A player has won
                    if (playerPiece == maximisingPlayerPiece) {
                        return maxScore;
                    } else {
                        return -maxScore;
                    }
                }
            }
        }

        return 0; // Tie
    }

    private Integer[] getValidMoves(Integer[] board) {
        // Get all available moves
        Integer[] moves = new Integer[board.length];
        int moveCount = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                moves[moveCount] = i;
                moveCount += 1;
            }
        }
        // Truncate length of array by creating a new one
        Integer[] movesTruncated = new Integer[moveCount];
        System.arraycopy(moves, 0, movesTruncated, 0, moveCount);

        return movesTruncated;
    }

    private Boolean isFilled(Integer[] board) {
        boolean isFilled = true;

        for (int j : board) {
            if (j == 0) { // Empty space
                isFilled = false;
                break;
            }
        }

        return isFilled;
    }

    private boolean canMakeMove(Integer[] board) {
        // Player cannot make a move if game is won or board is filled
        if (isFilled(board)) {
            return false;
        }

        int score = evaluateBoard(board);

        return score != -maxScore && score != maxScore;
    }

    public Integer[] getBestMoveBestScore(Integer[] board, boolean isMaximisingPlayer) {
        Integer[] movesLeft = getValidMoves(board);
        int bestMove = movesLeft[0];

        if (movesLeft.length == 1) {
            Integer[] new_board = board.clone();
            new_board[movesLeft[0]] = isMaximisingPlayer ? maximisingPlayerPiece : minimisingPlayerPiece;
            return new Integer[]{movesLeft[0], evaluateBoard(new_board)};
        }

        int bestScore = isMaximisingPlayer ? -maxScore : maxScore;
        for (int j : movesLeft) { // Iterate over every available move
            // Make new board
            Integer[] new_board = board.clone();
            // Place selected piece and evaluate
            new_board[j] = isMaximisingPlayer ? maximisingPlayerPiece : minimisingPlayerPiece; // Place new move
            int evaluation = evaluateBoard(new_board);
            int wantedScore = isMaximisingPlayer ? maxScore : -maxScore;
            // If move is winning, pick it without iterating deeper
            if (evaluation == wantedScore) {
                return new Integer[]{j, evaluation};
            }
            if (canMakeMove(new_board)) {
                // Get next best opponent move
                Integer[] opponentBestResult = getBestMoveBestScore(new_board, !isMaximisingPlayer);

                int opponentBestScore = opponentBestResult[1];
                if (opponentBestScore >= bestScore && isMaximisingPlayer) {
                    bestScore = opponentBestScore;
                    bestMove = j;
                } else if (opponentBestScore <= bestScore && !isMaximisingPlayer) {
                    bestScore = opponentBestScore;
                    bestMove = j;
                }
            } else {
                // Opponent cannot make a subsequent move (leaf node)
                if ((evaluation >= bestScore) && isMaximisingPlayer) {
                    bestScore = evaluation;
                    bestMove = j;
                } else if ((evaluation <= bestScore) && !isMaximisingPlayer) {
                    bestScore = evaluation;
                    bestMove = j;
                }
            }
        }
        return new Integer[]{bestMove, bestScore};
    }
}
