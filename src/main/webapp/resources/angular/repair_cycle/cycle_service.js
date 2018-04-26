var App = angular.module('cycleApp', ["ngTable"]);

angular.module('cycleApp').factory('CycleService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = '/equipments';

    var factory = {
        getEquipmentsOnMaintenance: getAllEquipments,
    };

    return factory;

    function getAllEquipments() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching all equipments');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);