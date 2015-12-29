'use strict';

angular.module('capacityApp')
	.controller('EmployeeDetailController', ['$scope', '$route', '$location', '$http', '$uibModal', '$filter', 'Employee', function ($scope, $route, $location, $http, $uibModal, $filter, Employee) {
		$scope.id = $route.current.params.id;

		if ($scope.id == 'new') {
			$scope.employee = new Employee();
			$scope.employee.contract = {
				workingHours: [
					{dayOfWeek:1,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:2,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:3,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:4,start:'08:00:00.000',end:'16:00:00.000'},
					{dayOfWeek:5,start:'08:00:00.000',end:'16:00:00.000'}]
			};
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
				var abilities = response.data;
				return abilities.filter(function(abilitiy) {
					return abilitiy.name.toLowerCase().indexOf($query.toLowerCase()) != -1;
				});
			});
		};
		

		$scope.showAddWorkingHoursDialog = function() {
			var modalInstance = $uibModal.open({
				templateUrl: 'app/module/employee/view/addWorkingHours.html',
				backdrop : 'static',
				resolve: {
					employee: function() {
						return $scope.employee;
					}
				},
				controller : function($scope, $uibModalInstance, employee) {
					$scope.title = 'Add working hours';
					$scope.message = 'Please specify the day to add';
					$scope.confirmButtons = [ ];
					for (var i = 1; i < 8; i++) {
						var found = false;
						employee.contract.workingHours.forEach(function(element, index) {
							if (element.dayOfWeek == i) {
								found = true;
							}
						});
						if (!found) {
							$scope.confirmButtons.push( { value: i, label: $filter('dayOfWeekToString')(i) } );
						}
					}
					$scope.cancelButtons = [ {value: 'Cancel', label: 'Cancel'} ];

					$scope.ok = function(value) {
						$uibModalInstance.close(value);
					};

					$scope.cancel = function(value) {
						$uibModalInstance.dismiss(value);
					};
				}
			});
			modalInstance.result.then(function(value) {
				if ($scope.employee.contract.workingHours == undefined) {
					$scope.employee.contract.workingHours = [];
				}
				$scope.employee.contract.workingHours.push({dayOfWeek:value,start:'08:00:00.000',end:'16:00:00.000'})
			});
		};

		$scope.removeWorkingHours = function(dayOfWeek) {
			var i = $scope.employee.contract.workingHours.length;
			while (i--){
				if ($scope.employee.contract.workingHours[i].dayOfWeek == dayOfWeek){
					$scope.employee.contract.workingHours.splice(i, 1);
				}
			}
		}
	}]
);