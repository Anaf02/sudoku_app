namespace SudokuSolver
{
    public class SudokuSolver
    {
        private static void Solve(Sudoku sudoku)
        {
            byte[][] board = sudoku.Grid;
            int n = 9;

            bool[,] rCheck = new bool[n, n + 1], cCheck = new bool[n, n + 1], gCheck = new bool[n, n + 1];

            int[,] gridIds = new int[n, n];
            for (int r = 0; r < n; r++)
            {
                for (int c = 0; c < n; c++)
                {
                    gridIds[r, c] = 3 * (r / 3) + c / 3;
                }
            }

            for (int r = 0; r < n; r++)
            {
                for (int c = 0; c < n; c++)
                {
                    byte val = board[r][c];
                    if (val != 0)
                    {
                        rCheck[r, val] = true;
                        cCheck[c, val] = true;
                        gCheck[GridID(r, c), val] = true;
                    }
                }
            }

            Fill(0, 0);

            bool Fill(int r, int c)
            {
                if (c == n)
                {
                    r++;
                    c = 0;
                }

                if (r == n) return true;

                if (board[r][c] != 0)
                    return Fill(r, c + 1);

                for (byte digit = 1; digit <= 9; digit++)
                {
                    if (!(rCheck[r, digit] || cCheck[c, digit] || gCheck[gridIds[r, c], digit]))
                    {
                        board[r][c] = digit;
                        rCheck[r, digit] = cCheck[c, digit] = gCheck[gridIds[r, c], digit] = true;

                        if (Fill(r, c + 1)) return true;

                        board[r][c] = 0;
                        rCheck[r, digit] = cCheck[c, digit] = gCheck[gridIds[r, c], digit] = false;
                    }
                }

                return false;
            }

            static int GridID(int r, int c) => 3 * (r / 3) + c / 3;
        }

        public static string ProcessSudoku(string line)
        {
            Sudoku sudoku = new Sudoku(line);
            Solve(sudoku);

            var result = new char[81];
            int index = 0;
            for (int i = 0; i < 9; i++)
            {
                for (int j = 0; j < 9; j++)
                {
                    result[index++] = (char)('0' + sudoku.Grid[i][j]);
                }
            }
            return new string(result);
        }
    }
}