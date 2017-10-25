
import com.sun.istack.internal.logging.Logger;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.PrintJob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Amai
 */
public class frmClase09 extends javax.swing.JDialog {

    /**
     * Creates new form frmClase09
     */
    //Constantes para las columnas
    static final int INT_COL_CODIGO = 0;
    static final int INT_COL_NOMBRE = 1;
    static final int INT_COL_PRECIO = 2;
    static final int INT_COL_CANTIDAD = 3;
    static final int INT_COL_IMPORTE = 4;
    static final int INT_COL_EXISTENCIA = 5;
    static final int INT_COL_COSTO = 6;

    //Constantes para las columnas
    static final int INT_COL_EMPRESA = 0;
    static final int INT_COL_SUCURSAL = 1;
    static final int INT_COL_DIRECCION1 = 2;
    static final int INT_COL_DIRECCION2 = 3;
    static final int INT_COL_TELEFONOS = 4;
    static final int INT_COL_RFC = 5;

    static final int INT_KEY_LF = 10;
    static final int INT_KEY_RETURN = 13;
    static final int INT_KEY_F1 = 112;
    //variables para las funcionespropias
    Funciones oFunc = new Funciones();
    connection oConn = new connection();

    //Varioables para la operacion de las ventas
    boolean bAgruparProductos;
    boolean bVerificarExistencias;
    boolean bMensajesExito;
    String sTextoFinalTicket;

    public frmClase09(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //Maximiza la ventana
        this.setSize(this.getToolkit().getScreenSize());

        lblUtilidad.setVisible(false);
        lblCancelada.setVisible(false);

        //inicializa
        sbInicializa();
    }

    private void sbVentaParametros()
    {
       
        // Variable para el Query
        String sQuery;       
        
        // Prepara el Query
        sQuery ="Select * from 07tblparametros";
                
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        
        // Capturo el Error
        try {
            
            // Verifico que haya habido resultados
            if (oConn.setResult.next())
            {
        
                // Obtiene si agrupa productos
                if  (oConn.setResult.getInt("intAgruparProductos")==1)
                    bAgruparProductos = true;
                else
                    bAgruparProductos = false;
                
                // Obtiene si vende sin existencias
                if  (oConn.setResult.getInt("intVerificarExistencias")==1)
                    bVerificarExistencias = true;
                else
                    bVerificarExistencias = false;

                // Obtiene si vende sin existencias
                if  (oConn.setResult.getInt("intMensajesExito")==1)
                    bMensajesExito=true;
                else
                    bMensajesExito = false;
                
                // Obtiene si vende sin existencias
                sTextoFinalTicket = oConn.setResult.getString("strTicketInfoFinal");
                    

            }
        } catch (SQLException ex) {
            oFunc.SubSistemaMensaje("sbVentaParametros:"+ex.getMessage().toString());
        }
        
    
        
    }
    
    // Busca dato en la tabla
    private boolean fnProductoEnVenta()
    {
        // Variables para Fila y Columns
        int fil;
        String sDato;
        String sPrecio;
        String sCantidad;
        String sCosto;
        boolean bResult=false;
        int iProductos ;
        double decTotal;
        double decUtilidad;
        
        // Variable para formato
        DecimalFormat formateador = new DecimalFormat("####.00");
       
        
        // Pasa a mayúsculas
        sDato = txtCodigo.getText().trim().toUpperCase();
        
        if (!sDato.isEmpty())
        {            

            // Ciclo para buscar en la Venta
            for(fil = 0; fil < tblProductos.getRowCount();fil++)
            {
                //Obtiene dato contenido en una celda de la tabla
                String value = (String)tblProductos.getValueAt(fil, 0);

                // lo pasa a Mayúsculas
                value = value.toUpperCase();

                // Aqui verifica si encuentra el dato
                if(value.lastIndexOf(sDato)>=0)
                {     
    
                    // Obtengo la Cantidad y el Precio
                    sCantidad = tblProductos.getModel().getValueAt(fil,INT_COL_CANTIDAD).toString();
                    sPrecio = tblProductos.getModel().getValueAt(fil,INT_COL_PRECIO).toString();
                    sCosto = tblProductos.getModel().getValueAt(fil,INT_COL_COSTO).toString();

                    // Cantidad
                    iProductos = Integer.valueOf(sCantidad) + 1;
                    tblProductos.getModel().setValueAt(iProductos,fil, INT_COL_CANTIDAD);
                    
                    // Productos
                    iProductos = Integer.valueOf(lblProductos.getText()) + 1;
                    lblProductos.setText(String.valueOf(iProductos));

                    // Importe en la Venta
                    decTotal = Double.valueOf(sPrecio)*(Integer.valueOf(sCantidad)+1);    
                    tblProductos.getModel().setValueAt(formateador.format(decTotal),fil,INT_COL_IMPORTE);
                    
                    // El Total
                    decTotal = Double.valueOf(lblTotal.getText()) + Double.valueOf(sPrecio);                    
                    lblTotal.setText(formateador.format(decTotal));

                    // lA UTILIDAD
                    decUtilidad = Double.valueOf(lblUtilidad.getText()) + Double.valueOf(sPrecio) - Double.valueOf(sCosto);                    
                    lblUtilidad.setText(formateador.format(decUtilidad));

                    // Inicializa el Código
                    txtCodigo.setText(null);
                    txtCodigo.setForeground(Color.black);
                    bResult = true;
                }

            }    
        }
        
        
        
        // Retorna el Valor
        return bResult;
        
        
    }
    
    private void sbInicializa()
    {
        // Variable para la fecha
        Date dateHoy = new Date();
    
        // Variable para dar formato
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        // Inicializa Folio y Fecha
        btnFecha.setText(formato.format(dateHoy));
        
        // Coloca el Folio de Nueva Cuenta
        spnTicket.setValue(fnIntGetFolioVenta());
        
        // Coloca el Costo
        lblUtilidad.setText("0");
        lblProductos.setText("0");
        lblTotal.setText("0.00");
        
        //Habilita Controles
        sbHabilitaVenta(false);
        
        // Inicializa tabla de Productos
        sbTablaProductosInicializa();
        
        // Obtiene los parámetros de Venta
        sbVentaParametros();
        
    }
    
