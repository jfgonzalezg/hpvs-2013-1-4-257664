/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import Entity.Owner;
import Entity.Person;
import Entity.Pet;
import Entity.Veterinarian;
import Services.AppointmentService;
import Services.EmployeeService;
import Services.MedicalRecordService;
import Services.OwnerService;
import Services.PersonService;
import Services.PetService;
import Services.VacineService;
import Services.VeterinarianService;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import util.GestorTabla;

/**
 *
 * @author Ivan C
 */
public class PrincipalGui extends javax.swing.JFrame {

    private DefaultTableModel modelo;
    private JTable tabla;
    private GestorTabla gTabla;
    private ArrayList<String> cols;
    private ArrayList<String> row;
    private ArrayList<ArrayList> rows;
    private int anchocolumna[];
    private String nombrecolumna[];
    AppointmentService appointmentservice;
    JScrollPane pane;
    EmployeeService employeeservice;
    MedicalRecordService medicalrecordservice;
    OwnerService ownerservice;
    PersonService personservice;
    PetService petservice;
    VacineService vacineservice;
    VeterinarianService veterinarianservice;

    /**
     * Creates new form PrincipalGui
     */
    public PrincipalGui() {
        initComponents();

        EntityManagerFactory emf;
        EntityManager em;
        Map<String, String> properties = new HashMap<>();

        properties.put("javax.perstence.jdbc.user", "root");
        properties.put("javax.perstence.jdbc.password", "");

        emf = Persistence.createEntityManagerFactory("hpvs-2013-1-4-257664PU", properties);
        em = emf.createEntityManager();


        appointmentservice = new AppointmentService(emf);
        employeeservice = new EmployeeService(emf);
        medicalrecordservice = new MedicalRecordService(emf);
        ownerservice = new OwnerService(emf);
        personservice = new PersonService(emf);
        petservice = new PetService(emf);
        vacineservice = new VacineService(emf);
        veterinarianservice = new VeterinarianService(emf);




    }

    private void verTabla(ArrayList cons, int indexsearch) {

        anchocolumna = new int[11];
        nombrecolumna = new String[11];

        anchocolumna[0] = 60;
        anchocolumna[1] = 40;
        anchocolumna[2] = 100;
        anchocolumna[3] = 120;
        anchocolumna[4] = 70;
        anchocolumna[5] = 100;
        anchocolumna[6] = 100;
        anchocolumna[7] = 70;
        anchocolumna[8] = 100;
        anchocolumna[9] = 60;
        anchocolumna[10] = 60;



        pane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };//Evito la edición de celdas
        cols = new ArrayList<String>();
        row = new ArrayList<String>();
        rows = new ArrayList<ArrayList>();
        gTabla = new GestorTabla(modelo);
        tabla = new JTable(modelo);

        //Dibujo de Tabla Veterinarians
        if (indexsearch == 1) {

            nombrecolumna[0] = "Nombres";
            nombrecolumna[1] = "Apellidos";
            nombrecolumna[2] = "SSN";
            nombrecolumna[3] = "Salario";
            nombrecolumna[4] = "Especialidades";

            for (int i = 0; i < 5; i++) {
                cols.add(nombrecolumna[i]);
            }

            gTabla.cuadrarColumnas(cols);

            for (Iterator<Veterinarian> it = cons.iterator(); it.hasNext();) {
                Veterinarian veteri = it.next();
                row = new ArrayList<String>();
                row.add(veteri.getName());
                row.add(veteri.getLastname());
                row.add(veteri.getSSN());
                row.add(veteri.getSalary() + "");
                row.add(veteri.getSpecialities());

                rows.add(row);
            }

            gTabla.agregarFilas(rows);

        } else if (indexsearch == 2) {    //Dibujo tabla Pets

            nombrecolumna[0] = "ID";
            nombrecolumna[1] = "Nombre";
            nombrecolumna[2] = "Edad";
            nombrecolumna[3] = "Especie";
            nombrecolumna[4] = "Peso";

            for (int i = 0; i < 5; i++) {
                cols.add(nombrecolumna[i]);
            }

            gTabla.cuadrarColumnas(cols);

            for (Iterator<Pet> it = cons.iterator(); it.hasNext();) {
                Pet pet = it.next();
                row = new ArrayList<String>();
                row.add(pet.getId() + "");
                row.add(pet.getName());
                row.add(pet.getAge() + "");
                row.add(pet.getSpecies());
                row.add(pet.getWeight() + "");

                rows.add(row);
            }

            gTabla.agregarFilas(rows);

        } else if (indexsearch == 3) {           //dibujo tabla Owners

            nombrecolumna[0] = "Nombre";
            nombrecolumna[1] = "Apellidos";
            nombrecolumna[2] = "SSN";

            for (int i = 0; i < nombrecolumna.length; i++) {
                cols.add(nombrecolumna[i]);
            }

            gTabla.cuadrarColumnas(cols);

            for (Iterator<Owner> it = cons.iterator(); it.hasNext();) {
                Owner own = it.next();
                row = new ArrayList<String>();
                row.add(own.getName());
                row.add(own.getLastname());
                row.add(own.getSSN());


                rows.add(row);
            }

            gTabla.agregarFilas(rows);

        }
        if (indexsearch == 4) {      //Dibujo tabla Persons

            nombrecolumna[0] = "Nombre";
            nombrecolumna[1] = "Apellidos";
            nombrecolumna[2] = "SSN";

            for (int i = 0; i < nombrecolumna.length; i++) {
                cols.add(nombrecolumna[i]);
            }

            gTabla.cuadrarColumnas(cols);

            for (Iterator<Person> it = cons.iterator(); it.hasNext();) {
                Person pers = it.next();
                row = new ArrayList<String>();
                row.add(pers.getName());
                row.add(pers.getLastname());
                row.add(pers.getSSN());



                rows.add(row);
            }

            gTabla.agregarFilas(rows);

        }





