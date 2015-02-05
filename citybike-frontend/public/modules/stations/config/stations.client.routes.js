'use strict';

//Setting up route
angular.module('stations').config(['$stateProvider',
	function($stateProvider) {
		// Stations state routing
		$stateProvider.
		state('find-stations', {
			url: '/stations/find',
			templateUrl: 'modules/stations/views/find-stations.client.view.html'
		}).
		state('stationsByContract', {
			url: '/stations/list/:contractId',
			templateUrl: 'modules/stations/views/list-stations.client.view.html'
		}).
		state('list-contracts', {
			url: '/stations/listContracts',
			templateUrl: 'modules/stations/views/list-contracts.client.view.html'
		}).
		state('view-station', {
			url: '/stations/:stationId',
			templateUrl: 'modules/stations/views/view-station.client.view.html'
		}); 
	}
]);