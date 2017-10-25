
import java.awt.Color;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amai
 */
public class frmClase07 extends javax.swing.JDialog {

    /**
     * Creates new form frmClse07
     */
    
    //Variables para las funciones propias
    connection oConn = new connection();
    Funciones oFunc = new Funciones();
    
    public frmClase07(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        //Maximiza la ventana
        this.setSize(this.getToolkit().getScreenSize());
        
        //Formate  la tabla  y Carga los productos
        sbTablaProductosFormat();
        sbProductosCarga();
        
    }
    private void sbTablaProductosFormat(){
        //Declaro un modelo  de datos
        DefaultTableModel modelo = new DefaultTableModel();
        
        // Añadimos  columnas al modelo de datos
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
        
        // Coloca el modelo en la tabla
        tblProductos.setModel(modelo);
        
        // Establezco los anchos de las columnas
        TableColumn tblColumn =tblProductos.getColumn("Codigo");
        tblColumn.setPreferredWidth(125);
        //Nombre Corto
        tblColumn = tblProductos.getColumn("Nombre");
        tblColumn.setPreferredWidth(150);
        // Nombre Largo
        tblColumn = tblProductos.getColumn("Descripcion");
        tblColumn.setPreferredWidth(250);
        
         // Directamente
        tblProductos.getColumn("Medida").setPreferredWidth(50);
        
        // Alinear  a la derecha las cantidades y precios
        DefaultTableCellRenderer cellAlinear = new DefaultTableCellRenderer();
        cellAlinear.setHorizontalAlignment(SwingConstants.RIGHT);
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(6).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(7).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(8).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(9).setCellRenderer(cellAlinear);
        
        // Color en los encabezados
        tblProductos.getTableHeader().setBackground(Color.lightGray);
        tblProductos.getTableHeader().setForeground(Color.blue);
        tblProductos.setFont(new java.awt.Font("Tahoma", 0, 14)); 
        
    }
//Procedimiento para cargar los productos
    private void sbProductosCarga(){
        // Variable para el Query
        String sQuery;
        int iCuenta;
        int iMaxRows;
        
        //Variable para  el modelo de la tabla
        DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();
        //Obtiene el numero de datos
        iMaxRows = tblDatos.getRowCount();
        
        //Ciclo para borrar
        for(iCuenta=0; iCuenta<iMaxRows;iCuenta++)
            tblDatos.removeRow(0);
        //Prepara los datos vacios iniciales
        Object [] oFila = new Object[10];
        
        //Prepara el Query
        sQuery = "Select * from tblproductos";
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        
        // Capturo el Error
        try{
            // Verifico que aya habido resuktados
            iCuenta =0;
            while (oConn.setResult.next()){
               //Obtengo los datos  em el Objet Fila
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
               
               // Agrega el dato
               tblDatos.addRow(oFila);   
            }
            // Cierro los resultados
            oConn.setResult.close();
            
        }catch(SQLException ex){
            oFunc.SubSistemaMensaje("sbProductosCarga:"+ex.getMessage().toString()); 
        }
        // Coloca el modelo de Nueva Cuenta
        tblProductos.setModel(tblDatos);
        //modelo.removeRow(1); // Borra la primera fila
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnadd = new javax.swing.JButton();
        btnupd = new javax.swing.JButton();
        btndel = new javax.swing.JButton();
        btnfind = new javax.swing.JButton();
        btnhelp = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        Productos = new javax.swing.JMenu();
        opcadd = new javax.swing.JMenuItem();
        opcupd = new javax.swing.JMenuItem();
        opcdelete = new javax.swing.JMenuItem();
        opcbuscar = new javax.swing.JMenuItem();
        opcclose = new javax.swing.JMenuItem();
        Ayuda = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 113, Short.MAX_VALUE)
        );

        tblProductos.setAutoCreateRowSorter(true);
        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblProductosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        jToolBar1.setRollover(true);

        btnadd.setFont(new java.awt.Font("Lucida Sans", 1, 10)); // NOI18N
        btnadd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // NOI18N
        btnadd.setText("Agregar");
        btnadd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnadd.setMaximumSize(new java.awt.Dimension(65, 65));
        btnadd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnadd);

        btnupd.setFont(new java.awt.Font("Lucida Sans", 1, 10)); // NOI18N
        btnupd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/upd.png"))); // NOI18N
        btnupd.setText("Actualizar");
        btnupd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnupd.setMaximumSize(new java.awt.Dimension(64, 64));
        btnupd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnupd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnupdActionPerformed(evt);
            }
        });
        jToolBar1.add(btnupd);

        btndel.setFont(new java.awt.Font("Lucida Sans", 1, 10)); // NOI18N
        btndel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btndel.setText("Eliminar");
        btndel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btndel.setMaximumSize(new java.awt.Dimension(64, 64));
        btndel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btndel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndelActionPerformed(evt);
            }
        });
        jToolBar1.add(btndel);

        btnfind.setFont(new java.awt.Font("Lucida Sans", 1, 10)); // NOI18N
        btnfind.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnfind.setText("Buscar");
        btnfind.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnfind.setMaximumSize(new java.awt.Dimension(64, 64));
        btnfind.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnfind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnfindActionPerformed(evt);
            }
        });
        jToolBar1.add(btnfind);

        btnhelp.setFont(new java.awt.Font("Lucida Sans", 1, 10)); // NOI18N
        btnhelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ahelp.png"))); // NOI18N
        btnhelp.setText("Ayuda");
        btnhelp.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnhelp.setMaximumSize(new java.awt.Dimension(64, 64));
        btnhelp.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(btnhelp);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 587, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(499, 499, 499)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(181, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        Productos.setText("Productos");

        opcadd.setText("Insertar");
        opcadd.setPreferredSize(new java.awt.Dimension(250, 25));
        opcadd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcaddActionPerformed(evt);
            }
        });
        Productos.add(opcadd);

        opcupd.setText("Editar");
        opcupd.setPreferredSize(new java.awt.Dimension(250, 25));
        opcupd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcupdActionPerformed(evt);
            }
        });
        Productos.add(opcupd);

        opcdelete.setText("Eliminar");
        opcdelete.setPreferredSize(new java.awt.Dimension(250, 25));
        opcdelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcdeleteActionPerformed(evt);
            }
        });
        Productos.add(opcdelete);

        opcbuscar.setText("Buscar");
        opcbuscar.setPreferredSize(new java.awt.Dimension(250, 25));
        opcbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcbuscarActionPerformed(evt);
            }
        });
        Productos.add(opcbuscar);

        opcclose.setText("Cerrar");
        opcclose.setPreferredSize(new java.awt.Dimension(250, 25));
        opcclose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opccloseActionPerformed(evt);
            }
        });
        Productos.add(opcclose);

        jMenuBar1.add(Productos);

        Ayuda.setText("Ayuda");
        jMenuBar1.add(Ayuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcaddActionPerformed
        // TODO add your handling code here:
        //Llamo al boton de insertar
        btnaddActionPerformed(evt);
    }//GEN-LAST:event_opcaddActionPerformed

    private void btnaddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaddActionPerformed
        // TODO add your handling code here:
        // Declaro la variable para la forma  de BC Productos
        frmClase07b frmProductosABC = new frmClase07b(null,true);
        
        // Muestra la forma
        frmProductosABC.setTitle(frmProductosABC.getTitle()+"-Insertar");
        frmProductosABC.setVisible(true);
        
        //Carga los productos al terminar
        sbProductosCarga();
    }//GEN-LAST:event_btnaddActionPerformed

    private void btnfindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnfindActionPerformed
        // TODO add your handling code here:
        opcbuscarActionPerformed(evt);
    }//GEN-LAST:event_btnfindActionPerformed

    private void btnupdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdActionPerformed
        // TODO add your handling code here:
        // Variable para el Codigo
        String sCodigo;
        
        //Valida  que este seleccionado un dato
        if(tblProductos.getSelectedRow()>=0){
            // Variable para  el modelo  d ela tabla
            DefaultTableModel tblDatos =(DefaultTableModel) tblProductos.getModel();
           
            //Obtengo el codigo de la  celda
            sCodigo = tblDatos.getValueAt(tblProductos.getSelectedRow(),0).toString();
            
            //Declaro variable para la forma  de ABC Productos
            frmClase07b frmProductosABC = new frmClase07b(null,true);
            
            //muestra la forma
            frmProductosABC.setTitle(frmProductosABC.getTitle()+"-Editar");
            frmProductosABC.setCodigo(sCodigo);
            frmProductosABC.fnBoolProductoExiste(true);
            frmProductosABC.setVisible(true);
            
            //Carga los productos al terminar
            sbProductosCarga();
        }
        else
            oFunc.SubSistemaMensaje("Seleccione un Producto");
    }//GEN-LAST:event_btnupdActionPerformed

    private void btndelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndelActionPerformed
        // TODO add your handling code here:
        // Variable para el Codigo
        String sCodigo;
        String sQuery;
        int iResult;
        
        // Valida  que este  seleccionado un dato
        if(tblProductos.getSelectedRow()>0){
            
            // Confirmacion de la eliminacion
            if(oFunc.fnIntSistemaPregunta("Desea realmente eliminar  el Registro")==JOptionPane.YES_OPTION){
               //Variable para el modelo de la tabla
               DefaultTableModel tblDatos =(DefaultTableModel) tblProductos.getModel();
               //Obtengo el codigo de la celda
               sCodigo = tblDatos.getValueAt(tblProductos.getSelectedRow(),0).toString();
               
               //Prepara el Query  para Borrar
               sQuery = "Delete from tblproductos Where strProductoCodigo = '" + sCodigo + "'";
               
               if(oConn.FnBoolQueryExecuteUpdate(sQuery))
                   oFunc.SubSistemaMensaje("El producto ha sido  eliminado");
               
               //Carga los productos  al terminar
               sbProductosCarga();
            }
        }
        else
            oFunc.SubSistemaMensaje("Seleccione un producto");
    }//GEN-LAST:event_btndelActionPerformed

    private void opcupdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcupdActionPerformed
        // TODO add your handling code here:
        btnupdActionPerformed(evt);
    }//GEN-LAST:event_opcupdActionPerformed

    private void opcdeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcdeleteActionPerformed
        // TODO add your handling code here:
        btndelActionPerformed(evt);
    }//GEN-LAST:event_opcdeleteActionPerformed

    private void sbProductoBuscaDato(String sDato){
        //Variable para fila y columna
        int fil, col;
        boolean bEncontro=false;
        
        //Pasa a mayusculas
        sDato = sDato.toUpperCase();
        
        //Limpia seleccion de la tabla
        tblProductos.clearSelection();
        
        for(fil = 0; fil < tblProductos.getRowCount();fil++){
            for(col = 0;col < tblProductos.getColumnCount();col++){
                //Obtiene datos contenidos  en una celda  de la tabla
                String  value = (String )tblProductos.getValueAt(fil,col);
                //Lo pasa a Mayusculas
                value = value.toUpperCase();
                
                if(value.lastIndexOf(sDato)>=0){
                    //Selecciona celda  si el texto  es  encontrado
                    tblProductos.changeSelection(fil, col, false,false);
                    bEncontro=true;
                    break;
                }
            }
            if(bEncontro)
                break;
        }     
    }
    private void opcbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcbuscarActionPerformed
        // TODO add your handling code here:
        String sDato;
        
        //Optiene el dato
        sDato=oFunc.fnIntSistemaSolicitaDatos("Capture dato a buscar");
        
        //Busca el dato
        sbProductoBuscaDato(sDato);
        
    }//GEN-LAST:event_opcbuscarActionPerformed

    private void tblProductosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMousePressed
        // TODO add your handling code here:
          // Para Obtener el Código
        String sCodigo;
        
        // Verifica doble Click
        if (evt.getClickCount() == 1) 
        {
            /*
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
            */
        }
    }//GEN-LAST:event_tblProductosMousePressed

    private void opccloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opccloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_opccloseActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClase07.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
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
    private javax.swing.JMenu Ayuda;
    private javax.swing.JMenu Productos;
    private javax.swing.JButton btnadd;
    private javax.swing.JButton btndel;
    private javax.swing.JButton btnfind;
    private javax.swing.JButton btnhelp;
    private javax.swing.JButton btnupd;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem opcadd;
    private javax.swing.JMenuItem opcbuscar;
    private javax.swing.JMenuItem opcclose;
    private javax.swing.JMenuItem opcdelete;
    private javax.swing.JMenuItem opcupd;
    private javax.swing.JTable tblProductos;
    // End of variables declaration//GEN-END:variables
}
