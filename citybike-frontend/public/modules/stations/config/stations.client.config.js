'use strict';

// Configuring the stations module
angular.module('stations').run(['Menus',
	function(Menus) {
		// Set top bar menu items
		Menus.addMenuItem('topbar', 'Stations', 'stations', 'dropdown', '/stations', true);
		Menus.addSubMenuItem('topbar', 'stations', 'List Contracts', 'stations/listContracts');
		Menus.addSubMenuItem('topbar', 'stations', 'Find stations', 'stations/find');
	}
]);

