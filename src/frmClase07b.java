
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import sun.util.logging.PlatformLogger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amai
 */
public class frmClase07b extends javax.swing.JDialog {

    /**
     * Creates new form frmClase07
     */
    
    //Declaro variables de conexion
    connection oConn = new connection();
    
    //Variable para las funciones propias
    Funciones oFunc = new Funciones();
    
     public frmClase07b(java.awt.Frame parent, boolean modal) {
       // Código de Inicialización
        super(parent, modal);
        initComponents();
        
        //Centro la Forma   
        this.setLocationRelativeTo(null);
        
         // deshabilita resize
        this.setResizable(false);
        
        // Capturo el Evento del Existencia Inicial
        setJTexFieldChanged(txtinicial);
        
        // Carga el combo de Medidas
        sbCargaMedidas();
        
        // Se alinea a la derecha los campos numéricos
        txtinicial.setHorizontalAlignment(JTextField.RIGHT);
        lblentradas.setHorizontalAlignment(JTextField.RIGHT);
        lblsalida.setHorizontalAlignment(JTextField.RIGHT);
        lblactual.setHorizontalAlignment(JTextField.RIGHT);
        txtcosto.setHorizontalAlignment(JTextField.RIGHT);
        txtprecio.setHorizontalAlignment(JTextField.RIGHT);

    }

     private boolean fnBoolDatosCorrectos(){
        // Variable de Resultado
        boolean bResultado=true;
        int iValor;
        String sValor;
        javax.swing.ImageIcon oIconoAceptar = new javax.swing.ImageIcon(getClass().getResource("/ok.png"));
        javax.swing.ImageIcon oIconoCancelar = new javax.swing.ImageIcon(getClass().getResource("/cancelar.png"));
        
        
        // Valida el Código
        if (txtcodigo.getText().trim().isEmpty() || txtcodigo.getText().trim().length()> 15 ){    
            lblIcoCodigo.setIcon(oIconoCancelar);
            bResultado = false;
        }
        else
            lblIcoCodigo.setIcon(oIconoAceptar);
        
        // Valida el Nombre
        if (txtnombre.getText().trim().isEmpty() || txtnombre.getText().trim().length()> 20 ){    
            lblIconNombre.setIcon(oIconoCancelar);
            bResultado = false;
        }
        else
            // Hace visible su ícono correspondiente
            lblIconNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ok.png")));
        
        // Valida la Descripción
        if (txtdesc.getText().trim().isEmpty() || txtdesc.getText().trim().length()> 50 )
        {    
            lblIconDesc.setIcon(oIconoCancelar);
            bResultado = false;
        }
        else
            lblIconDesc.setIcon(oIconoAceptar);
        
        // Valida la Medida
     if (cbomedida.getEditor().getItem().toString().isEmpty() || cbomedida.getEditor().getItem().toString().length()>3 ){    
            lblIconMedida.setIcon(oIconoCancelar);
            bResultado = false;
        }else            
            lblIconMedida.setIcon(oIconoAceptar);

        // Valida la Existencia Inicial
        sValor = txtinicial.getText().toString();      
        if (! oFunc.fnBoolIsInteger(sValor)){    
            lblIconInicial.setIcon(oIconoCancelar);
            bResultado = false;
        }
        else
            if (Integer.valueOf(sValor) <= 0){
                lblIconInicial.setIcon(oIconoCancelar);
                bResultado = false;
            }
            else
                lblIconInicial.setIcon(oIconoAceptar);
        
        // Valida el Costo
        sValor = txtcosto.getText().toString();      
        if (! oFunc.fnBoolIsDecimal(sValor)){    
            lblIconCosto.setIcon(oIconoCancelar);
            bResultado = false;
        }
        else
             if(Double.valueOf(sValor) <= 0)
             {
                lblIconCosto.setIcon(oIconoCancelar);
                bResultado = false;
             }
            else
                lblIconCosto.setIcon(oIconoAceptar);
        
        // Valida el Precio
        sValor = txtprecio.getText().toString();      
        if (! oFunc.fnBoolIsDecimal(sValor))        
        {    
            lblIconPrecio.setIcon(oIconoCancelar);
            bResultado = false;
        }
        else
             if(Double.valueOf(sValor)<=0)
             {
                lblIconPrecio.setIcon(oIconoCancelar);
                bResultado = false;
             }
            else
                lblIconPrecio.setIcon(oIconoAceptar);
               
        
        
        // retorna el Resultado
        return bResultado;
    }
     
