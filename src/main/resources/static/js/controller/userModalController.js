(function() {
	var module = angular.module('userAccountManager');

	var controller = function($scope, $uibModalInstance, items) {
		var re = /^\S+@\S+\.\S+$/;
		$scope.re = new RegExp(re);
		$scope.user = angular.copy(items.user);

		$scope.submit = function() {
			$uibModalInstance.close($scope.user);
		}

		$scope.close = function() {
			$uibModalInstance.dismiss("Modal dismissed!");
		}
	};

	module.controller('userModalCtrl', controller);
}());