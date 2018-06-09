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
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author dani
 */
public class tramitarDatosViaje1_controlador extends HttpServlet {

    Connection Conexion;

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        ConexionBBDD ConexBD;
        try {
            ConexBD = ConexionBBDD.GetConexion();
            Conexion = ConexBD.GetCon();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(tramitarDatosViaje1_controlador.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(tramitarDatosViaje1_controlador.class.getName()).log(Level.SEVERE, null, ex);

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
        String origen = (String) request.getParameter("origen");
        String destino = (String) request.getParameter("destino");
        String fechaString = (String) request.getParameter("fecha");
        int numPersonas = Integer.valueOf(request.getParameter("numPersonas"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaDate = LocalDate.parse(fechaString, formatter);
        Billete billete = new Billete(origen, destino, fechaDate, numPersonas);

        System.out.print(billete.toString());
        Operaciones operaciones = new Operaciones();

        HttpSession session = request.getSession();

        try {
            billete.setCodigo(operaciones.generarCodigo());
            session.setAttribute("billete", billete);
            session.setAttribute("horarios", operaciones.getHorariosRuta(billete, Conexion));
        } catch (SQLException ex) {
            Logger.getLogger(tramitarDatosViaje1_controlador.class.getName()).log(Level.SEVERE, null, ex);
        }

        response.sendRedirect("Vistas/seleccionBillete_vista.jsp");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet tramitarDatosViaje1_controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet tramitarDatosViaje1_controlador at " + "</h1>");

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
