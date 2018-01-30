<%-- 
    Document   : index
    Created on : 11-ene-2018, 18:12:05
    Author     : Dani
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="Vistas/js/JavaScript.js" type="text/javascript"></script>
        <title>JSP Page</title>
        <link href="Vistas/css/css.css" rel="stylesheet" type="text/css"/>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
    <a href="index.jsp"><img src="Vistas/Imagenes/Logo.png" class="logo" alt="Logo"/></a>
    <nav class="navbar navbar-expand-lg navbar-light bg-light" id="navBar">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item" >
                    <a class="nav-link" href="Vistas/contacto_vista.jsp">Contacto <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Tarifas</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Promociones</a>
                </li>
            </ul>
        </div>
    </nav>
</head>
<body onload="getEstaciones()">
    <main id="contenido">
        <section class="formulario_inicio">
            <form action="tramitarDatosViaje1_controlador">
                <div class="form-group">
                    <input type="text" class="form-control" list="estaciones1"   placeholder="Origen" name="origen" onchange="getRutas(this)" required>
                    <datalist type="text" id="estaciones1">
                    </datalist>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control" list="estaciones2" name="destino" placeholder="Destino" required>
                    <datalist type="text" id="estaciones2">
                    </datalist>
                </div>
                <div class="form-group">
                    <input type="date" class="form-control"  placeholder="Fecha de ida" name="fecha" required>
                </div>
                <div class="form-group">
                    <input type="number" class="form-control"  placeholder="Numero de personas" name="numPersonas" min="1" required>
                </div>
                <button type="submit" class="btn btn-warning">Confirmar</button>
            </form>
        </section>
        <section  class="carousel slide" data-ride="carousel">
            <div class="carousel slide" data-ride="carousel">
                <ol class="carousel-indicators">
                    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                </ol>
                <div id="carousel" class="carousel-inner">
                    <div class="carousel-item active">
                        <img class="d-block w-100" src="Vistas/Imagenes/Carouse/1.jpg" alt="First slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="Vistas/Imagenes/Carouse/2.jpg" alt="Second slide">
                    </div>
                    <div class="carousel-item">
                        <img class="d-block w-100" src="Vistas/Imagenes/Carouse/3.jpg" alt="Third slide">
                    </div>
                </div>
            </div>
        </section>

        <section id="info_empresa" class="container">
            <div class="row">
                <div class="col-sm" id="columna_info">
                    <div class="caja_imagen_icon_flat"><img class="imagen_icon_flat" src="Vistas/Imagenes/bus.png"  alt="bus"/></div>
                    <h2>Maxima profesionalidad</h2><br>
                    <p>Más allá de la extensa oferta de servicios de Transporte Regular y Transporte Discrecional, las posibilidades 
                        para el cliente de Varian se amplían con servicios de alquiler de flotas de vehículos a medida de sus necesidades a 
                        través de fórmulas de Renting y similares, de organización de congresos y de actividades culturales de salud - ocio.</p>
                </div>
                <div class="col-sm" id="columna_info">
                    <div class="caja_imagen_icon_flat"><img class="imagen_icon_flat" src="Vistas/Imagenes/chatting.png"  alt="bus"/></div>
                    <h2>Asistencia en todo momento</h2>
                    <p>Más allá de la extensa oferta de servicios de Transporte Regular y Transporte Discrecional, las posibilidades 
                        para el cliente de Varian se amplían con servicios de alquiler de flotas de vehículos a medida de sus necesidades a 
                        través de fórmulas de Renting y similares, de organización de congresos y de actividades culturales de salud - ocio.</p>
                </div>
                <div class="col-sm" id="columna_info">
                    <div class="caja_imagen_icon_flat"><img class="imagen_icon_flat" src="Vistas/Imagenes/movil.png"  alt="bus"/></div>
                    <h2>Seguimiento GPS</h2>
                    <br>
                    <p>Más allá de la extensa oferta de servicios de Transporte Regular y Transporte Discrecional, las posibilidades 
                        para el cliente de Varian se amplían con servicios de alquiler de flotas de vehículos a medida de sus necesidades a 
                        través de fórmulas de Renting y similares, de organización de congresos y de actividades culturales de salud - ocio.</p>
                </div>
            </div>
        </section>
    </main>
<footer id="footer_pagina">© 1960 - 2018 Varian SL Inc.</footer>
</body>
</html>
