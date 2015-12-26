'use strict';

angular.module('capacityApp')
	.controller('EmployeeDetailController', ['$scope', '$route', '$location', '$http', 'Employee', function ($scope, $route, $location, $http, Employee) {
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

		$scope.loadAbilities = function($query) {
			return $http.get('rest/abilities').then(function(response) {
				var countries = response.data;
				return countries.filter(function(country) {
					return country.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
				});
			});
		};
	}]
);