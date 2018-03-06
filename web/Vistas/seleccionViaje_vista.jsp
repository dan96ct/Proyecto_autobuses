<%-- 
    Document   : seleccionBillete_vista
    Created on : 12-ene-2018, 9:03:30
    Author     : dani
--%>

<%@page import="Modelo.Billete"%>
<%@page import="Modelo.Horario"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="js/JavaScript.js" type="text/javascript"></script>
        <link href="css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
    <div id="barra_superior"><a href="../index.jsp"><img src="Imagenes/Logo.png" class="logo" alt="Logo"/></a></div>
</head>
<body>

    <%
        ArrayList<Billete> viajes = (ArrayList<Billete>) session.getAttribute("viajes");
    %>

    <main id="contenido">
        <section id="lista_horarios">
            <%for (int i = 0; i < viajes.size(); i++) {

            %>
            <article class="horario" id="<%out.print("horario" + i); %>"> <label class="datoHorario">H.Salida:<strong><% out.print(viajes.get(i).getHoraSalida()); %></strong> </label>
                <label class="datoHorario"> H.llegada<strong><% out.print(viajes.get(i).getHoraLlegada()); %></strong></label>
                <label class="datoHorario">Origen<b><% out.print(viajes.get(i).getOrigen()); %></b></label>
                <label class="datoHorario">Destino:<strong><% out.print(viajes.get(i).getDestino()); %></strong></label>
                <label class="datoHorario">Dia:<strong><% out.print(viajes.get(i).getDia()); %></strong></label><button name="<%out.print(i);%>" onclick="borrarViaje(<% out.print("'" + viajes.get(i).getIdViaje()+ "'"); %>)" id="boton_horario" class="btn btn-warning">Elegir viaje</button></article>
                <%}%>
        </section>
    </main>
    <footer id="footer_pagina">© 1960 - 2018 Varian SL Inc.</footer>
</body>
</html>
