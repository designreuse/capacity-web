(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Employee', function($resource) {
		return $resource('rest/employee/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	});
})();
