
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amai
 */
public class Globales {
    
        // Variable para guardar las consultas
    static String sConsultaDato;
    
    // Proceso de Venta
    static String sSystemUser;  // Usuario del Sistema
    static String sTotalVenta;
    static boolean bVentaRealizada;
    static String sFormaPago;
    static String sReferencia;
    static String sMotivoCancelacion;
    static String sFecha1;   // Intervalos en Reportes
    static String sFecha2;   // Intervalos en Reportes
    static String sRole;     // Para el Role del Usuario
    static String sUsuario;  // Para el Reporte de Bitacora
    
    // Declaro variable de Conexion
    connection oConn = new connection();
    
    public boolean fnBoolBitacoraActiva()
    {
      String  sQuery;
      int     intActiva;
      boolean bResult=false;
      
      // Prepara query para verificar que bitácora esté activa
      sQuery =" Select intBitacoraActiva from tblparametros";
      
      // Ejecuta el Query
      oConn.FnBoolQueryExecute(sQuery);
      
        try 
        {
            oConn.setResult.next();
            intActiva = oConn.setResult.getInt("intBitacoraActiva");
            if (intActiva>0)
                bResult=true;
        } catch (SQLException ex) {
            Logger.getLogger(frmClase04.class.getName()).log(Level.SEVERE, null, ex);
        }
      
        // Retorna el Resultado
      return bResult;
      
    }
    
    // Graba en bitacora
    public void sbGrabaBitacora(String sUser, String sDescripcion)
    {
        // Variable para el Query 
        String sQuery;
       
        // Variable para la fecha
        Date dateHoy = new Date();
    
        // Variable para dar formato
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
       
       // Query para Insertar
       sQuery = "Insert into bitacora values (";
       sQuery = sQuery + "'"+sUser+"',";
       sQuery = sQuery + "'"+formato.format(dateHoy)+"',";
       sQuery = sQuery + "'"+sDescripcion+"')";
       
        // Ejecuta la Insercion
       oConn.FnBoolQueryExecuteUpdate(sQuery);
       
      
    }
}
