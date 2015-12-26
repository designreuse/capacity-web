'use strict';

angular.module('capacityApp')
	.controller('EmployeeListController', ['$scope', '$location', 'Employee', function ($scope, $location, Employee) {
		Employee.query(function(employees) {
	        $scope.employees = employees;
	        
	        $scope.add = function() {
	        	$location.path('/employees/new');
        	};
	    });
	}]
);