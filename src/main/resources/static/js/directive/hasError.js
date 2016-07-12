(function() {
	var module = angular.module("userAccountManager");

	module.directive("hasError", function() {
		var directive = {
		    require: "^form",
		    link : function(scope, element, attr, formCtrl) {
			    var input = $(element).find("input[data-ng-model]");
			    var inputName = input.attr("name");
			    if (element.length) {
				    scope.$watch(function() {
					    return formCtrl[inputName].$invalid
					            && formCtrl[inputName].$dirty;
				    }, function(invalid) {
					    $(element).toggleClass("has-error", invalid);
				    }, true);
			    }
		    }
		};
		return directive;
	});
}());