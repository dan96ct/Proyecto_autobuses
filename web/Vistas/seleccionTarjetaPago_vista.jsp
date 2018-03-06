<%-- 
    Document   : seleccionTarjetaPago_vista
    Created on : 05-mar-2018, 17:31:02
    Author     : Dani
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="Modelo.Cliente"%>
<%@page import="Modelo.Billete"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pago del billete</title>
        <script src="js/JavaScript.js" type="text/javascript"></script>
        <link href="css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
    <div id="barra_superior"><a href="../index.jsp"><img src="Imagenes/Logo.png" class="logo" alt="Logo"/></a></div>

</head>
<body>
    
    <% Billete billete = (Billete) session.getAttribute("billete");
        
        Cliente cliente =  (Cliente) session.getAttribute("cliente");%>
    <main id="contenido">
        <section id="resumenDatos">
            <h2>Resumen de datos</h2>
            <label><b>Origen:</b> <% out.print(billete.getOrigen());%></label><br>
            <label><b>Destino:</b> <% out.print(billete.getDestino());%></label><br>
            <label><b>Fecha</b> <% out.print(billete.getDia());%></label><br>
            <label><b>Hora salida:</b> <% out.print(billete.getHoraSalida());%></label><br>
            <label><b>Hora llegada:</b> <% out.print(billete.getHoraLlegada());%></label><br>
            <label><b>Numero de pasajeros:</b> <% out.print(billete.getPersonas());%></label><br>
            <label><b>Precio:</b> <% out.print(billete.getPrecio());%></label><br>
        </section>
        <section id="seleccionTarjeta">
            <h2>Seleccione una tarjeta</h2>
            <% for (int i = 0; i < cliente.getTarjetas().size(); i++) {
            %>
            <div id="<%out.print(cliente.getTarjetas().get(i).getId()); %>" onclick="confirmarTarjeta(this);" class="tarjeta">
                <label> <% out.print(cliente.getTarjetas().get(i).getTipo()); %></label><br>
                <label><% out.print(cliente.getTarjetas().get(i).getNumero()); %></label><br>
                <label><% out.print(cliente.getTarjetas().get(i).getFechaCaducidad()); %></label><br>
            </div>
            <%
                }
            %>
            <a href="pagoTarjetaNueva_vista.jsp"><button class="btn btn-warning" style="margin: 0 auto; margin-top: 20px;"> Añadir nueva tarjeta</button></a>

        </section><br>
        <section id="pasajeros_datos">
            <div class="btn-group btn-group-lg" id="botones_formulario" role="group" aria-label="Large button group">
                <%for (int i = 0; i < billete.getArrayPasajeros().size(); i++) {
                        System.out.println(billete.getArrayPasajeros().get(i).toString());
                %>
                <script>
                    var pasajero = {"numPasajero": <% out.print("'" + i + "'"); %>, "nif": <% out.print("'" + billete.getArrayPasajeros().get(i).getIdentificador() + "'"); %>, "nombre": <% out.print("'" + billete.getArrayPasajeros().get(i).getNombre() + "'"); %>, "apellidos": <% out.print("'" + billete.getArrayPasajeros().get(i).getApellido() + "'"); %>, "email": <% out.print("'" + billete.getArrayPasajeros().get(i).getCorreo() + "'"); %>, "asiento": <% out.print("'" + billete.getArrayPasajeros().get(i).getAsiento() + "'"); %>, "id": <% out.print("'" + billete.getArrayPasajeros().get(i).getId() + "'"); %>};
                    añadirPasajero(pasajero);
                </script>
                <button type="button" onclick="datosPasajero(this);" name="<% out.print(billete.getArrayPasajeros().get(i).getId()); %>" class="btn btn-secondary btn-lg btn-block">Pasajero<% out.print(i + 1); %></button><%
                    }
                %>
            </div>
            <h2>Datos pasajero</h2>
            <label id="nombre">Seleccione un pasajero</label><br>
            <label id="apellidos"></label><br>
            <label id="nif"></label><br>
            <label id="asiento"></label><br>

        </section>

    </main>
</body>
</html>
