/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.directive('labelGroup', labelGroup);

	function labelGroup() {
		var autoincrementId = 0;

		/**
		 * @param {ng.IScope} scope
		 */
		function link(scope, element) {
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

		return {
			templateUrl: 'app/scripts/directive/labelGroup.html',
			restrict: 'E',
			replace: true,
			transclude: true,
			scope: {
				label: '@'
			},
			link: link
		};
	}
})();
