'use strict';

angular.module('stations').factory('Stations', ['$resource',
	function($resource) {
		return $resource('stations/:stationId', { stationId: '@_id', contractId: '@contractId', latitude: '@latitude', longitude: '@longitude', radius: '@radius'
		}, {
			listContracts: {
      			method : 'GET', 
      			url : 'http://localhost:8080/rest/contract/list',
      			isArray: true
			},
			listStations: {
      			method : 'GET', 
      			url : 'http://localhost:8080/rest/station/list/:contractId',
      			isArray: true
			},
			getStation: {
      			method : 'GET', 
      			url : 'http://localhost:8080/rest/station/:stationId'
			},
			geoSearch: {
      			method : 'GET', 
      			url : 'http://localhost:8080/rest/station/geoSearch?latitude=:latitude&longitude=:longitude&radius=:radius',
      			isArray: true
			},
			textSearch: {
      			method : 'POST', 
      			url : 'http://localhost:8080/rest/station/textSearch',
      			isArray: true
			}
		});
	}
]);