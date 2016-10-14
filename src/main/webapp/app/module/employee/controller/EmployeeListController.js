(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EmployeeListController', EmployeeListController);

	EmployeeListController.$inject = ['$location', '$rootScope', 'Employee', '$http'];

	function EmployeeListController($location, $rootScope, Employee, $http) {
		/* jshint validthis: true */
		var vm = this;

		vm.permissions = [];

		$http.get('rest/permissions/my').then(function(response) {
			vm.permissions = response.data;
			Employee.query(function(employees) {
				vm.employees = employees;
			});
		});

		vm.add = function() {
			$location.path('/employees/new');
		};

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = function(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		};

		vm.searchFilter = function(string) {
			$rootScope.search = string;
		};

		vm.getEmployeeStyle = function(employee) {
			// Current users only sees active employees, no calculation necessary
			if (!vm.showContracts()) {
				return '';
			}

			if (!employee.contract.start || new Date(employee.contract.start + ' 00:00:00') <= new Date()) {
				// Employee does not have a start date or the start date was before our current date
				if (!employee.contract.end || new Date(employee.contract.end + ' 23:59:59') >= new Date()) {
					// Employee does not have a end date or the end date was after our current date
					return '';
				} else {
					// Employee does have a end date and it's before the current date
					return 'text-decoration: line-through';
				}
			} else {
				// Employee does have a start date and it's after the current date
				return 'text-decoration: line-through';
			}
		}

		vm.showContracts = function() {
			return vm.permissions.indexOf('VIEW_WITHOUT_CONTRACT');
		}

		vm.clone = function(id) {
			$location.path('/employees/clone/'+id);
		};
	}
})();
