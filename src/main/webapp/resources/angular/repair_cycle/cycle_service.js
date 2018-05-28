var App = angular.module('cycleApp', []);

angular.module('cycleApp').factory('CycleService', ['$http', '$q', function($http, $q){

    var EQUIPMENT_SERVICE_URI = '/equipments/all';
    var SUBDIVISION_SERVICE_URI = '/subdivisions/all';
    var CYCLE_URI = '/planning/repair_cycle/';

    var factory = {
        getEquipmentsBySubdivision: getEquipmentsBySubdivision,
        getAllEquipments: getAllEquipments,
        getRepairCycleByEquipment: getRepairCycleByEquipment
    };

    return factory;

    function getAllEquipments() {
        var deferred = $q.defer();
        $http.get(EQUIPMENT_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching all subdivisions');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getEquipmentsBySubdivision(subdivisionId) {
        var deferred = $q.defer();
        $http.get(EQUIPMENT_SERVICE_URI + "/" + subdivisionId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while equipment by subdivision -' + subdivisionId);
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function getRepairCycleByEquipment(equipmentId) {
        var deferred = $q.defer();
        $http.get(CYCLE_URI + "/" + equipmentId)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Equipments on maintenance');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

}]);