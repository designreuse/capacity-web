/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />
(function() {
	'use strict';

	angular
		.module('capacityApp')
		.filter('roleName', roleName);

	function roleName() {
		return function(input) {
			switch (input) {
			case 'SHOW_USERS': return 'Show users';
			case 'ADMIN_USERS': return 'Administrate users';
			case 'SHOW_CAPACITY': return 'Show capacity';
			case 'ADMIN_CAPACITY': return 'Administrate capacity';
			case 'VIEW_WITHOUT_CONTRACT': return 'View employees with inactive contracts';
			}
			return input;
		};
	}
})();
