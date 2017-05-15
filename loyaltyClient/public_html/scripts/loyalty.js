/* global angular */

var module = angular.module('LoyaltyApp', ['ngResource']);
module.controller('LoyaltyController', function ($scope, $resource) {

	if (!window.WebSocket) {
		alert("WebSockets are not supported by this browser");
	} else {
		initializeSocket();
	}
	function initializeSocket() {
		ws = new WebSocket("ws://localhost:8087/ajax");
		ws.onmessage = function (event) {
			var object = JSON.parse(event.data);
			$scope.customer = object.customers[0];
			$scope.$apply();			
		};
	}
	
	$scope.signIn = function () {
		var email = document.getElementById("email").value;
		ws.send(email);
	};
}
);
