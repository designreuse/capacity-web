'use strict';

angular.module('capacityApp')
	.factory('Episode', function($resource) {
	return $resource('rest/episode/:id', { id: '@id' }, {
		update: {
			method: 'PUT'
		}
	});
});