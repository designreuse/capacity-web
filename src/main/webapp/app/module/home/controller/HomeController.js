(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('HomeController', HomeController);

	HomeController.$inject = ['$scope'];

	function HomeController($scope) {
		$scope.title = 'Welcome';
	}
})();
