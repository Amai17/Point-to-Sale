

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amai
 */
public class frmClase08 extends javax.swing.JDialog {

    /**
     * Creates new form Clase08
     */
    //Variables para las funciones propias
    Funciones oFunc = new Funciones();
    connection oConn = new connection();
    //Las posiciones delas ventanas
    int iPosX;
    int iPosY;
    
    public frmClase08(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //Centro la forma
        this.setLocationRelativeTo(null);
        //Obtiene  la posicion d ela ventana
        iPosX = this.getLocation().x;
        iPosY = this.getLocation().y;
        //Deshabilita  resize
        this.setResizable(false);
        
        //Inicializa la captura
        sbInicializaCaptura();
    }
    
    //Inicializa los datos
    private void sbInicializaCaptura(){
        //Variable para la fecha
        Date dateHoy = new Date();
        //Variable par dar formato ala fecha
        SimpleDateFormat formato =new SimpleDateFormat("yyyy.MM.dd hh.mm.ss");
        
        //Inicializa Folio y Fecha
        btnFolio.setText(String.format("%05d",fnIntFolioGet()));
        lblFechaView.setText(formato.format(dateHoy));
        //Inicializa Codigo, Nombre y Existencia
        txtCodigo.setText(null);
        lblNombreView.setText(null);
        lblExistenciaView.setText(null);
        //Inicializa Costo, Precio y Cantidad
        txtCosto.setText("0");
        txtPrecio.setText("0");
        txtCantidad.setText("0");
        //Inicializa Descripcion
        txtDescripcion.setText(null);
    }
    public boolean fnBoolProductoExiste(boolean bDisplay){
    //Para devolver el resultado
    boolean bResultado=false;
    //Para el Query
    String sQuery;
    
    //Prepara el query
    sQuery = "Select * from tblproductos Where strProductoCodigo='"+txtCodigo.getText()+"'";
    
    //Ejecuta el Query
    oConn.FnBoolQueryExecute(sQuery);
    
    //Captura el error
    try{
        //Verifico que aya habido resusultados
        if(oConn.setResult.next()){
             if(bDisplay){
                 //Obtego los datos
                 lblNombreView.setText(oConn.setResult.getString("strProductoNombre"));
                 lblExistenciaView.setText(oConn.setResult.getString("intProductoActual"));
                 txtCosto.setText(oConn.setResult.getString("decProductoCosto"));
                 txtPrecio.setText(oConn.setResult.getString("decProductoPrecio"));
             }
             //Resultado
             bResultado =true;
    }
        else
        {
            if(bDisplay)
            {
                lblNombreView.setText("Producto no existe");
                lblExistenciaView.setText("*****");
                txtCosto.setText("*****");
                txtPrecio.setText("*****");
            }
        }
        //Cierro los Resultados
        oConn.setResult.close();
    }  catch (SQLException ex) {
        oFunc.SubSistemaMensaje("fnBoolProductoExiste:"+ex.getMessage().toString());
    }
    return bResultado;
    }
    //Funcion para el folio
    private int fnIntFolioGet(){
        //Variable para el resultado
        int iResult=0;
        //Variable para el Query
        String sQuery;
        
        //Prepara el Query
        sQuery ="Select intFolioInventario from tblfolios";
        //Ejecuta el Query
            oConn.FnBoolQueryExecute(sQuery);
            
            //Capturo el error
            try{
                // Verifico que aya habido resultados
                if(oConn.setResult.next()){
              //Obtine el folio siguiente
              iResult = oConn.setResult.getInt("intFolioInventario");
                }
            } catch (SQLException ex) {
                oFunc.SubSistemaMensaje("fnIntFolioGet:"+ex.getMessage().toString());
            }
            //Retorna el resultado
            return iResult;
    }
    //Verifica que el Folio exista
    public boolean fnBoolFolioExiste(){
        //Para devolver el resultado
        boolean bResultado=false;
        //Para el Query
        String sQuery;
        
        //Prepara el Query
        sQuery ="Select * from tblinventario Where intInvFolio="+btnFolio.getText();
        
        // Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        //Capturo el Error
        try{
            //verifico que haya habido resultados
            if(oConn.setResult.next())
            {
                //Coloca los datos  del Folio
                lblFechaView.setText(oConn.setResult.getString("datInvFecha"));
                
                 if (oConn.setResult.getString("strInvMovimiento").lastIndexOf("Entrada")>=0)
                   cboMovimiento.setSelectedIndex(0);
                else
                   cboMovimiento.setSelectedIndex(1);
                
                //Obtengo los datos
                txtCodigo.setText(oConn.setResult.getString("strProductoCodigo"));
                txtCantidad.setText(oConn.setResult.getString("intInvCantidad"));
                txtDescripcion.setText(oConn.setResult.getString("txtInvDescripcion"));
            //Resultado
            bResultado  = true;
            }
            //Cierro la conexion
            oConn.setResult.close();
            
        } catch(SQLException ex){ 
            oFunc.SubSistemaMensaje("fnBoolFolioExiste:"+ex.getMessage().toString());
        }
        return bResultado;
    }
    private boolean fnBoolDatosCorrectos(){
        //Variable de resultados
        boolean  bResultado=true;
        String sMensaje="";
        int iValor;
        String sValor;
        
        //valida el codigo
        if(txtCodigo.getText().trim().isEmpty() || txtCodigo.getText().trim().length()==0){
            sMensaje =" Código \n";
            bResultado =false;
        }
        else
            if(! fnBoolProductoExiste(false)){
                sMensaje = "Producto no Existe \n";
                bResultado = false;
            }
        //valida el costo
        sValor = txtCosto.getText().toString() ;
        if(! oFunc.fnBoolIsDecimal(sValor)){
            sMensaje = sMensaje + "Costo \n";
            bResultado = false;
        }
        else
            if(Double.valueOf(sValor) <=0)
            {
                sMensaje  = sMensaje + "Costo \n";
                bResultado = false;
            }
        //valida el precio
         sValor = txtPrecio.getText().toString() ;
        if(! oFunc.fnBoolIsDecimal(sValor)){
            sMensaje = sMensaje + "Precio \n";
            bResultado = false;
        }
        else
            if(Double.valueOf(sValor) <=0)
            {
                sMensaje  = sMensaje + "Precio \n";
                bResultado = false;
            }
        //Valida Cantidad
         sValor = txtCantidad.getText().toString() ;
        if(! oFunc.fnBoolIsInteger(sValor)){
            sMensaje = sMensaje + "Cantidad \n";
            bResultado = false;
        }
        else
            if(Integer.valueOf(sValor) <=0)
            {
                sMensaje  = sMensaje + "Cantidad \n";
                bResultado = false;
            }
        //Valida Descripcion
        if(txtDescripcion.getText().isEmpty()){
         sMensaje  = sMensaje + "Descripcion \n";
         bResultado = false;   
        }
        if(! sMensaje.isEmpty())
            oFunc.SubSistemaMensaje("Debe revisar los siguientes datos:\n "+sMensaje);
       
            // retorna el resultado
            return bResultado;
        
    }
    
