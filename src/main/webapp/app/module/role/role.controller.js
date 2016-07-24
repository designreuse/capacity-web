(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('RoleDetailController', RoleDetailController);

	RoleDetailController.$inject = ['$route', '$location', '$http', 'Role'];

	function RoleDetailController($route, $location, $http, Role) {
		/* jshint validthis: true */
		var vm = this;

		vm.isSelected = isSelected;
		vm.toggleSelection = toggleSelection;

		activate();

		function activate() {
			if ($route.current.params.id == 'new') {
				vm.role = new Role();
				vm.role.permissions = [];
				vm.save = function() {
					vm.role.$save(function() {
						$location.path('/roles');
					});
				};
			} else {
				Role.get({id: $route.current.params.id}).$promise.then(function(role) {
					vm.role = role;
				});
				vm.save = function() {
					vm.role.$update(function() {
						$location.path('/roles');
					});
				};
			}

			$http.get('rest/permissions').then(function(response) {
				vm.permissions = response.data;
			});
		}

		function isSelected(permission) {
			return vm.role && vm.role.permissions.indexOf(permission) !== -1;
		}

		function toggleSelection(permission) {
			var index = vm.role.permissions.indexOf(permission);
			if (index !== -1) {
				vm.role.permissions.splice(index, 1);
			} else {
				vm.role.permissions.push(permission);
			}
		}
	}
})();
