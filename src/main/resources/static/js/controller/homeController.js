(function() {
	var module = angular.module("userAccountManager");

	var controller = function($scope, $rootScope, userFactory, $uibModal) {

		$scope.desc = false;
		var lastSortParam = "";

		// Fetch all users:
		userFactory.getAll().success(function(response) {
			$scope.users = response;
		});
		
		var initializeModal = function(user) {
			var modalInstance = $uibModal.open({
			    animation : true,
			    templateUrl : 'js/directive/editUserModal.html',
			    controller : 'userModalCtrl',
			    size : "sm",
			    resolve : {
				    items : function() {
					    return {
						    user : user
					    };
				    }
			    }
			});
			return modalInstance;
		};

		$scope.deleteUser = function(user) {
			var userId = user.userId;
			if ($rootScope.currentUser.userId === userId) {
				alert("You are not allowed to delete your own profile!");
				return;
			}
			userFactory.deleteUser(userId).success(function() {
				$scope.users = $scope.users.filter(function(user) {
					return user.userId != userId;
				});
			});
		};

		$scope.editUser = function(user) {
			var modalInstance = initializeModal(user);
			
			modalInstance.result.then(function(user) {
				console.log(user);
			}, function() {
			});

		};

		$scope.createUser = function() {
			var modalInstance = initializeModal();
		};

		$scope.sort = function(sortParam) {
			if (sortParam == lastSortParam) {
				$scope.desc = !$scope.desc;
			} else {
				lastSortParam = sortParam;
				$scope.desc = false;
			}
			userFactory.getAll(sortParam, $scope.desc).success(function(response) {
				$scope.users = response;
			});
		};

		$scope.logout = function() {
			userFactory.invalidateSession();
		};
	};
	module.controller("homeController", [ "$scope", "$rootScope", "userFactory", "$uibModal",
	        controller ]);
}());