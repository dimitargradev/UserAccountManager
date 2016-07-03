(function() {
	var module = angular.module("userAccountManager");

	module.directive("hasError", function() {
		var directive = {
			link: function(scope, element, attr, ctrl){
				var input = $(element).find("input[data-ng-model]");
				var inputName = input.attr("name");
				if (element.length) {
					scope.$watch(function(){
						return scope.loginForm[inputName].$invalid && scope.loginForm[inputName].$dirty;
					}, function(invalid){
						$(element).toggleClass("has-error", invalid);
					}, true);
				}
			}
		};
		return directive;
	});
}());