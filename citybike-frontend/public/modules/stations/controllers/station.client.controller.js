'use strict';

angular.module('stations').controller('StationController', ['$scope', '$stateParams',	 'Stations', 
	//$scope.search = {};

	function($scope, $stateParams, Stations) {
		$scope.listContracts = function() {
			Stations.listContracts(function(contracts) {
				$scope.contracts = contracts;
			});
		};

		$scope.listStations = function() {
			Stations.listStations({'contractId' : $stateParams.contractId}, function(stations) {
				$scope.stations = stations;
			});	
		};

		$scope.viewStation = function() {
			Stations.getStation({'stationId' : $stateParams.stationId}, function(station) {
				$scope.station = station;
				$scope.map = { center: { latitude: station.position.coordinates[1], longitude: station.position.coordinates[0] }, zoom: 15 };
			});	
		};

		$scope.geoSearch = function() {
			Stations.geoSearch($scope.search, function(stations) {
				$scope.stations = stations;
				$scope.map = { center: { latitude: $scope.search.latitude, longitude: $scope.search.longitude }, zoom: 12 };
			});	
		};

		$scope.textSearch = function() {
			console.log($scope.search);
			Stations.textSearch($scope.search, function(stations) {
				$scope.stations = stations;
			});	
		};
	}
]);