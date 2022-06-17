import { FC } from "react";
import {
  TableBody,
  TableContainer,
  TableHead,
  TableWrapper,
  TD,
  TR,
} from "./Table.styled";

interface TableProps {
  columns: ColumnData[];
  rows: any[];
}

interface ColumnData {
  field: string;
  headerName: string;
}

const Table: FC<TableProps> = ({ columns, rows }): JSX.Element => {
  return (
    <TableContainer>
      <TableWrapper>
        <TableHead>
          <TR>
            {columns.map((col) => (
              <th>{col.headerName}</th>
            ))}
          </TR>
        </TableHead>
        <TableBody>
          {rows.map((row, index) => {
            if (index >= 9) return;

            return (
              <TR>
                {columns.map((col) => (
                  <TD>{row[col.field]}</TD>
                ))}
              </TR>
            );
          })}
        </TableBody>
      </TableWrapper>
    </TableContainer>
  );
};

export default Table;
