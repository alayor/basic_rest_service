angular
    .module('app', [
        'ui.router', 'translation', 'ngSanitize'
    ])
    .config([
        '$urlRouterProvider', '$stateProvider', '$translateProvider', function($urlRouteProvider, $stateProvider) {
            $urlRouteProvider.otherwise('/');

            $stateProvider
                .state('welcome', {
                    url: '/',
                    views: {
                        content: {
                            templateUrl: '/templates/login/welcome.html',
                            controller: 'signinCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('signin', {
                    url: '/signin',
                    views: {
                        content: {
                            templateUrl: '/templates/login/signin.html',
                            controller: 'signinCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('logout', {
                    url: '/',
                    controller: 'logoutCtrl',
                    views:{
                        content: {
                            controller: 'logoutCtrl'
                        }
                    }
                })
                .state('signup', {
                    url: '/signup',
                    views: {
                        content: {
                            templateUrl: '/templates/login/signup.html',
                            controller: 'signupCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('change_password', {
                    url: '/change_password',
                    views: {
                        content: {
                            templateUrl: '/templates/login/change_password.html',
                            controller: 'changePasswordCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('send_activation_email', {
                    url: '/send_activation_email',
                    views: {
                        content: {
                            templateUrl: '/templates/login/send_activation_email.html',
                            controller: 'sendActivationEmailCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('send_temp_password_email', {
                    url: '/send_temp_password_email',
                    views: {
                        content: {
                            templateUrl: '/templates/login/send_temp_password_email.html',
                            controller: 'sendTempPasswordEmailCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('activate', {
                    url: '/activate',
                    views: {
                        content: {
                            templateUrl: '/templates/login/activate.html',
                            controller: 'activateCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('home', {
                    url: '/home',
                    views: {
                        header: {
                            templateUrl: '/templates/home/nav.html',
                            controller: 'navCtrl'
                        },
                        content: {
                            templateUrl: '/templates/home/home.html',
                            controller: 'homeCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('offers', {
                    url: '/offers',
                    views: {
                        header: {
                            templateUrl: '/templates/home/nav.html',
                            controller: 'navCtrl'
                        },
                        content: {
                            templateUrl: '/templates/home/offers.html',
                            controller: 'offersCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('users', {
                    url: '/users',
                    views: {
                        header: {
                            templateUrl: '/templates/home/nav.html',
                            controller: 'navCtrl'
                        },
                        content: {
                            templateUrl: '/templates/home/users.html',
                            controller: 'usersCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('purchased_tickets', {
                    url: '/purchased_tickets',
                    views: {
                        header: {
                            templateUrl: '/templates/home/nav.html',
                            controller: 'navCtrl'
                        },
                        content: {
                            templateUrl: '/templates/home/purchased_tickets.html',
                            controller: 'purchasedTicketsCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                })
                .state('promotions', {
                    url: '/promotions',
                    views: {
                        header: {
                            templateUrl: '/templates/home/nav.html',
                            controller: 'navCtrl'
                        },
                        content: {
                            templateUrl: '/templates/home/promotions.html',
                            controller: 'promotionsCtrl'
                        },
                        footer: {
                            templateUrl: '/templates/home/footer.html',
                            controller: 'footerCtrl'
                        }
                    }
                });
        }
    ]);
