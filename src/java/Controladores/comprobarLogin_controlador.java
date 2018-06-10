/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ConexionBBDD;
import DAO.Operaciones;
import Modelo.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Dani
 */
public class comprobarLogin_controlador extends HttpServlet {

    private Connection Conexion;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        ConexionBBDD ConexBD;
        try {
            ConexBD = ConexionBBDD.GetConexion();
            Conexion = ConexBD.GetCon();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(comprobarLogin_controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(comprobarLogin_controlador.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet comprobarLogin_controlador</title>");
            out.println("</head>");
            out.println("<body>");

            String email = (String) request.getParameter("Email");
            String pass = (String) request.getParameter("psw");
            Operaciones operacion = new Operaciones();
            Cliente cliente = new Cliente();
            try {
                operacion.compruebaLogin(Conexion, email, pass);
                System.out.println(operacion.recuperarDatosCliente(Conexion, email, pass));
                cliente = operacion.recuperarDatosCliente(Conexion, email, pass);

                HttpSession session = request.getSession(true);
                session.setAttribute("cliente", cliente);
                response.sendRedirect("Vistas/seleccionTarjetaPago_vista.jsp");

            } catch (SQLException ex) {
                Logger.getLogger(comprobarLogin_controlador.class.getName()).log(Level.SEVERE, null, ex);

            } catch (Excepciones.Excepcion exc) {
                HttpSession session = request.getSession();
                session.setAttribute("error", exc.getMessage());
                response.sendRedirect("/Proyecto_autobuses/Vistas/Error_vista.jsp");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
