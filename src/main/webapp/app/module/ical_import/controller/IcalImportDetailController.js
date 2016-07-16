(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('IcalImportDetailController', IcalImportDetailController);

	IcalImportDetailController.$inject = ['$route', '$location', 'IcalImport'];

	function IcalImportDetailController($route, $location, IcalImport) {
		/* jshint validthis: true */
		var vm = this;

		vm.id = $route.current.params.id;

		if (vm.id == 'new') {
			vm.icalImport = new IcalImport();
			vm.save = function() {
				vm.icalImport.$save(function() {
					$location.path('/ical_imports');
				});
			};
		} else {
			vm.icalImport = {};
			IcalImport.get({id: vm.id}, function(icalImport) {
				vm.icalImport = icalImport;
				if (vm.icalImport.employeeIcalImports) {
					angular.forEach(vm.icalImport.employeeIcalImports, function(element, index) {
						vm.selection[element.employee.id] = element;
					});
				}
			});
			vm.save = function() {
				vm.icalImport.$update(function() {
					$location.path('/ical_imports');
				});
			};
		}
	}
})();
