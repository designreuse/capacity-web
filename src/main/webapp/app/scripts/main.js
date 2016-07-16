(function() {
    angular
        .module('capacityApp')
		.controller('MainCtrl', MainCtrl);

	MainCtrl.$inject = ['$location', '$http'];

	function MainCtrl($location, $http) {
		/* jshint validthis: true */
		var vm = this;

		vm.currentModule = function() {
			var currentPath = $location.path();
			if (currentPath.startsWith('/')) {
				currentPath = currentPath.substring(1);
			}
			var firstSlash = currentPath.indexOf('/');
			if (firstSlash >= 0) {
				currentPath = currentPath.substring(0, firstSlash);
			}
			return currentPath;
		};

		$http.get('rest/version').then(function(response) {
			vm.version = response.data;
		});
	}
})();
