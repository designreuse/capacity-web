(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('EpisodeListController', EpisodeListController);

	EpisodeListController.$inject = ['$location', '$rootScope', 'Episode'];

	function EpisodeListController($location, $rootScope, Episode) {
		/* jshint validthis: true */
		var vm = this;

		Episode.query(function(episodes) {
			vm.episodes = episodes;
		});

		vm.add = function() {
			$location.path('/episodes/new');
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

		vm.clone = function(id) {
			$location.path('/episodes/clone/'+id);
		}
	}
})();
