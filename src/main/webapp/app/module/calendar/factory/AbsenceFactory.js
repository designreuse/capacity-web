/// <reference path="../../../../../../../typings/globals/angular/index.d.ts" />
/// <reference path="../../../../../../../typings/globals/angular-resource/index.d.ts" />

(function() {
	'use strict';

	angular.module('capacityApp')
		.factory('Absence', Absence);

	Absence.$inject = ['$resource'];

	/**
	 * @param {ng.resource.IResourceService} $resource
	 */
	function Absence($resource) {
		return $resource('rest/absence/:id', { id: '@id' }, {
			update: {
				method: 'PUT'
			}
		});
	}
})();
