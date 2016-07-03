(function() {
	var module = angular.module("userAccountManager");

	module.factory('httpConfigFactory', [ '$window', function($window) {
		var config = {
			headers : {}
		};
		config.headers["accept"] = "application/json; charset=utf-8";
		var factory = {
			getConfig : function(options) {
				var token = $window.localStorage.getItem("token");
				if (token) {
					config.headers["X-AUTH-TOKEN"] = token;
				}
				return config;
			}
		};
		return factory;
	} ]);
}());