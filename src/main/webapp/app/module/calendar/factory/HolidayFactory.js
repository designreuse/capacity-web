'use strict';

angular.module('capacityApp')
	.factory('Holiday', function($resource) {
		return $resource('rest/holiday/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	});