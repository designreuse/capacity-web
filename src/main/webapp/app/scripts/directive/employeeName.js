'use strict';

angular.module('capacityApp')
	.directive('employeeName', function() {
		return {
			templateUrl: 'app/scripts/directive/employeeName.html',
			restrict: 'E',
			replace: true,
			scope: {
				employee: '='
			}
		};
	});