(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('IcalImport', IcalImport);

	IcalImport.$inject = ['$resource'];

	function IcalImport($resource) {
		return $resource('rest/ical_import/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