    public boolean fnBoolProductoExiste(boolean bDisplay){
        // Para devolver el resultado
        boolean bResultado=false;
        
        // Para el Query
        String sQuery;
        // Prepara el Query 
        sQuery  = "Select * from tblproductos Where strProductoCodigo='"+txtcodigo.getText()+"'";
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        // Capturo el Error
        try {
            // Verifico que haya habido resultados
            if (oConn.setResult.next()){
                // Verifica si debe desplegar
                if (bDisplay){
                    // Obtengo los datos
                    txtnombre.setText(oConn.setResult.getString("strProductoNombre"));
                    txtdesc.setText(oConn.setResult.getString("strProductoDescripcion"));
                    cbomedida.setSelectedItem(oConn.setResult.getString("strProductoMedida"));
                    txtinicial.setText(oConn.setResult.getString("intProductoInicial"));
                    lblentradas.setText(oConn.setResult.getString("intProductoEntradas"));
                    lblsalida.setText(oConn.setResult.getString("intProductoSalidas"));
                    lblactual.setText(oConn.setResult.getString("intProductoActual"));
                    txtcosto.setText(oConn.setResult.getString("decProductoCosto"));
                    txtprecio.setText(oConn.setResult.getString("decProductoPrecio"));
                }
                // Resultado
                bResultado = true;
            }
            // Cierro los Resultados
            oConn.setResult.close();    
        } catch (SQLException ex) {
            oFunc.SubSistemaMensaje("fnBoolProductoExiste:"+ex.getMessage().toString());
        }        
        // Retorna el Resultado
        return bResultado;
    }
       // Procedimiento para insertar el producto
    private void sbInsertaProducto(){
        String sQuery;
        
        // Prepara el Query para Insertar
        sQuery = "Insert into tblproductos Values (";
        sQuery += "'"+txtcodigo.getText().toString()+"',";
        sQuery += "'"+txtnombre.getText().toString()+"',";
        sQuery += "'"+txtdesc.getText().toString()+"',";
        sQuery += "'"+cbomedida.getEditor().getItem().toString()+"',";
        sQuery += txtinicial.getText().toString()+",";
        sQuery += lblentradas.getText().toString()+",";
        sQuery += lblsalida.getText().toString()+",";
        sQuery += lblactual.getText().toString()+",";
        sQuery += txtcosto.getText().toString()+",";
        sQuery += txtprecio.getText().toString()+")";
        
        // Para ver el Query
        //oFunc.SubSistemaMensaje(sQuery);
                          
        // Ejecuta la Insercion
        if (oConn.FnBoolQueryExecuteUpdate(sQuery))
            oFunc.SubSistemaMensaje("La Inserción se ha realizado con éxito");
        
        // Cierra la Forma
        this.dispose();
        
    }
    
