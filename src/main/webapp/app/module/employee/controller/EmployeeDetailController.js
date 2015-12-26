'use strict';

angular.module('capacityApp')
	.controller('EmployeeDetailController', ['$scope', '$route', '$location', 'Employee', function ($scope, $route, $location, Employee) {
		$scope.id = $route.current.params.id;
		
		if ($scope.id == 'new') {
			$scope.employee = new Employee();

			$scope.save = function() {
				$scope.employee.$save(function() {
			        $location.path('/employees');
				});
			};
		} else {
			Employee.get({id: $scope.id}, function(employee) {
		       $scope.employee = employee;
		    });

			$scope.save = function() {
				$scope.employee.$update(function() {
			        $location.path('/employees');
				});
			};
		}
		
	}]
);