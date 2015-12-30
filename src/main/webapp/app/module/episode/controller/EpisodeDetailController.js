'use strict';

angular.module('capacityApp')
	.controller('EpisodeDetailController', ['$scope', '$route', '$location', 'Episode', 'Employee', function ($scope, $route, $location, Episode, Employee) {
		$scope.id = $route.current.params.id;

		$scope.selection = [];

		if ($scope.id == 'new') {
			$scope.episode = new Episode();
			$scope.save = function() {
				$scope.episode.employeeEpisodes = [];
				angular.forEach($scope.selection, function(element, index) {
					$scope.episode.employeeEpisodes.push(element);
				});
				$scope.episode.$save(function() {
					$location.path('/episodes');
				});
			};
		} else {
			Episode.get({id: $scope.id}, function(episode) {
				$scope.episode = episode;
				if ($scope.episode.employeeEpisodes) {
					angular.forEach($scope.episode.employeeEpisodes, function(element, index) {
						$scope.selection[element.employee.id] = element;
					});
				}
			});
			$scope.save = function() {
				$scope.episode.employeeEpisodes = [];
				angular.forEach($scope.selection, function(element, index) {
					$scope.episode.employeeEpisodes.push(element);
				});
				$scope.episode.$update(function() {
					$location.path('/episodes');
				});
			};
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

	}]);