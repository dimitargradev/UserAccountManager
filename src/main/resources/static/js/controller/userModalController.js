(function() {
	var module = angular.module('userAccountManager');

	var controller = function($scope, $uibModalInstance, items) {
		// Email regex:
		var re = /^\S+@\S+\.\S+$/;
		$scope.re = new RegExp(re);

		// Define if we are creating or editing an user;
		if (items.user) {
			$scope.user = angular.copy(items.user);
			$scope.submitButtonName = "Update";
			$scope.modalTitle = "Edit";
		} else {
			$scope.createMode = true;
			$scope.submitButtonName = "Create";
			$scope.modalTitle = "Create";
		}

		$scope.submit = function() {
			if ($scope.userForm.$valid) {
				$uibModalInstance.close($scope.user);
			}
		}

		$scope.close = function() {
			$uibModalInstance.dismiss("Modal dismissed!");
		}
	};

	module.controller('userModalCtrl', controller);
}());