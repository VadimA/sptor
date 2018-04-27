var App = angular.module('cycleApp', []);

angular.module('cycleApp').factory('CycleService', ['$http', '$q', function($http, $q){

    var EQUIPMENT_SERVICE_URI = '/equipments/subdivisions';
    var SUBDIVISION_SERVICE_URI = '/subdivisions/all';

    var factory = {
        getEquipmentsBySubdivision: getEquipmentsBySubdivision,
        getAllSubdivision: getAllSubdivision
    };

    return factory;

    function getAllSubdivision() {
        var deferred = $q.defer();
        $http.get(SUBDIVISION_SERVICE_URI)
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
}]);