(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Absence', Absence);

	Absence.$inject = ['$resource'];

	function Absence($resource) {
		return $resource('rest/absence/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
