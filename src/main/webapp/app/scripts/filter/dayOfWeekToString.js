/// <reference path="../../../../../../typings/globals/angular/index.d.ts" />

(function() {
	'use strict';

	angular
		.module('capacityApp')
		.filter('dayOfWeekToString', dayOfWeekToString);

	function dayOfWeekToString() {
		return function(input) {
			switch (input) {
			case 1: return 'Monday';
			case 2: return 'Tuesday';
			case 3: return 'Wednesday';
			case 4: return 'Thursday';
			case 5: return 'Friday';
			case 6: return 'Saturday';
			case 7: return 'Sunday';
			}
			return 'Invalid day: ' + input;
		};
	}
})();
