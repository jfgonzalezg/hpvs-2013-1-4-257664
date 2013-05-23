/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ivan C
 */
public class GestorTabla {

    DefaultTableModel model;

    public GestorTabla(DefaultTableModel model) {
        this.model = model;
    }

    public void cuadrarColumnas(ArrayList<String> cols) {
        for (Iterator<String> it = cols.iterator(); it.hasNext();) {
            String string = it.next();
            model.addColumn(string);
        }
    }

    public void agregarFilas(ArrayList<ArrayList> data) {
        for (int i = 0; i < data.size(); i++) {
            model.addRow(new Vector(data.get(i)));
        }
    }
}
