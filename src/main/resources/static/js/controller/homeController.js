(function() {
	var module = angular.module("userAccountManager");

	var controller = function($scope, $rootScope, userFactory, $uibModal) {

		$scope.desc = false;
		var lastSortParam = "";
		// Email regex:
		var re = /^\S+@\S+\.\S+$/;
		$scope.re = new RegExp(re);

		// Fetch all users:
		userFactory.getAll().success(function(response) {
			$scope.users = response;
		});

		var initializeModal = function(user) {
			var modalInstance = $uibModal.open({
			    animation : true,
			    templateUrl : 'html/editUserModal.html',
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
				var userIndex;
				userFactory.updateUser(user).success(function(response) {
					$.each($scope.users, function(index, user) {
						if (user.userId === response.userId) {
							userIndex = index;
							return false;
						}
					});
					if (typeof userIndex !== "undefined") {
						$scope.users[userIndex] = response;
					}
				}).error(function(response) {
			        if (response && response.fieldErrors) {
				        var msg = "The following errors occured:\n";
				        response.fieldErrors.forEach(function(error) {
					        var line = "The filed '" + error.fieldName + "' "
					                + error.message + "\n";
					        msg += line;
				        });
				        alert(msg);
			        } else {
			        	alert("Ooops! An unexpected error occured!\n" +
	        			" Please try again later!");
			        }
		        });
			});
		};

		$scope.createUser = function() {
			var modalInstance = initializeModal();

			modalInstance.result.then(function(user) {
				userFactory.createUser(user).success(function(response) {
					$scope.users.push(response);
				}).error(function(response) {
			        if (response && response.fieldErrors) {
				        var msg = "The following errors occured:\n";
				        response.fieldErrors.forEach(function(error) {
					        var line = "The filed '" + error.fieldName + "' "
					                + error.message + "\n";
					        msg += line;
				        });
				        alert(msg);
			        } else {
			        	alert("Ooops! An unexpected error occured!\n" +
			        			" Please try again later!");
			        }
		        });
			});
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