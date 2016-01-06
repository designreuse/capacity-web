'use strict';

angular.module('capacityApp')
	.factory('IcalImport', function($resource) {
	return $resource('rest/ical_import/:id', { id: '@id' }, {
		update: {
			method: 'PUT'
		}
	});
});