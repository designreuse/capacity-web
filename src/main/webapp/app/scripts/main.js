(function() {
    angular
        .module('capacityApp')
		.controller('MainCtrl', MainCtrl);

	MainCtrl.$inject = ['$scope', '$location', '$http'];

	function MainCtrl($scope, $location, $http) {
		$scope.currentModule = function() {
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
			$scope.version = response.data;
		});
	}
})();