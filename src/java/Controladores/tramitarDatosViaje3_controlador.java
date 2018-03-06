/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controladores;

import Modelo.Billete;
import Modelo.Pasajero;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;

/**
 *
 * @author dani
 */
@WebServlet(name = "tramitarDatosViaje3_controlador", urlPatterns = {"/tramitarDatosViaje3_controlador"})
public class tramitarDatosViaje3_controlador extends HttpServlet {

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
        String nombre = (String) request.getParameter("nombre");
        String apellidos = (String) request.getParameter("apellidos");
        String email = (String) request.getParameter("email");
        String nif = (String) request.getParameter("nif");
        String asiento = (String) request.getParameter("asiento");
        String id = (String) request.getParameter("id");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + id);
        HttpSession session = request.getSession();
        Billete billete = new Billete();
        billete = (Billete) session.getAttribute("billete");

        boolean validar = true;
        for (int i = 0; i < billete.getArrayPasajeros().size(); i++) {
            if (billete.getArrayPasajeros().get(i).getId().equals(id)) {
                billete.getArrayPasajeros().get(i).setNombre(nombre);
                billete.getArrayPasajeros().get(i).setApellido(apellidos);
                billete.getArrayPasajeros().get(i).setCorreo(email);
                billete.getArrayPasajeros().get(i).setIdentificador(nif);
                billete.getArrayPasajeros().get(i).setAsiento(Integer.parseInt(asiento));
                validar = false;
                break;
            }
        }
        if (validar) {
            Pasajero pasajero = new Pasajero(nombre, apellidos, email, nif);
            pasajero.setAsiento(Integer.parseInt(asiento));
            pasajero.setId(id);
            billete.addPasajero(pasajero);
        }

        session.setAttribute("billete", billete);
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet tramitarDatosViaje3_controlador</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet tramitarDatosViaje3_controlador at " + request.getContextPath() + "</h1>");
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
