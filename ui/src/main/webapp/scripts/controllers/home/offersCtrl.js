angular
    .module('app')
    .controller('offersCtrl', [
        '$scope', '$http', 'ApiService', '$window', 'Session', '$translate', function($scope, $http, ApiService, $window, Session, $translate) {
            if(Session.isClosed()) {
                $window.location.href = "/";
            }
            if(Session.user.admin) {
                $window.location.href = "/#/users";
            }
            $scope.message = '';
            $scope.isProcessing = true;
            $scope.showMessage = false;
            $scope.isError = false;
            $scope.offers = [];
            ApiService.sendRequestToApi('GET', 'offers')
                .success(function(data) {
                    console.log(data);
                    $scope.isProcessing = false;
                    if (data.success) {
                        $scope.offers = data.object;
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

            $scope.buyTicket = function(idx) {
                $scope.showMessage = false;
                $scope.isProcessing = true;
                $scope.isError = false;
                var ticket = {
                    userId: Session.user.userId,
                    amount: $scope.offers[idx].amount,
                    currency: $scope.offers[idx].currency,
                    from: $scope.offers[idx].from,
                    to: $scope.offers[idx].to,
                    quantity:  $("#quantity_" + idx).val()
                };

                ApiService.sendRequestToApi('POST', 'purchase', ticket)
                    .success(function(data) {
                        console.log(data);
                        $scope.isProcessing = false;
                        if (data.success) {
                            $scope.message = data.message;
                            $("#purchase_button_" + idx).hide();
                            $("#quantity_" + idx).hide();
                            $("#purchase_label_" + idx).removeClass("ng-hide");
                            $("#send_email_" + idx).removeClass("ng-hide");
                            $("#download_pdf_" + idx).removeClass("ng-hide");
                        } else {
                            $scope.message = data.message;
                            $scope.isError = true;
                        }
                        $scope.showMessage = true;
                    })
                    .error(function() {
                        $scope.isProcessing = false;
                        $scope.isError = true;
                        $scope.message = $translate.instant('AN_ERROR_OCCURRED');
                        $scope.showMessage = true;
                    });
            };

            $scope.sendEmail = function(idx) {
                $scope.showMessage = false;
                $scope.isProcessing = true;
                $scope.isError = false;
                var ticket = {
                    userId: Session.user.userId,
                    amount: $scope.offers[idx].amount,
                    currency: $scope.offers[idx].currency,
                    from: $scope.offers[idx].from,
                    to: $scope.offers[idx].to,
                    quantity:  $("#quantity_" + idx).val()
                };

                ApiService.sendRequestToApi('POST', 'notification/email', ticket)
                    .success(function(data) {
                        console.log(data);
                        $scope.isProcessing = false;
                        if (data.success) {
                            $scope.message = data.message;
                            $("#send_email_" + idx).hide();
                        } else {
                            $scope.message = data.message;
                            $scope.isError = true;
                        }
                        $scope.showMessage = true;
                    })
                    .error(function() {
                        $scope.isProcessing = false;
                        $scope.isError = true;
                        $scope.message = $translate.instant('AN_ERROR_OCCURRED');
                        $scope.showMessage = true;
                    });
            };

            $scope.downloadPdf = function(idx) {
                $scope.showMessage = false;
                $scope.isProcessing = true;
                $scope.isError = false;
                var ticket = {
                    userId: Session.user.userId,
                    amount: $scope.offers[idx].amount,
                    currency: $scope.offers[idx].currency,
                    from: $scope.offers[idx].from,
                    to: $scope.offers[idx].to,
                    quantity:  $("#quantity_" + idx).val()
                };

                ApiService.sendRequestToApi('POST', 'notification/generate_pdf', ticket)
                    .success(function(data) {
                        console.log(data);
                        $scope.isProcessing = false;
                        $window.open(ApiService.apiUrl()+'notification/pdf/' + data.object, '_blank');
                    })
                    .error(function() {
                        $scope.isProcessing = false;
                        $scope.isError = true;
                        $scope.message = $translate.instant('AN_ERROR_OCCURRED');
                        $scope.showMessage = true;
                    });
            }
        }
    ]);


