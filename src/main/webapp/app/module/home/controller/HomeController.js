(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('HomeController', HomeController);

	function HomeController() {
		/* jshint validthis: true */
		var vm = this;

		vm.title = 'Welcome';
	}
})();
