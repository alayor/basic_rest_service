angular
    .module('app')
    .factory('AuthService', [
        '$http', '$window',  'Session', 'ApiService', '$translate', function($http, $window, Session, ApiService, $translate) {
            var authService = {};

            authService.logout = function() {
                Session.destroy();
                $window.location.href = "/#/signin";
            };

            authService.isAuthenticated = function() {
                return !!Session.user.companyId;
            };

            return authService;
        }
    ]);