
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Amai
 */
public class frmClase04 extends javax.swing.JFrame {

    // Declaro variable de Conexion
    connection oConn   = new connection();
    Globales oGlobal = new Globales();
    /**
     * Creates new form clase03
     */
    public frmClase04() {
        
           //intento conexion
        if (!oConn.FnBoolConnectionOpen("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/puntodeventa", "root", "")) {
            //mensaje de que no logro la conexion
            JOptionPane.showMessageDialog(null, "No se logro la conexion al Host");
            //finaliza la aplicacion
            System.exit(0);
        } else {
            //inicial los compnentes
            initComponents();

            //Centro la forma
            this.setLocationRelativeTo(null);
            this.setTitle("Iniciando Sesion");

            //Inicializar
            lblUsuario.setText("Usuario");
            lblPassword.setText("Password");
            txtusuario.setText("");
            txppass.setText("");
            btnCancelar.setText("Cancelar");
            btnAceptar.setText("Aceptar");

          /*  //deshabilitar los despliegues
            lblName.setText("Name");
            lblRole.setText("Role");
            lblName.setEnabled(false);
            lblName.setBackground(Color.DARK_GRAY);
            lblRole.setEnabled(false);
            lblRole.setBackground(Color.DARK_GRAY);*/
        }
       
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
        lblUsuario = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        Role = new javax.swing.JLabel();
        txppass = new javax.swing.JPasswordField();
        txtusuario = new javax.swing.JTextField();
        lblName = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JButton();
        btnAceptar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Loggin");

        lblUsuario.setText("Usuario:");

        lblPassword.setText("Password:");

        Name.setText("Name");

        Role.setText("Role:");

        lblName.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblRol.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuario)
                    .addComponent(lblPassword)
                    .addComponent(Name)
                    .addComponent(Role))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txppass, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addComponent(txtusuario, javax.swing.GroupLayout.Alignment.LEADING))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(lblRol, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(124, 124, 124))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtusuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txppass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Name))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRol, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Role))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        btnCancelar.setText("Cancelar");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
        });

        btnAceptar.setText("Aceptar");
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
        });
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnCancelar)
                        .addGap(35, 35, 35)
                        .addComponent(btnAceptar))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancelar)
                    .addComponent(btnAceptar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"Presiona Cancelar");
        this.dispose();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        // TODO add your handling code here:
        //JOptionPane.showMessageDialog(null,"Presiona Aceptar");
        // Declaro variable para Sentencia
        String sqlStmt;
        
        // Verifico que esté intentando ingresar
        if (btnAceptar.getText().equals("Aceptar"))
        {
            // Valida que datos correctos
            
            if (FnBoolValidaDatos())
            {
                // Prepara Variable para realizar el Query
                sqlStmt = "Select * from tblusers";
                sqlStmt += " Where strUserIde='"+txtusuario.getText()+"'";
                sqlStmt += " And   strUserPass='"+String.valueOf(txppass.getPassword()) +"'";

                // Capturo el Error
                try {
                    // Ejecuto la Consulta
                    if (oConn.FnBoolQueryExecute(sqlStmt)){    

                        // Verifico que haya habido resultados
                        if (oConn.setResult.next()){
                            //oConn.setResult.first();
                            //System.out.println ("Col 1:"+oConn.setResult.getString (1)); 
                            lblName.setText(oConn.setResult.getString ("strUserName"));
                            lblRol.setText(oConn.setResult.getString ("strRoleName"));
                            Globales.sRole = oConn.setResult.getString ("strRoleName");

                            //Cambio el Texto del Boton
                            btnAceptar.setText("Iniciando");
                            
                            // Coloco en variable global el usuario del sustema
                            Globales.sSystemUser=txtusuario.getText();
                            
                            //Deshabilito Cancelar
                            btnCancelar.setEnabled(false);
                        }
                        else{
                            lblName.setText("Usuario y contraseña incorrecta");
                            lblRol.setText("Intenta de nuevo ...");
                        }
                        // Cierra la Consulta
                        oConn.setResult.close();
                    }
                } catch (RuntimeException e){
                     // Mensaje de que no logrola conexion
                     JOptionPane.showMessageDialog(null,"Este Mensaje"+e);

                }catch (com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException e){
                     // Mensaje de que no logrola conexion
                     JOptionPane.showMessageDialog(null,"Este Mensaje"+e);

                }catch (SQLException ex) {                                
                    Logger.getLogger(frmClase04.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else
        {
            
            // Declaro una variable de la Nueva Forma
            frmClase04b frmPrincipal = new frmClase04b(); 
            
            // Hago visible la forma
            frmPrincipal.setVisible(true);
            
            /*Component[] components;       
            components = frmPrincipal.getComponents();  
            System.out.println("components.length " + components.length );  
            for (int i = 0; i < components.length; i++) 
            {  
                 System.out.println("view components[i] " + components[i]);  
                 components[i].setEnabled(false);  
            }  
            */
            if (oGlobal.fnBoolBitacoraActiva())
                oGlobal.sbGrabaBitacora(Globales.sSystemUser, "Ingreso al Sistema");
            
            // Cierra esta forma
            this.dispose();
        }
    }

     // Función para Validar Datos de Captura
    private boolean FnBoolValidaDatos(){
        // Variable para el Mensaje
        String sMensaje="";
        
        // Valida el Usuario
        if (txtusuario.getText().length()==0)
        {
            // Añade el Usuario al Mensaje
            sMensaje = "Usuario";
            
            // Coloca el Foco en el Objeto
            txtusuario.requestFocus();
        }
        
        // Valida el Password
        if (txppass.getPassword().length==0){
            // Valida si no falló con el usuario para mandar el foco
            if (sMensaje.length()==0)
                // Mando el Foco al Password si no ha fallado algun dato previo
                txppass.requestFocus();
            
            // Añade el Usuario al Mensaje
            sMensaje = sMensaje+"\n"+"Password";
        }
        // Verifica si hay que desplegar el Mensaje
        if (sMensaje.length() > 0){
            //Prepara el Mensaje
            sMensaje = "Debe de Capturar los siguiente datos:\n"+sMensaje;
            JOptionPane.showMessageDialog(null, sMensaje);
            // Devuelve falso
            return false;
        }
        else{
            // Retorna Correcto
            return true;    
        }
    }//GEN-LAST:event_btnAceptarMouseClicked


    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(frmClase03.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClase03.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClase03.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClase03.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmClase04().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Name;
    private javax.swing.JLabel Role;
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txppass;
    private javax.swing.JTextField txtusuario;
    // End of variables declaration//GEN-END:variables
}
