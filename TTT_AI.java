public class TTT_AI {
    static final int maximisingPlayerPiece = 1;
    static final int minimisingPlayerPiece = -1;
    static final int boardWidth = 3;
    static final int maxScore = 100;

    public static int evaluateBoard(int[] board) {
        int[] pieces = new int[]{minimisingPlayerPiece, maximisingPlayerPiece};
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

    public static int[] getValidMoves(int[] board) {
        // Get all available moves
        int[] moves = new int[board.length];
        int moveCount = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                moves[moveCount] = i;
                moveCount += 1;
            }
        }
        // Truncate length of array by creating a new one
        int[] movesTruncated = new int[moveCount];
        System.arraycopy(moves, 0, movesTruncated, 0, moveCount);
        return movesTruncated;
    }

    public static boolean isFilled(int[] board) {
        boolean isFilled = true;
        for (int j : board) {
            if (j == 0) { // Empty space
                isFilled = false;
                break;
            }
        }
        return isFilled;
    }

    public static boolean canMakeMove(int[] board) {
        // Player cannot make a move if game is won or board is filled
        if (isFilled(board)) {
            return false;
        }
        int score = evaluateBoard(board);
        return score != -maxScore && score != maxScore;
    }

    public static int[] getBestMoveBestScore(int[] board, boolean isMaximisingPlayer) {
        int[] movesLeft = getValidMoves(board);
        int bestMove = movesLeft[0];
        if (movesLeft.length == 1) {
            return new int[]{movesLeft[0], evaluateBoard(board)};
        }
        int bestScore = isMaximisingPlayer ? -maxScore : maxScore;
        for (int j : movesLeft) { // Iterate over every available move
            // Make new board
            int[] new_board = board.clone();
            // Place selected piece and evaluate
            new_board[j] = isMaximisingPlayer ? maximisingPlayerPiece : minimisingPlayerPiece; // Place new move
            int evaluation = evaluateBoard(new_board);
            int wantedScore = isMaximisingPlayer ? maxScore : -maxScore;
            // If move is winning, pick it without iterating deeper
            if (evaluation == wantedScore) {
                return new int[]{j, evaluation};
            }
            if (canMakeMove(new_board)) {
                // Get next best opponent move
                int[] opponentBestResult = getBestMoveBestScore(new_board, !isMaximisingPlayer);
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
        return new int[]{bestMove, bestScore};
    }

    public static void main(String[] args) {
        //int[] board = {1, 1, -1, 0, -1, 0, 0, 0, 0};
        //int[] board = {0, 0, 1, 0, 1, 0, 1, 0, 0};
        //int[] board = {1, 1, 0, 0, 0, 0, 0, -1, -1};
        int[] board = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        // Testing AI against itself
        int count = 0;
        boolean turnMax = true;
        while (canMakeMove(board)) {
            count += 1;
            System.out.println(count);

            int[] result = getBestMoveBestScore(board, turnMax);
            board[result[0]] = turnMax ? maximisingPlayerPiece : minimisingPlayerPiece;
            turnMax = !turnMax;

            for (int i : board) {
                System.out.printf(i + " ");
            }
            System.out.printf("\n");
        }
        System.out.println("Score: " + evaluateBoard(board));

        //int[] result = getBestMoveBestScore(board, false);
        //System.out.println("Best move: " + result[0]);
        //System.out.println("Score after move: " + result[1]);
    }
}
