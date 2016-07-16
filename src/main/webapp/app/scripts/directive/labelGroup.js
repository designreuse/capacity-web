(function() {
	'use strict';

	angular.module('capacityApp')
		.directive('labelGroup', labelGroup);
	
	function labelGroup() {
		var autoincrementId = 0;
		
		return {
			templateUrl: 'app/scripts/directive/labelGroup.html',
			restrict: 'E',
			replace: true,
			transclude: true,
			scope: {
				label: '@'
			},
			link: function(scope, element, attrs, formController) {
				var controls = element.find(':input');
				if (controls.length > 0) {
					var id = controls[0].id;
					if (!id) {
						id = 'control_id_' + (autoincrementId++);
						controls[0].id = id;
					}
					scope.forControl = id;
				}
			}
		};
	}
})();
