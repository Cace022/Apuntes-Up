package restaurante.Database;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class MenuTableModel extends AbstractTableModel {
    private String[] columns = {"ID", "Nombre", "Precio", "Imagen"};
    private List<String[]> menuData;

    @Override
    public int getRowCount() {
        return menuData == null ? 0 : menuData.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return menuData.get(rowIndex)[columnIndex];
    }

    public void setMenuData(List<String[]> data) {
        this.menuData = data;
        fireTableDataChanged();
    }

    public int getDishId(int rowIndex) {
        return Integer.parseInt(menuData.get(rowIndex)[0]); // ID está en la columna 0
    }
}

