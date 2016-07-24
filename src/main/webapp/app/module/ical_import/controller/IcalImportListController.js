(function() {
	'use strict';

	angular.module('capacityApp')
		.controller('IcalImportListController', IcalImportListController);

	CompletedController.$inject = ['$uibModalInstance', 'progress'];

	function CompletedController($uibModalInstance, progress) {
		/* jshint validthis: true */
		var vm = this;

		vm.title = 'Import completed';
		vm.message = 'The import was completed successfully (Skipped: ' + progress.result.skipped + ', Created: ' + progress.result.created  + ', Updated: ' + progress.result.updated + ', Deleted: ' + progress.result.deleted + ')';
		vm.confirmButtons = [ { value: 'ok', label: 'OK' }];
		vm.cancelButtons = [ ];

		vm.ok = function(value) {
			$uibModalInstance.close(value);
		};

		vm.cancel = function(value) {
			$uibModalInstance.dismiss(value);
		};
	}

	ProgressController.$inject = ['$uibModalInstance', 'response', 'ws', '$uibModal', '$rootScope'];

	function ProgressController($uibModalInstance, response, ws, $uibModal, $rootScope) {
		/* jshint validthis: true */
		var vm = this;

		vm.title = 'Importing ICAL';
		vm.progress = {
			value: 0,
			max: 1,
			message: 'About to start'
		};
		vm.confirmButtons = [ { value: 'ok', label: 'OK' }];
		vm.cancelButtons = [ ];

		ws.onmessage = function(event) {
			var newProgress = JSON.parse(event.data);
			vm.progress.value = newProgress.value;
			vm.progress.max = newProgress.max ? newProgress.max : 1;
			vm.progress.message = newProgress.message;
			vm.progress.result = newProgress.result;
			if (vm.progress.result) {
				vm.ok('ok');
			}
			$rootScope.$apply();
		};

		ws.onerror = function(){
			ws.close();
		};

		vm.ok = function(value) {
			$uibModalInstance.close(value);

			if (vm.progress.result) {
				var modalInstanceCompleted = $uibModal.open({
					templateUrl: 'app/views/modalDialog.html',
					backdrop: 'static',
					controller: CompletedController,
					controllerAs: 'vm',
					resolve: {
						progress: vm.progress
					}
				});
				modalInstanceCompleted.result.then(function() {
					IcalImport.query(function(icalImports) {
						vm.icalImports = icalImports;
					});
				});
			}

		};
	}

	IcalImportListController.$inject = ['$location', '$rootScope', '$http', '$uibModal', 'IcalImport'];

	function IcalImportListController($location, $rootScope, $http, $uibModal, IcalImport) {
		/* jshint validthis: true */
		var vm = this;

		IcalImport.query(function(icalImports) {
			vm.icalImports = icalImports;
		});

		vm.add = function() {
			$location.path('/ical_imports/new');
		};

		vm.predicate = 'name';
		vm.reverse = false;
		vm.order = function(predicate) {
			vm.reverse = (vm.predicate === predicate) ? !vm.reverse : false;
			vm.predicate = predicate;
		};

		vm.searchFilter = function(string) {
			$rootScope.search = string;
		};

		vm.import = function(id) {
			$http.get('rest/ical_import/import/' + id).then(function (response) {
				var ws;
				if (window.location.protocol == 'https:') {
					ws = new WebSocket('wss://' + window.location.host + window.location.pathname + 'async/progress/' + response.data);
				} else {
					ws = new WebSocket('ws://' + window.location.host + window.location.pathname + 'async/progress/' + response.data);
				}
				var modalInstance = $uibModal.open({
					templateUrl: 'app/views/progressDialog.html',
					backdrop: 'static',
					controller: ProgressController,
					controllerAs: 'vm',
					resolve: {
						response: response,
						ws: ws
					}
				});
				modalInstance.result.then(function() {
					if (ws) {
						ws.close();
					}
				});


			});
		};
	}
})();
