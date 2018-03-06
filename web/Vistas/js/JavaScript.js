/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function AJAXCrearObjeto() {
    if (window.XMLHttpRequest) {
// navegadores que siguen los estándares
        objetoAjax = new XMLHttpRequest();
    } else {
// navegadores obsoletos
        objetoAjax = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return objetoAjax;
}
var arrayPasajeros = [];
var arrayAsientos = [];
var arrayAsientosOcupados = [];

function addAsientoOcupado(asiento) {
    arrayAsientosOcupados.push(asiento);
}
function comprobarAsientosOcupados() {
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        for (var j = 0; j < arrayAsientosOcupados.length; j++) {
            if (todosLosAsientos[i].getAttribute("id") == arrayAsientosOcupados[j]) {
                var th = document.createElement('th');
                th.setAttribute("style", "color:red; font-size:20px;");
                th.innerHTML = "[ ]";
                todosLosAsientos[i].parentNode.replaceChild(th, todosLosAsientos[i]);
            }

        }
    }

}

function cogerDatosHorario(horaS, horaLL, precio, idHorario) {
    location.href = '/Proyecto_autobuses/tramitarDatosViaje2_controlador?horaS=' + horaS + '&horaLL=' + horaLL + '&precio=' + precio + '&id=' + idHorario;
}
function comprobarDatosPasajero() {
    var numPasajero = document.getElementById("tituloFormulario_usuario").textContent;

    if (comprobarCamposVacios() === true) {

        switch (numPasajero) {
            case 'Datos de pasajero 1':
                guardarDatosPasajero(1);
                break;
            case 'Datos de pasajero 2':
                guardarDatosPasajero(2);
                break;
            case 'Datos de pasajero 3':
                guardarDatosPasajero(3);
                break;
            case 'Datos de pasajero 4':
                guardarDatosPasajero(4);
                break;
            case 'Datos de pasajero 5':
                guardarDatosPasajero(5);
                break;
            default:
        }
    }
}

