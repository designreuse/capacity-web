'use strict';

angular.module('capacityApp')
	.factory('Absence', function($resource) {
		return $resource('rest/absence/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	});
