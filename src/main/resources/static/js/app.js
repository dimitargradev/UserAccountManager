(function() {
	angular.module("userAccountManager", []);

	var loginOrRegisterController = function($scope) {
		$scope.loginPage = true;
	};
	
	angular.module("userAccountManager").controller(
			"loginOrRegisterController",
			[ "$scope", loginOrRegisterController ]);
}());