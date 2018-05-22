/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import DAO.ConexionBBDD;
import DAO.Operaciones;
import Modelo.Billete;
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
public class tramitarDatosViaje2_controlador extends HttpServlet {

    Connection Conexion;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        ConexionBBDD ConexBD;
        try {
            ConexBD = ConexionBBDD.GetConexion();
            Conexion = ConexBD.GetCon();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(tramitarDatosViaje2_controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(tramitarDatosViaje2_controlador.class.getName()).log(Level.SEVERE, null, ex);

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
        String horaSalida = (String) request.getParameter("horaS");
        String horaLL = (String) request.getParameter("horaLL");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int idViaje = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();
        Billete billete = new Billete();
        billete = (Billete) session.getAttribute("billete");
        billete.setHoraSalida(horaSalida);
        billete.setHoraLlegada(horaLL);
        billete.setPrecio(precio);
        billete.setIdViaje(idViaje);
        session.setAttribute("billete", billete);

        Operaciones operacion = new Operaciones();
        try {
            session.setAttribute("asientos", operacion.getAsientosOcupados(Conexion, billete));
        } catch (SQLException ex) {
            Logger.getLogger(tramitarDatosViaje2_controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("Vistas/formulariosBillete_vista.jsp");

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet tramitarDatosViaje2_controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet tramitarDatosViaje2_controlador at " + request.getContextPath() + "</h1>");
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
