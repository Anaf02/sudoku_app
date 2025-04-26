using Microsoft.AspNetCore.Mvc;

namespace SudokuApi.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class SudokuController : ControllerBase
    {
        private const string InputFilePath = "Resources/input.txt";
        private const string OutputFilePath = "Resources/output.txt";

        [HttpGet("unsolved/{index}")]
        public IActionResult GetUnsolvedSudoku(int index)
        {
            try
            {
                string[] lines = System.IO.File.ReadAllLines(InputFilePath);

                if (index < 0 || index >= lines.Length)
                {
                    return NotFound($"No unsolved sudoku puzzle found at index {index}.");
                }

                return Ok(new { index, puzzle = lines[index] });
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error reading input file: {ex.Message}");
            }
        }

        [HttpGet("solved/{index}")]
        public IActionResult GetSolvedSudoku(int index)
        {
            try
            {
                string[] lines = System.IO.File.ReadAllLines(OutputFilePath);

                if (index < 0 || index >= lines.Length)
                {
                    return NotFound($"No solved sudoku puzzle found at index {index}.");
                }

                return Ok(new { index, solution = lines[index] });
            }
            catch (Exception ex)
            {
                return StatusCode(500, $"Error reading output file: {ex.Message}");
            }
        }
    }
}