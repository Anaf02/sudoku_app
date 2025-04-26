namespace SudokuSolver
{
    public class Program
    {
        private static void Main()
        {
            string[] lines = File.ReadAllLines("input.txt");
            string[] results = new string[lines.Length];

            Parallel.For(0, lines.Length, i =>
            {
                results[i] = SudokuSolver.ProcessSudoku(lines[i]);
            });

            File.WriteAllLines("output.txt", results);
        }
    }
}