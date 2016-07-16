(function() {
	var module = angular.module("userAccountManager");

	var controller = function($scope, $location, userFactory) {

		// Email regex:
		var re = /^\S+@\S+\.\S+$/;
		$scope.re = new RegExp(re);
		
		var loginSuccess = function() {
			// Reload the "/" route
			$location.path("/home");
		};
		var loginError = function(status) {
			if (status === 401) {
				alert("Bad credentials!");
			} else {
				alert("Ooops! Something went wrong!");
			}
		};
		var loginOptions = {
		    success : loginSuccess,
		    error : loginError
		};
		
		$scope.loginPage = true;
		$scope.user = {};

		$scope.login = function() {
			if ($scope.loginForm.username.$valid && $scope.loginForm.password.$valid) {
				var user = {
				    username : $scope.user.username,
				    password : $scope.user.password
				};
				userFactory.login(user, loginOptions);
			}
		};
		$scope.register = function() {
			if ($scope.loginForm.$valid) {
				var password = $scope.user.password;
				var promise = userFactory.registerUser($scope.user);
				promise.then(function(response) {
					response.data.password = password;
					userFactory.login(response.data, loginOptions);
				});
			}
		};
	};

	module.controller("loginController", [ "$scope", "$location", "userFactory", controller ]);
}());