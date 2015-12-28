'use strict';

angular.module('capacityApp')
	.controller('EmployeeListController', ['$scope', '$location', '$rootScope', 'Employee', function ($scope, $location, $rootScope, Employee) {
		Employee.query(function(employees) {
			$scope.employees = employees;
		});

		$scope.add = function() {
			$location.path('/employees/new');
		};

		$scope.searchFilter = function(string) {
			$rootScope.search = string;
		};
	}]
);