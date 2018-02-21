<%-- 
    Document   : formulariosBillete_vista
    Created on : 31-ene-2018, 18:14:54
    Author     : Dani
--%>

<%@page import="Modelo.Billete"%>
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
    <main id="contenido">
        <% if (session.getAttribute("horarios") == null) {
        %>
        <% }
            Billete billete = new Billete();
            billete = (Billete) session.getAttribute("billete");
        %><section id="formulario_billetes">
            <ul class="nav nav-tabs">
                <%for (int i = 1; i < billete.getPersonas() + 1; i++) {%>
                <li class="nav-item">
                    <button class="nav-link <% if (i == 1) {
                            out.print("active");
                        } %>" id="<% out.print(i);%>" onclick="activarFormulario(this);" type="button"><% out.print("Pasajero " + i);%></button>
                </li>
                <%}
                %>
            </ul>
            <form id="formulario_usuario">
                <h2 id="tituloFormulario_usuario">Datos de pasajero 1</h2>
                <hr>
                <div class="form-group">
                    <label for="indentificacion">Numero de identificacion</label>
                    <input type="text" class="form-control" id="identificador" name="identificador1" aria-describedby="indentificacion" placeholder="Introduce tu numero de autentificacion" required>
                </div>
                <div class="form-group">
                    <label for="nombre">Nombre</label>
                    <input type="text" class="form-control" id="nombre" name="nombre1" aria-describedby="nombre" placeholder="Introduce tu nombre" required>
                </div>
                <div class="form-group">
                    <label for="apellidos">Apellidos</label>
                    <input type="text" class="form-control" id="apellidos" name="apellidos1" aria-describedby="apellidos" placeholder="Introduce tus apellidos" required>
                </div>
                <table id="autobus">
                    <tr><td colspan="2" id="tituloTabla">Elige el asiento</td></tr>
                    <tr><td id="1" onclick="elegirAsiento(this);">[  ]</td><td id="2" onclick="elegirAsiento(this);">[  ]</td></tr>
                    <tr><td id="3" onclick="elegirAsiento(this);">[  ]</td><td id="4" onclick="elegirAsiento(this);">[  ]</td></tr>
                    <tr><td id="5" onclick="elegirAsiento(this);">[  ]</td><td id="6" onclick="elegirAsiento(this);">[  ]</td></tr>
                    <tr><td id="7" onclick="elegirAsiento(this);">[  ]</td><td id="8" onclick="elegirAsiento(this);">[  ]</td></tr>
                </table>
                <button type="button" class="btn btn-success" onclick="comprobarDatosPasajero();">Confirmar pasajero</button>
                <button type="button" class="btn btn-success" onclick="confirmarTodosLosDatos('<% out.print(billete.getPersonas()); %>')">Confirmar todos los datos</button>
            </form>
        </section>
    </main>
</body>
</html>
