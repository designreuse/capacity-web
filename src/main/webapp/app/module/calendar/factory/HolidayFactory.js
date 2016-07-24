/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />
/// <reference path="../../../../../../../typings/globals/angular-resource/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Holiday', Holiday);

	Holiday.$inject = ['$resource'];

	/**
	 * @param {ng.resource.IResourceService} $resource
	 */
	function Holiday($resource) {
		return $resource('rest/holiday/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