function elegirAsiento(elemento) {
    var todosLosAsientos = document.getElementsByTagName("td");
    var validar = true;
    for (var i = 0; i < todosLosAsientos.length; i++) {
        for (var f = 0; f < arrayAsientos.length; f++) {
            if (todosLosAsientos[i].getAttribute("id") === arrayAsientos[f].numeroAsiento) {
                todosLosAsientos[i].setAttribute("style", "color:red;");
                todosLosAsientos[i].setAttribute("name", "ocupado");
                validar = false;
            }
        }
        if (validar === true) {
            todosLosAsientos[i].setAttribute("style", "color:black;");
            todosLosAsientos[i].setAttribute("name", "");
            if (todosLosAsientos[i].getAttribute("id") == elemento.getAttribute("id")) {
                elemento.setAttribute("style", "color:green;");
                elemento.setAttribute("name", "activado");
            }

        } else {
            validar = true;
        }
    }

    var numPasajero = document.getElementById("tituloFormulario_usuario").textContent;
    var num = 0;
    switch (numPasajero) {
        case 'Datos de pasajero 1':
            num = 1;
            break;
        case 'Datos de pasajero 2':
            num = 2;
            break;
        case 'Datos de pasajero 3':
            num = 3;
            break;
        case 'Datos de pasajero 4':
            num = 4;
            break;
        case 'Datos de pasajero 5':
            num = 5;
            break;
        default:
    }
    for (var i = 0; i < arrayAsientos.length; i++) {
        if (arrayAsientos[i].ocupante == num) {
            for (var f = 0; f < todosLosAsientos.length; f++) {
                if (todosLosAsientos[f].getAttribute("id") == arrayAsientos[i].numAsiento) {
                    todosLosAsientos[f].setAttribute("style", "color:black;");
                }
            }
        }
    }
}
function comprobarCamposVacios() {
    var validacion = true;
    var identificador = document.getElementById("identificador");
    var nombre = document.getElementById("nombre");
    var apellidos = document.getElementById("apellidos");

    if (identificador.value === "") {
        identificador.setAttribute("class", "form-control is-invalid");
        validacion = false;
    }
    if (nombre.value === "") {
        nombre.setAttribute("class", "form-control is-invalid");
        if (validacion !== false) {
            validacion = false;
        }

    }
    if (apellidos.value === "") {
        apellidos.setAttribute("class", "form-control is-invalid");
        if (validacion !== false) {
            validacion = false;
        }
    }

    var todosLosAsientos = document.getElementsByTagName("td");
    var validar = false;
    for (var i = 0; i < todosLosAsientos.length; i++) {
        if (todosLosAsientos[i].getAttribute("name") == "activado") {
            validar = true;
            break;
        }
    }
    if (validar == false) {
        document.getElementById("tituloTabla").setAttribute("style", "color:red;");
        if (validacion !== false) {
            validacion = false;

        }
    }

    return validacion;
}
function guardarDatosPasajero(numero) {
    var numIdentificacion = document.getElementById("identificador").value;
    var nombre = document.getElementById("nombre").value;
    var apellidos = document.getElementById("apellidos").value;

    var asiento = 0;
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        if (todosLosAsientos[i].getAttribute("name") == "activado") {
            asiento = todosLosAsientos[i].getAttribute("id");
            var asientoObjeto = {'numeroAsiento': asiento, 'ocupante': numero};
            arrayAsientos.push(asientoObjeto);
        }
    }
    var datosIntroducidos = false;
    for (var i = 0; i < arrayPasajeros.length; i++) {
        if (arrayPasajeros[i].numPasajero == numero) {
            arrayPasajeros[i] = {"numPasajero": numero, "nif": numIdentificacion, "nombre": nombre, "apellidos": apellidos, "asiento": asiento, "id": arrayPasajeros[i].id};
            datosIntroducidos = true;
            objetoAjax = AJAXCrearObjeto(); //crea el objeto
            objetoAjax.open('GET', '/Proyecto_autobuses/tramitarDatosViaje3_controlador?nif=' + numIdentificacion + '&nombre=' + nombre + '&apellidos=' + apellidos + '&asiento=' + asiento + '&id=' + arrayPasajeros[i].id);
            objetoAjax.send();
            objetoAjax.onreadystatechange = function () {
                if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
                }
            }
        }
    }
    if (datosIntroducidos == false) {
        var pasajero = {"numPasajero": numero, "nif": numIdentificacion, "nombre": nombre, "apellidos": apellidos, "asiento": asiento, "id": generarID()};
        objetoAjax = AJAXCrearObjeto(); //crea el objeto
        objetoAjax.open('GET', '/Proyecto_autobuses/tramitarDatosViaje3_controlador?nif=' + numIdentificacion + '&nombre=' + nombre + '&apellidos=' + apellidos + '&asiento=' + asiento + '&id=' + pasajero.id);
        objetoAjax.send();
        objetoAjax.onreadystatechange = function () {
            if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
            }
        }
        arrayPasajeros.push(pasajero);
    }
    formularioConfirmado();

}
function comprobarFormularios(numPersonas) {
    var validar = false;
    if (numPersonas == arrayPasajeros.length) {
        validar = true;
    }
    return validar;

}
function confirmarTodosLosDatos(numPersonas) {
    if (comprobarFormularios(numPersonas) === true) {
        var json = JSON.stringify(arrayPasajeros);
        location.href = 'pagoBillete_vista.jsp';
    } else {
        alert("Faltan formularios por completar");
    }
}
function getEstaciones() {
    objetoAjax = AJAXCrearObjeto(); //crea el objeto
    objetoAjax.open('GET', '/Proyecto_autobuses/getEstaciones_controlador');
    objetoAjax.send();
    objetoAjax.onreadystatechange = function () {
        if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
            mostrarEstaciones();
        }
    }
    var calendario = document.getElementById("fechaIda");
    darFechaMinima(calendario);
}
function getRutas(estacion) {
    objetoAjax = AJAXCrearObjeto(); //crea el objeto
    objetoAjax.open('GET', '/Proyecto_autobuses/getRuta_controlador?estacion=' + estacion.value);
    objetoAjax.send();
    objetoAjax.onreadystatechange = function () {
        if (objetoAjax.readyState === 4 && objetoAjax.status === 200) {
            mostrarRutas();
        }
    }
}
function activarFormulario(element) {
    var numForm = element.getAttribute("id");
    var boton_activado = document.getElementsByClassName("nav-link active");
    boton_activado[0].setAttribute("class", "nav-link");
    element.setAttribute("class", "nav-link active");
    document.getElementById("tituloFormulario_usuario").innerHTML = "Datos de pasajero " + numForm;
    document.getElementById("identificador").setAttribute("name", "identificador" + numForm);
    document.getElementById("nombre").setAttribute("name", "nombre" + numForm);
    document.getElementById("apellidos").setAttribute("name", "apellidos" + numForm);

    document.getElementById("identificador").value = "";
    document.getElementById("nombre").value = "";
    document.getElementById("apellidos").value = "";

    formularioNormal();

    comprobarFormularioCompleto(numForm);


}
function comprobarFormularioCompleto(numForm) {
    for (var i = 0; i < arrayPasajeros.length; i++) {
        if (arrayPasajeros[i].numPasajero == numForm) {
            document.getElementById("identificador").value = arrayPasajeros[i].nif;
            document.getElementById("nombre").value = arrayPasajeros[i].nombre;
            document.getElementById("apellidos").value = arrayPasajeros[i].apellidos;

            formularioConfirmado(arrayPasajeros[i].numPasajero);
        }
    }
}
function formularioConfirmado(numPasajero) {
    document.getElementById("identificador").setAttribute("class", "form-control is-valid");
    document.getElementById("nombre").setAttribute("class", "form-control is-valid");
    document.getElementById("apellidos").setAttribute("class", "form-control is-valid");
    var arrayForm = document.getElementsByClassName("form-group");
    for (var i = 0; i < arrayForm.length; i++) {
        arrayForm[i].setAttribute("style", "color:green;");
    }
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        for (var f = 0; f < arrayAsientos.length; f++) {
            if (todosLosAsientos[i].getAttribute("id") === arrayAsientos[f].numeroAsiento && numPasajero === arrayAsientos[f].ocupante) {
                todosLosAsientos[i].setAttribute("style", "color:green;");
                break;
            }
        }
    }

}
function formularioNormal() {
    document.getElementById("identificador").setAttribute("class", "form-control");
    document.getElementById("nombre").setAttribute("class", "form-control");
    document.getElementById("apellidos").setAttribute("class", "form-control");
    var arrayForm = document.getElementsByClassName("form-group");
    for (var i = 0; i < arrayForm.length; i++) {
        arrayForm[i].setAttribute("style", "color:black;");
    }
    var todosLosAsientos = document.getElementsByTagName("td");
    var todosLosAsientos = document.getElementsByTagName("td");
    for (var i = 0; i < todosLosAsientos.length; i++) {
        var validar = true;
        for (var f = 0; f < arrayAsientos.length; f++) {
            if (todosLosAsientos[i].getAttribute("id") == arrayAsientos[f].numeroAsiento) {
                todosLosAsientos[i].setAttribute("style", "color:red;");
                todosLosAsientos[i].setAttribute("name", "ocupado");
                validar = false;
                break;
            }
        }
        if (validar == true) {
            todosLosAsientos[i].setAttribute("style", "color:black;");
        } else {
            validar = true;
        }
    }


}
function borrarHijos(nodo) {
    if (nodo.hasChildNodes())
    {
        while (nodo.childNodes.length >= 1)
        {
            nodo.removeChild(nodo.firstChild);
        }
    }
}
function mostrarRutas() {
    var datos = objetoAjax.responseText;
    var estacionesLista = JSON.parse(datos);
    var estacionesList = document.getElementById("estaciones2");
    for (var i = 0; i < estacionesLista[0].length; i++) {
        var option = document.createElement("option");
        option.innerHTML = estacionesLista[0][i];
        estacionesList.appendChild(option);
    }
}
function mostrarEstaciones() {
    var datos = objetoAjax.responseText;
    var estacionesLista = JSON.parse(datos);
    var estacionesList = document.getElementById("estaciones1");
    for (var i = 0; i < estacionesLista[0].length; i++) {
        var option = document.createElement("option");
        option.innerHTML = estacionesLista[0][i];
        estacionesList.appendChild(option);
    }

}
function generarID() {
    var arrayLetras = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k'];
    var id = "";
    for (var i = 0; i < 9; i++) {
        var numero = Math.floor((Math.random() * 10) + 0);
        id = id + arrayLetras[numero];
    }
    return id;
}
function añadirPasajero(pasajero) {
    arrayPasajeros.push(pasajero);
}
function datosPasajero(elemento) {
    var id = elemento.getAttribute("name");
    for (var i = 0; i < arrayPasajeros.length; i++) {
        if (arrayPasajeros[i].id == id) {
            $('#nombre').empty();
            $('#nombre').append('<b>Nombre </b>' + arrayPasajeros[i].nombre);
            $('#apellidos').empty();
            $('#apellidos').append('<b>Apellido </b>' + arrayPasajeros[i].apellidos);
            $('#nif').empty();
            $('#nif').append('<b>NIF </b>' + arrayPasajeros[i].nif);
            $('#asiento').empty();
            $('#asiento').append('<b>Asiento </b>' + arrayPasajeros[i].asiento);
        }
    }
}
function cargarRegistro() {
    $('#formulario_pago').empty();
    var formulario_pago = document.getElementById("formulario_pago");
    var h2 = document.createElement("h2");
    h2.innerHTML = "Rellene el formulario para completar el pago";
    formulario_pago.appendChild(h2);

    var form = document.createElement("form");
    form.setAttribute("action", "../guardarDatosViaje_controlador");
    formulario_pago.appendChild(form);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "NIF");
    label.innerHTML = "NIF del titular de la tarjeta";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "NIF");
    input.setAttribute("id", "NIFPago");
    input.setAttribute("placeholder", "Introduce tu NIF");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Email");
    label.innerHTML = "Email del titular de la tarjeta";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "email");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "Email");
    input.setAttribute("id", "email");
    input.setAttribute("placeholder", "Introduce tu correo electronico");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Nombre");
    label.innerHTML = "Nombre del titular de la tarjeta";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "nombre");
    input.setAttribute("id", "nombrePago");
    input.setAttribute("placeholder", "Introduce tu nombre");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Apellidos");
    label.innerHTML = "Apellidos del titular de la tarjeta";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "text");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "apellidos");
    input.setAttribute("id", "apellidosPago");
    input.setAttribute("placeholder", "Introduce tu apellido");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Contraseña");
    label.innerHTML = "Contraseña";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "password");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "psw");
    input.setAttribute("id", "psw");
    input.setAttribute("placeholder", "Introduce una contraseña");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Tipo de tarjeta");
    label.innerHTML = "Tipo de tarjeta";
    div.appendChild(label);
    div.appendChild(document.createElement("br"));

    var select = document.createElement("select");
    select.setAttribute("name", "tarjetas");
    select.setAttribute("id", "tipoTarjeta");

    var option = document.createElement("option");
    option.innerHTML = "MasterCard";
    select.appendChild(option);

    var option = document.createElement("option");
    option.innerHTML = "Visa";
    select.appendChild(option);
    div.appendChild(select);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "NumeroTarjeta");
    label.innerHTML = "Numero de tarjeta";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "number");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "numeroTarjeta");
    input.setAttribute("id", "numeroTarjeta");
    input.setAttribute("placeholder", "Numero de tarjeta");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Fecha de caducidad");
    label.innerHTML = "Fecha de caducidad";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "month");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "caducidadTarjeta");
    input.setAttribute("id", "caducidadTarjeta");
    input.setAttribute("placeholder", "Fecha");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "CVV");
    label.innerHTML = "CVV";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "number");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "CVV");
    input.setAttribute("id", "CVV");
    input.setAttribute("placeholder", "CVV");
    div.appendChild(input);
    form.appendChild(div);

    var button = document.createElement("button");
    button.setAttribute("type", "submit");
    button.setAttribute("class", "btn btn-primary");
    button.innerHTML = "Confirmar datos";
    form.appendChild(button);

}
function cargaLogin() {

    $('#formulario_pago').empty();
    var formulario_pago = document.getElementById("formulario_pago");
    var h2 = document.createElement("h2");
    h2.innerHTML = "Rellene el formulario para completar el pago";
    formulario_pago.appendChild(h2);

    var form = document.createElement("form");
    form.setAttribute("action", "../comprobarLogin_controlador");
    formulario_pago.appendChild(form);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Email");
    label.innerHTML = "Email del titular de la tarjeta";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "email");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "Email");
    input.setAttribute("id", "email");
    input.setAttribute("placeholder", "Introduce tu correo electronico");
    div.appendChild(input);
    form.appendChild(div);

    var div = document.createElement("div");
    div.setAttribute("class", "form-group");
    var label = document.createElement("label");
    label.setAttribute("for", "Contraseña");
    label.innerHTML = "Contraseña";
    div.appendChild(label);
    var input = document.createElement("input");
    input.setAttribute("type", "password");
    input.setAttribute("class", "form-control");
    input.setAttribute("name", "psw");
    input.setAttribute("id", "psw");
    input.setAttribute("placeholder", "Introduce una contraseña");
    div.appendChild(input);
    form.appendChild(div);

    var button = document.createElement("button");
    button.setAttribute("class", "btn btn-primary");
    button.innerHTML = "Confirmar datos";
    form.appendChild(button);
}
function confirmarTarjeta(elemento) {
    var idTarjeta = elemento.getAttribute("id");
    location.href = '/Proyecto_autobuses/comprobarTarjeta_controlador?idTarjeta=' + idTarjeta;

}
function darFechaMinima(elemento) {
    var today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth() + 1; //January is 0!
    var yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd
    }
    if (mm < 10) {
        mm = '0' + mm
    }
    today = yyyy + '-' + mm + '-' + dd;
    elemento.setAttribute("min", today);

}