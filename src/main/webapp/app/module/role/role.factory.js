/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />
(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Role', Role);

	function Role($resource) {
		return $resource('rest/role/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