        tabla = new JTable(modelo);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);//permite seleccionar solo una fila
        tabla.getTableHeader().setReorderingAllowed(false); //Evito el cambio de columnas
        tabla.getTableHeader().setResizingAllowed(true); //Evito la redimensión de las columnas por el usuario
        tabla.setBorder(BorderFactory.createEmptyBorder());
        //lo que tenía en el método anterior
        tabla.setRowHeight(30);

        tabla.setMaximumSize(new Dimension(500, 300));
        tabla.setRowMargin(3);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        for (int i = 0; i < anchocolumna.length; i++) {
            if (i == 5) {
                i++;
            }

            tabla.getColumnModel().getColumn(i).setPreferredWidth(anchocolumna[i]);

        }



        pane.setSize(500, 320);
        //jScrollPane1 = new JScrollPane(tabla,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        //JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pane.setViewportView(tabla);

        pane.setBounds(35, 120, 500, 300);
        pane.setBorder(BorderFactory.createEmptyBorder());
        add(pane);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Buscar:");

        jButton1.setText("BUSCAR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Veterinarians by ID", "Pets by ID", "Owners by ID", "Persons by ID" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField1))
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addContainerGap(280, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap(397, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String textff = jTextField1.getText();
       // System.out.print(jComboBox1.getSelectedIndex() + textff);
        search(jComboBox1.getSelectedIndex(), textff);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void search(int index, String textf) { // eleccion de consulta segun busqueda
        
        int tmp;
        long tmp2;
        tmp = Integer.parseInt(textf);
        tmp2 = (int)tmp;
        
        switch (index) {

            case 0: { // con este case, llamo la tabla de Veterinarians
                try {

                    validate();
                    eliminarTabla();
                   // verTabla(veterinarianservice.findVeterinarian(tmp2),1);
                    validate();

                } catch (Exception ex) {
                    System.out.print("no cambia");
                }

                break;
            }

            case 1: {


                validate();
                eliminarTabla();
                try {
                    // verTabla(petservice.findPet(tmp2),2);
                } catch (Exception ex) {
                }
                validate();





                break;
            }

            case 2: {
                try {
                    validate();
                    eliminarTabla();
                    // verTabla(ownerservice.findOwner(tmp2),3);
                    validate();

                } catch (Exception ex) {
                    System.out.print("no cambia");
                }

                break;
            }

            case 3: {
                try {

                    validate();
                    eliminarTabla();
//                    verTabla(personservice.findPerson(tmp2),4);
                    validate();

                } catch (Exception ex) {
                    System.out.print("no cambia");
                }

                break;
            }

            default: {
                System.out.print("no cambia * no hay opcion");
            }
        }

    }

    private void eliminarTabla() {

        try {
            pane.remove(tabla);
            this.remove(pane);
        } catch (Exception e) {
            System.out.print("no elimina tabla");
        }


    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalGui().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
