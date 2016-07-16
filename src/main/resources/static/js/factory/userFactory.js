(function() {
	var module = angular.module("userAccountManager");

	module.factory('userFactory', [ '$http', '$rootScope', "$window", 
	    "httpConfigFactory", "$location",  function($http, $rootScope, $window, 
	    httpConfigFactory, $location) {
			// Private fields:
			var getConfig = httpConfigFactory.getConfig;
			
			var setToken = function(token) {
				if (token){
					$window.localStorage.setItem("token", token);
				} else {
					$window.localStorage.removeItem("token");
				}
			};
			
			var requestCurrentUser = function(options) {
				var promise = $http.get("/api/users/current", getConfig());
				return promise;
			};
			// Public interface:
			return {
				// Returns the promise of the newly registered user
				registerUser : function(user) {
					var promise = $http.post("/api/register",user);
					return promise;
				},
				// Handels two requests:
				// 1) Login, which, when successful, returns 200OK + a header
				// with the
				// authentication TOKEN;
				// 2) Gets the current user - this request contains the
				// authentication
				// TOKEN returned from 1), because it must go through the
				// authentication filter on the server in order to reach some
				// safe resources (the current user in this case).
				login : function(user, options) {
					var body = {
					    username: user.username,
					    password: user.password
					};
					var promise = $http.post("/api/login",body);
					
					promise.success(function(response, status, headers){
						setToken(headers()["x-auth-token"]);
						requestCurrentUser().success(function(response){
							$rootScope.currentUser = response;
							options.success();
						}).error(function(response, status){
							$rootscope.currentUser = {};
							options.error(status);
						});
					});
					
					promise.error(function(response, status){
						options.error(status);
					});
				},
				
				getAll : function(page, sortParam, direction) {
					var url = "/api/users/all?page=" + page;
					if (sortParam) {
						var sortDirection = direction ? "DESC" : "ASC";
						url += "&" + "orderBy=" + sortParam + "&direction=" + sortDirection;
					}
					var promise = $http.get(url, getConfig());
					return promise;
				},
				
				updateUser : function(user) {
					var url = "/api/users";
					var promise = $http.put(url, user, getConfig());
					return promise;
				},
				
				createUser : function(user) {
					var url = "/api/users";
					var promise = $http.post(url, user, getConfig());
					return promise;
				},
				
				deleteUser : function(userId) {
					var url = "/api/users/delete/" + userId;
					var promise = $http.delete(url, getConfig());
					return promise;
				},
				
				checkUniqueness : function(name, value){
					var params = {};
					params[name] = value;
					
					var config = {
						method: "GET",
						url: "/api/users/unique",
						params: params
					}
					
					var promise = $http(config);
					return promise;
				},
				
				getCurrentUser : function() {
					if ($rootScope.currentUser) {
						return $rootScope.currentUser;
					} else {
						requestCurrentUser().success(function(response){
							$rootScope.currentUser = response;
						});
					}
				},
				
				getToken : function() {
					return $window.localStorage.getItem("token");
				},
				
				invalidateSession : function() {
					setToken(null);
					$rootScope.currentUser = {};
					$location.path("/");
				}
			}
	} ]);
}());