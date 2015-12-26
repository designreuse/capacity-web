angular.module('capacityApp', ['ngResource', 'ngRoute', 'ngTagsInput', 'ui.calendar'])
	.controller('MainCtrl', ['$scope', '$location', function($scope, $location) {
		$scope.currentModule = function() {
			var currentPath = $location.path();
			if (currentPath.startsWith('/')) {
				currentPath = currentPath.substring(1);
			}
			var firstSlash = currentPath.indexOf('/');
			if (firstSlash >= 0) {
				currentPath = currentPath.substring(0, firstSlash);
			}
			return currentPath;
		}

	}]);