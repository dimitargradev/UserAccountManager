(function() {
	var module = angular.module("userAccountManager");

	var controller = function($scope, $rootScope, userFactory, $uibModal) {

		// Sorting params:
		$scope.desc = false;
		var lastSortParam = "";

		// Email regex:
		var re = /^\S+@\S+\.\S+$/;
		$scope.re = new RegExp(re);

		// Fetch all users:
		_fetchUsers(0);

		$scope.deleteUser = function(user) {
			var userId = user.userId;
			if ($rootScope.currentUser.userId === userId) {
				alert("You are not allowed to delete your own profile!");
				return;
			}
			
			var isTheLastOnPage = $scope.users.length === 1;
			userFactory.deleteUser(userId).success(function() {
				// If we've just deleted the last record on the page - 
				// fetch the first page again:
				if (isTheLastOnPage) {
					$scope.currentPage = null;
					_fetchUsers(0);
				} else {
					$scope.users = $scope.users.filter(function(user) {
						return user.userId != userId;
					});
				}
			});
		};

		$scope.editUser = function(user) {
			var modalInstance = _initializeModal(user);

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
				}).error(
				        function(response) {
					        if (response && response.fieldErrors) {
						        var msg = "The following errors occured:\n";
						        response.fieldErrors.forEach(function(error) {
							        var line = "The filed '" + error.fieldName + "' "
							                + error.message + "\n";
							        msg += line;
						        });
						        alert(msg);
					        } else {
						        alert("Ooops! An unexpected error occured!\n"
						                + " Please try again later!");
					        }
				        });
			});
		};

		$scope.createUser = function() {
			var modalInstance = _initializeModal();

			modalInstance.result.then(function(user) {
				userFactory.createUser(user).success(function(response) {
					$scope.users.push(response);
				}).error(
				        function(response) {
					        if (response && response.fieldErrors) {
						        var msg = "The following errors occured:\n";
						        response.fieldErrors.forEach(function(error) {
							        var line = "The filed '" + error.fieldName + "' "
							                + error.message + "\n";
							        msg += line;
						        });
						        alert(msg);
					        } else {
						        alert("Ooops! An unexpected error occured!\n"
						                + " Please try again later!");
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
			_fetchUsers($scope.currentPage.value, sortParam, $scope.desc);
		};

		$scope.logout = function() {
			userFactory.invalidateSession();
		};

		$scope.onPageChange = function() {
			_fetchUsers($scope.currentPage.value);
		}

		function _fetchUsers(pageNumber, sortParam, isDesc) {
			userFactory.getAll(pageNumber, sortParam, isDesc).success(function(response) {
				$scope.users = response.entities;
				_initializePages(response.numberOfPages);
			});
		}

		function _initializeModal(user) {
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
		}

		function _initializePages(numberOfPages) {
			var currentPage = 0;
			var pages = [];
			while (currentPage < numberOfPages) {
				var page = {
				    value : currentPage,
				    name : "Page " + (currentPage + 1)
				};
				pages.push(page);
				currentPage++;
			}
			$scope.pages = pages;
			$scope.currentPage = $scope.currentPage || pages[0];
		}
	};
	module.controller("homeController", [ "$scope", "$rootScope", "userFactory", "$uibModal",
	        controller ]);
}());