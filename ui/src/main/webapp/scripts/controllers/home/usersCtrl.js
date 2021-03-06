angular
    .module('app')
    .controller('usersCtrl', [
        '$scope', '$http', 'ApiService', '$window', 'Session', '$translate', function($scope, $http, ApiService, $window, Session, $translate) {
            if(Session.isClosed()) {
                $window.location.href = "/";
            }
            if(!Session.user.admin) {
                $window.location.href = "/#/offers";
            }
            $scope.message = '';
            $scope.isProcessing = true;
            $scope.showMessage = false;
            $scope.isError = false;
            $scope.offers = [];
            ApiService.sendRequestToApi('GET', 'admin/users')
                .success(function(data) {
                    console.log(data);
                    $scope.isProcessing = false;
                    if (data.success) {
                        $scope.users = data.object;
                    } else {
                        $scope.message = data.message.message;
                        $scope.isError = true;
                    }
                })
                .error(function() {
                    $scope.isProcessing = false;
                    $scope.message = $translate.instant('AN_ERROR_OCCURRED');
                    $scope.showMessage = true;
                    $scope.isError = true;
                });
        }
    ]);


