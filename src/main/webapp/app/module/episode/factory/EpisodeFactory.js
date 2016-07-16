(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Episode', Episode);

	Episode.$inject = ['$resource'];

	function Episode($resource) {
		return $resource('rest/episode/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
