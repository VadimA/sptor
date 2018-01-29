var App = angular.module('planApp', ["ngTable"]);

angular.module('planApp').factory('PlanService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = '/planning/';

    var factory = {
        getEquipmentsOnMaintenance: getEquipmentsOnMaintenance,
    };

    return factory;

    function getEquipmentsOnMaintenance() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI + "equipments/")
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