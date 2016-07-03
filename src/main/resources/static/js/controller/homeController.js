(function() {
	var module = angular.module("userAccountManager");

	var controller = function($scope, $rootScope, userFactory) {

		$scope.desc = false;
		var lastSortParam = "";
		
		// Fetch all users:
		userFactory.getAll().success(function(response){
			$scope.users = response;
		});

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
		
		$scope.onUserEdit = function(user) {
			if ($rootScope.currentUser.userId === user.userId) {
				$scope.selfEditing = true;
			}
			var editUser = function(){
				$scope.selfEditing = false;
				$("#editUserModal").modal("hide");
				var promise = userFactory.updateUser($scope.selectedUser);
				promise.success(function(response){
					var index;
					$scope.users.forEach(function(user, idx){
						if(user.userId === response.userId) {
							index = idx;
							return;
						}
					});
					debugger;
					$scope.users[index] = response;
				});
			};
			$("button[name=update]").on("click", editUser);
			$scope.selectedUser = angular.copy(user);
			$("#editUserModal").modal("show");
		};
		
		$scope.sort = function(sortParam) {
			if (sortParam == lastSortParam) {
				$scope.desc = !$scope.desc;
			} else {
				lastSortParam = sortParam;
				$scope.desc = false;
			}
			userFactory.getAll(sortParam, $scope.desc).success(function(response){
				$scope.users = response;
			});
		};
		
		$scope.logout = function(){
			userFactory.invalidateSession();
		};
	};

	module.controller("homeController", [ "$scope", "$rootScope", "userFactory", controller ]);
}());