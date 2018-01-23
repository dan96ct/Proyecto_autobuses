<%-- 
    Document   : seleccionBillete_vista
    Created on : 12-ene-2018, 9:03:30
    Author     : dani
--%>

<%@page import="Modelo.Horario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
    <div id="barra_superior"><a href="../index.jsp"><img src="Imagenes/Logo.png" class="logo" alt="Logo"/></a></div>
</head>
<body>
    <% if (session.getAttribute("horarios") == null) {
    %>
    <% }
        ArrayList<Horario> horarios = (ArrayList<Horario>) session.getAttribute("horarios");
        session.invalidate(); %>
    <div id="contenido" style="min-height: 900px;">
        <%  for (int i = 0; i < horarios.size(); i++) {
            %><div id="horario"> <label class="datoHorario"><b>H.Salida: </b><% out.print(horarios.get(i).getHoraSalida()); %> </label>
            <label class="datoHorario"> <b>H.llegada: </b><% out.print(horarios.get(i).getHoraLlegada()); %></label>
            <label class="datoHorario"><b>Plazas: </b><% out.print(horarios.get(i).getPlazasLibres()); %></label>
            <label class="datoHorario"><b>Precio: </b><% out.print(horarios.get(i).getPrecio()); %> </label></div>
            <%

                }
            %>
</div>
<footer id="footer_pagina">© 1960 - 2018 Varian SL Inc.</footer>

</body>
</html>
