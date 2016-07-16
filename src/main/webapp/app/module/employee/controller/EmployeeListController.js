(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EmployeeListController', EmployeeListController);

	EmployeeListController.$inject = ['$scope', '$location', '$rootScope', 'Employee'];

	function EmployeeListController($scope, $location, $rootScope, Employee) {
		Employee.query(function(employees) {
			$scope.employees = employees;
		});

		$scope.add = function() {
			$location.path('/employees/new');
		};

		$scope.predicate = 'name';
		$scope.reverse = false;
		$scope.order = function(predicate) {
			$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
			$scope.predicate = predicate;
		};

		$scope.searchFilter = function(string) {
			$rootScope.search = string;
		};
	}
})();
