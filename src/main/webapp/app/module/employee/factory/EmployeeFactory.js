(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Employee', Employee);

	function Employee($resource) {
		return $resource('rest/employee/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