    private boolean fnBoolFolioIncrementa(){
        //Variable de resultados
        boolean bResult = false;
        String strSqlStmt;
        
        //Query para actualizar el folio
        strSqlStmt = " Update tblFolios set intFolioInventario = intFolioInventario + 1";
        
        if(oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
            bResult = true;
        return bResult;
    }
    private void sbRegistraEntrada(){
        String sQuery; //Para el Query
        String Pass; //Para  el Password
        
        //Coloca la transaccion a false  que sea una transacion
        oConn.SubAutoCommit(false);
        
        //Intenta  realizar  las 3  afectaciones a la BD
        if(fnBoolProductoActualiza(true))
            if(fnBoolFolioIncrementa())
                if(fnBoolInventarioRegistra(true))
                {
                    //Commit
                    oConn.SubCommit();
                    //Mensaje
                    oFunc.SubSistemaMensaje("Se ha registrado la Entrada con Éxito");
                }
               else
                //Realizo Rollback
                oConn.SubRollBack();
            else
               //Realiza el Rolback
               oConn.SubRollBack();
         else
             //Realiza el Rolback
             oConn.SubRollBack();
    }   
    private void sbRegistraSalida(){
        String sQuery; //Para el Query
        String sPass; //Para el pasword
        
        //Coloca la transaccion a false para que sea una transaccion
        oConn.SubAutoCommit(false);
        
        //Intenta  realizar  las 3  afectaciones a la BD
        if(fnBoolProductoActualiza(false))
            if(fnBoolFolioIncrementa())
                if(fnBoolInventarioRegistra(false ))
                {
                //Commit
                    oConn.SubCommit();
                oFunc.SubSistemaMensaje("Se ha registrado la Salida con Éxito");
                }
               else
                //Realizo Rollback
                oConn.SubRollBack();
            else
               //Realiza el Rolback
               oConn.SubRollBack();
         else
             //Realiza el Rolback
             oConn.SubRollBack();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        btnFolio = new javax.swing.JButton();
        lblFechaView = new javax.swing.JLabel();
        cboMovimiento = new javax.swing.JComboBox<String>();
        txtCodigo = new javax.swing.JTextField();
        lblNombreView = new javax.swing.JLabel();
        lblExistenciaView = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        txtPrecio = new javax.swing.JTextField();
        txtCantidad = new javax.swing.JTextField();
        txtDescripcion = new java.awt.TextArea();
        btnInicializar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Capture Movimiento", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N

        jLabel1.setText("Folio:");

        jLabel2.setText("Fecha:");

        jLabel3.setText("Movimiento:");

        jLabel4.setText("Código:");

        jLabel5.setText("Nombre:");

        jLabel6.setText("Existencia:");

        jLabel7.setText("Costo:");

        jLabel8.setText("Precio:");

        jLabel9.setText("Cantidad:");

        jLabel10.setText("Descripcion:");

        btnFolio.setText("00000");
        btnFolio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFolioActionPerformed(evt);
            }
        });

        lblFechaView.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));

        cboMovimiento.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Entrada" }));

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        lblNombreView.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));

        lblExistenciaView.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(204, 204, 255), null));

        btnInicializar.setText("Inicializar");
        btnInicializar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicializarActionPerformed(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnFolio, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                            .addComponent(lblFechaView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboMovimiento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCodigo)
                            .addComponent(lblNombreView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblExistenciaView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCosto)
                            .addComponent(txtPrecio)
                            .addComponent(txtCantidad)))
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnInicializar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptar)
                .addGap(106, 106, 106))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnFolio))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblFechaView, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboMovimiento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblNombreView, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblExistenciaView, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCosto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInicializar)
                    .addComponent(btnAceptar))
                .addGap(26, 26, 26))
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

    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        // TODO add your handling code here:
        this.setLocation(iPosX,iPosY);
    }//GEN-LAST:event_formComponentMoved

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        // TODO add your handling code here:
        fnBoolProductoExiste(true);
        //verifica si presiono F1
        if(evt.getKeyCode() ==112){ //F1 =112
            //Inicializa el dato de consulta
            Globales.sConsultaDato ="";
            // Coloca el Query de la consulta
                connection.sQuery ="Select strProductoCodigo as Codigo, strProductoNombre as Nombre from tblproductos";
               
                //Declaro una variable  de instancia  de la forma consulta
                frmClase08b frmCodigos = new frmClase08b(null,true);
                frmCodigos.setTitle("Consulta de Códigos");
                frmCodigos.setVisible(true);
  //Agrregar if              
                //Despliega el Dato Seleccionado
                txtCodigo.setText(Globales.sConsultaDato);
                //Despliega el Producto
                fnBoolProductoExiste(true);
        }
        
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void btnFolioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFolioActionPerformed
        // TODO add your handling code here:
        Globales.sConsultaDato="";
        //Coloca el Query de la consulta
        connection.sQuery ="Select intInvFolio as Folio, strInvMovimiento as Movto, datInvFecha as Fecha from `tblinventario` ";
            
        //Crea  la forma  Coloca  el titulo  y  lo hace visible
        frmClase08b frmFolio = new frmClase08b(null,true);
        frmFolio.setTitle("Consulta de Folio");
        frmFolio.setVisible(true);
        
        //Despliega el Dato Seleccionado
        btnFolio.setText(String.format("%05d", Integer.parseInt(Globales.sConsultaDato)));
        
        //Despliega  los datos  del Folio
        fnBoolFolioExiste();
        fnBoolProductoExiste(true);
    }//GEN-LAST:event_btnFolioActionPerformed

    private void btnInicializarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicializarActionPerformed
        // TODO add your handling code here:
        sbInicializaCaptura();
    }//GEN-LAST:event_btnInicializarActionPerformed
    private boolean fnBoolProductoActualiza(boolean bEntrada){
            //Variable de Resultados
            boolean bResult=false;
            //Para el Query
            String strSqlStmt;
            
         //Query que actualiza  el Precio , costo , entradas  y existencias  del producto
         strSqlStmt = "Update tblproductos set ";
         strSqlStmt = strSqlStmt + " decProductoCosto = "+txtCosto.getText()+",";
         strSqlStmt = strSqlStmt + " decProductoPrecio = "+txtPrecio.getText()+",";
            
         //Verifica  si es entrada o salida
         if(bEntrada)
         {
         strSqlStmt = strSqlStmt + " intProductoEntradas = intProductoEntradas  + "+txtCantidad.getText()+"'";    
         strSqlStmt = strSqlStmt + " intProductoActual   = intProductoActual    + "+txtCantidad.getText();
         }
         else
         {
         strSqlStmt = strSqlStmt + " intProductoSalidas = intProductoSalidas + "+txtCantidad.getText()+"'";
         strSqlStmt = strSqlStmt + " intProductoActual = intProductoActual - "+txtCantidad.getText();
         }
          //Condiciona de acuerdo al código
          strSqlStmt = strSqlStmt + " Where strProductoCodigo = '"+txtCodigo.getText()+"'";
           if(oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
               bResult =true;
           //retorna el resulktado de la operac ion
           return bResult;
    }  
    
    private boolean fnBoolInventarioRegistra(boolean bEntrada){
        //variable para la fecha
         Date dateHoy = new Date();
         
         //Variuable para el formato
         SimpleDateFormat formato =new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
         
         //Variable de resultados
         boolean bResult = false;
         
         String strSqlStmt;  //Para el Query
         
         //Query para insertar el Movimiento de Inventario
         // Query para Insertar el Movimiento de Inventario
        strSqlStmt = "Insert into tblInventario (intInvFolio,strInvMovimiento,datInvFecha,intInvCantidad,strProductoCodigo,intVentaFolio,txtInvDescripcion)";
        strSqlStmt = strSqlStmt + " Values ("+btnFolio.getText()+",";
        if (bEntrada)
           strSqlStmt = strSqlStmt + "'Entrada',";
         else
             strSqlStmt = strSqlStmt + "'Salida',";
          strSqlStmt = strSqlStmt + "'"+formato.format(dateHoy)+"',";
          strSqlStmt = strSqlStmt + txtCantidad.getText()+",";
          strSqlStmt = strSqlStmt + "'"+txtCodigo.getText()+ "',0,";
          strSqlStmt = strSqlStmt + "'"+txtDescripcion.getText()+"')";
          
          //Ejecuta la insercion
          if (oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
              bResult = true;
          
          //Retorna el resultado
          return bResult;
    }
    
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
       if (fnBoolDatosCorrectos())
            
            // Valida que operación va a realizar
            if (cboMovimiento.getSelectedIndex()==0)
                sbRegistraEntrada();
            else
                sbRegistraSalida();
               
            // Inicializa la captura
            sbInicializaCaptura();
    }//GEN-LAST:event_btnAceptarActionPerformed

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
            java.util.logging.Logger.getLogger(frmClase08.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClase08.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClase08.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClase08.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmClase08 dialog = new frmClase08(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnFolio;
    private javax.swing.JButton btnInicializar;
    private javax.swing.JComboBox<String> cboMovimiento;
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
    private javax.swing.JLabel lblExistenciaView;
    private javax.swing.JLabel lblFechaView;
    private javax.swing.JLabel lblNombreView;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCosto;
    private java.awt.TextArea txtDescripcion;
    private javax.swing.JTextField txtPrecio;
    // End of variables declaration//GEN-END:variables
}
