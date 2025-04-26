namespace SudokuSolver
{
    public class Sudoku
    {
        public byte[][] Grid { get; private set; }

        public Sudoku(string line)
        {
            if (line.Length != 81)
                throw new ArgumentException("Each Sudoku puzzle must have exactly 81 characters.");

            Grid = new byte[9][];
            for (int i = 0; i < 9; i++)
            {
                Grid[i] = new byte[9];
                for (int j = 0; j < 9; j++)
                {
                    char ch = line[i * 9 + j];
                    Grid[i][j] = (byte)(ch - '0');
                }
            }
        }
    }
}