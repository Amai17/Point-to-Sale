

import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.*;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amai
 */
public class frmClase071 extends javax.swing.JDialog {

    /**
     * Creates new form frmClase07
     */
    
     // Variable para las funciones propias
    Funciones  oFunc = new Funciones();
    connection oConn = new connection();
     
 
    public frmClase071(java.awt.Frame parent, boolean modal) 
    {
        // Inicialización de la Forma    
        super(parent, modal);
        initComponents();
        
        // Maximiza la Ventana
        this.setSize(this.getToolkit().getScreenSize());  
                
        // Formate la tabla y Carga los Productos
        sbTablaProductosFormat();
        sbProductosCarga();
        
    }
    
    // Procedimiento para dar formato a la Tabla
    private void sbTablaProductosFormat()
    {
        
        // Declaro un modelo de datos
        DefaultTableModel modelo = new DefaultTableModel();      
                
        // Añadimos columnas al modelo de datos
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Descripcion");
        modelo.addColumn("Medida");
        modelo.addColumn("Inicial");
        modelo.addColumn("Entradas");
        modelo.addColumn("Salidas");
        modelo.addColumn("Actual");
        modelo.addColumn("Costo");
        modelo.addColumn("Precio");
                
        // Coloca el Modelo en la Tabla
        tblProductos.setModel(modelo);
        
        // Establezco los anchos de las columnas
        TableColumn tblColumn = tblProductos.getColumn("Codigo"); 
        tblColumn.setPreferredWidth(125);
        
        
        // Nombre Corto
        tblColumn = tblProductos.getColumn("Nombre");        
        tblColumn.setPreferredWidth(150);
        
        // Nombre largo
        tblColumn = tblProductos.getColumn("Descripcion");
        tblColumn.setPreferredWidth(250);
        
        // Directamente
        tblProductos.getColumn("Medida").setPreferredWidth(50);
        
        // Alinear a la derecha las cantidas y precios
        DefaultTableCellRenderer cellAlinear = new DefaultTableCellRenderer();
        
        // Asignamos el Alineamiento Horizontal a la derecha
        cellAlinear.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Asignamos la Variable de celda de alineamiento a cada una de las columnas a alinear
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(6).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(7).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(8).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(9).setCellRenderer(cellAlinear);
       
       
        // Color de los Encabezados
        tblProductos.getTableHeader().setBackground(Color.lightGray);
        tblProductos.getTableHeader().setForeground(Color.blue);
         tblProductos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
    }
    // Procedimiento para cargar los productos
    private void sbProductosCarga()
    {
        // Variable para el Query
        String sQuery;
        int iCuenta;
        int iMaxRows;
        
        // Variable para el modelo de la tabla
        DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();
        
        //tblProductos.removeAll();
        
        // Obtiene el Número de Datos
        iMaxRows = tblDatos.getRowCount();
        
        // Ciclo para Borrar
        for (iCuenta=0; iCuenta<iMaxRows;iCuenta++)
             tblDatos.removeRow(0);
        
        // Prepara los datos vacios iniciales
        Object [] oFila = new Object[10];
        
        // Prepara el Query
        sQuery = "Select * from tblproductos";
        
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        
        // Capturo el Error
        try {

            // Verifico que haya habido resultados
            iCuenta=0;
            while (oConn.setResult.next())
            {
        
                // Obtengo los datos en el Objeto Fila
                oFila[0]=oConn.setResult.getString("strProductoCodigo");
                oFila[1]=oConn.setResult.getString("strProductoNombre");                
                oFila[2]=oConn.setResult.getString("strProductoDescripcion");
                oFila[3]=oConn.setResult.getString("strProductoMedida");
                oFila[4]=oConn.setResult.getString("intProductoInicial");
                oFila[5]=oConn.setResult.getString("intProductoEntradas");
                oFila[6]=oConn.setResult.getString("intProductoSalidas");
                oFila[7]=oConn.setResult.getString("intProductoActual");
                oFila[8]=oConn.setResult.getString("decProductoCosto");
                oFila[9]=oConn.setResult.getString("decProductoPrecio");
                
                // Agrega el Dato
                tblDatos.addRow(oFila);
                //tblDatos.setValueAt(oFila[1], iCuenta++, 1); // Cambia el valor de la fila 1, columna 2.

            }
            
        // Cierro los Resultados
        oConn.setResult.close();
                
        } catch (SQLException ex) {
            oFunc.SubSistemaMensaje("sbProductosCarga:"+ex.getMessage().toString());
        }
        
        // Coloca el Modelo de Nueva Cuenta
        tblProductos.setModel(tblDatos);
        
        //modelo.setValueAt ("nuevo valor", 0, 1); // Cambia el valor de la fila 1, columna 2.
        //modelo.removeRow (1); // Borra la primera fila
    }
    
    

    
     //----------------------------------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        tbProductos = new javax.swing.JToolBar();
        btnProductoAdd = new javax.swing.JButton();
        btnProductoUpd = new javax.swing.JButton();
        btnProductoDel = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnProductoBuscar = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        btnProductoAyuda = new javax.swing.JButton();
        pnlProductos = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        mnuProductos = new javax.swing.JMenuBar();
        opcProductos = new javax.swing.JMenu();
        opcProductoInsertar = new javax.swing.JMenuItem();
        opcProductoEditar = new javax.swing.JMenuItem();
        opcProductoEliminar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        opcProductoBuscar = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        opcProductoCerrar = new javax.swing.JMenuItem();
        opcAyuda = new javax.swing.JMenu();
        opcAyudaTutorial = new javax.swing.JMenuItem();

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);

        btnProductoAdd.setFont(new java.awt.Font("Lucida Sans", 0, 10)); // NOI18N
        btnProductoAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnProductoAdd.setText("Add");
        btnProductoAdd.setFocusable(false);
        btnProductoAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductoAdd.setMaximumSize(new java.awt.Dimension(64, 64));
        btnProductoAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoAddActionPerformed(evt);
            }
        });
        tbProductos.add(btnProductoAdd);

        btnProductoUpd.setFont(new java.awt.Font("Lucida Sans", 0, 11)); // NOI18N
        btnProductoUpd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/edit.png"))); // NOI18N
        btnProductoUpd.setText("Upd");
        btnProductoUpd.setFocusable(false);
        btnProductoUpd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductoUpd.setMaximumSize(new java.awt.Dimension(64, 64));
        btnProductoUpd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductoUpd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoUpdActionPerformed(evt);
            }
        });
        tbProductos.add(btnProductoUpd);

        btnProductoDel.setFont(new java.awt.Font("Lucida Sans", 0, 11)); // NOI18N
        btnProductoDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnProductoDel.setText("Del");
        btnProductoDel.setFocusable(false);
        btnProductoDel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductoDel.setMaximumSize(new java.awt.Dimension(64, 64));
        btnProductoDel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductoDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoDelActionPerformed(evt);
            }
        });
        tbProductos.add(btnProductoDel);
        tbProductos.add(jSeparator1);

        btnProductoBuscar.setFont(new java.awt.Font("Lucida Sans", 0, 11)); // NOI18N
        btnProductoBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnProductoBuscar.setText("Find");
        btnProductoBuscar.setFocusable(false);
        btnProductoBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductoBuscar.setMaximumSize(new java.awt.Dimension(64, 64));
        btnProductoBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductoBuscarActionPerformed(evt);
            }
        });
        tbProductos.add(btnProductoBuscar);
        tbProductos.add(jSeparator4);

        btnProductoAyuda.setFont(new java.awt.Font("Lucida Sans", 0, 11)); // NOI18N
        btnProductoAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N
        btnProductoAyuda.setText("Help");
        btnProductoAyuda.setFocusable(false);
        btnProductoAyuda.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductoAyuda.setMaximumSize(new java.awt.Dimension(64, 64));
        btnProductoAyuda.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        tbProductos.add(btnProductoAyuda);

        tblProductos.setAutoCreateRowSorter(true);
        tblProductos.setFont(new java.awt.Font("Lucida Sans", 0, 11)); // NOI18N
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"2", "3", "4", "5"},
                {"6", "7", "8", null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProductosMousePressed(evt);
            }
        });
        pnlProductos.setViewportView(tblProductos);

        opcProductos.setText("Productos");
        opcProductos.setContentAreaFilled(false);
        opcProductos.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcProductos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        opcProductos.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        opcProductoInsertar.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcProductoInsertar.setText("Insertar");
        opcProductoInsertar.setPreferredSize(new java.awt.Dimension(250, 25));
        opcProductoInsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcProductoInsertarActionPerformed(evt);
            }
        });
        opcProductos.add(opcProductoInsertar);
        opcProductoInsertar.getAccessibleContext().setAccessibleParent(opcProductos);

        opcProductoEditar.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcProductoEditar.setText("Editar");
        opcProductoEditar.setPreferredSize(new java.awt.Dimension(250, 25));
        opcProductoEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcProductoEditarActionPerformed(evt);
            }
        });
        opcProductos.add(opcProductoEditar);

        opcProductoEliminar.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcProductoEliminar.setText("Eliminar");
        opcProductoEliminar.setPreferredSize(new java.awt.Dimension(250, 25));
        opcProductoEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcProductoEliminarActionPerformed(evt);
            }
        });
        opcProductos.add(opcProductoEliminar);
        opcProductos.add(jSeparator2);

        opcProductoBuscar.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcProductoBuscar.setText("Buscar");
        opcProductoBuscar.setPreferredSize(new java.awt.Dimension(250, 25));
        opcProductoBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcProductoBuscarActionPerformed(evt);
            }
        });
        opcProductos.add(opcProductoBuscar);
        opcProductos.add(jSeparator3);

        opcProductoCerrar.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcProductoCerrar.setText("Cerrar");
        opcProductoCerrar.setPreferredSize(new java.awt.Dimension(250, 25));
        opcProductoCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcProductoCerrarActionPerformed(evt);
            }
        });
        opcProductos.add(opcProductoCerrar);

        mnuProductos.add(opcProductos);
        opcProductos.getAccessibleContext().setAccessibleParent(opcProductos);

        opcAyuda.setText("Ayuda");
        opcAyuda.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcAyuda.setMaximumSize(new java.awt.Dimension(59, 32767));
        opcAyuda.setPreferredSize(new java.awt.Dimension(250, 25));

        opcAyudaTutorial.setFont(new java.awt.Font("Lucida Sans", 0, 12)); // NOI18N
        opcAyudaTutorial.setText("Tutorial");
        opcAyudaTutorial.setPreferredSize(new java.awt.Dimension(250, 25));
        opcAyuda.add(opcAyudaTutorial);

        mnuProductos.add(opcAyuda);

        setJMenuBar(mnuProductos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tbProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
            .addComponent(pnlProductos)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tbProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMousePressed
        // TODO add your handling code here:
        
        // Para Obtener el Código
        String sCodigo;
        
        // Verifica doble Click
        if (evt.getClickCount() == 1) 
        {
            
             // Variable para el modelo de la tabla
             DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();
            
            // Obtengo el Codigo de la celda
            sCodigo = tblDatos.getValueAt(tblProductos.getSelectedRow(),0).toString();
            
             // Declaro la variable para la forma de ABC Productos
             frmClase07b frmProductosABC = new frmClase07b(null, true);
        
             // Muestra la Forma
             frmProductosABC.setTitle(frmProductosABC.getTitle()+"-Editar");
             frmProductosABC.setCodigo(sCodigo);
             frmProductosABC.fnBoolProductoExiste(true);
             frmProductosABC.setVisible(true);
             
             // Carga los productos al terminar        
             sbProductosCarga();
            
        }
    }//GEN-LAST:event_tblProductosMousePressed

    private void btnProductoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoAddActionPerformed
        // TODO add your handling code here:
        // Declaro la Variable de la Forma de Procesos
        
        // Declaro la variable para la forma de ABC Productos
        frmClase07b frmProductosABC = new frmClase07b(null, true);
        
        // Muestra la Forma
        frmProductosABC.setTitle(frmProductosABC.getTitle()+"-Insertar");
        frmProductosABC.setVisible(true);
        
        // Carga los productos al terminar        
        sbProductosCarga();
        
        
    }//GEN-LAST:event_btnProductoAddActionPerformed

    private void btnProductoUpdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoUpdActionPerformed
        // TODO add your handling code here:
        
        // Variable para el Código
        String sCodigo;
        
        // Valida que esté seleccionado  un dato
        if (tblProductos.getSelectedRow()>=0)
        {
            // Variable para el modelo de la tabla
             DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();
            
            // Obtengo el Codigo de la celda
            sCodigo = tblDatos.getValueAt(tblProductos.getSelectedRow(),0).toString();
            
             // Declaro la variable para la forma de ABC Productos
             frmClase07b frmProductosABC = new frmClase07b(null, true);
        
             // Muestra la Forma
             frmProductosABC.setTitle(frmProductosABC.getTitle()+"-Editar");
             frmProductosABC.setCodigo(sCodigo);
             frmProductosABC.fnBoolProductoExiste(true);
             frmProductosABC.setVisible(true);
             
             // Carga los productos al terminar        
             sbProductosCarga();
        }
        else
            oFunc.SubSistemaMensaje("Seleccione un Producto");
    }//GEN-LAST:event_btnProductoUpdActionPerformed

    private void btnProductoDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoDelActionPerformed
        // TODO add your handling code here:
        // Variable para el Código
        String sCodigo;
        String sQuery;
        int iResult;
        
       
        // Valida que esté seleccionado  un dato
        if (tblProductos.getSelectedRow()>=0)
        {
             // Confirmación de la Eliminación
             if (oFunc.fnIntSistemaPregunta("Desea realmente eliminar el Registro")==JOptionPane.YES_OPTION)
             {     
                // Variable para el modelo de la tabla
                DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();

                // Obtengo el Codigo de la celda
                sCodigo = tblDatos.getValueAt(tblProductos.getSelectedRow(),0).toString();

                // Prepara el Query para Borrar
                sQuery = "Delete from 04tblProductos Where strProductoCodigo='"+sCodigo+"'";

                if (oConn.FnBoolQueryExecuteUpdate(sQuery))
                   oFunc.SubSistemaMensaje("El Producto ha sido eliminado");

                // Carga los productos al terminar        
                sbProductosCarga();
             }
        }
        else
            oFunc.SubSistemaMensaje("Seleccione un Producto");
    }//GEN-LAST:event_btnProductoDelActionPerformed

    private void opcProductoEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcProductoEliminarActionPerformed
        // TODO add your handling code here:

        // Ejecuto el Botón de la barra de herramientas
        btnProductoDelActionPerformed(evt);

    }//GEN-LAST:event_opcProductoEliminarActionPerformed

    private void opcProductoEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcProductoEditarActionPerformed
        // TODO add your handling code here:

        // Llamo al Botón de la Barra de Herramientas
        btnProductoUpdActionPerformed(evt);
    }//GEN-LAST:event_opcProductoEditarActionPerformed

    private void opcProductoInsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcProductoInsertarActionPerformed
        // TODO add your handling code here:

        // Llamo al Botón de la Barra de Herramientas
        btnProductoAddActionPerformed(evt);

    }//GEN-LAST:event_opcProductoInsertarActionPerformed

    private void opcProductoCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcProductoCerrarActionPerformed
        // TODO add your handling code here:
        
        // Cierra la Forma
        this.dispose();
    }//GEN-LAST:event_opcProductoCerrarActionPerformed

    private void sbProductoBuscaDato(String sDato)
    {
        // Variables para Fila y Columns
        int fil, col;
        boolean bEncontro=false;
        
        // Pasa a mayúsculas
        sDato = sDato.toUpperCase();
        
        //Limpia selección de la tabla
        tblProductos.clearSelection();

        for(fil = 0; fil < tblProductos.getRowCount();fil++)
        {
            for(col = 0; col < tblProductos.getColumnCount(); col++)
            {              
                 //Obtiene dato contenido en una celda de la tabla
                 String value = (String)tblProductos.getValueAt(fil, col);
                 
                 // lo pasa a Mayúsculas
                 value = value.toUpperCase();
                                  
                 //if(value.equals(sDato))
                 if(value.lastIndexOf(sDato)>=0)
                 {     
                       //Selecciona celda si el texto es encontrado     
                       //tblProductos.changeSelection(col, col, bEncontro, bEncontro);
                       tblProductos.changeSelection(fil, col, false, false);
                       bEncontro=true;
                       break;
                 }
             }
             if (bEncontro)
                 break;
        }
    }
    private void opcProductoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcProductoBuscarActionPerformed

        // TODO add your handling code here:
        String sDato;
        
        // Obtiene el Dato
        sDato=oFunc.fnIntSistemaSolicitaDatos("Capture dato a Buscar");
        
        // Busca el Dato
        sbProductoBuscaDato(sDato);
        
    }//GEN-LAST:event_opcProductoBuscarActionPerformed

    private void btnProductoBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductoBuscarActionPerformed
        // TODO add your handling code here:
        opcProductoBuscarActionPerformed(evt);
    }//GEN-LAST:event_btnProductoBuscarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the dialog
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                frmClase07 dialog = new frmClase07(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnProductoAdd;
    private javax.swing.JButton btnProductoAyuda;
    private javax.swing.JButton btnProductoBuscar;
    private javax.swing.JButton btnProductoDel;
    private javax.swing.JButton btnProductoUpd;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JMenuBar mnuProductos;
    private javax.swing.JMenu opcAyuda;
    private javax.swing.JMenuItem opcAyudaTutorial;
    private javax.swing.JMenuItem opcProductoBuscar;
    private javax.swing.JMenuItem opcProductoCerrar;
    private javax.swing.JMenuItem opcProductoEditar;
    private javax.swing.JMenuItem opcProductoEliminar;
    private javax.swing.JMenuItem opcProductoInsertar;
    private javax.swing.JMenu opcProductos;
    private javax.swing.JScrollPane pnlProductos;
    private javax.swing.JToolBar tbProductos;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables
}
