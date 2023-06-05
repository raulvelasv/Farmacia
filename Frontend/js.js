// Función para realizar una solicitud GET al servidor
function enviarGET() {
    var http = new XMLHttpRequest();
    let mail = document.getElementById("mail").value;
    let pass = document.getElementById("pass").value;

    // Abrir una solicitud GET al servidor para realizar el login
    http.open("GET", "http://localhost:3000/Entornos/Login?mail=" + mail + "&pass=" + pass, true);
    http.send();

    http.onreadystatechange = function() {
        if (http.readyState == 4 && http.status == 200) {
            let session = http.responseText;
            if (session != "0") {
                // Si se recibe una sesión válida, guardar el correo y la sesión en sessionStorage
                window.sessionStorage.setItem("mail", mail);
                window.sessionStorage.setItem("session", session);
                console.log("Respuesta del servidor: " + session); // Agrega este console.log para depurar

                document.getElementById("resultat").innerHTML = "Login correcto";
                
                // Pausa de 7 décimas de segundo antes de redirigir a la página de gestión
                setTimeout(function() {
                    gestion();
                }, 700);
            } else {
                // Si se recibe una sesión inválida, mostrar un mensaje de login incorrecto
                document.getElementById("resultat").innerHTML = "Login incorrecto";
                console.log("Respuesta del servidor: " + session); // Agrega este console.log para depurar
            }
        }
    }
}

// Función para redirigir a la página de gestión
function gestion() {
    var codigoSesion = sessionStorage.getItem("session");
    if (codigoSesion != 0) {
        // Si se ha iniciado sesión, redirigir a la página de gestión
        window.location.href = "Gestion.html";
    }
}

// Función para cerrar sesión
function logOut() {
    // Borrar el correo y la sesión del sessionStorage
    sessionStorage.removeItem('mail');
    sessionStorage.removeItem('session');
    // Redirigir a la página de login
    window.location.href = 'Login.html';

}
// Función para redirigir a la página de Alta o al Login
function irAlta() {
    // Obtengo el valor del sessionStorage
    var codigoSesion = sessionStorage.getItem("session");
    
    // Verifica si se ha guardado la información de la sesión
    if (codigoSesion != 0) {
        // Si se ha iniciado sesión, redirigir a la página de Alta
        window.location.href = "Alta.html";
    } else {
        // Si no se ha iniciado sesión, redirigir a la página de Login
        window.location.href = "Login.html";
    }
}

// Función para obtener la tabla de datos
function getTable() {
    // Obtener el correo electrónico y la sesión del sessionStorage
    var mail = sessionStorage.getItem('mail');
    var session = sessionStorage.getItem('session');
    
    // Enviar una solicitud GET al backend con los parámetros mail y session
    var http = new XMLHttpRequest();
    http.open('GET', "http://localhost:3000/Entornos/ServeXips?mail=" + mail + "&session=" + session, true);
    http.send();
    
    http.onreadystatechange = function() {
        if (http.readyState == 4 && http.status === 200) {
            // Asignar la respuesta recibida a la variable tabla
            let tabla = http.responseText;
            document.getElementById("tabla").innerHTML = tabla;
        }
    }
}

// Función para obtener la lista de pacientes
function getPatients() {
    // Obtener el correo electrónico y la sesión del sessionStorage
    var mail = sessionStorage.getItem('mail');
    var session = sessionStorage.getItem('session');
    
    // Enviar una solicitud GET al backend con los parámetros mail y session
    var http = new XMLHttpRequest();
    http.open('GET', "http://localhost:3000/Entornos/ServePatients?mail=" + mail + "&session=" + session, true);
    http.send();
    
    http.onreadystatechange = function() {
        if (http.readyState == 4 && http.status == 200) {
            let listaPacientes = JSON.parse(http.responseText);
            
            let select = document.getElementById("seleccionPacientes");
            
            // Recorrer la lista de pacientes y crear opciones para el elemento select
            for (let i = 0; i < listaPacientes.length; i++) {
                let option = document.createElement("option");
                option.text = listaPacientes[i];
                option.value = listaPacientes[i];
                select.add(option);
            }
        }
    }
}

// Función para obtener la lista de medicinas
function getMedicines() {
    // Obtener el correo electrónico y la sesión del sessionStorage
    var mail = sessionStorage.getItem('mail');
    var session = sessionStorage.getItem('session');
    
    // Enviar una solicitud GET al backend con los parámetros mail y session
    var http = new XMLHttpRequest();
    http.open('GET', "http://localhost:3000/Entornos/ServeMedicines?mail=" + mail + "&session=" + session, true);
    http.send();
    
    http.onreadystatechange = function() {
        console.log("Ready state: " + http.readyState);
        console.log("Status: " + http.status);
        if (http.readyState == 4 && http.status == 200) {
            let listaMedicines = JSON.parse(http.responseText);
            
            let select = document.getElementById("seleccionMedicinas");
            
            // Recorrer la lista de medicinas y crear opciones para el elemento select
            for (let i = 0; i < listaMedicines.length; i++) {
                let option = document.createElement("option");
                option.text = listaMedicines[i].name;
                option.value = listaMedicines[i].id;
                select.add(option);
            }
        }
    }
}

// Función para enviar un release
function enviarRelease() {
    let http = new XMLHttpRequest();

    // Obtener el correo electrónico y la sesión del sessionStorage
    let mail = sessionStorage.getItem("mail");
    let session = sessionStorage.getItem("session");
    
    // Obtener los valores de los elementos del formulario
    let idXip = document.getElementById("idXip").value;
    let mailPaciente = document.getElementById("seleccionPacientes").value;
    let idMed = document.getElementById("seleccionMedicinas").value;
    let fecha = document.getElementById("fechaVencimiento").value;

    http.open("POST", "http://localhost:3000/Entornos/Release", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    
    // Enviar los parámetros en el cuerpo de la solicitud POST
    http.send("mail=" + mail + "&session=" + session + "&xip_id=" + idXip + "&patient_mail=" + mailPaciente + "&id_medicine=" + idMed + "&end_date=" + fecha);

    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            document.querySelector(".alta").innerHTML = http.responseText;
            idXip = "";
            fecha = "";
        }
    }
}