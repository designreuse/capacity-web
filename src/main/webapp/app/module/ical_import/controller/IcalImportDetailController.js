'use strict';

angular.module('capacityApp')
	.controller('IcalImportDetailController', ['$scope', '$route', '$location', 'IcalImport', function ($scope, $route, $location, IcalImport) {
		$scope.id = $route.current.params.id;

		if ($scope.id == 'new') {
			$scope.icalImport = new IcalImport();
			$scope.save = function() {
				$scope.icalImport.$save(function() {
					$location.path('/ical_imports');
				});
			};
		} else {
			$scope.icalImport = {};
			IcalImport.get({id: $scope.id}, function(icalImport) {
				$scope.icalImport = icalImport;
				if ($scope.icalImport.employeeIcalImports) {
					angular.forEach($scope.icalImport.employeeIcalImports, function(element, index) {
						$scope.selection[element.employee.id] = element;
					});
				}
			});
			$scope.save = function() {
				$scope.icalImport.$update(function() {
					$location.path('/ical_imports');
				});
			};
		}

	}]);