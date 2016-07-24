/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.directive('employeeName', employeeName);

	function employeeName() {
		return {
			templateUrl: 'app/scripts/directive/employeeName.html',
			restrict: 'E',
			replace: true,
			scope: {
				employee: '='
			}
		};
	}
})();