    // Habilita la Venta
    private void sbHabilitaVenta(boolean bVenta)
    {
        
        // Habilitados en Venta        
        tblProductos.setEnabled(bVenta);
        btnCobrar.setEnabled(bVenta);
        txtCodigo.setEnabled(bVenta);
        
        // Deshabilitados en Venta
        spnTicket.setEnabled(!bVenta);
        btnImprimir.setEnabled(!bVenta);
        btnIniciar.setEnabled(!bVenta);
        btnBuscar.setEnabled(!bVenta);
        
    }
    // Función para obtener el Folio de Venta
    private int fnIntGetFolioVenta()
    {
        // Variable para el Resultado
        int iResult=0;

        // Variable para el Query
        String sQuery;
        
        
        // Prepara el Query
        sQuery ="Select intFolioVenta from 05tblfolios";
                
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        
        // Capturo el Error
        try {
            
            // Verifico que haya habido resultados
            if (oConn.setResult.next())
            {
        
                // Obtiene el Folio Siguiente
                iResult = oConn.setResult.getInt("intFolioVenta");

            }
        } catch (SQLException ex) {
            oFunc.SubSistemaMensaje("fnIntGetFolioVenta:"+ex.getMessage().toString());
        }
        
        // Retorna el Resultado
        return iResult;
        
    }
    
