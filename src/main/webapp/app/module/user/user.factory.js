/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />
(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('User', User);

	function User($resource) {
		return $resource('rest/user/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
