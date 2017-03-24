/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ABC;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Luis Alvarez
 */
public class Rockman {
    private static Connection conn;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String user = "root";
    private static String password = "kaka08rm";
    private static String url = "jdbc:mysql://localhost:3306/abc";
    private String usuario;
    private int administrador;

    public Rockman() {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println("error al conectar" + e);
        }
        usuario = "SYSTEM";
        administrador = 1;
    }

    public Connection getConn() {
        return conn;
    }

    public int getAdministrador() {
        return administrador;
    }
    
    
    public void desconectar(){
        conn = null;
        if(conn == null){
            System.out.println("Conexion terminada");
        }
    }
    
    public boolean ingresar(String user, String pass) throws SQLException{
        String consulta = "SELECT Correo_Primario, Contraseña FROM afiliados";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        while(rs.next()){
            String usuario = rs.getString("Correo_Primario");
            String contraseña = rs.getString("Contraseña");
            if(usuario.equalsIgnoreCase(user) && contraseña.equalsIgnoreCase(pass)){
                return true;
            }
        }
        st.close();
        return false;
    }
    
    public int cantAfiliados() throws SQLException{
        String consulta = "SELECT Correo_Primario, Contraseña FROM afiliados";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public int cantAfiliadosActivos() throws SQLException{
        String consulta = "SELECT Correo_Primario, Contraseña FROM afiliados "
                + "WHERE Activo=1";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public int cantCuentas() throws SQLException{
        String consulta = "SELECT * FROM cuentas";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public Object[][] modeloAfiliados() throws SQLException{
        String consulta = "SELECT ID_Afiliado,Contraseña,Primer_Nombre,Segundo_Nombre,"
                + "Primer_Apellido,Segundo_Apellido,Telefono,"
                + "Direccion,Correo_Primario,Correo_Secundario,"
                + "Fecha_Nacimiento,Fecha_Inicio,Activo,Tipo,Usuario FROM afiliados"
                + " WHERE Activo = 1";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        Object[][] afi = new Object[cantAfiliadosActivos()+1][15];
        int fila = 0;
        int col = 0;
        while(rs.next()){
            int id = rs.getInt("ID_Afiliado");
            afi[fila][col] = id;
            col++;
            String con = rs.getString("Contraseña");
            afi[fila][col] = con;
            col++;
            String pnom = rs.getString("Primer_Nombre");
            afi[fila][col] = pnom;
            col++;
            String snom = rs.getString("Segundo_Nombre");
            afi[fila][col] = snom;
            col++;
            String pape = rs.getString("Primer_Apellido");
            afi[fila][col] = pape;
            col++;
            String sape = rs.getString("Segundo_Apellido");
            afi[fila][col] = sape;
            col++;
            String tel = rs.getString("Telefono");
            afi[fila][col] = tel;
            col++;
            String dir = rs.getString("Direccion");
            afi[fila][col] = dir;
            col++;
            String cp = rs.getString("Correo_Primario");
            afi[fila][col] = cp;
            col++;
            String cs = rs.getString("Correo_Secundario");
            afi[fila][col] = cs;
            col++;
            Date dn = rs.getDate("Fecha_Nacimiento");
            afi[fila][col] = dn;
            col++;
            Date di = rs.getDate("Fecha_Inicio");
            afi[fila][col] = di;
            col++;
            int ac = rs.getInt("Activo");
            if(ac==1){
                afi[fila][col] = "Activo";
            }else{
                afi[fila][col] = "Inactivo";
            }
            col++;
            int ad = rs.getInt("Tipo");
            if(ad==1){
                afi[fila][col] = "Administrador";
            }else{
                afi[fila][col] = "Normal";
            }
            if(administrador==0){
                afi[fila][col] = "-------";
            }
            col++;
            afi[fila][col] = rs.getString("Usuario");
            if(administrador==0){
                afi[fila][col] = "-------";
            }
            col=0;
            fila++;
        }
        afi[fila][0]=cantAfiliados()+1;
        st.close();
        return afi;       
    }
    
    public String[] elementos(){
        String ele[] = {"ID","CONTRA","PN","SN","PA","SA","TEL","DIR","CPRI","CSEC","FNAC",
        "FINI","ACT","TIPO","US"};
        return ele;
    }
    
    public void agregarAfiliado(JTable jt) throws SQLException{
        String sql = "{call SP_CreateAfiliado(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setInt(1, cantAfiliados()+1);
        cs.setString(2, (String) jt.getValueAt(cantAfiliadosActivos(), 1));
        cs.setString(3, (String) jt.getValueAt(cantAfiliadosActivos(), 2));
        cs.setString(4, (String) jt.getValueAt(cantAfiliadosActivos(), 3));
        cs.setString(5, (String) jt.getValueAt(cantAfiliadosActivos(), 4));
        cs.setString(6, (String) jt.getValueAt(cantAfiliadosActivos(), 5));
        cs.setString(7, (String) jt.getValueAt(cantAfiliadosActivos(), 6));
        cs.setString(8, (String) jt.getValueAt(cantAfiliadosActivos(), 7));
        cs.setString(9, (String) jt.getValueAt(cantAfiliadosActivos(), 8));
        cs.setString(10, (String) jt.getValueAt(cantAfiliadosActivos(), 9));
        cs.setString(11, (String) jt.getValueAt(cantAfiliadosActivos(), 10));
        cs.setInt(12, cantCuentas()+1);
        cs.setInt(13, cantCuentas()+2);
        String tipo = (String) jt.getValueAt(cantAfiliadosActivos(), 10);
        if(tipo.equalsIgnoreCase("ADMINISTRADOR")){
            cs.setInt(14, 1);
        }else{
            cs.setInt(14, 0);
        }
        cs.setString(15, usuario);
        cs.execute();
        cs.close();
    }
    
    public void eliminarAfiliado(int id) throws SQLException{
        String sql = "{call SP_DeleteAfiliado(?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setInt(1, id);
        cs.execute();
        cs.close();
    }
    
    public void modificarAfiliado(JTable jt, int fila) throws SQLException{
        String sql = "{call SP_UpdateAfiliado(?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setInt(1, (int) jt.getValueAt(fila, 0));
        cs.setString(2, (String) jt.getValueAt(fila, 2));
        cs.setString(3, (String) jt.getValueAt(fila, 3));
        cs.setString(4, (String) jt.getValueAt(fila, 4));
        cs.setString(5, (String) jt.getValueAt(fila, 5));
        cs.setString(6, (String) jt.getValueAt(fila, 6));
        cs.setString(7, (String) jt.getValueAt(fila, 7));
        cs.setString(8, (String) jt.getValueAt(fila, 8));
        cs.setString(9, (String) jt.getValueAt(fila, 9));
        cs.setDate(10, (Date) jt.getValueAt(fila, 10));
        cs.execute();
        cs.close();
    }
    
    public Object[][] modeloCuentas(int mostrar,int adm) throws SQLException{
        String where = "";
        if(mostrar==1){
            where = " where c.Tipo=1";
        }
        if(mostrar==2){
            where = " where c.Tipo=2";
        }
        String consulta = "select c.Num_Cuenta, c.ID_Afiliado,"
                + " c.Saldo, c.Fecha_Apertura, c.Antiguedad, c.Tipo,"
                + " c.Usuario from cuentas c inner join afiliados a "
                + "on a.ID_Afiliado = c.ID_Afiliado and a.Activo=1" + where;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        Object[][] afi = new Object[cantAfiliadosActivos()*2][7];
        int fila = 0;
        int col = 0;
        while(rs.next()){
            int id = rs.getInt("c.Num_Cuenta");
            afi[fila][col] = id;
            col++;
            String con = rs.getString("c.ID_Afiliado");
            afi[fila][col] = con;
            col++;
            String pnom = rs.getString("c.Saldo");
            afi[fila][col] = pnom;
            col++;
            String snom = rs.getString("c.Fecha_Apertura");
            afi[fila][col] = snom;
            col++;
            String pape = rs.getString("c.Antiguedad");
            afi[fila][col] = pape;
            col++;
            int ac = rs.getInt("c.Tipo");
            if(ac==1){
                afi[fila][col] = "Ahorro";
            }else{
                afi[fila][col] = "Aportaciones";
            }
            col++;
            afi[fila][col] = rs.getString("c.Usuario");
            if(adm==0){
                afi[fila][col]="--------";
            }
            col=0;
            fila++;
        }
        st.close();
        return afi;       
    }
    
    public String[] elementosCuentas(){
        String ele[] = {"#CUENTA","ID_AFILIADO","SALDO","FECHA_APERTURA","ANTIGUEDAD(Meses)","TIPO","CREADOR"};
        return ele;
    }
    
    public void actualizarAntiguedad() throws SQLException{
        String sql = "{call SP_UpdateCuentasFechas()}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.execute();
        cs.close();
    }
    
    public int contarPrestamos() throws SQLException{
        String consulta = "SELECT * FROM prestamos";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public Object[][] modeloPrestamos(int id) throws SQLException{
        String afiliado = " where a.ID_Afiliado = "+id;
        if(id==-1){
            afiliado = "";
        }
        String consulta = "select p.Num_Prestamo, p.ID_Afiliado,"
                + " p.Fecha, p.Monto, p.Periodos, p.Saldo,"
                + " p.Emitido, p.Fecha_Modificacion from prestamos p inner join afiliados a "
                + "on a.ID_Afiliado = p.ID_Afiliado and a.Activo=1" + afiliado;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        Object[][] mol = new Object[contarPrestamos()+1][8];
        int fila = 0;
        while(rs.next()){
            mol[fila][0] = rs.getInt("p.Num_Prestamo");
            mol[fila][1] = rs.getInt("p.ID_Afiliado");
            mol[fila][2] = rs.getDate("p.Fecha");
            mol[fila][3] = rs.getDouble("p.Monto");
            mol[fila][4] = rs.getInt("p.Periodos");
            mol[fila][5] = rs.getDouble("p.Saldo");
            mol[fila][6] = rs.getString("p.Emitido");
            mol[fila][7] = rs.getDate("p.Fecha_Modificacion");
            if(administrador==0){
                mol[fila][6] = rs.getString("--------");
                mol[fila][7] = rs.getDate("--------");
            }
            fila++;
        }
        if(id==-1){
            mol[contarPrestamos()][0] = contarPrestamos()+1;
        }
         return mol;
    }
    
    public String[] elementosPrestamos(){
        String ele[] = {"NUM PRESTAMO","ID AFILIADO","FECHA","MONTO","PERIODOS","SALDO",
            "EMITIDO POR","MODIFICACION"};
        return ele;
    }
    
    public void agregarPrestamo(JTable jt) throws SQLException{
        String sql = "{call SP_CreatePrestamo(?,?,?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        String id =  (String) jt.getValueAt(contarPrestamos(), 1);
        String dob = (String) jt.getValueAt(contarPrestamos(), 3);
        String pe = (String) jt.getValueAt(contarPrestamos(), 4);
        System.out.println(id+"-"+dob+"-"+pe);
        int afi = Integer.parseInt(id);
        double monto = Double.parseDouble(dob);
        int periodo = Integer.parseInt(pe);
        cs.setInt(1, contarPrestamos()+1);
        cs.setInt(2, afi);
        cs.setDouble(3, monto);
        cs.setInt(4, periodo);
        cs.setDouble(5, 0.00);
        cs.setString(6,usuario);
        cs.execute();
        cs.close();
    }
    
    public int cantidadAbonos() throws SQLException{
        String consulta = "SELECT * FROM abonos";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public Object[][] modeloAbonos(int tipo) throws SQLException{
        String inner = " inner join cuentas c on a.Num_Cuenta = c.Num_Cuenta and c.Tipo = "+tipo;
        if(tipo == 0){
            inner = "";
        }
        Object[][] mol = new Object[cantidadAbonos()][5];
        String consulta = "select a.Num_Abono, a.Num_Cuenta, a.Monto,"
                + " a.Fecha, a.Descripcion from Abonos a" + inner;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int fila = 0;
        while(rs.next()){
            mol[fila][0] = rs.getInt("a.Num_Abono");
            mol[fila][1] = rs.getInt("a.Num_Cuenta");
            mol[fila][2] = rs.getDouble("a.Monto");
            mol[fila][3] = rs.getDate("a.Fecha");
            mol[fila][4] = rs.getString("a.Descripcion");
            fila++;
        }
        rs.close();
        return mol;
    }
    
    public String[] elementosAbonos(){
        String ele[] = {"NUM_ABONO","NUM_CUENTA","MONTO","FECHA","DESCRIPCION"};
        return ele;
    }
    
    public double saldo(int cuenta,int tipo) throws SQLException{
        String consulta = "SELECT Num_Cuenta, Saldo FROM cuentas where tipo="+tipo;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        while(rs.next()){
            if(rs.getInt("Num_Cuenta")==cuenta){
                return rs.getDouble("Saldo");
            }
        }
        rs.close();
        return -1.1;
    }
    
    public int cuenta(int afi, int tipo) throws SQLException{
        String consulta = "SELECT Num_Cuenta , ID_Afiliado, Tipo FROM cuentas";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        while(rs.next()){
            if(rs.getInt("ID_Afiliado")==afi && rs.getInt("Tipo")==tipo){
                return rs.getInt("Num_Cuenta");
            }
        }
        rs.close();
        return -1;
    }
    
    public int cantidadRetiros() throws SQLException{
        String consulta = "SELECT * FROM retiros";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public Object[][] modeloRetiros() throws SQLException{
        Object[][] mol = new Object[cantidadRetiros()][5];
        String consulta = "select Num_Retiro, Num_Cuenta, Monto,"
                + " Fecha, Descripcion from Retiros";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int fila = 0;
        while(rs.next()){
            mol[fila][0] = rs.getInt("Num_Retiro");
            mol[fila][1] = rs.getInt("Num_Cuenta");
            mol[fila][2] = rs.getDouble("Monto");
            mol[fila][3] = rs.getDate("Fecha");
            mol[fila][4] = rs.getString("Descripcion");
            fila++;
        }
        rs.close();
        return mol;
    }
    
    public String[] elementosRetiros(){
        String ele[] = {"NUM_RETIRO","NUM_CUENTA","MONTO","FECHA","DESCRIPCION"};
        return ele;
    }
     
    public void agregarRetiro(int cuenta,double saldo) throws SQLException{
         String sql = "{call SP_CreateRetiro(?,?,?,?)}";
         CallableStatement cs = conn.prepareCall(sql);
         cs.setInt(1, cantidadRetiros()+1);
         cs.setInt(2,cuenta);
         cs.setDouble(3, saldo);
         cs.setString(4, usuario);
         cs.execute();
         cs.close();
    }
    
    public int cantidadLiqui() throws SQLException{
        String consulta = "SELECT * FROM liquidaciones_total";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public Object[][] modeloLiquida() throws SQLException{
        Object[][] mol = new Object[cantidadLiqui()][4];
        String consulta = "select Num_Liquidacion, Num_Afiliado, Monto,"
                + " Fecha from liquidaciones_total";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int fila = 0;
        while(rs.next()){
            mol[fila][0] = rs.getInt("Num_Liquidacion");
            mol[fila][1] = rs.getInt("Num_Afiliado");
            mol[fila][2] = rs.getDouble("Monto");
            mol[fila][3] = rs.getDate("Fecha");
            fila++;
        }
        return mol;
    }
    
    public String[] elementosLiqui(){
        String ele[] = {"NUM_LIQUIDACION","NUM_AFILIADO","MONTO","FECHA"};
        return ele;
    }
    
    public void agregarLiq(int id) throws SQLException{
        int ca,cp;
        ca = cuenta(id,1);
        cp = cuenta(id,2);
        double total = saldo(ca,1)+saldo(cp,2);
        String sql = "{call SP_CreateLiquidacion(?,?,?,?)}";
        CallableStatement cs = conn.prepareCall(sql);
        cs.setInt(1, cantidadLiqui()+1);
        cs.setInt(2,id);
        cs.setDouble(3, total);
        cs.setString(4, usuario);
        cs.execute();
        cs.close();
    }
    
    public double totalSaldoAportaciones(int id) throws SQLException{
        String sql = "Select Saldo from cuentas where tipo=2";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        double saldoTotal = 0.00;
        while(rs.next()){
            saldoTotal = saldoTotal +  rs.getDouble("Saldo");
        }
        return saldoTotal;
    }
    
    public void abonos() throws SQLException{
        String sql1 = "Select c.Num_Cuenta, c.ID_Afiliado, a.Activo from cuentas c "
                + "inner join afiliados a on c.ID_Afiliado = a.ID_Afiliado and a.Activo = 1 where c.Tipo = 1";
        String sql2 = "Select c.Num_Cuenta, c.ID_Afiliado, a.Activo from cuentas c "
                + "inner join afiliados a on c.ID_Afiliado = a.ID_Afiliado and a.Activo = 1 where c.Tipo = 2";
        String sql3 = "{call SP_CreateAbono(?,?,?,?)}";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(sql1);
        Statement st2 = conn.createStatement();
        ResultSet rs2 = st2.executeQuery(sql2);
        CallableStatement cs = conn.prepareCall(sql3);
        while(rs1.next() && rs2.next()){
            cs.setInt(1,cantidadAbonos()+1);
            cs.setInt(2,cantidadAbonos()+2);
            cs.setInt(3,rs1.getInt("c.Num_Cuenta"));
            cs.setInt(4,rs2.getInt("c.Num_Cuenta"));
            cs.execute();
        }
        cs.close();
        rs1.close();
        rs2.close();
    }
    
    public int cantidadDividendos() throws SQLException{
        String consulta = "SELECT * FROM dividendos";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public void dividendos() throws SQLException{
        String sql = "Select c.Num_Cuenta, c.ID_Afiliado, c.Saldo from cuentas c "
                + "inner join afiliados a on c.ID_Afiliado = a.ID_Afiliado and a.Activo = 1 where c.Tipo = 2";
        String sql1 = "{call SP_CreateDividendos(?,?,?,?)}";
        Statement st1 = conn.createStatement();
        ResultSet rs1 = st1.executeQuery(sql);
        CallableStatement cs = conn.prepareCall(sql1);
        while(rs1.next()){
            cs.setInt(1, cantidadDividendos()+1);
            cs.setInt(2,rs1.getInt("c.ID_Afiliado"));
            cs.setDouble(3, totalSaldoAportaciones(rs1.getInt("c.ID_Afiliado")));
            cs.setDouble(4, rs1.getDouble("c.Saldo"));
            cs.execute();
        }
        rs1.close();
        cs.close();
    }
    
    
    public Object[][] modeloDividendos(int mes, int año) throws SQLException{
        String where = "where Month(d.Fecha)="+mes+ " and Year(d.Fecha)="+año;
        if(mes==0){
            where = "";
        }
        Object[][] mol = new Object[cantidadDividendos()+1][6];
        String consulta = "select a.ID_Afiliado, d.Fecha, a.Primer_Nombre, "
                + "a.Primer_Apellido, d.Saldo_Aportacion,d.Ganancia from Dividendos d "
                + "inner join afiliados a on a.ID_Afiliado=d.ID_Afiliado and a.Activo=1 "+where;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        double tot=0,apor=0;
        int cant = 0;
        int fila = 0;
        while(rs.next()){
            mol[fila][0] = rs.getInt("a.ID_Afiliado");
            mol[fila][1] = rs.getDate("d.Fecha");
            mol[fila][2] = rs.getString("a.Primer_Nombre") +" "+ rs.getString("a.Primer_Apellido");
            mol[fila][3] = rs.getDouble("d.Saldo_Aportacion");
            mol[fila][4] = 100*rs.getDouble("d.Ganancia")/rs.getDouble("d.Saldo_Aportacion");
            mol[fila][5] = rs.getDouble("d.Ganancia");
            tot += rs.getDouble("d.Ganancia");
            apor +=rs.getDouble("d.Saldo_Aportacion");
            cant++;
            fila++;
        }
        mol[cantidadDividendos()][0] = "TOTALES";
        mol[cantidadDividendos()][2] = cant;
        mol[cantidadDividendos()][3] = apor;
        mol[cantidadDividendos()][5] = tot;
        return mol;
    }
    
    public String[] elementosDiv(){
        String ele[] = {"ID_Afiliado","FECHA","NOMBRE","SALDO_APORTACION","PORCENTAJE(%)","GANANCIA"};
        return ele;
    }
    
    public int cantidadPagos(int p) throws SQLException{
        String where = " where Num_Prestamo="+p;
        if(p==0){
            where = "";
        }
        String consulta = "SELECT * FROM pagos"+where;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(consulta);
        int longitud = 0;
        while(rs.next()){
            longitud++;
        }
        st.close();
        return longitud;
    }
    
    public Object[][] modeloPagos() throws SQLException{
        String sql = "select Num_Pago ,Num_Prestamo, Fecha,MontoP, Intereses, Capital from pagos";
        Object[][] mol = new Object[cantidadPagos(0)][7];
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        int fila = 0;
        while(rs.next()){
            mol[fila][0] = rs.getInt("Num_Pago");
            mol[fila][1] = rs.getInt("Num_Prestamo");
            mol[fila][2] = rs.getDate("Fecha");
            mol[fila][3] = rs.getDouble("Monto");
            mol[fila][4] = rs.getDouble("Intereses");
            mol[fila][5] = rs.getDouble("Capital");
            mol[fila][6] = "SYSTEM";
            fila++;
        }
        return mol;
    }
    
    public String[] elementosPag(){
        String ele[] = {"NUM_PAGO","NUM_PRESTA","FECHA","MONTO","INTERESES","CAPITAL","HECHO"};
        return ele;
    }
    
    public void pagos() throws SQLException{
        String sql = "select Num_Prestamo ,Monto, Periodos from prestamos where Pagado=0";
        String call = "{call SP_CreatePago(?,?,?,?)}";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        CallableStatement cs = conn.prepareCall(call);
        while(rs.next()){
            int cant = cantidadPagos(rs.getInt("Num_Prestamo"))+1;
            int pres = rs.getInt("Num_Prestamo");
            int per = rs.getInt("Periodos");
            double mon = rs.getDouble("Monto");
            cs.setInt(1, cant);
            cs.setInt(2, pres);
            cs.setInt(3, per);
            cs.setDouble(4, mon);
            System.out.println(cant+"-"+pres+"-"+per+"-"+mon);
            cs.execute();
        }
        cs.close();
        rs.close();
    }
}
