'use strict';

angular.module('capacityApp')
	.controller('EpisodeListController', ['$scope', '$location', '$rootScope', 'Episode', function ($scope, $location, $rootScope, Episode) {
		Episode.query(function(episodes) {
			$scope.episodes = episodes;
		});

		$scope.add = function() {
			$location.path('/episodes/new');
		};

		$scope.predicate = 'name';
		$scope.reverse = false;
		$scope.order = function(predicate) {
			$scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
			$scope.predicate = predicate;
		};

		$scope.searchFilter = function(string) {
			$rootScope.search = string;
		};

		$scope.clone = function(id) {
			$location.path('/episodes/clone/'+id);
		}
	}]
);