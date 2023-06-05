/*
function enviarPOST() {
    var http = new XMLHttpRequest();
// Aqui pondremos donde queremos que se vaya enviando la informacion 
// (ordenador localhost,port 3000,projecta tomcat,clase Login, true es que me deixa fer coses una vegada enviat, 
// no necesit esperar a que me contesti)

    let mail = document.getElementById("mail").value
    let pass = document.getElementById("pass").value
    http.open("POST", "http://localhost:3000/Tomcat/Login",true);
    // peticio des atributs segons es tipus arxiu
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded"); // si volem cercar es tipos de content-type aquest es per clau-valor
    // enviam
    http.send("mail="+mail+"&pass="+pass);

    http.onreadystatechange = function() {
        if(this.readyState==4 && this.status==200) {   // es numero 4 es es darrer pas de verificacio i 200 es que ha anat tot be
            let session = this.responseText;
            if(session!=0){
                window.sessionStorage.setItem("mail",mail);
                window.sessionStorage.setItem("session",session);
                document.getElementById("resultat").innerHTML="Login correcto";
            }else{
                document.getElementById("resultat").innerHTML="Login incorrecto";
            }
        }
    }
}

*/
function enviarGET() {
    var http = new XMLHttpRequest();
    let mail = document.getElementById("mail").value;
    let pass = document.getElementById("pass").value;
    http.open("GET", "http://localhost:3000/Entornos/Login?mail=" + mail + "&pass=" + pass, true);
    http.send();
    http.onreadystatechange = function() {
        if (http.readyState == 4 && http.status == 200) {
            let session = http.responseText;
            if (session !="0") {
                window.sessionStorage.setItem("mail", mail);
                window.sessionStorage.setItem("session", session);
                console.log("Respuesta del servidor: " + session); // Agrega este console.log para depurar

                document.getElementById("resultat").innerHTML="Login correcto";
                //pausa de 7 decimas
                setTimeout(function() {
                    gestion();
                },700);
            } else {
                document.getElementById("resultat").innerHTML = "Login incorrecto";
                console.log("Respuesta del servidor: " + session); // Agrega este console.log para depurar
            }
        }
    }
}

function gestion(){
    var codigoSesion =sessionStorage.getItem("session");
    if (codigoSesion != 0) {
        // Redirigir a la página de Gestión
        window.location.href = "Gestion.html";
    }
}

function logOut(){
    //borra el correo  y la sesion del sessionStorage
    sessionStorage.removeItem('mail');
    sessionStorage.removeItem('sesion');
    //Redirige a la pagina de Login
    window.location.href='Login.html';
}
function irAlta() {

    // Obtengo el valor del sessionStorage
    var codigoSesion = sessionStorage.getItem("session");
    // Verifica si se ha guardado la información
    if (codigoSesion != 0) {

        // Redirigir a la página de Gestión
        window.location.href = "Alta.html";
    } else {

        window.location.href = "Login.html";
    }
}

function getTable(){
    //Obtiene el correo electronico y la sesion del sessionStorage
    var mail = sessionStorage.getItem('mail');
    var session = sessionStorage.getItem('session');
    //envia una solicitud GET al backend con los parametros mail y session
    var http = new XMLHttpRequest();
    http.open('GET',"http://localhost:3000/Entornos/ServeXips?mail=" + mail + "&session=" +session, true);
    http.send();
    http.onreadystatechange = function(){
        if(http.readyState==4 && http.status===200){
             // Asigna la respuesta recibida a la variable responseHtml
            let tabla = http.responseText;
            document.getElementById("tabla").innerHTML = tabla;
        }
    }
}

function getPatients(){
        //Obtiene el correo electronico y la sesion del sessionStorage
    var mail = sessionStorage.getItem('mail');
    var session = sessionStorage.getItem('session');
        //envia una solicitud GET al backend con los parametros mail y session
    var http = new XMLHttpRequest();
    http.open('GET',"http://localhost:3000/Entornos/ServePatients?mail=" + mail + "&session=" +session, true);
    http.send();
    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            let listaPacientes = JSON.parse(http.responseText);
            
            let select = document.getElementById("seleccionPacientes");


            for (let i = 0; i < listaPacientes.length; i++) {
                let option = document.createElement("option");
                option.text = listaPacientes[i];
                option.value = listaPacientes[i];
                select.add(option);
            }
        }
    } 
}

function getMedicines(){
    //Obtiene el correo electronico y la sesion del sessionStorage
    var mail = sessionStorage.getItem('mail');
    var session = sessionStorage.getItem('session');
    //envia una solicitud GET al backend con los parametros mail y session
    var http = new XMLHttpRequest();
    http.open('GET',"http://localhost:3000/Entornos/ServeMedicines?mail=" + mail + "&session=" +session, true);
    http.send();
    http.onreadystatechange = function () {
        console.log("Ready state: " + http.readyState);
        console.log("Status: " + http.status);
        if (http.readyState == 4 && http.status == 200) {
            let listaMedicines = JSON.parse(http.responseText);

            let select = document.getElementById("seleccionMedicinas");

            for (let i = 0; i < listaMedicines.length; i++) {
                let option = document.createElement("option");
                option.text = listaMedicines[i].name;
                option.value = listaMedicines[i].id;
                select.add(option);
            }
        }
    }
}

function enviarRelease() {
    let http = new XMLHttpRequest();

    let mail = sessionStorage.getItem("mail");
    let session = sessionStorage.getItem("session");
    
    let idXip = document.getElementById("idXip").value;
    let mailPaciente = document.getElementById("seleccionPacientes").value;
    let idMed = document.getElementById("seleccionMedicinas").value;
    let fecha = document.getElementById("fechaVencimiento").value;

    http.open("POST", "http://localhost:3000/Entornos/Release", true);
    http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    http.send("mail=" + mail + "&session=" + session + "&xip_id=" + idXip + "&patient_mail=" + mailPaciente + "&id_medicine=" + idMed + "&end_date=" + fecha);

    http.onreadystatechange = function () {
        if (http.readyState == 4 && http.status == 200) {
            document.querySelector(".alta").innerHTML = http.responseText;
            idXip = "";
            fecha = "";
        }
    }
}


