var app = angular.module("userAccountManager");

app.directive("ngUnique", [ "userFactory", function(userFactory) {
	return {
	    require : "ngModel",
	    link : function(scope, elem, attrs, model) {
    	// Timeout needed because of modals: if we invoke elem.on() without timeout
    	// there is the risk of the element not to be rendered when the event
    	// is being registered;
	    	var modelInitialValue;
		    setTimeout(function(){
		    	// Prevent uniqueness check when editing some data and leave
		    	// the input as it is;
		    	modelInitialValue = model.$modelValue;
		    	elem.on("blur", function() {
				    if (scope.loginPage || model.$modelValue === modelInitialValue) {
					    return;
				    } else {
					    var promise = userFactory.checkUniqueness(
					    		model.$name, model.$modelValue);
					    promise.success(function(){
					    	model.$setValidity('unique', true);
					    }).error(function() {
					    	model.$setValidity('unique', false);
					    });
				    }
			    });
		    }, 200);
	    }
	}
} ]);