(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EpisodeDetailController', EpisodeDetailController);

	EpisodeDetailController.$inject = ['$route', '$location', 'Episode', 'Employee'];

	function EpisodeDetailController($route, $location, Episode, Employee) {
		/* jshint validthis: true */
		var vm = this;

		vm.id = $route.current.params.id;

		vm.selection = [];

		vm.datepicker = {
			startOpened: false,
			endOpened: false
		};

		function prepareDto() {
			vm.episode.employeeEpisodes = [];
			angular.forEach(vm.selection, function(element, index) {
				vm.episode.employeeEpisodes.push(element);
			});
			if (vm.episode.start && typeof vm.episode.start.getMonth === 'function') {
				vm.episode.start = moment(vm.episode.start).format('YYYY-MM-DD');
			}
			if (vm.episode.end && typeof vm.episode.end.getMonth === 'function') {
				vm.episode.end = moment(vm.episode.end).format('YYYY-MM-DD');
			}
		}

		if (vm.id == 'new') {
			vm.episode = new Episode();
			vm.save = function() {
				prepareDto();
				vm.episode.$save(function() {
					$location.path('/episodes');
				});
			};
		} else {
			var isClone = $location.path().slice(0, '/episodes/clone'.length) == '/episodes/clone';
			vm.episode = {};
			Episode.get({id: vm.id}, function(episode) {
				vm.episode = episode;
				if (isClone) {
					vm.episode.name = 'Clone of ' + vm.episode.name;
					vm.episode.id = undefined;
					if (vm.episode.employeeEpisodes) {
						angular.forEach(vm.episode.employeeEpisodes, function(element, index) {
							element.id = undefined;
							vm.selection[element.employee.id] = element;
						});
					}
				} else {
					if (vm.episode.employeeEpisodes) {
						angular.forEach(vm.episode.employeeEpisodes, function(element, index) {
							vm.selection[element.employee.id] = element;
						});
					}
				}
				vm.episode.start = new Date(vm.episode.start);
				vm.episode.end = new Date(vm.episode.end);
			});
			if (isClone) {
				vm.save = function() {
					prepareDto();
					vm.episode.$save(function() {
						$location.path('/episodes');
					});
				};
			} else {
				vm.save = function() {
					prepareDto();
					vm.episode.$update(function() {
						$location.path('/episodes');
					});
				};
			}
		}

		vm.isSelected = function(employeeId) {
			return vm.selection[employeeId] !== undefined;
		};

		vm.toggleSelection = function(employee) {
			if (vm.isSelected(employee.id)) {
				vm.selection[employee.id] = undefined;
			} else {
				vm.selection[employee.id] = {
					employee: employee,
					velocity: employee.velocity
				};
			}
		};

		Employee.query(function(employees) {
			vm.employees = employees;
		});
	}
})();
