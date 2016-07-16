(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Holiday', Holiday);

	Holiday.$inject = ['$resource'];

	function Holiday($resource) {
		return $resource('rest/holiday/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
