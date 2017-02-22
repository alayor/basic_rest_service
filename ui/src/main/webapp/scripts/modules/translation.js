angular.module('translation', ['pascalprecht.translate'])
    .config(function($translateProvider) {
        $translateProvider.translations('en', {
            AVAILABLE_OFFERS: 'Available Offers',
            AMOUNT: 'Amount',
            SIGNIN: 'Log In',
            FROM: 'From',
            TO: 'To',
            PLEASE_SIGNIN: 'Enter your email and password',
            SIGNUP: "Create account",
            BUY_TICKET: "Buy ticket",
            PURCHASED_TICKETS: "Purchased tickets",
            PLEASE_SIGNUP: "Enter your information",
            EMAIL: "Email",
            PASSWORD: "Password",
            NEW_PASSWORD: "New password",
            CONFIRM_PASSWORD: "Confirm password",
            NAME: "Name",
            SEND_EMAIL: "Send Email",
            DOWNLOAD_PDF: "Download PDF",
            AN_ERROR_OCCURRED: "An error has occurred. Please contact our support department.",
            USER_LIST: "Users List",
            LOGOUT: "Log out",
            QUANTITY: "Quantity"
        })
            .translations('es', {
                AVAILABLE_OFFERS: 'Ofertas disponibles',
                AMOUNT: 'Cantidad',
                SIGNIN: 'Ingresar',
                FROM: 'Origen',
                TO: 'Destino',
                PLEASE_SIGNIN: 'Ingrese su correo electronico y passwprd',
                SIGNUP: "Crear cuenta",
                BUY_TICKET: "Comprar ticket",
                PURCHASED_TICKETS: "Tickets comprados",
                PLEASE_SIGNUP: "Ingrese su informacion",
                EMAIL: "Email",
                PASSWORD: "Contrasena",
                NEW_PASSWORD: "Nueva contrasena",
                CONFIRM_PASSWORD: "Confirmar contrasena",
                NAME: "Nombre",
                SEND_EMAIL: "Enviar correo electronico",
                DOWNLOAD_PDF: "Descargar PDF",
                AN_ERROR_OCCURRED: "Ha ocurrido un error. Favor the comunicarsec con nuestro departamento de soporte.",
                USER_LIST: "Lista de usuarios",
                LOGOUT: "Salir",
                QUANTITY: "Cantidad"
            });
        $translateProvider.preferredLanguage('en');
    });
