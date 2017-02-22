angular
    .module('app')
    .factory('ApiService', [
        '$http', '$window',  'Session', '$translate', '$location', function($http, $window, Session, $translate, $location) {
            var service = {};
            service.apiUrl = function (){
                return 'http://localhost:9090/api/';
            };

            service.apiUrlRoot = function(){
                return "";
            };

            service.sendRequestToApi = function(method, path, data) {
                var userId = Session.user ? Session.user.userId : '';
                var apiKey = Session.user ? Session.user.apiKey : '';
                return $http({
                    method: method,
                    url: service.apiUrl() +  service.apiUrlRoot() +   path,
                    data: data,
                    headers: {'Content-Type': 'application/json', 'Api-Key': apiKey, 'User-Id': userId, 'Language': $translate.use()}
                })
            };

            service.sendRequest = function(method, path, data) {
                return $http({
                    method: method,
                    url: service.apiUrl() + path,
                    data: data,
                    headers: {'Content-Type': 'application/json', 'Language': $translate.use()}
                })
            };
            return service;
        }
    ]);
