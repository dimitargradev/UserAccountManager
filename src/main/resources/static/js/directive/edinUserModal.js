(function() {
	var module = angular.module("userAccountManager");

	module.directive("editUser", function() {
		var directive = {
		    restrict : "E",
		    scope : true,
		    link : function(scope, element, attrs) {
		    	scope.save = function(){
		    		debugger;
		    	};
		    }
		};

		return directive;
	});
}());