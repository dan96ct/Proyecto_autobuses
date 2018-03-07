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
        ArrayList<Horario> horarios = (ArrayList<Horario>) session.getAttribute("horarios");
        Billete billete = new Billete();
        billete = (Billete) session.getAttribute("billete");
    %>

    <main id="contenido">
        <section id="lista_horarios">
            <section class="info_viaje">
                <label class="label_busqueda">Busqueda</label><label class="label_billetes"> Billetes:<b><% out.print(billete.getPersonas());%></b></label>
                <hr>
                <p id="p_infoViaje"><b><%  out.print(billete.getDia()); %></b></p>
                <p id="p_infoViaje">Origen: <strong><%out.print(billete.getOrigen()); %></strong> - Destino: <strong><%out.print(billete.getDestino()); %></strong></p>
            </section>
            <%  for (int i = 0; i < horarios.size(); i++) {
                    if (billete.getPersonas() <= horarios.get(i).getPlazasLibres()) {

            %><article class="horario" id="<%out.print("horario" + i); %>"> <label class="datoHorario">H.Salida:<strong><% out.print(horarios.get(i).getHoraSalida()); %></strong> </label>
                <label class="datoHorario"> H.llegada<strong><% out.print(horarios.get(i).getHoraLlegada()); %></strong></label>
                <label class="datoHorario">Plazas:<b><% out.print(horarios.get(i).getPlazasLibres()); %></b></label>
                <label class="datoHorario">Precio:<strong><% out.print(horarios.get(i).getPrecio()); %></strong></label><button name="<%out.print(i);%>" onclick="cogerDatosHorario(<% out.print("'" + horarios.get(i).getHoraSalida() + "'" + "," + "'" + horarios.get(i).getHoraLlegada() + "'" + "," + "'" + horarios.get(i).getPrecio() + "'" + "," + "'" + horarios.get(i).getId()+ "'"); %>)" id="boton_horario" class="btn btn-warning">Elegir horario</button></article>
                <%

                } else {%>
            <article class="horario" id="<%out.print("horario" + i); %>"> <label class="datoHorario">H.Salida:<strong><% out.print(horarios.get(i).getHoraSalida()); %></strong> </label>
                <label class="datoHorario"> H.llegada<strong><% out.print(horarios.get(i).getHoraLlegada()); %></strong></label>
                <label class="datoHorario" style="color: red;">Plazas:<b><% out.print(horarios.get(i).getPlazasLibres()); %></b></label>
                <label class="datoHorario">Precio:<strong><% out.print(horarios.get(i).getPrecio()); %></strong></label><button name="<%out.print(i);%>" onclick="cogerDatosHorario(<% out.print("'" + horarios.get(i).getHoraSalida() + "'" + "," + "'" + horarios.get(i).getHoraLlegada() + "'" + "," + "'" + horarios.get(i).getId()+ "'"); %>)" id="boton_horario" class="btn btn-warning" disabled>Elegir horario</button><br>
                <label style="color: red;">Lo lamentamos, pero no hay suficientes plazas disponibles</label>
            </article>
            <%
                    }
                }

            %>
        </section>
    </main>
    <footer id="footer_pagina">© 1960 - 2018 Varian SL Inc.</footer>
</body>
</html>
