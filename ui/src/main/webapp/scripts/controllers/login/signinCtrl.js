angular
    .module('app')
    .controller('signinCtrl', [
        '$scope', '$http', '$rootScope', '$window', 'authEvents', 'AuthService', '$translate', 'Session', 'ApiService',
        function($scope, $http, $rootScope, $window, authEvents, AuthService, $translate, Session, ApiService) {
            $scope.showMessage = false;
            $scope.isError = false;
            $scope.isUserActive = true;
            $scope.credentials = {
                username: '',
                password: ''
            };
            $scope.isProcessing = false;
            $scope.message = '';
            $scope.login = function(credentials) {
                $scope.showMessage = false;
                $scope.isError = false;
                $scope.isUserActive = true;
                $scope.isProcessing = true;
                ApiService.sendRequest('POST',  'users/login', credentials)
                    .success(function(data) {
                        $scope.isProcessing = false;
                        if (data.success) {
                            Session.create(data.object);
                            if(data.object.admin) {
                                $window.location.href = "/#/users";
                            } else {
                                $window.location.href = "/#/offers";
                            }
                        } else {
                            $scope.message = data.message;
                            $scope.showMessage = true;
                            $scope.isError = true;
                        }
                    })
                    .error(function() {
                        $scope.isProcessing = false;
                        $scope.message = $translate.instant('AN_ERROR_OCCURRED');
                        $scope.showMessage = true;
                        $scope.isError = true;
                    });

            };
        }
    ]);
