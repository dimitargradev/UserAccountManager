(function() {
	var module = angular.module("userAccountManager", [ "ngRoute", "ngMessages", "ui.bootstrap"]);

	module.config(function($routeProvider, $httpProvider) {
		$routeProvider.when("/", {
		    templateUrl : '/html/login.html',
		    controller : 'loginController'
		}).when("/home", {
		    templateUrl : '/html/home.html',
		    controller : 'homeController'
		}).otherwise({
			templateUrl : '/html/404.html'
		});

		$httpProvider.interceptors.push('responseObserver');
	});

	module.run([ "$rootScope", "$window", "userFactory",
        function($rootScope, $window, userFactory) {
	        $rootScope.$on("$routeChangeStart", function(event, next, current) {
		        if (!next) {
			        return;
		        }

		        var token = $window.localStorage.getItem("token");
		        if (!token) {
			        next.templateUrl = "/html/login.html";
			        next.controller = "loginController";
		        }
		        if (!$rootScope.currentUser) {
		        	userFactory.getCurrentUser();
		        }
	        })
        } ]);
}());