    // Verifica que el Folio exista
    public boolean fnBoolVentaExiste()
    {
        // Para devolver el resultado
        boolean bResultado=false;
        
        int iCantidad;
        float fPrecio;
        float fImporte;
        String sFecha;
        
        // Inicializa-----------------------------------
        // Coloca el Costo
        lblUtilidad.setText("0");
        lblProductos.setText("0");
        lblTotal.setText("0.00");
        lblCancelada.setText("");
        
        // Inicializa tabla de Productos
        sbTablaProductosInicializa();               
        
        // Para el Query
        String sQuery;
        
        // Variable para el modelo de la tabla
        DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();
         
        // Prepara los datos vacios iniciales
        Object [] oFila = new Object[10];
        
        // Prepara el Query
        sQuery  = "Select * from 08tblventas Where intVentaFolio="+spnTicket.getValue().toString();
        
        //Ejecuta el Query
        if (oConn.FnBoolQueryExecute(sQuery))
        {
            try 
            {
                
                // Verifico que haya habido resultados
                if (oConn.setResult.next())
                {
                    //Coloca los datos de la venta
                    spnTicket.setValue(oConn.setResult.getInt("intVentaFolio"));
                    
                    // Inicializa Folio y Fecha                   
                    sFecha = oConn.setResult.getString("dateVentaFecha");
                    sFecha = sFecha.substring(0, sFecha.length()-2);
                    btnFecha.setText(sFecha);
                    
                    //btnFecha.setText(oConn.setResult.getString("dateVentaFecha"));
                    lblTotal.setText(String.format("%5.2f",(float)oConn.setResult.getFloat("decVentaImporte")));
                    lblProductos.setText(oConn.setResult.getString("intVentaProductos"));
                    lblCancelada.setText(oConn.setResult.getString("strVentaCancelada"));
                    
                    // Resultado
                    bResultado = true;

                }
                
                // Cierro los Resultados
                oConn.setResult.close();
                
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(frmClase09.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        
        // Obtiene el detalle de la Venta
        if (bResultado)
        {
            // Prepara el Query
            sQuery  = "Select * from 09tblventasdetalle Where intVentaFolio="+spnTicket.getValue().toString();

            //Ejecuta el Query
            if (oConn.FnBoolQueryExecute(sQuery))
            {
                try 
                {
                    while (oConn.setResult.next())
                    {
                        // Obtengo los datos en el Objeto Fila
                        oFila[INT_COL_CODIGO]=oConn.setResult.getString("strProductoCodigo");
                        oFila[INT_COL_NOMBRE]=oConn.setResult.getString("strProductoNombre");                
                        oFila[INT_COL_PRECIO]=oConn.setResult.getString("decProductoPrecio");
                        oFila[INT_COL_CANTIDAD]=oConn.setResult.getString("intVentaCantidad");

                        // Obtiene Cantidad, Precio y Calcula Importe
                        iCantidad = oConn.setResult.getInt("intVentaCantidad");
                        fPrecio = oConn.setResult.getFloat("decProductoPrecio");
                        fImporte = iCantidad * fPrecio;
                        oFila[INT_COL_IMPORTE] = String.format("%5.2f",fImporte);
                                
                        // Agrega el Dato
                        tblDatos.addRow(oFila);    
                    }
               
                    // Cierro los Resultados
                    oConn.setResult.close();

                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(frmClase09.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Coloca los datos
                tblProductos.setModel(tblDatos);
            }    
        }    
                
        // Retorna el Resultado
        return bResultado;
        
    }
    
    
    // Verifica que el Producto exista
    public void sbProductoAgrega()
    {
         // Para el Total
        double decTotal;
        double decCosto;
        double decPrecio;
        double decUtilidad;
        boolean bContinua = true;
         // Para el Número de Artículos
        int iProductos;
        
        
        // Variable para formato
        DecimalFormat formateador = new DecimalFormat("####.00");        
       
        // Variable para el modelo de la tabla
        DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();

        // Prepara los datos vacios iniciales
        Object [] oFila = new Object[10];
        
        // Para el Query
        String sQuery;
        
        // Valida que haya algo en el Codigo
        if (! txtCodigo.getText().trim().isEmpty())
        {
            // Prepara el Query
            sQuery  = "Select * from 04tblProductos Where strProductoCodigo='"+txtCodigo.getText()+"'";

            //Ejecuta el Query
            oConn.FnBoolQueryExecute(sQuery);

            // Capturo el Error
            try {
                // Verifico que haya habido resultados
                if (oConn.setResult.next())
                {
                    
                    // Verifica si checa existencias
                    if (bVerificarExistencias)
                    {
                        // Verifica existencias
                        if (oConn.setResult.getInt("intProductoActual")<=0)
                        {
                            oFunc.SubSistemaMensaje("Producto Sin Existencia");
                            txtCodigo.setForeground(Color.red);
                            bContinua=false;
                        }
                    }   
                    if (bContinua)
                    {
                    
                        // Obtengo los datos en el Objeto Fila
                        oFila[INT_COL_CODIGO]=oConn.setResult.getString("strProductoCodigo");
                        oFila[INT_COL_NOMBRE]=oConn.setResult.getString("strProductoNombre");
                        oFila[INT_COL_PRECIO]=oConn.setResult.getString("decProductoPrecio");
                        oFila[INT_COL_CANTIDAD]="1";
                        oFila[INT_COL_IMPORTE]=oConn.setResult.getString("decProductoPrecio");
                        oFila[INT_COL_EXISTENCIA]=oConn.setResult.getString("intProductoActual");
                        oFila[INT_COL_COSTO]=oConn.setResult.getString("decProductoCosto");


                        // Obtengo valores de Precio y Costo
                        decPrecio = oConn.setResult.getDouble("decProductoPrecio");
                        decCosto = oConn.setResult.getDouble("decProductoCosto");

                        // Agrega el Dato
                        //tblDatos.addRow(oFila);
                        tblDatos.insertRow(0, oFila);                       

                        // Coloca el Modelo de Nueva Cuenta
                        tblProductos.setModel(tblDatos);
                        txtCodigo.setForeground(Color.black);

                        // Limpia el código al final
                        txtCodigo.setText(null);

                        // Productos
                        iProductos = Integer.valueOf(lblProductos.getText()) + 1;
                        lblProductos.setText(String.valueOf(iProductos));

                        // El Total
                        decTotal = Double.valueOf(lblTotal.getText()) + Double.valueOf(oConn.setResult.getString("decProductoPrecio"));
                        lblTotal.setText(formateador.format(decTotal));

                        // La Utilidad
                        decUtilidad = Double.valueOf(lblUtilidad.getText()) + decPrecio - decCosto;
                        lblUtilidad.setText(formateador.format(decUtilidad));

                    }
                }
                else 
                {
                    oFunc.SubSistemaMensaje("Producto No Existe");
                    txtCodigo.setForeground(Color.red);
                }

                // Cierro los Resultados
                oConn.setResult.close();

            } catch (SQLException ex) {
                oFunc.SubSistemaMensaje("sbProductoAgrega:"+ex.getMessage().toString());
            }
        }
            
        
        
    }
    
    // Procedimiento para dar formato a la Tabla
    private void sbTablaProductosInicializa()
    {

        
        // Declaro un modelo de datos
        DefaultTableModel modelo = new DefaultTableModel();      
                
        // Añadimos columnas al modelo de datos
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Precio");
        modelo.addColumn("Cantidad");
        modelo.addColumn("Importe");
        modelo.addColumn("Existencia");
        modelo.addColumn("Costo");
        
                
        // Coloca el Modelo en la Tabla
        tblProductos.setModel(modelo);
        
        // Directamente
        tblProductos.getColumn("Codigo").setWidth(150);
        tblProductos.getColumn("Codigo").setPreferredWidth(150);        
        tblProductos.getColumn("Nombre").setWidth(450);
        tblProductos.getColumn("Nombre").setPreferredWidth(450);                
        tblProductos.setFont(new java.awt.Font("Lucida Sans", 0, 14)); 
       
        // Alinear a la derecha las cantidades y precios
        DefaultTableCellRenderer cellAlinear = new DefaultTableCellRenderer();
        
        // Asignamos el Alineamiento Horizontal a la derecha
        cellAlinear.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Asignamos la Variable de celda de alineamiento a cada una de las columnas a alinear
        tblProductos.getColumnModel().getColumn(2).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(3).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(4).setCellRenderer(cellAlinear);
        tblProductos.getColumnModel().getColumn(5).setCellRenderer(cellAlinear);
       
       
        // Color de los Encabezados
        tblProductos.getTableHeader().setBackground(Color.lightGray);
        tblProductos.getTableHeader().setForeground(Color.blue);
        tblProductos.getTableHeader().setFont(new java.awt.Font("Lucida Sans", 0, 14)); 
        
        
        // Remueve la Columna de la Vista
        tblProductos.removeColumn(tblProductos.getColumnModel().getColumn(6));
                
    }
    
    private boolean fnBoolCancelaVentaEncabezado()
    {
        // Para los resultados
        boolean bResult = false;
        
        // Para la sentencia Sql
        String strSqlStmt; // Para el Query
        
        // Query para actualizar el Folio
        strSqlStmt =  " Update 08tblventas Set ";
        strSqlStmt += " strVentaCancelada = '"+Globales.sMotivoCancelacion+"'";
        strSqlStmt += " Where intVentaFolio = "+spnTicket.getValue().toString();
        
        // Ejecuta la Sentencia
        if (oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
            bResult = true;
        
        // Retorna el Valor
        return bResult;    
    }
    
    // Función para Grabar el Encabezado de la Venta
    private boolean fnBoolGrabaVentaEncabezado()
    {
        
        // Para los resultados
        boolean bResult = false;
                
        // Para la sentencia Sql
        String strSqlStmt; 
        
        // Query para actualizar el Folio
        strSqlStmt = "Insert into 08tblventas ";
        strSqlStmt = strSqlStmt + "(intVentaFolio,";
	strSqlStmt = strSqlStmt + "dateVentaFecha,";
	strSqlStmt = strSqlStmt + "intVentaProductos,";
	strSqlStmt = strSqlStmt + "decVentaImporte,";
	strSqlStmt = strSqlStmt + "decVentaUtilidad,";
	strSqlStmt = strSqlStmt + "strVentaFormaPago,";
	strSqlStmt = strSqlStmt + "strVentaCancelada,";
	strSqlStmt = strSqlStmt + "strVentaReferencia)";
        strSqlStmt = strSqlStmt + "Values ";        
        //strSqlStmt = strSqlStmt + "("+String.valueOf(fnIntGetFolioVenta())+",";    //ojo    
        strSqlStmt = strSqlStmt + "("+spnTicket.getValue().toString()+",";    //ojo    
        strSqlStmt = strSqlStmt + "'"+btnFecha.getText()+"',";
        strSqlStmt = strSqlStmt + lblProductos.getText()+",";
        strSqlStmt = strSqlStmt + lblTotal.getText()+",";
        // La utilidad esta desplegada momentaneamente en la forma de pago
        strSqlStmt = strSqlStmt + lblUtilidad.getText()+","; 
        strSqlStmt = strSqlStmt + "'"+Globales.sFormaPago+"',"; 
        strSqlStmt = strSqlStmt + "'',"; 
        strSqlStmt = strSqlStmt + "'"+Globales.sReferencia+"')"; 
        
        // Ejecuta la Sentencia
        if (oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
            bResult = true;
        
        // Retorna el Valor
        return bResult;
        
        
    }
// Función para obtener el Folio
    private int fnIntGetFolioInventario()
    {
        // Variable para el Resultado
        int iResult=0;

        // Variable para el Query
        String sQuery;
        
        
        // Prepara el Query
        sQuery ="Select intFolioInventario from 05tblfolios";
                
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        
        // Capturo el Error
        try {
            
            // Verifico que haya habido resultados
            if (oConn.setResult.next())
            {
        
                // Obtiene el Folio Siguiente
                iResult = oConn.setResult.getInt("intFolioInventario");

            }
        } catch (SQLException ex) {
            oFunc.SubSistemaMensaje("fnIntGetFolioInventario:"+ex.getMessage().toString());
        }
        
        // Retorna el Resultado
        return iResult;
        
    }
    
    private boolean fnBoolRegistraMovimiento(String sCod,String sCan,boolean bCancel)
    {
        
        // Declara la variable de resultados
        boolean bResult=false;
        
        // para el Folio del Inventario
        String sFolioInventario;
        
        // Query
        String sQuery;
        
        // Obtengo el Folio del Inventario
        sFolioInventario = String.valueOf(fnIntGetFolioInventario());
        
        
        // Prepara el Query para ejecutar la actualización
        sQuery = "Insert into 06tblInventario (intInvFolio,strInvMovimiento,";
        sQuery += "datInvFecha,intInvCantidad,strProductoCodigo,intVentaFolio,txtInvDescripcion)";
        sQuery += "Values ("+sFolioInventario+",";

        // Verifica si es cancelación para colocarlo como salida o entrada
        if (! bCancel)
            sQuery += "'Salida',";            
        else
            sQuery += "'Entrada',";            
       
        // La fecha
        sQuery += "'"+btnFecha.getText()+"',";
        sQuery += sCan+",";
        sQuery += "'"+sCod+"',";
        sQuery += spnTicket.getValue().toString()+",";
        if (! bCancel)
            sQuery += "'Salida por Venta')";
        else
            sQuery += "'Entrada por Cancelación de Venta')";
       
        
        
        // Ejecuta la Sentencia
        if (oConn.FnBoolQueryExecuteUpdate(sQuery))
            if (fnBoolActualizaFolioInventario())
                bResult = true;
        
        // Retorna el resultado
        return bResult;
        
    }
    
    // Actualiza la Existencia 
    private boolean fnBoolActualizaExistenciaSalida(String sCod, String sCan, boolean bCancel)
    {
        
        // Variable de Retorno
        boolean bResult = false;
       
        // Query
        String sQuery;
        
        
        // Prepara el Query para ejecutar la actualización
        
        if (! bCancel)
        {
            sQuery = "Update 04tblProductos Set intProductoSalidas = intProductoSalidas  + " + sCan + ",";
            sQuery = sQuery + " intProductoActual = intProductoActual - "+sCan;
        }
        else
        {
            sQuery = "Update 04tblProductos Set intProductoSalidas = intProductoSalidas  - " + sCan + ",";
            sQuery = sQuery + " intProductoActual = intProductoActual + "+sCan;
        }
        
        // Condiciona Sentencia de acuerdo al producto
        sQuery = sQuery + " Where strProductoCodigo='"+sCod+"'";
        
        // Ejecuta la Sentencia
        if (oConn.FnBoolQueryExecuteUpdate(sQuery))
            bResult = true;
        
        // Retorna el Resultado
        return bResult;
       
    }
    
    private boolean fnBoolCancelaVentaDetalle()
    {
        
        // Para los resultados
        boolean bResult = true;
        int iFila;
        String sCodigo, sNombre, sPrecio, sCantidad;
        
        // Para la sentencia Sql
        String strSqlStmt; // Para el Query
        
        // Ciclo para grabar el detalle de la venta
        for(iFila = 0; iFila < tblProductos.getRowCount();iFila++)
        {
            //Obtiene dato contenido en una celda de la tabla
            sCodigo = tblProductos.getValueAt(iFila, INT_COL_CODIGO).toString();
            sCantidad = tblProductos.getValueAt(iFila, INT_COL_CANTIDAD).toString();
            
           
            // Actualiza la Existencia del Producto
            if (fnBoolActualizaExistenciaSalida(sCodigo,sCantidad,true))
            {
                if (!fnBoolRegistraMovimiento(sCodigo, sCantidad,true)) 
                {
                    bResult = false;
                    break;
                }                
            }
            else
            {
                bResult = false;
                break;
            }
                    
        }             
        // Retorna el Valor
        return bResult;
    }
    
    // Función para grabar el Detalle de la Venta
    private boolean fnBoolGrabaVentaDetalle()
    {
        // Para los resultados
        boolean bResult = true;
        
        // Variable para las filas de la Tabla de Productos
        int iFila;
        
        // Variables para los diferentes datos
        String sCodigo, sNombre, sPrecio, sCantidad, sCosto;
        
        // Para la sentencia Sql
        String strSqlStmt; // Para el Query
        
        // Ciclo para grabar el detalle de la venta
        for(iFila = 0; iFila < tblProductos.getRowCount();iFila++)
        {
            //Obtiene dato contenido en una celda de la tabla
            sCodigo = tblProductos.getModel().getValueAt(iFila, INT_COL_CODIGO).toString();
            sNombre = tblProductos.getModel().getValueAt(iFila, INT_COL_NOMBRE).toString();
            sPrecio = tblProductos.getModel().getValueAt(iFila, INT_COL_PRECIO).toString();
            sCantidad = tblProductos.getModel().getValueAt(iFila, INT_COL_CANTIDAD).toString();
            sCosto = tblProductos.getModel().getValueAt(iFila, INT_COL_COSTO).toString();            
           
            // Actualiza la Existencia del Producto
            if (fnBoolActualizaExistenciaSalida(sCodigo,sCantidad,false)) 
                if (fnBoolRegistraMovimiento(sCodigo, sCantidad,false))
                {
                    // Prepara Query para grabar venta detalle
                    strSqlStmt = " Insert into 09tblventasdetalle ";
                    strSqlStmt = strSqlStmt + "(intVentaFolio,";
                    strSqlStmt = strSqlStmt + " strProductoCodigo,";
                    strSqlStmt = strSqlStmt + " strProductoNombre,";
                    strSqlStmt = strSqlStmt + " intVentaCantidad,";
                    strSqlStmt = strSqlStmt + " decProductoPrecio,";
                    strSqlStmt = strSqlStmt + " decProductoCosto)";
                    strSqlStmt = strSqlStmt + " Values (";
                    strSqlStmt = strSqlStmt + spnTicket.getValue().toString()+",";
                    strSqlStmt = strSqlStmt + "'"+sCodigo+"',";
                    strSqlStmt = strSqlStmt + "'"+sNombre+"',";
                    strSqlStmt = strSqlStmt + sCantidad+",";
                    strSqlStmt = strSqlStmt + sPrecio+",";
                    strSqlStmt = strSqlStmt + sCosto+")";
                    
                    // Ejecuta la Sentencia
                    if ( ! oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
                    {
                        bResult = false;
                        break;
                    }
                    
                }
                else
                    break;
            else            
                break;
            
        }     
        
        // Retorna el Valor
        return bResult;
        
    }
    
    // Función para actualizar el Folio
    private boolean fnBoolActualizaFolioInventario()
    {
        // Para controlar el Resultado
        boolean bResult = false;
        
        // Para la sentencia Sql
        String strSqlStmt; // Para el Query
        
        // Query para actualizar el Folio
        strSqlStmt = "Update 05tblFolios set intFolioInventario = intFolioInventario + 1";
        
        // Ejecuta la Sentencia
        if (oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
            bResult = true;
        
        // Retorna el Resultado
        return bResult;
        
    }
    
    // Función para actualizar el Folio
    private boolean fnBoolActualizaFolioVenta()
    {
        // Para controlar el Resultado
        boolean bResult = false;
        
        // Para la sentencia Sql
        String strSqlStmt; // Para el Query
        
        // Query para actualizar el Folio
        strSqlStmt = "Update 05tblFolios set intFolioVenta = intFolioVenta + 1";
        
        // Ejecuta la Sentencia
        if (oConn.FnBoolQueryExecuteUpdate(strSqlStmt))
            bResult = true;
        
        // Retorna el Resultado
        return bResult;
        
    }
    
    // Procedimiento para grabar una Venta
    private void sbGrabaVenta()
    {
        
        // Obtengo la fecha y hora de nueva cuenta
        Date dateHoy = new Date();
    
        // Variable para dar formato
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        
        // Inicializa Folio y Fecha
        btnFecha.setText(formato.format(dateHoy));
        
        // Coloca el Folio de Nueva Cuenta
        spnTicket.setValue(fnIntGetFolioVenta());
        
        // Para controlar la Transacción
        boolean bCommit=false;
        
        // Inicia la transacción colocando el commit a false
        oConn.SubAutoCommit(false);
        
        // Intenta ejecutar cada uno de los procesos
        if (fnBoolGrabaVentaEncabezado())
            if (fnBoolGrabaVentaDetalle())
                if (fnBoolActualizaFolioVenta())
                    bCommit = true;
        
        // Verifica si debe hacer commit
        if (bCommit)
        {
            // Realiza la confirmación de la transaccion
            oConn.SubCommit();
            if (bMensajesExito)
                oFunc.SubSistemaMensaje("Se ha grabado el Folio:"+spnTicket.getValue().toString()+"  Correctamente");
        }
        else
            oConn.SubRollBack();

        // Inicializa despues de grabar
        sbInicializa();
        
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
        spnTicket = new javax.swing.JSpinner();
        btnBuscar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnIniciar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnCobrar = new javax.swing.JButton();
        btnFecha = new javax.swing.JButton();
        lblUtilidad = new javax.swing.JLabel();
        lblCancelada = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblProductos = new javax.swing.JLabel();
        lblImporteLetras = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Ticket"));

        spnTicket.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnTicketStateChanged(evt);
            }
        });

        btnBuscar.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/search.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnCancelar.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnIniciar.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/venta.png"))); // NOI18N
        btnIniciar.setText("Iniciar");
        btnIniciar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Tahoma", 3, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imprime.png"))); // NOI18N
        btnImprimir.setText("Imprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnCobrar.setFont(new java.awt.Font("Tahoma", 3, 18)); // NOI18N
        btnCobrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cob.png"))); // NOI18N
        btnCobrar.setText("Cobrar");
        btnCobrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCobrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCobrarActionPerformed(evt);
            }
        });

        btnFecha.setText("Fecha");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(spnTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUtilidad, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(lblCancelada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnIniciar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCobrar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscar)
                            .addComponent(btnIniciar)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE))
                            .addComponent(btnCobrar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblUtilidad, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCancelada, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(1, 1, 1))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(spnTicket)
                .addContainerGap())
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));

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
        tblProductos.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                tblProductosFocusGained(evt);
            }
        });
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 204, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("$");

        lblTotal.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(0, 204, 255));
        lblTotal.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTotal.setText("0.00");
        lblTotal.setBorder(javax.swing.BorderFactory.createTitledBorder("Total de Venta"));
        lblTotal.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                lblTotalPropertyChange(evt);
            }
        });

        lblProductos.setFont(new java.awt.Font("Tahoma", 3, 36)); // NOI18N
        lblProductos.setForeground(new java.awt.Color(0, 204, 255));
        lblProductos.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblProductos.setText("0");
        lblProductos.setBorder(javax.swing.BorderFactory.createTitledBorder("Productos"));

        lblImporteLetras.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        lblImporteLetras.setForeground(new java.awt.Color(0, 204, 255));
        lblImporteLetras.setText("Cero Pesos 00/100 Moneda Nacional");
        lblImporteLetras.setBorder(javax.swing.BorderFactory.createTitledBorder("Importe con Letra"));

        txtCodigo.setBorder(javax.swing.BorderFactory.createTitledBorder("Código del Producto"));
        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCodigoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(lblImporteLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 801, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(lblProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImporteLetras, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        // TODO add your handling code here:
        //Coloca el Folio
        spnTicket.setValue(fnIntGetFolioVenta());
        //Habilita la captura para venta
        sbHabilitaVenta(true);

        //Coloca el foco en el codigo
        txtCodigo.requestFocus();

    }//GEN-LAST:event_btnIniciarActionPerformed

    private void txtCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyPressed
        // TODO add your handling code here:

        //Verificar fin de linea
        if (evt.getKeyCode() == INT_KEY_LF) {
            if (bAgruparProductos) {
                if (!fnProductoEnVenta()) {
                    sbProductoAgrega();
                }
            } else //Valida el prodcuto
            {
                sbProductoAgrega();
            }
            //Consume el caracter
            evt.consume();
        }
        //Verifica el return
        if (evt.getKeyCode() == INT_KEY_RETURN) {
            if (bAgruparProductos) {
                if (!fnProductoEnVenta()) {
                    sbProductoAgrega();
                }
            } else //Valida el prodcuto
            {
                sbProductoAgrega();
            }
            //Consume el caracter
            evt.consume();
        }
        //VERIFICA SI PRESIONO LA TECLA F1
        if (evt.getKeyCode() == INT_KEY_F1) {
            //Inicializa el dato de consulta
            Globales.sConsultaDato = "";
            //Coloca el Query de la cpnsulta
            connection.sQuery = "Select strProductoCodigo as Codigo, strProductoNombre";

            //Declaro una variable instanmcia de la forma de comnsulta
            frmClase08b frmCodigos = new frmClase08b(null, true);
            frmCodigos.setTitle("Consulta de Códigos");
            frmCodigos.setVisible(true);

            //Despliega el dato seleccionado
            txtCodigo.setText(Globales.sConsultaDato);
            if (bAgruparProductos) {
                if (!fnProductoEnVenta()) {
                    sbProductoAgrega();
                }

            } else //Valida el prodcuto
            {
                sbProductoAgrega();
            }
            //Consume el caracter
            evt.consume();
        }
    }//GEN-LAST:event_txtCodigoKeyPressed

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:
        int iProductos;
        int iRow;
        double decTotal;
        double decUtilidad;
        String sCantidad;
        String sImporte;
        String sCosto;
        String sPrecio;
        //Variable para el formato 
        DecimalFormat formateador = new DecimalFormat("####.00");
        //Obtiene la cantidad y el precio  para disminuir del importe y del Total
        iRow = tblProductos.getSelectedRow();
        //Variable para el modelo de la tabla
        DefaultTableModel tblDatos = (DefaultTableModel) tblProductos.getModel();
        //Obtengo Precio Cantidad Importey el Costo
        sPrecio = tblDatos.getValueAt(iRow, INT_COL_PRECIO).toString();
        sCantidad = tblDatos.getValueAt(iRow, INT_COL_CANTIDAD).toString();
        sImporte = tblDatos.getValueAt(iRow, INT_COL_IMPORTE).toString();
        sCosto = tblDatos.getValueAt(iRow, INT_COL_COSTO).toString();

        //Actuañizar el niumero d eproductos
        iProductos = Integer.valueOf(lblProductos.getText()) - Integer.valueOf(sCantidad);
        lblProductos.setText(String.valueOf(iProductos));

        //El total
        decTotal = Double.valueOf(lblTotal.getText()) - Double.valueOf(sImporte);
        decUtilidad = Double.valueOf(lblUtilidad.getText()) - ((Double.valueOf(sPrecio) - Double.valueOf(sCosto)));
        //Valida que no sea cero la cantidad
        if (iProductos > 0) {
            lblTotal.setText(formateador.format(decTotal));
            lblUtilidad.setText(formateador.format(decUtilidad));
        } else {
            lblTotal.setText("0.00");
            lblUtilidad.setText("0.00");
        }
        //Elimina el Row
        tblDatos.removeRow(iRow);
        tblProductos.setModel(tblDatos);
    }//GEN-LAST:event_tblProductosMouseClicked

    private void tblProductosFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tblProductosFocusGained
        // TODO add your handling code here:
        txtCodigo.requestFocus();
    }//GEN-LAST:event_tblProductosFocusGained

    private void lblTotalPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_lblTotalPropertyChange
        // TODO add your handling code here:
        String sEntero;
        String sDecimal;
        int iLonguitud;
        int iEntero;
        //obtengo la longuituf
        iLonguitud = lblTotal.getText().length();
        //Obtengo la parte entera
        sEntero = lblTotal.getText().substring(0, iLonguitud - 3);
        iEntero = Integer.valueOf(sEntero);
        //Obtengo la parte decimal
        sDecimal = lblTotal.getText().substring(iLonguitud - 2) + "/100 M.N";
        lblImporteLetras.setText(oFunc.getStringOfNumber(iEntero) + " PESOS " + sDecimal);
    }//GEN-LAST:event_lblTotalPropertyChange

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
       if (!btnIniciar.isEnabled())
            sbInicializa();
        else
        {
            // Verifica que la venta existe
            if (fnBoolVentaExiste())
                
                // Verifica si está cancelada
                if (lblCancelada.getText().isEmpty())
                {
                    // Inicializa el Motivo de Cancelacion
                    Globales.sMotivoCancelacion="";                    
                    
                    // Crea la forma y la visualiza
                    frmClase09c frmCancelacion = new frmClase09c(null,true);
                    frmCancelacion.setVisible(true); 
                    
                    if (Globales.sMotivoCancelacion.length()>0)
                        sbCancelaVenta();
                    else
                        // Inicializa
                        sbInicializa();  
                }
                else
                    oFunc.SubSistemaMensaje("La Venta ya se encuentra cancelada por:"+lblCancelada.getText());
            else
                oFunc.SubSistemaMensaje("La Venta que desea cancelar No existe");
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCobrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCobrarActionPerformed
        // TODO add your handling code here:
        if (lblProductos.getText() == "0") {
            oFunc.SubSistemaMensaje("Debe capturar por lo menos 1 producto");
        } else {
            //Coloca el total de la venta
            Globales.sTotalVenta = lblTotal.getText();
            Globales.bVentaRealizada = false;
            Globales.sReferencia = "";
            Globales.sFormaPago = "";

            //Crea la instancia de la Forma
            frmClase09b frmCobro = new frmClase09b(null, true);
            //Coloca los datos
            frmCobro.setVisible(true);
            //Verifica que la venta se aya realizado
            if (Globales.bVentaRealizada) {
                sbGrabaVenta();
            } else {
                sbInicializa();
            }
        }
    }//GEN-LAST:event_btnCobrarActionPerformed

    private void spnTicketStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnTicketStateChanged
        // TODO add your handling code here:
        if (Integer.valueOf(spnTicket.getValue().toString()) > 0) //Verifica que exista la venta
        {
            fnBoolVentaExiste();
        } else {
            spnTicket.setValue(0);
        }
    }//GEN-LAST:event_spnTicketStateChanged

    private void sbCancelaVenta()
    {
                
        // Para controlar la Transacción
        boolean bCommit=false;
        
        // Inicia la transacción colocando el commit a false
        oConn.SubAutoCommit(false);
        
        // Intenta ejecutar cada uno de los procesos
        if (fnBoolCancelaVentaEncabezado())
            if (fnBoolCancelaVentaDetalle())
                bCommit = true;
        
        // Verifica si debe hacer commit
        if (bCommit)
        {
            oConn.SubCommit();
            if (bMensajesExito)
               oFunc.SubSistemaMensaje("Se ha Cancelado la Venta Correctamente");
        }
        else
            oConn.SubRollBack();

        // Inicializa despues de grabar
        sbInicializa();  
    }
    
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        Globales.sConsultaDato = "";
        connection.sQuery = "Select intVentaFolio as Folio, dateVentaFecha as Fecha, intVentaProductos as Producto, decVentaImporte as Importe ";
        //Crea la forma Coloca titulo y la hace visible
        frmClase08b frmFolios = new frmClase08b(null, true);
        frmFolios.setTitle("Consulta de Folios");
        frmFolios.setVisible(true);

        //DEspliega el dato selñeccionado
        spnTicket.setValue(Integer.parseInt(Globales.sConsultaDato));
        //DEspliegla  los datos del folio
        fnBoolVentaExiste();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
        if(fnBoolVentaExiste()){
            //Se manda a imprimir el ticket
            sbImprimeTicket();
            if(bMensajesExito)
                oFunc.SubSistemaMensaje("Se ha impreso el ticket correctamente");
        }
        else
            oFunc.SubSistemaMensaje("El ticket no Existe");
    }//GEN-LAST:event_btnImprimirActionPerformed
        
    void sbGetDatosEmpresa(String sDatos[])
    {        
        // Variable para el Query
        String sQuery;
        
        
        // Prepara el Query
        sQuery ="Select * from tblEmpresa";
                
        //Ejecuta el Query
        oConn.FnBoolQueryExecute(sQuery);
        
        // Capturo el Error
        try {
            
            // Verifico que haya habido resultados
            if (oConn.setResult.next())
            {
        
                // Obtiene los datos en el vector
                sDatos[INT_COL_EMPRESA] = oConn.setResult.getString("strEmpresaNombre");
                sDatos[INT_COL_SUCURSAL] = oConn.setResult.getString("strEmpresaSucursal");
                sDatos[INT_COL_DIRECCION1] = oConn.setResult.getString("strEmpresaDireccion1");
                sDatos[INT_COL_DIRECCION2] = oConn.setResult.getString("strEmpresaDireccion2");
                sDatos[INT_COL_TELEFONOS] = oConn.setResult.getString("strEmpresaTelefonos");
                sDatos[INT_COL_RFC] = oConn.setResult.getString("strEmpresaRfc");

            }
        } catch (SQLException ex) {
            oFunc.SubSistemaMensaje("sbGetDatosEmpresa:"+ex.getMessage().toString());
        }        
    }
    
    void sbImprimeTicket()
    {
        // Variable para obtener la altura de la font
        int iAlturaFont;
        
        // Para controlar las filas
        int iFila;
        
        // La posición del eje Y de la impresión
        int iPosY;
        
        // Para manipular datos
        String sDato;
        
        // Para imprimir la linea en el ticket
        String sLinea;
    
        // El Frame para la venta de diálogo de Impresión
        Frame f = new Frame();
        
        // Vector para los datos de la Empresa
        String sEmpresa[]=new String[6];            
        
        // Se obtiene el objeto PrintJob
        PrintJob pjob = f.getToolkit().getPrintJob( f,"Impresion de Ticket",null );
        
        // Se obtiene el objeto graphics sobre el que pintar
        Graphics pg = pjob.getGraphics();     
        
        // Se fija el font de caracteres con que se escribe
        pg.setFont( new Font( "Lucida Console",Font.PLAIN,10 ) );
        
        // Obtengo la altura de la Font
        iAlturaFont = pg.getFontMetrics().getHeight();
        
        // Obtiene los datos de la Empresa
        sbGetDatosEmpresa(sEmpresa);
                
        // Encabezado del Ticket --------------------------------------------------
        //             1234567890123456789012345678901234567890
        pg.drawString("----------------------------------------",5,iAlturaFont*1 );
        pg.drawString(sEmpresa[INT_COL_EMPRESA],5,iAlturaFont*2 );
        pg.drawString(sEmpresa[INT_COL_SUCURSAL],5,iAlturaFont*3 );
        pg.drawString(sEmpresa[INT_COL_DIRECCION1],5,iAlturaFont*4 );
        pg.drawString(sEmpresa[INT_COL_DIRECCION2],5,iAlturaFont*5 );        
        pg.drawString(sEmpresa[INT_COL_TELEFONOS],5,iAlturaFont*6 );
        pg.drawString(sEmpresa[INT_COL_RFC],5,iAlturaFont*7 );
        pg.drawString("----------------------------------------",5,iAlturaFont*8 );
        sDato = "Folio:"+spnTicket.getValue().toString();
        sDato = String.format("%1$-14s",sDato)+" ";
        sLinea = sDato+ "Fecha:"+btnFecha.getText();
        pg.drawString(sLinea,5,iAlturaFont*9 );
        pg.drawString("----------------------------------------",5,iAlturaFont*10 );
        //-------------------------------------------------------------------------
        
        //Detalle del Ticket-------------------------------------------------------
        //pg.drawString( "ARTICULO        DESCRIPCIÓN             ",5,120 );
        //pg.drawString( " CANTIDAD    PRECIO              IMPORTE",5,120 );        
        //              123456789012345 1234567 99 999.99 999.99
        pg.drawString( "CODIGO-D-BARRAS NOMBRE  CN PRECIO IMPORT",5,iAlturaFont*11 );
        // Ciclo para imprimir el Detalle
        // Ciclo para buscar en la Venta
        for(iFila = 0; iFila < tblProductos.getRowCount();iFila++)
        {
           //Obtiene el código y lo formatea a 15 caracteres
           sDato = tblProductos.getValueAt(iFila, INT_COL_CODIGO).toString();
           sDato = String.format("%1$-16s",sDato);
           sLinea = sDato;
           
           //Obtiene el nombre y lo formatea a 2 caracteres
           sDato = tblProductos.getValueAt(iFila, INT_COL_NOMBRE).toString();
           if (sDato.length()>7)
               sDato = sDato.substring(0,7)+ " ";
           else
               sDato = String.format("%1$-7s",sDato)+" ";
           sLinea += sDato;
           
           //Obtiene la cantidad y lo formatea a 2 caracteres
           sDato = tblProductos.getValueAt(iFila, INT_COL_CANTIDAD).toString();
           sDato = String.format("%1$2s",sDato)+" ";
           sLinea += sDato;
           
           //Obtiene el precio y lo formatea a 6 caracteres
           sDato = tblProductos.getValueAt(iFila, INT_COL_PRECIO).toString();
           sDato = String.format("%1$6s",sDato)+" ";
           sLinea += sDato;
           
           //Obtiene el importe y lo formatea a 6 caracteres
           sDato = tblProductos.getValueAt(iFila, INT_COL_IMPORTE).toString();
           sDato = String.format("%1$6s",sDato);
           sLinea += sDato;
           
           // Imprime la linea de venta
           iPosY=iAlturaFont*(10+iFila+2);
           pg.drawString(sLinea,5,iPosY);
           

        }    
        
        //-------------------------------------------------------------------------
        
        //----------------------------------------------------------------
        iPosY=iAlturaFont*(10+iFila+2);
        pg.drawString( "----------------------------------------",5,iPosY );
        iPosY=iAlturaFont*(10+iFila+3);
        sDato = "Productos:"+lblProductos.getText()+" Total:";
        sDato = String.format("%1$-19s",sDato)+" ";
        sLinea = sDato;
        sDato = lblTotal.getText();
        sDato = String.format("%1$20s",sDato);
        sLinea += sDato;
        pg.drawString( sLinea,5,iPosY);
        iPosY=iAlturaFont*(10+iFila+4);
        pg.drawString( lblImporteLetras.getText(),5,iPosY);
        iPosY=iAlturaFont*(10+iFila+5);
        pg.drawString(sTextoFinalTicket,5,iPosY);
        //----------------------------------------------------------------
        
        // Se finaliza la página
        pg.dispose();
        
        // Se hace que la impresora termine el trabajo y escupa la página
        pjob.end();          
        
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
            java.util.logging.Logger.getLogger(frmClase09.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmClase09.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmClase09.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmClase09.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                frmClase09 dialog = new frmClase09(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCobrar;
    private javax.swing.JButton btnFecha;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCancelada;
    private javax.swing.JLabel lblImporteLetras;
    private javax.swing.JLabel lblProductos;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblUtilidad;
    private javax.swing.JSpinner spnTicket;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextField txtCodigo;
    // End of variables declaration//GEN-END:variables

    
}