    private void sbModificaProducto(){
        
        // Variable para el Query
        String sQuery;
               
        // Prepara el Query para Insertar
        sQuery = " Update tblproductos Set ";
        sQuery += " strProductoNombre='"+txtnombre.getText().toString()+"',";
        sQuery += " strProductoDescripcion='"+txtdesc.getText().toString()+"',";
        sQuery += " strProductoMedida='"+cbomedida.getEditor().getItem()+"',";
        sQuery += " intProductoInicial="+txtinicial.getText()+",";
        sQuery += " intProductoEntradas="+lblentradas.getText()+",";
        sQuery += " intProductoSalidas="+lblsalida.getText()+",";
        sQuery += " intProductoActual="+lblactual.getText()+",";
        sQuery += " decProductoCosto="+txtcosto.getText()+",";
        sQuery += " decProductoPrecio="+txtprecio.getText();
        sQuery += " Where strProductoCodigo='"+txtcodigo.getText()+"'";
        
        // Para ver el Query
        //oFunc.SubSistemaMensaje(sQuery);
                        
        // Ejecuta la Insercion
        if (oConn.FnBoolQueryExecuteUpdate(sQuery))
            oFunc.SubSistemaMensaje("La Modificación se ha realizado con éxito");
        
        // Cierra la Forma
        this.dispose();
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlDetalle = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtcodigo = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtdesc = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtinicial = new javax.swing.JTextField();
        txtcosto = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        lblIcoCodigo = new javax.swing.JLabel();
        lblIconNombre = new javax.swing.JLabel();
        lblIconDesc = new javax.swing.JLabel();
        lblIconMedida = new javax.swing.JLabel();
        lblIconInicial = new javax.swing.JLabel();
        lblIconCosto = new javax.swing.JLabel();
        lblIconPrecio = new javax.swing.JLabel();
        lblactual = new javax.swing.JLabel();
        lblsalida = new javax.swing.JLabel();
        lblentradas = new javax.swing.JLabel();
        cbomedida = new javax.swing.JComboBox();
        btnaceptar = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pnlDetalle.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del Producto"));

        jLabel1.setText("Código:");

        jLabel2.setText("Nombre:");

        jLabel3.setText("Descripcón:");

        jLabel4.setText("Medida");

        jLabel5.setText("Inicial:");

        jLabel6.setText("Entradas:");

        jLabel7.setText("Salidas:");

        jLabel8.setText("Actual");

        txtcodigo.setMaximumSize(new java.awt.Dimension(170, 30));
        txtcodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtcodigoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcodigoKeyTyped(evt);
            }
        });

        txtnombre.setMaximumSize(new java.awt.Dimension(170, 30));
        txtnombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtnombreActionPerformed(evt);
            }
        });

        txtdesc.setMaximumSize(new java.awt.Dimension(170, 30));

        jLabel9.setText("Costo:");

        jLabel10.setText("Precio:");

        txtinicial.setMaximumSize(new java.awt.Dimension(170, 30));

        lblIcoCodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N

        lblIconNombre.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N

        lblIconDesc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N

        lblIconMedida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N
        lblIconMedida.setToolTipText("5 Caracteres para la Medida");

        lblIconInicial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N

        lblIconCosto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N

        lblIconPrecio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/help.png"))); // NOI18N

        lblactual.setText("0");
        lblactual.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblactual.setMaximumSize(new java.awt.Dimension(170, 30));

        lblsalida.setText("0");
        lblsalida.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblsalida.setMaximumSize(new java.awt.Dimension(170, 30));

        lblentradas.setText("0");
        lblentradas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        lblentradas.setMaximumSize(new java.awt.Dimension(170, 30));

        javax.swing.GroupLayout pnlDetalleLayout = new javax.swing.GroupLayout(pnlDetalle);
        pnlDetalle.setLayout(pnlDetalleLayout);
        pnlDetalleLayout.setHorizontalGroup(
            pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel4))
                .addGap(25, 25, 25)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtnombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtdesc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtinicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtprecio)
                    .addComponent(txtcosto)
                    .addComponent(lblactual, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(lblsalida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblentradas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbomedida, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIconMedida)
                    .addComponent(lblIcoCodigo)
                    .addComponent(lblIconNombre)
                    .addComponent(lblIconDesc)
                    .addComponent(lblIconInicial)
                    .addComponent(lblIconCosto)
                    .addComponent(lblIconPrecio))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        pnlDetalleLayout.setVerticalGroup(
            pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetalleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtcodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIcoCodigo))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtnombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblIconNombre)))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtdesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconDesc))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblIconMedida)
                    .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(cbomedida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconInicial))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(lblentradas, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblsalida, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblactual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtcosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconCosto))
                .addGap(18, 18, 18)
                .addGroup(pnlDetalleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtprecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblIconPrecio))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnaceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ok.png"))); // NOI18N
        btnaceptar.setText("Aceptar");
        btnaceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnaceptarActionPerformed(evt);
            }
        });

        btncancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cancelar.png"))); // NOI18N
        btncancel.setText("Cancelar");
        btncancel.setMaximumSize(new java.awt.Dimension(105, 39));
        btncancel.setMinimumSize(new java.awt.Dimension(105, 39));
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pnlDetalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(btnaceptar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btncancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnaceptar)
                    .addComponent(btncancel, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtnombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtnombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtnombreActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btncancelActionPerformed

    private void btnaceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnaceptarActionPerformed
        // TODO add your handling code here:
         if (this.getTitle().toString().lastIndexOf("Insertar")>=0)
        {
            if (fnBoolProductoExiste(true))
                oFunc.SubSistemaMensaje("El Código de Producto YA existe");
            else
                if (fnBoolDatosCorrectos())
                   sbInsertaProducto();
        }
        else
            if (! fnBoolProductoExiste(false))
                oFunc.SubSistemaMensaje("El Código de Producto NO existe");
            else
                if (fnBoolDatosCorrectos())
                   sbModificaProducto();
    }//GEN-LAST:event_btnaceptarActionPerformed

    private void txtcodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyTyped
        // TODO add your handling code here:
         if (this.getTitle().toString().lastIndexOf("Editar")>=0)
            evt.consume();
    }//GEN-LAST:event_txtcodigoKeyTyped

    private void txtcodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcodigoKeyPressed
        // TODO add your handling code here:
         if (this.getTitle().toString().lastIndexOf("Editar")>=0)
            evt.consume();
    }//GEN-LAST:event_txtcodigoKeyPressed

   private void sbCargaMedidas(){
        // Variable para el Query
        String sQuery;
        
        // Prepara el Query
        sQuery ="Select distinct(strProductoMedida) from tblproductos";
        
        if (oConn.FnBoolQueryExecute(sQuery)){
            try {
                // Verifica resultados
                 while (oConn.setResult.next()){                     
                     // Obtiene los datos de la Consulta
                     cbomedida.addItem(oConn.setResult.getString ("strProductoMedida"));
                 }
                 // Cierra Resultados
                 oConn.setResult.close();
            }catch (SQLException ex) {
                //JOptionPane.showMessageDialorootPane,ex);
                oFunc.SubSistemaMensaje(ex.toString());
                Logger.getLogger(frmClase05.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // Desselecciona
        cbomedida.setSelectedIndex(-1);
    }
   
    private void setJTexFieldChanged(JTextField txt){
        // Crear una variable para 
        DocumentListener documentListener = new DocumentListener() {
            
            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                //oFunc.SubSistemaMensaje("Update");
                printIt(documentEvent);
            }
            
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                //oFunc.SubSistemaMensaje("Insert");
                printIt(documentEvent);
            }
            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                //oFunc.SubSistemaMensaje("Remove");
                printIt(documentEvent);
            }
        };
        
        // Agregamos el capturador de evento del objeto
        txt.getDocument().addDocumentListener(documentListener);
    }
    private void printIt(DocumentEvent documentEvent){
    
        // Definir una variable de tipo de eventos
        DocumentEvent.EventType type = documentEvent.getType();

        if (type.equals(DocumentEvent.EventType.CHANGE)){
          //  oFunc.SubSistemaMensaje("Change");
             txtEjemploJTextFieldChanged();
        }
        else if (type.equals(DocumentEvent.EventType.INSERT)){
             txtEjemploJTextFieldChanged();

        }
        else if (type.equals(DocumentEvent.EventType.REMOVE)){
            txtEjemploJTextFieldChanged();
        }
    }
    private void txtEjemploJTextFieldChanged(){
        
        int Suma;
        int Inicial;
        int Entradas;
        int Salidas;
        
        //if (txtInicial.getText().isEmpty())
        if (! oFunc.fnBoolIsInteger(txtinicial.getText()))    
            this.lblactual.setText("Error");
        else{
            // Obtengo los datoa
            Inicial = Integer.valueOf(txtinicial.getText());
            Entradas = Integer.valueOf(lblentradas.getText());
            Salidas = Integer.valueOf(lblsalida.getText());
            Suma = Inicial+Entradas-Salidas;
            //Copiar el contenido del jtextfield al jlabel
            this.lblactual.setText(String.valueOf(Suma));
        }
    }
    
    // Setter para asiganar código
    public void setCodigo(String sCodigo){
        // Coloco el Código
        txtcodigo.setText(sCodigo);
    }
       
    
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
            java.util.logging.Logger.getLogger(frmClase07b.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClase07b.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClase07b.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClase07b.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmClase07b dialog = new frmClase07b(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnaceptar;
    private javax.swing.JButton btncancel;
    private javax.swing.JComboBox cbomedida;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblIcoCodigo;
    private javax.swing.JLabel lblIconCosto;
    private javax.swing.JLabel lblIconDesc;
    private javax.swing.JLabel lblIconInicial;
    private javax.swing.JLabel lblIconMedida;
    private javax.swing.JLabel lblIconNombre;
    private javax.swing.JLabel lblIconPrecio;
    private javax.swing.JLabel lblactual;
    private javax.swing.JLabel lblentradas;
    private javax.swing.JLabel lblsalida;
    private javax.swing.JPanel pnlDetalle;
    private javax.swing.JTextField txtcodigo;
    private javax.swing.JTextField txtcosto;
    private javax.swing.JTextField txtdesc;
    private javax.swing.JTextField txtinicial;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtprecio;
    // End of variables declaration//GEN-END:variables
}
