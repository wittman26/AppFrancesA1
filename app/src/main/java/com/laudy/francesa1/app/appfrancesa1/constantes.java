package com.laudy.francesa1.app.appfrancesa1;

/**
 * Created by Laudy on 29/10/2016.
 */

public class Constantes {
    //URLs usadas para consultas
    public static final String SERVIDOR = "http://laudyfrancesa1.eu5.org";
    public static final String LOGIN = SERVIDOR + "/login.php";
    public static final String NUEVO_USUARIO = SERVIDOR + "/nuevousuario.php";
    public static final String ACTUALIZAR_USUARIO = SERVIDOR + "/actualizarusuario.php";
    public static final String ELIMINAR_PERFIL = SERVIDOR + "/eliminarperfil.php";
    public static final String DOSSIERS = SERVIDOR + "/dossiers.php";
    public static final String ACTAPREND = SERVIDOR + "/actividades.php";
    public static final String PREGUNTAS = SERVIDOR + "/preguntas.php";
    public static final String GUARDAR = SERVIDOR + "/guardarpuntaje.php";
    public static final String LOGROS = SERVIDOR + "/logros.php";

    public static final String IMAGENES = SERVIDOR + "/images/";
    public static final String AUDIOS = SERVIDOR + "/mp3/";

    //Nombre de parámetros
    public static final String IDDOSSIER = "IDDOSSIER";
    public static final String NOMBREDOSSIER = "NOMBREDOSSIER";
    public static final String IDACTAPREND = "IDACTAPREND";
    public static final String NOMBREACT = "NOMBREACT";
    public static final String PUNTAJETOTAL = "PUNTAJETOTAL";
    public static final String PUNTAJEMAXIMO = "PUNTAJEMAXIMO";

    //Mensajes
    public static final String MALUSUARIO = "Usuario o contraseña incorrectos";
    public static final String BIEN = "Trés bien!";
    public static final String MAL = "Attention!";

    //Tipos de pregunta
    public static final String IMAGEN = "I";
    public static final String AUDIO = "A";
    public static final String TEXTO = "T";
}
