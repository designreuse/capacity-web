'use strict';

angular.module('capacityApp')
	.controller('EpisodeDetailController', ['$scope', '$route', '$location', 'Episode', function ($scope, $route, $location, Episode) {
		$scope.id = $route.current.params.id;

		if ($scope.id == 'new') {
			$scope.episode = new Episode();
			$scope.save = function() {
				$scope.episode.$save(function() {
					$location.path('/episodes');
				});
			};
		} else {
			Episode.get({id: $scope.id}, function(episode) {
				$scope.episode = episode;
			});
			$scope.save = function() {
				$scope.episode.$update(function() {
					$location.path('/episodes');
				});
			};
		}
	}]);