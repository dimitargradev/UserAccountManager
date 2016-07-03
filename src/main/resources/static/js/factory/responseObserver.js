(function() {
	var module = angular.module("userAccountManager");

	module.factory('responseObserver', [ '$location', "$q", "$window",
	    function($location, $q, $window) {
		var factory = {
			responseError : function(response) {
				if(response && response.status === 403) {
					$window.localStorage.removeItem("token");
					$location.path("/");
					return $q.reject(response);
				}
				return $q.reject(response);
			}
		};
		return factory;
	} ]);
}());