(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EpisodeDetailController', EpisodeDetailController);

	EpisodeDetailController.$inject = ['$scope', '$route', '$location', 'Episode', 'Employee'];

	function EpisodeDetailController($scope, $route, $location, Episode, Employee) {
		$scope.id = $route.current.params.id;

		$scope.selection = [];

		$scope.datepicker = {
			startOpened: false,
			endOpened: false
		};

		function prepareDto() {
			$scope.episode.employeeEpisodes = [];
			angular.forEach($scope.selection, function(element, index) {
				$scope.episode.employeeEpisodes.push(element);
			});
			if ($scope.episode.start && typeof $scope.episode.start.getMonth === 'function') {
				$scope.episode.start = moment($scope.episode.start).format('YYYY-MM-DD');
			}
			if ($scope.episode.end && typeof $scope.episode.end.getMonth === 'function') {
				$scope.episode.end = moment($scope.episode.end).format('YYYY-MM-DD');
			}
		}

		if ($scope.id == 'new') {
			$scope.episode = new Episode();
			$scope.save = function() {
				prepareDto();
				$scope.episode.$save(function() {
					$location.path('/episodes');
				});
			};
		} else {
			var isClone = $location.path().slice(0, '/episodes/clone'.length) == '/episodes/clone';
			$scope.episode = {};
			Episode.get({id: $scope.id}, function(episode) {
				$scope.episode = episode;
				if (isClone) {
					$scope.episode.name = 'Clone of ' + $scope.episode.name;
					$scope.episode.id = undefined;
					if ($scope.episode.employeeEpisodes) {
						angular.forEach($scope.episode.employeeEpisodes, function(element, index) {
							element.id = undefined;
							$scope.selection[element.employee.id] = element;
						});
					}
				} else {
					if ($scope.episode.employeeEpisodes) {
						angular.forEach($scope.episode.employeeEpisodes, function(element, index) {
							$scope.selection[element.employee.id] = element;
						});
					}
				}
				$scope.episode.start = new Date($scope.episode.start);
				$scope.episode.end = new Date($scope.episode.end);
			});
			if (isClone) {
				$scope.save = function() {
					prepareDto();
					$scope.episode.$save(function() {
						$location.path('/episodes');
					});
				};
			} else {
				$scope.save = function() {
					prepareDto();
					$scope.episode.$update(function() {
						$location.path('/episodes');
					});
				};
			}
		}

		$scope.isSelected = function(employeeId) {
			return $scope.selection[employeeId] !== undefined;
		};

		$scope.toggleSelection = function(employee) {
			if ($scope.isSelected(employee.id)) {
				$scope.selection[employee.id] = undefined;
			} else {
				$scope.selection[employee.id] = {
					employee: employee,
					velocity: employee.velocity
				};
			}
		};

		Employee.query(function(employees) {
			$scope.employees = employees;
		});
	}
})();
