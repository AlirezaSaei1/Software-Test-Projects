using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

using ArtOfTest.WebAii.Controls.HtmlControls;

namespace new_test_project
{
    public static class FindElementHelper
    {
        public static HtmlControl FindControlInTableCell(HtmlTableRow tableRow, string cellContentToCompare)
        {
            HtmlTableCell tableCell = tableRow.Cells[2]; // the date is in the third cell
            
            if (tableCell.TextContent.Contains(cellContentToCompare))
            {
                HtmlTableCell c1 = tableRow.Cells[0]; // look at the first cell in the row
                return c1.ChildNodes[0].As<HtmlControl>(); // find the checkbox within the cell
            }
            
            return null;
        }
    }
}
